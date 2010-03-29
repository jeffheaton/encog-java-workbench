/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.frames.network;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.neural.networks.logic.ART1Logic;
import org.encog.neural.networks.logic.BAMLogic;
import org.encog.neural.networks.logic.BoltzmannLogic;
import org.encog.neural.networks.logic.FeedforwardLogic;
import org.encog.neural.networks.logic.HopfieldLogic;
import org.encog.neural.networks.logic.NeuralLogic;
import org.encog.neural.networks.logic.SimpleRecurrentLogic;
import org.encog.neural.networks.synapse.DirectSynapse;
import org.encog.neural.networks.synapse.OneToOneSynapse;
import org.encog.neural.networks.synapse.PartialSynapse;
import org.encog.neural.networks.synapse.WeightedSynapse;
import org.encog.neural.networks.synapse.WeightlessSynapse;
import org.encog.neural.networks.synapse.neat.NEATSynapse;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.MapDataFrame;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.frames.network.NetworkTool.Type;
import org.encog.workbench.frames.query.NetworkQueryFrame;
import org.encog.workbench.models.NetworkListModel;
import org.encog.workbench.process.training.Training;
import org.encog.workbench.process.validate.ValidateNetwork;

public class NetworkFrame extends EncogCommonFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar toolbar;
	private JButton buttonRandomize;
	private JButton buttonQuery;
	private JButton buttonTrain;
	private JButton buttonValidate;
	private JButton buttonProperties;
	private JComboBox comboLogic;
	private NetworkListModel model;
	private JScrollPane scroll;

	private List<NetworkTool> tools = new ArrayList<NetworkTool>();

	private ImageIcon layerBasic;
	private ImageIcon layerContext;
	private ImageIcon layerRBF;
	private ImageIcon synapseDirect;
	private ImageIcon synapseOneToOne;
	private ImageIcon synapseWeight;
	private ImageIcon synapseWeightless;
	private long lastPopup;

	private NetworkToolbar networkToolbar;
	private NetworkDiagram networkDiagram;

	public static final String[] LOGIC = { "Feed Forward", "Simple Recurrent",
			"Adaptive Resonance Theory", "Bidirectional", "Boltzmann",
			"Hopfield" };

	public NetworkFrame(final BasicNetwork data) {
		setEncogObject(data);
		addWindowListener(this);
		this.networkToolbar = new NetworkToolbar(this);

		tools.add(new NetworkTool("Basic", Icons.getLayerBasic(), Type.layer,
				BasicLayer.class));
		tools.add(new NetworkTool("Context", Icons.getLayerContext(),
				Type.layer, ContextLayer.class));
		tools.add(new NetworkTool("RBF", Icons.getLayerRBF(), Type.layer,
				RadialBasisFunctionLayer.class));
		tools.add(new NetworkTool("Weighted", Icons.getSynapseWeight(),
				Type.synapse, WeightedSynapse.class));
		tools.add(new NetworkTool("Weightless", Icons.getSynapseWeightless(),
				Type.synapse, WeightlessSynapse.class));
		tools.add(new NetworkTool("Direct", Icons.getSynapseDirect(),
				Type.synapse, DirectSynapse.class));
		tools.add(new NetworkTool("One-To-One", Icons.getSynapseOneToOne(),
				Type.synapse, OneToOneSynapse.class));
		tools.add(new NetworkTool("NEAT", Icons.getSynapseNEAT(),
				Type.synapse, NEATSynapse.class));
		tools.add(new NetworkTool("Partial", Icons.getSynapsePartial(),
				Type.synapse, PartialSynapse.class));

		this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
	}

	public void actionPerformed(final ActionEvent action) {
		if (action.getSource() == this.buttonQuery) {
			performQuery();
		} else if (action.getSource() == this.buttonRandomize) {
			performRandomize();
		} else if (action.getSource() == this.buttonTrain) {
			performTrain();
		} else if (action.getSource() == this.buttonValidate) {
			performValidate(true, true);
		} else if (action.getSource() == this.buttonProperties) {
			performProperties();
		} else if (action.getSource() == this.comboLogic) {
			collectLogic();
		}
	}

	public boolean performValidate(boolean reportSuccess, boolean reportError) {

		try {
			if (this.networkDiagram != null) {
				this.networkDiagram.fixOrphans();

				if (this.networkDiagram.getOrphanLayers().size() > 0) {
					if (reportError) {
						EncogWorkBench.displayError(
								"Warning, Network Validation Error",
								"There are unconnected layers. These will be lost"
										+ " if the network is saved.");
					}
					return false;
				}
			}

			BasicNetwork network = (BasicNetwork) this.getEncogObject();
			Exception e = ValidateNetwork.finalizeStructure(network);
			if (e != null && reportError) {
				EncogWorkBench.displayError(
						"Warning, Network Validation Error", e.getMessage());
				return false;
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Unexpected Internal Error", t
					.toString()
					+ "\n" + t.getMessage());
		}

		if (reportSuccess)
			EncogWorkBench.displayMessage("Success",
					"This neural network seems ok.");
		return true;

	}
	
	
	private void performTrain() {
		Training training = new Training();
		training.perform(EncogWorkBench.getInstance().getMainWindow(),(BasicNetwork)this.getEncogObject());
	}

	private void performRandomize() {
		if (performValidate(false, true)) {
			if (EncogWorkBench.askQuestion("Are you sure?",
					"Randomize network weights and lose all training?")) {
				((BasicNetwork) this.getEncogObject()).reset();
			}
		}

	}

	private void performQuery() {
		try
		{
		if (performValidate(false, true)) {

			NeuralLogic logic = ((BasicNetwork) this.getEncogObject())
					.getLogic();

			if (logic instanceof ART1Logic) {
				EncogWorkBench
						.displayMessage(
								"Training",
								"Sorry, but the workbench currently cannot be used to query this network type.\n"
										+ "You will need to create a program with the Encog Core to query it.");
			} else if (logic instanceof BAMLogic) {
				EncogWorkBench
						.displayMessage(
								"Training",
								"Sorry, but the workbench currently cannot be used to query this network type.\n"
										+ "You will need to create a program with the Encog Core to query it.");
			} else if (logic instanceof BoltzmannLogic) {
				EncogWorkBench
						.displayMessage(
								"Training",
								"Sorry, but the workbench currently cannot be used to query this network type.\n"
										+ "You will need to create a program with the Encog Core to query it.");
			} else if (logic instanceof FeedforwardLogic) {
				NetworkQueryFrame query = new NetworkQueryFrame(
						((BasicNetwork) this.getEncogObject()));
				query.setVisible(true);
			} else if (logic instanceof HopfieldLogic) {
				EncogWorkBench
						.displayMessage(
								"Training",
								"Sorry, but the workbench currently cannot be used to query this network type.\n"
										+ "You will need to create a program with the Encog Core to query it.");
			} else if (logic instanceof SimpleRecurrentLogic) {
				NetworkQueryFrame query = new NetworkQueryFrame(
						((BasicNetwork) this.getEncogObject()));
				query.setVisible(true);
			} else {
				EncogWorkBench
						.displayMessage(
								"Training",
								"Sorry, but the workbench currently cannot be used to query this network type.\n"
										+ "You will need to create a program with the Encog Core to query it.");
			}
		}
		}
		catch(Throwable t)
		{
			EncogWorkBench.displayError("Error", t, ((BasicNetwork) this.getEncogObject()), null);
		}

	}

	/**
	 * @return the data
	 */
	public BasicNetwork getData() {
		return (BasicNetwork) getEncogObject();
	}

	public void windowOpened(final WindowEvent arg0) {
		setSize(640, 480);
		final Container content = getContentPane();
		content.setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.comboLogic = new JComboBox(LOGIC));
		this.toolbar.add(this.buttonRandomize = new JButton("Randomize"));
		this.toolbar.add(this.buttonQuery = new JButton("Query"));
		this.toolbar.add(this.buttonTrain = new JButton("Train"));
		this.toolbar.add(this.buttonValidate = new JButton("Validate"));
		this.toolbar.add(this.buttonProperties = new JButton(
				"Network Properties"));

		this.buttonRandomize.addActionListener(this);
		this.buttonQuery.addActionListener(this);
		this.buttonTrain.addActionListener(this);
		this.buttonValidate.addActionListener(this);
		this.buttonProperties.addActionListener(this);
		this.comboLogic.addActionListener(this);

		content.add(this.toolbar, BorderLayout.PAGE_START);
		this.scroll = new JScrollPane(networkDiagram = new NetworkDiagram(this));
		content.add(this.scroll, BorderLayout.CENTER);

		content.add(this.networkToolbar, BorderLayout.WEST);

		setTitle("Edit Neural Network");
		setLogic();

	}

	public List<NetworkTool> getTools() {
		return tools;
	}

	public NetworkToolbar getNetworkToolbar() {
		return networkToolbar;
	}

	public NetworkDiagram getNetworkDiagram() {
		return networkDiagram;
	}

	public void clearSelection() {
		this.networkDiagram.clearSelection();
		this.networkToolbar.clearSelection();

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public NetworkTool findTool(Layer layer) {
		for (NetworkTool tool : this.tools) {
			if (tool.getClassType() == layer.getClass()) {
				return tool;
			}
		}
		return null;
	}

	public void windowClosing(final WindowEvent e) {
		if( !performValidate(false, false) )
		{
			if( (System.currentTimeMillis()-this.lastPopup)>1000 )
			{
			if( !EncogWorkBench.askQuestion("Unconnected Layers", "There are unconnected layers that will be lost if you close this network.\nDo you wish to continue?"))
			{
				// this is a total hack, but for some reason this event fires twice?
				this.lastPopup = System.currentTimeMillis();
				return;
			}
			}
			else
			{
				return;
			}
		}
		
		super.windowClosing(e);
		if (this.networkDiagram != null) {
			this.networkDiagram.close();
			this.networkDiagram = null;
		}
		dispose();
	}

	public void performProperties() {
		MapDataFrame frame = new MapDataFrame(((BasicNetwork) this
				.getEncogObject()).getProperties(), "Network Properties");
		frame.setVisible(true);
	}

	private void setLogic() {
		NeuralLogic logic = ((BasicNetwork) this.getEncogObject()).getLogic();
		if (logic instanceof ART1Logic)
			this.comboLogic.setSelectedIndex(2);
		else if (logic instanceof BAMLogic)
			this.comboLogic.setSelectedIndex(3);
		else if (logic instanceof BoltzmannLogic)
			this.comboLogic.setSelectedIndex(4);
		else if (logic instanceof FeedforwardLogic)
			this.comboLogic.setSelectedIndex(0);
		else if (logic instanceof HopfieldLogic)
			this.comboLogic.setSelectedIndex(5);
		else
			this.comboLogic.setSelectedIndex(1);
	}

	private void collectLogic() {
		NeuralLogic newLogic;

		switch (this.comboLogic.getSelectedIndex()) {
		case 0:
			newLogic = new FeedforwardLogic();
			break;
		case 2:
			newLogic = new ART1Logic();
			break;
		case 3:
			newLogic = new BAMLogic();
			break;
		case 4:
			newLogic = new BoltzmannLogic();
			break;
		case 5:
			newLogic = new HopfieldLogic();
			break;
		default:
			newLogic = new SimpleRecurrentLogic();
			break;
		}

		BasicNetwork network = (BasicNetwork) this.getEncogObject();
		if (!network.getLogic().getClass().getSimpleName().equals(
				newLogic.getClass().getSimpleName())) {
			try {
				newLogic.init(network);
			} catch (Exception e) {
				EncogWorkBench.displayError("Error",e, network, null);
				setLogic();
			}
			network.setLogic(newLogic);
		}
	}
}
