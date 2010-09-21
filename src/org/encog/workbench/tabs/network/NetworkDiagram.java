/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.tabs.network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.encog.EncogError;
import org.encog.mathutil.rbf.GaussianFunction;
import org.encog.mathutil.rbf.RadialBasisFunction;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.neural.networks.synapse.BasicSynapse;
import org.encog.neural.networks.synapse.DirectSynapse;
import org.encog.neural.networks.synapse.OneToOneSynapse;
import org.encog.neural.networks.synapse.Synapse;
import org.encog.neural.networks.synapse.SynapseType;
import org.encog.neural.networks.synapse.WeightedSynapse;
import org.encog.neural.networks.synapse.WeightlessSynapse;
import org.encog.neural.networks.synapse.neat.NEATSynapse;
import org.encog.neural.prune.PruneSelective;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.WorkbenchFonts;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.layers.EditBasicLayer;
import org.encog.workbench.dialogs.layers.EditContextLayer;
import org.encog.workbench.dialogs.layers.EditNEATSynapse;
import org.encog.workbench.dialogs.layers.EditRadialLayer;
import org.encog.workbench.dialogs.synapse.EditSynapseDialog;
import org.encog.workbench.process.validate.ValidateNetwork;
import org.encog.workbench.tabs.network.NetworkTool.Type;
import org.encog.workbench.util.CollectionFormatter;
import org.encog.workbench.util.MouseUtil;

public class NetworkDiagram extends JPanel implements MouseListener,
		MouseMotionListener, ActionListener {

	enum Side {
		Top, Bottom, Left, Right
	}

	public static final int VIRTUAL_WIDTH = 2000;
	public static final int VIRTUAL_HEIGHT = 2000;
	public static final int ARROWHEAD_WIDTH = 10;
	public static final int LABEL_HEIGHT = 16;
	private final NetworkTab parent;
	private Layer selected;
	private Layer fromLayer;
	private int dragOffsetX;
	private int dragOffsetY;
	private Image offscreen;
	private Graphics offscreenGraphics;
	private List<Layer> layers = new ArrayList<Layer>();
	private Set<Layer> orphanLayers = new HashSet<Layer>();
	private JPopupMenu popupNetworkLayer;
	private JMenuItem popupNetworkLayerDelete;
	private JMenuItem popupNetworkLayerEdit;
	private JPopupMenu popupNetworkSynapse;
	private JMenuItem popupNetworkSynapseDelete;
	private JMenuItem popupNetworkSynapseEdit;
	private Synapse selectedSynapse;

	public NetworkDiagram(NetworkTab parent) {
		this.parent = parent;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setPreferredSize(new Dimension(VIRTUAL_HEIGHT, VIRTUAL_WIDTH));
		getLayers();

		this.popupNetworkLayer = new JPopupMenu();
		this.popupNetworkLayerEdit = addItem(this.popupNetworkLayer,
				"Edit Layer", 'e');
		this.popupNetworkLayerDelete = addItem(this.popupNetworkLayer,
				"Delete Layer", 'd');

		this.popupNetworkSynapse = new JPopupMenu();
		this.popupNetworkSynapseDelete = addItem(this.popupNetworkSynapse,
				"Delete Synapse", 'd');
		this.popupNetworkSynapseEdit = addItem(this.popupNetworkSynapse,
				"Edit", 'm');
	}

	private void obtainOffScreen() {
		if (this.offscreen == null) {
			this.offscreen = this.createImage(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			this.offscreenGraphics = this.offscreen.getGraphics();
		}
	}

	public void paint(Graphics g) {
		obtainOffScreen();
		offscreenGraphics.setColor(Color.WHITE);
		offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());
		offscreenGraphics.setColor(Color.BLACK);

		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();
		for (Layer layer : this.layers) {
			// draw any synapse arrows
			for (Synapse synapse : layer.getNext()) {
				if (synapse.isSelfConnected())
					drawSelfConnectedSynapse(offscreenGraphics, synapse);
				else
					drawSynapse(offscreenGraphics, synapse);
			}

			// draw the actual layer
			DrawLayer.drawLayer(this, offscreenGraphics, layer);

			Collection<String> tags = network.getTags(layer);
			String label = CollectionFormatter.formatCollection(tags);

			if (label != null) {
				drawLabel(offscreenGraphics, layer, label);
			}

			if (this.selected == layer) {
				DrawLayer.drawSelection(offscreenGraphics, layer);
			}
			if (this.fromLayer == layer) {
				DrawLayer.drawFromSelection(offscreenGraphics, layer);
			}
		}

		g.drawImage(this.offscreen, 0, 0, this);

	}

	private void drawSelfConnectedSynapse(Graphics g, Synapse synapse) {

		if (synapse == this.selectedSynapse)
			g.setColor(Color.CYAN);
		else
			g.setColor(Color.BLACK);

		DrawArrow.drawSelfArrow(g, synapse);
		g.drawString(this.getSynapseText(synapse),
				synapse.getToLayer().getX() + 100, synapse.getFromLayer()
						.getY() - 20);

	}

	private String getSynapseText(Synapse synapse) {
		if (synapse instanceof WeightedSynapse) {
			return "Weighted";
		} else if (synapse instanceof WeightlessSynapse) {
			return "Weightless";
		} else if (synapse instanceof OneToOneSynapse) {
			return "1:1";
		} else if (synapse instanceof DirectSynapse) {
			return "Direct";
		} else if (synapse instanceof NEATSynapse) {
			return "NEAT";
		} else
			return "Unknown";
	}

	private void drawSynapse(Graphics g, Synapse synapse) {

		if (synapse == this.selectedSynapse)
			g.setColor(Color.CYAN);
		else
			g.setColor(Color.BLACK);

		String type = getSynapseText(synapse);
		DrawArrow.drawArrow(g, synapse, type);
	}

	private Layer findLayer(MouseEvent e) {
		// was a layer something clicked
		Layer clickedLayer = null;
		for (int i = layers.size() - 1; i >= 0; i--) {
			Layer layer = layers.get(i);
			if (contains(layer, e.getX(), e.getY())) {
				clickedLayer = layer;
			}
		}
		return clickedLayer;
	}

	private Synapse findSynapse(MouseEvent e) {
		for (Layer layer : this.layers) {
			for (Synapse synapse : layer.getNext()) {
				CalculateArrow arrow = new CalculateArrow(synapse, false);
				Polygon p = arrow.obtainPologygon();
				if (p.contains(e.getX(), e.getY()))
					return synapse;
			}
		}
		return null;
	}

	public void mouseClicked(MouseEvent e) {

		Layer clickedLayer = findLayer(e);

		if (MouseUtil.isRightClick(e)) {
			rightClick(e);
		} else if (e.getClickCount() == 2) {

			doubleClick(e, clickedLayer);
		}

	}

	private void rightClick(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		Layer clickedLayer = this.findLayer(e);

		if (clickedLayer != null) {
			this.popupNetworkLayer.show(e.getComponent(), x, y);
		} else {
			Synapse synapse = this.findSynapse(e);
			if (synapse != null) {
				this.popupNetworkSynapse.show(e.getComponent(), x, y);
			}
		}
	}

	private void doubleClick(MouseEvent e, Layer clickedLayer) {

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public void mousePressed(MouseEvent e) {

		Layer clickedLayer = findLayer(e);

		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();

		// was no layer clicked? Maybe a synapse was.
		if (clickedLayer == null) {
			Synapse clickedSynapse = findSynapse(e);
			if (clickedSynapse != null) {
				clearSelection();
				this.selectedSynapse = clickedSynapse;
				repaint();
				return;
			}
		}

		// is a synapse connection about to start or end
		if (this.parent.getNetworkToolbar().getSelected() != null) {
			// is it a synapse
			if (this.parent.getNetworkToolbar().getSelected().getType() == Type.synapse) {
				// about to start
				if (this.fromLayer == null) {
					clearSelection();
					this.selected = this.fromLayer = clickedLayer;
					repaint();
					return;
				} else {
					// about to create synapse
					createSynapse(clickedLayer);
					return;
				}
			}
		}

		// was something deselected or selected

		if (clickedLayer != null) {
			if (selected == clickedLayer && !MouseUtil.isRightClick(e)) {
				// deselected
				this.clearSelection();
				return;
			} else {
				// selected
				clearSelection();
				selected = clickedLayer;
				dragOffsetX = e.getX() - clickedLayer.getX();
				dragOffsetY = e.getY() - clickedLayer.getY();
				repaint();
				return;
			}
		}

		// nothing was selected, is there a toolbar item that needs to be added
		if (this.parent.getNetworkToolbar().getSelected() != null
				&& this.parent.getNetworkToolbar().getSelected().getType() == Type.synapse) {
			EncogWorkBench
					.displayError("Error",
							"Can't drop a synapse there, chose a 'from layer'\nthen a 'to layer'.");
		} else if (this.parent.getNetworkToolbar().getSelected() != null) {
			try {
				clearSelection();
				Class<? extends Layer> c = this.parent.getNetworkToolbar()
						.getSelected().getClassType();
				Layer layer = (Layer) c.newInstance();

				if (layer instanceof RadialBasisFunctionLayer) {
					((RadialBasisFunctionLayer) layer)
							.randomizeGaussianCentersAndWidths(-1, 1);
				}

				this.parent.getNetworkToolbar().setSelected(null);

				if (network.getLayer(BasicNetwork.TAG_INPUT) == null) {
					network.addLayer(layer);
					parent.performValidate(false, true);
				} else
					this.orphanLayers.add(layer);
				layer.setX(e.getX());
				layer.setY(e.getY());
				this.getLayers();
				this.selected = layer;
				this.parent.getNetworkToolbar().setSelected(null);
				repaint();
			} catch (InstantiationException e1) {
				throw new WorkBenchError(e1);
			} catch (IllegalAccessException e1) {
				throw new WorkBenchError(e1);
			}

		}

		// nothing was selected, deselect if something was previously
		if (this.selected != null || this.selectedSynapse != null) {
			this.clearSelection();
		}

	}

	private void createSynapse(Layer clickedLayer) {
		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();

		// validate any obvious errors
		if (this.fromLayer.isConnectedTo(clickedLayer)) {
			EncogWorkBench
					.displayError(
							"Can't Create Synapse",
							"There is already a synapse between these two layers.\nYou must delete it first.");
			this.parent.clearSelection();
			return;
		}

		if (clickedLayer != null) {
			// try to create it
			try {
				NetworkTool tool = this.parent.getNetworkToolbar()
						.getSelected();
				if (tool.getClassType() == WeightedSynapse.class) {
					this.fromLayer.addNext(clickedLayer, SynapseType.Weighted);
				} else if (tool.getClassType() == WeightlessSynapse.class) {
					this.fromLayer
							.addNext(clickedLayer, SynapseType.Weightless);
				} else if (tool.getClassType() == DirectSynapse.class) {
					this.fromLayer.addNext(clickedLayer, SynapseType.Direct);
				} else if (tool.getClassType() == OneToOneSynapse.class) {
					this.fromLayer.addNext(clickedLayer, SynapseType.OneToOne);
				} else if (tool.getClassType() == NEATSynapse.class) {
					this.fromLayer.addNext(clickedLayer, SynapseType.NEAT);
				}
			} catch (EncogError e) {
				EncogWorkBench.displayError("Synapse Error", e.getMessage());
			}
		}

		// recreate the network

		parent.performValidate(false, false);
		this.parent.clearSelection();
		repaint();
	}

	private boolean contains(Layer layer, int x, int y) {
		return (x > layer.getX() && (x < layer.getX() + DrawLayer.LAYER_WIDTH)
				&& y > layer.getY() && (y < layer.getY()
				+ DrawLayer.LAYER_HEIGHT));
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void getLayers() {
		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();

		parent.performValidate(false, false);

		// first remove any orphans that may have made it into the "real" list
		fixOrphans();

		// now build the layer list
		this.layers.clear();
		this.layers.addAll(network.getStructure().getLayers());
		this.layers.addAll(this.orphanLayers);
	}

	public void mouseDragged(MouseEvent e) {

		if (this.selected != null) {
			this.selected.setX(e.getX() - dragOffsetX);
			this.selected.setY(e.getY() - dragOffsetY);
			repaint();
		}

	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void clearSelection() {
		if (this.selected != null || this.selectedSynapse != null) {
			this.fromLayer = null;
			this.selected = null;
			this.selectedSynapse = null;
			repaint();
		}

	}

	public void actionPerformed(final ActionEvent action) {

		if (action.getSource() == this.popupNetworkLayerEdit) {
			if (this.selected instanceof ContextLayer)
				performContextLayerEdit();
			else if (this.selected instanceof RadialBasisFunctionLayer)
				performRadialLayerEdit();
			else if (this.selected instanceof BasicLayer)
				performBasicLayerEdit();

		} else if (action.getSource() == this.popupNetworkLayerDelete) {
			performLayerDelete();
		} else if (action.getSource() == this.popupNetworkSynapseDelete) {
			performSynapseDelete();
		} else if (action.getSource() == this.popupNetworkSynapseEdit) {
			performEditSynapse();
		}
	}


	
	private void performEditSynapse() {
		
		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();
		
		if( this.selectedSynapse instanceof NEATSynapse ) {
			NEATSynapse neatSynapse = (NEATSynapse)this.selectedSynapse;
			EditNEATSynapse dialog = new EditNEATSynapse(EncogWorkBench.getInstance().getMainWindow());
			
			dialog.setActivationFunction(neatSynapse.getActivationFunction());
			dialog.getSnapshot().setValue(neatSynapse.isSnapshot());
			
			if( dialog.process() )
			{
				neatSynapse.setActivationFunction(dialog.getActivationFunction());
				neatSynapse.setSnapshot(dialog.getSnapshot().getValue());
			}
		}
		else
		{
			
			EditSynapseDialog dialog = new EditSynapseDialog(
					EncogWorkBench.getInstance().getMainWindow(),
					network,this.selectedSynapse);
			if( dialog.process() )
			{
				((BasicSynapse)this.selectedSynapse).getMatrix().set(dialog.getMatrixTable().getMatrix());
				if( dialog.getMatrixTable().isShouldLimit() )
					network.setProperty(BasicNetwork.TAG_LIMIT, dialog.getMatrixTable().getLimitValue());
			}
		}
		
		network.getStructure().finalizeStructure();

	}

	public JMenuItem addItem(final JPopupMenu m, final String s, final int key) {

		final JMenuItem mi = new JMenuItem(s, key);
		mi.addActionListener(this);
		m.add(mi);
		return mi;
	}

	private void drawLabel(Graphics g, Layer layer, String str) {
		g.setFont(WorkbenchFonts.getTextFont());
		FontMetrics fm = g.getFontMetrics();

		int height = fm.getHeight();
		int width = fm.stringWidth(str);
		int x = layer.getX();
		int y = layer.getY() - NetworkDiagram.LABEL_HEIGHT;

		int center = (DrawLayer.LAYER_WIDTH / 2) - (width / 2);
		g.drawString(str, x + center, y + height - 3);
		g.drawRect(x, y, DrawLayer.LAYER_WIDTH, NetworkDiagram.LABEL_HEIGHT);
	}

	public void performSynapseDelete() {
		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();

		if (this.selectedSynapse != null
				&& EncogWorkBench.askQuestion("Are you sure?",
						"Do you want to delete this synapse?")) {
			// add all layers to orphan layers, some will be removed later
			this.orphanLayers.addAll(network.getStructure().getLayers());

			// perform the delete
			this.selectedSynapse.getFromLayer().getNext().remove(
					this.selectedSynapse);
			parent.performValidate(false, false);

			// handle any orphans
			this.fixOrphans();

			// rebuild the network
			getLayers();

			// does the output layer need to be adjusted
			this.repaint();
		}
	}

	public void performLayerDelete() {
		if (EncogWorkBench.askQuestion("Are you sure?",
				"Do you want to delete this layer?")) {
			BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();

			// add all layers to orphan layers, some will be removed later
			this.orphanLayers.addAll(network.getStructure().getLayers());

			// are we removing the input layer?
			if (this.selected == network.getLayer(BasicNetwork.TAG_INPUT)) {
				if (this.selected != null && this.selected.getNext().size() > 0) {
					Synapse nextSynapse = this.selected.getNext().get(0);
					Layer nextLayer = nextSynapse.getToLayer();
					network.tagLayer(BasicNetwork.TAG_INPUT, nextLayer);
				} else {
					network.getLayerTags().remove(BasicNetwork.TAG_INPUT);
					network.getLayerTags().remove(BasicNetwork.TAG_OUTPUT);
				}
			}

			// remove any synapses to this layer
			for (Synapse synapse : network.getStructure().getSynapses()) {
				if (synapse.getToLayer() == this.selected) {
					synapse.getFromLayer().getNext().remove(synapse);
				}
			}

			// rebuild the network & attempt to determine the output layer
			parent.performValidate(false, true);

			// fix the orphan list
			fixOrphans();
			this.orphanLayers.remove(this.selected);
			this.getLayers();

			// redraw
			this.clearSelection();
			repaint();
		}
	}

	public void fixOrphans() {

		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();
		network.getStructure().finalizeStructure();
		for (Layer layer : network.getStructure().getLayers()) {
			this.orphanLayers.remove(layer);
		}

	}

	private void performBasicLayerEdit() {
		EditBasicLayer dialog = new EditBasicLayer(EncogWorkBench.getInstance().getMainWindow(), this.selected);
		dialog.setActivationFunction(this.selected.getActivationFunction());
		dialog.getNeuronCount().setValue(this.selected.getNeuronCount());
		dialog.getUseBias().setValue(this.selected.hasBias());
		dialog.getBiasActivation().setValue(this.selected.getBiasActivation());
		dialog.setTableEditable();

		for (int i = 0; i < this.selected.getNeuronCount(); i++) {
			dialog.getThresholdTable().setValue(i, 0, "#" + (i + 1));
			if (this.selected.hasBias())
				dialog.getThresholdTable().setValue(i, 1,
						"" + this.selected.getBiasWeight(i));
			else
				dialog.getThresholdTable().setValue(i, 1, "N/A");
		}

		populateTags(dialog.getTags());

		if (dialog.process()) {
			// were thresholds added or removed
			if (!dialog.getUseBias().getValue()) {
				// removed
				this.selected.setBiasWeights(null);
			} else {
				// add thresholds if needed
				if (dialog.getUseBias().getValue()
						&& !this.selected.hasBias()) {
					this.selected.setBiasWeights(new double[this.selected
							.getNeuronCount()]);
				}
			}

			// did the neuron count change?
			if (dialog.getNeuronCount().getValue() != this.selected
					.getNeuronCount()) {
				PruneSelective prune = new PruneSelective(
						(BasicNetwork) this.parent.getEncogObject());
				prune.changeNeuronCount(this.selected, dialog.getNeuronCount()
						.getValue());
			} else {
				// if the neuron count did not change, copy any new threshold
				// values
				boolean updateThreshold = dialog.getUseBias().getValue();

				if (updateThreshold) {
					for (int i = 0; i < this.selected.getNeuronCount(); i++) {
						double value = 0;
						try {
							value = Double.parseDouble(dialog
									.getThresholdTable().getValue(i, 1));
						} catch (NumberFormatException e) {
							// just let the value go to zero
						}
						this.selected.setBiasWeight(i, value);
					}
				}
			}

			this.selected.setActivationFunction(dialog.getActivationFunction());
			this.selected.setBiasActivation(dialog.getBiasActivation().getValue());
			
			collectTags(this.selected, dialog.getTags());

			repaint();

		}

	}

	private void populateTags(BuildingListField tags) {
		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();

		for (String tag : network.getLayerTags().keySet()) {
			Layer value = network.getLayer(tag);
			if (value == this.selected) {
				tags.getModel().addElement(tag);
			}
		}
	}

	private void collectTags(Layer layer, BuildingListField tags) {
		BasicNetwork network = (BasicNetwork) this.parent.getEncogObject();

		// clear the tags for this layer
		Object[] allTags = network.getLayerTags().keySet().toArray();

		for (int i = 0; i < allTags.length; i++) {
			String tag = allTags[i].toString();
			Layer l = network.getLayer(tag);
			if (l == layer)
				network.getLayerTags().remove(tag);
		}

		// add the tags
		Object[] objs = tags.getModel().toArray();
		for (Object obj : objs) {
			String tag = obj.toString();
			network.tagLayer(tag, layer);
		}

	}

	private void performContextLayerEdit() {
		ContextLayer contextLayer = (ContextLayer) this.selected;
		EditContextLayer dialog = new EditContextLayer(EncogWorkBench.getInstance().getMainWindow(),
				contextLayer);
		dialog.setActivationFunction(this.selected.getActivationFunction());
		dialog.getNeuronCount().setValue(this.selected.getNeuronCount());
		dialog.getBiasActivation().setValue(this.selected.getBiasActivation());

		for (int i = 0; i < this.selected.getNeuronCount(); i++) {
			dialog.getThresholdTable().setValue(i, 0, "#" + (i + 1));
			if (contextLayer.hasBias())
				dialog.getThresholdTable().setValue(i, 1,
						"" + contextLayer.getBiasWeight(i));
			else
				dialog.getThresholdTable().setValue(i, 1, "N/A");
			dialog.getThresholdTable().setValue(i, 2,
					"" + contextLayer.getContext().getData(i));
		}

		if (this.selected.hasBias()) {
			dialog.getUseBias().setValue(true);
		} else {
			dialog.getUseBias().setValue(false);
		}

		dialog.setTableEditable();
		populateTags(dialog.getTags());

		if (dialog.process()) {
			// were thresholds added or removed
			if (!dialog.getUseBias().getValue()) {
				// removed
				this.selected.setBiasWeights(null);
			} else {
				// add thresholds if needed
				if (dialog.getUseBias().getValue()
						&& !this.selected.hasBias()) {
					this.selected.setBiasWeights(new double[this.selected
							.getNeuronCount()]);
				}
			}

			// did the neuron count change?
			if (dialog.getNeuronCount().getValue() != this.selected
					.getNeuronCount()) {
				PruneSelective prune = new PruneSelective(
						(BasicNetwork) this.parent.getEncogObject());
				prune.changeNeuronCount(this.selected, dialog.getNeuronCount()
						.getValue());
			} else {
				// if the neuron count did not change, copy threshold and
				// context values
				boolean updateThreshold = dialog.getUseBias().getValue();
				for (int i = 0; i < this.selected.getNeuronCount(); i++) {
					double thresholdValue = 0;
					double contextValue = 0;
					try {
						if (updateThreshold)
							thresholdValue = Double.parseDouble(dialog
									.getThresholdTable().getValue(i, 1));
						contextValue = Double.parseDouble(dialog
								.getThresholdTable().getValue(i, 2));
					} catch (NumberFormatException e) {
						// just let the value go to zero
					}
					if (updateThreshold)
						this.selected.setBiasWeight(i, thresholdValue);
					contextLayer.getContext().setData(i, contextValue);
				}
			}

			this.selected.setActivationFunction(dialog.getActivationFunction());
			this.selected.setBiasActivation(dialog.getBiasActivation().getValue());
			collectTags(this.selected, dialog.getTags());
			repaint();

		}

	}

	private void performRadialLayerEdit() {
		RadialBasisFunctionLayer radialLayer = (RadialBasisFunctionLayer) this.selected;
		EditRadialLayer dialog = new EditRadialLayer(EncogWorkBench.getInstance().getMainWindow(), radialLayer);
		dialog.getNeuronCount().setValue(this.selected.getNeuronCount());
		dialog.getDimensions().setValue(radialLayer.getDimensions());
		
		for(int i=0;i<radialLayer.getNeuronCount();i++)
		{
			dialog.getRadius().getModel().setValueAt(""+(i+1), i, 0);
			dialog.getRadius().getModel().setValueAt(""+radialLayer.getRadius()[i], i, 1);
		}
		
		int index = 0;
		for(int i=0;i<radialLayer.getNeuronCount();i++)
		{
			for(int j=0;j<radialLayer.getDimensions();j++)
			{
				dialog.getCenter().getModel().setValueAt(""+(i+1), index, 0);
				dialog.getCenter().getModel().setValueAt(""+(j+1), index, 1);
				dialog.getCenter().getModel().setValueAt(""+radialLayer.getCenter()[i][j], index, 2);
				index++;
			}
		}

		populateTags(dialog.getTags());

		if (dialog.process()) {

			if (dialog.getNeuronCount().getValue() != this.selected
					.getNeuronCount()) {
				// did the neuron count change?
				PruneSelective prune = new PruneSelective(
						(BasicNetwork) this.parent.getEncogObject());
				prune.changeNeuronCount(this.selected, dialog.getNeuronCount()
						.getValue());
			} else {

				try {
				// update the RBF's
				for(int i=0;i<radialLayer.getNeuronCount();i++)
				{
					radialLayer.getRadius()[i] = Double.parseDouble(dialog.getRadius().getModel().getValueAt(i, 1).toString());
				}
				
				index = 0;
				for(int i=0;i<radialLayer.getNeuronCount();i++)
				{
					for(int j=0;j<radialLayer.getDimensions();j++)
					{
						radialLayer.getCenter()[i][j] = Double.parseDouble(dialog.getCenter().getModel().getValueAt(index, 2).toString());
						index++;
					}
				}
				}
				catch(NumberFormatException ex)
				{
					EncogWorkBench.displayError("Error", "Invalid number");
				}

			}
			collectTags(this.selected, dialog.getTags());
			repaint();
		}

	}

	public NetworkTab getNetworkTab() {
		return this.parent;
	}

	public Set<Layer> getOrphanLayers() {
		return orphanLayers;
	}

	public void close() {
		this.offscreen = null;
		this.offscreenGraphics.dispose();
		this.offscreenGraphics = null;
	}

}
