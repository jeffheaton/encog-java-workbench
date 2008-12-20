/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.HopfieldLayer;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.util.NormalizeInput.NormalizationType;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.dialogs.EditFeedforwardLayer;
import org.encog.workbench.dialogs.EditHopfieldLayer;
import org.encog.workbench.dialogs.EditSOMLayer;
import org.encog.workbench.dialogs.EditSimpleLayer;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.frames.render.NetworkLayerRenderer;
import org.encog.workbench.models.NetworkListModel;

public class NetworkFrame extends EncogListFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar toolbar;
	private JButton addLayer;
	private JButton deleteLayer;
	private JButton editLayer;
	private JButton properties;
	private NetworkListModel model;

	private JPopupMenu popupNetworkLayer;
	private JMenuItem popupNetworkLayerDelete;
	private JMenuItem popupNetworkLayerEdit;
	private JMenuItem popupEditMatrix;

	public NetworkFrame(final BasicNetwork data) {
		setEncogObject(data);
		addWindowListener(this);
	}

	public void actionPerformed(final ActionEvent action) {
		if (action.getSource() == this.editLayer
				|| action.getSource() == this.popupNetworkLayerEdit) {
			performEditLayer();
		} else if (action.getSource() == this.deleteLayer
				|| action.getSource() == this.popupNetworkLayerDelete) {
			performDeleteLayer();
		} else if (action.getSource() == this.properties) {
			performProperties();
		} else if (action.getSource() == this.addLayer) {
			performAddLayer();
		} else if (action.getSource() == this.popupEditMatrix) {
			performEditMatrix();
		}
	}

	/**
	 * @return the data
	 */
	public BasicNetwork getData() {
		return (BasicNetwork) getEncogObject();
	}

	@Override
	protected void openItem(final Object item) {

		if (getSubwindows().getFrames().size() > 0) {
			EncogWorkBench.displayError("Can't Edit Layer",
					"Can't edit layers while matrix windows are open.");
			return;
		}

		if (item instanceof FeedforwardLayer) {
			final FeedforwardLayer layer = (FeedforwardLayer) item;
			final EditFeedforwardLayer dialog = new EditFeedforwardLayer(this);
			dialog.setResultActivation(layer.getActivationFunction());
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if (dialog.getCommand() == EditFeedforwardLayer.Command.OK) {
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				layer.setActivationFunction(dialog.getResultActivation());
				// was there a neuron count change?
				if (layer.getNeuronCount() != dialog.getResultNeuronCount()) {
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		} else if (item instanceof HopfieldLayer) {
			final HopfieldLayer layer = (HopfieldLayer) item;
			final EditHopfieldLayer dialog = new EditHopfieldLayer(this);
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if (dialog.getCommand() == EditHopfieldLayer.Command.OK) {
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				// was there a neuron count change?
				if (layer.getNeuronCount() != dialog.getResultNeuronCount()) {
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		} else if (item instanceof SOMLayer) {
			final SOMLayer layer = (SOMLayer) item;
			final EditSOMLayer dialog = new EditSOMLayer(this);
			dialog.setResultNormalization(layer.getNormalizationType());
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if (dialog.getCommand() == EditSOMLayer.Command.OK) {
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				layer.setNormalizationType(dialog.getResultNormalization());
				// was there a neuron count change?
				if (layer.getNeuronCount() != dialog.getResultNeuronCount()) {
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		} else if (item instanceof BasicLayer) {
			final BasicLayer layer = (BasicLayer) item;
			final EditSimpleLayer dialog = new EditSimpleLayer(this);
			dialog.setResultDescription(layer.getDescription());
			dialog.setResultName(layer.getName());
			dialog.setResultNeuronCount(layer.getNeuronCount());
			dialog.setVisible(true);
			if (dialog.getCommand() == EditSimpleLayer.Command.OK) {
				layer.setName(dialog.getResultName());
				layer.setDescription(dialog.getResultDescription());
				// was there a neuron count change?
				if (layer.getNeuronCount() != dialog.getResultNeuronCount()) {
					layer.setNeuronCount(dialog.getResultNeuronCount());
				}
			}
		}

	}

	private void performAddLayer() {

		SelectItem itemFeedfoward, itemSimple, itemSOM, itemHopfield;
		final List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(itemFeedfoward = new SelectItem("Feedforward Layer"));
		list.add(itemSimple = new SelectItem("Simple Layer"));
		list.add(itemSOM = new SelectItem("SOM Layer"));
		list.add(itemHopfield = new SelectItem("Hopfield Layer"));
		final SelectDialog dialog = new SelectDialog(this, list);
		if( !dialog.process() )
			return;
		
		final SelectItem result = dialog.getSelected();

		Layer layer;
		Layer baseLayer = null;
		final int index = this.contents.getSelectedIndex();
		if (index != -1 && index < getData().getLayers().size()) {
			baseLayer = getData().getLayers().get(index);
		}

		if (result == itemFeedfoward) {
			layer = new FeedforwardLayer(2);
			this.model.addLayer(baseLayer, layer);
		} else if (result == itemSimple) {
			layer = new BasicLayer(2);
			this.model.addLayer(baseLayer, layer);
		} else if (result == itemSOM) {
			layer = new SOMLayer(2, NormalizationType.Z_AXIS);
			this.model.addLayer(baseLayer, layer);
		} else if (result == itemHopfield) {
			layer = new HopfieldLayer(2);
			this.model.addLayer(baseLayer, layer);
		}

	}

	private void performDeleteLayer() {
		final int index = this.contents.getSelectedIndex();

		if (index != -1) {
			if (getSubwindows().getFrames().size() > 0) {
				EncogWorkBench
						.displayError("Can't Delete Layer",
								"Can't delete this layer while matrix windows are open.");
				return;
			}

			this.model.deleteLayer(index);
		} else {
			JOptionPane.showMessageDialog(this,
					"Please select what you wish to delete.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void performEditLayer() {
		final Object item = this.contents.getSelectedValue();
		openItem(item);
	}

	private void performEditMatrix() {
		final Layer item = (Layer) this.contents.getSelectedValue();
		if (item.getMatrix() == null) {
			EncogWorkBench.displayError("Error",
					"This layer does not have a matrix.");
			return;
		}

		if (getSubwindows()
				.checkBeforeOpen(item.getMatrix(), MatrixFrame.class)) {
			final MatrixFrame frame = new MatrixFrame((BasicNetwork)getData(), item);
			frame.setVisible(true);
			getSubwindows().add(frame);
		}

	}

	public void performProperties() {
		final EditEncogObjectProperties dialog = new EditEncogObjectProperties(
				this, getData());
		dialog.process();
	}

	@Override
	public void rightMouseClicked(final MouseEvent e, final Object item) {
		if (item != null) {
			this.popupNetworkLayer.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public void windowOpened(final WindowEvent arg0) {
		setSize(640, 480);
		final Container content = getContentPane();
		content.setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.deleteLayer = new JButton("Delete Layer"));
		this.toolbar.add(this.addLayer = new JButton("Add Layer"));
		this.toolbar.add(this.editLayer = new JButton("Edit Layer"));
		this.toolbar.add(this.properties = new JButton("Network Properties"));

		this.addLayer.addActionListener(this);
		this.editLayer.addActionListener(this);
		this.deleteLayer.addActionListener(this);
		this.properties.addActionListener(this);

		content.add(this.toolbar, BorderLayout.PAGE_START);
		final JPanel content2 = new JPanel();
		content2.setLayout(new BorderLayout());
		content.add(content2, BorderLayout.CENTER);

		final JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(new JLabel("==Input=="));
		final JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(new JLabel("==Output=="));

		content2.add(topPanel, BorderLayout.NORTH);
		content2.add(bottomPanel, BorderLayout.SOUTH);

		this.model = new NetworkListModel(getData());
		this.contents = new JList(this.model);
		this.contents.addMouseListener(this);
		this.contents.setCellRenderer(new NetworkLayerRenderer());
		content2.add(new JScrollPane(this.contents), BorderLayout.CENTER);
		this.contents.setFixedCellHeight(72);

		setTitle("Edit Neural Network");

		this.popupNetworkLayer = new JPopupMenu();
		this.popupNetworkLayerEdit = this.addItem(this.popupNetworkLayer,
				"Edit Layer", 'e');
		this.popupEditMatrix = this.addItem(this.popupNetworkLayer,
				"Edit Matrix", 'm');
		this.popupNetworkLayerDelete = this.addItem(this.popupNetworkLayer,
				"Delete", 'd');

	}
}
