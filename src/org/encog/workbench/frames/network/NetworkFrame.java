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
package org.encog.workbench.frames.network;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
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
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.neural.networks.synapse.DirectSynapse;
import org.encog.neural.networks.synapse.OneToOneSynapse;
import org.encog.neural.networks.synapse.WeightedSynapse;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.EncogListFrame;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.frames.network.NetworkTool.Type;
import org.encog.workbench.models.NetworkListModel;

public class NetworkFrame extends EncogCommonFrame {

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
	private JScrollPane scroll;
	
	private List<NetworkTool> tools = new ArrayList<NetworkTool>();
	

	
	private ImageIcon layerBasic;
	private ImageIcon layerContext;
	private ImageIcon layerRBF;
	private ImageIcon synapseDirect;
	private ImageIcon synapseOneToOne;
	private ImageIcon synapseWeight;
	private ImageIcon synapseWeightless;
	
	private NetworkToolbar networkToolbar;
	private NetworkDiagram networkDiagram;

	public NetworkFrame(final BasicNetwork data) {
		setEncogObject(data);
		addWindowListener(this);
		this.networkToolbar = new NetworkToolbar(this);
		
		tools.add(new NetworkTool("Basic",Icons.getLayerBasic(),Type.layer,BasicLayer.class));
		tools.add(new NetworkTool("Context",Icons.getLayerContext(),Type.layer,ContextLayer.class));
		tools.add(new NetworkTool("Radial Function",Icons.getLayerRBF(),Type.layer,RadialBasisFunctionLayer.class));
		tools.add(new NetworkTool("Weighted",Icons.getSynapseWeight(),Type.synapse,WeightedSynapse.class));
		tools.add(new NetworkTool("Weightless",Icons.getSynapseWeightless(),Type.synapse,WeightedSynapse.class));
		tools.add(new NetworkTool("Direct",Icons.getSynapseDirect(),Type.synapse,DirectSynapse.class));
		tools.add(new NetworkTool("One-To-One",Icons.getSynapseWeightless(),Type.synapse,OneToOneSynapse.class));
	}

	public void actionPerformed(final ActionEvent action) {

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
		this.toolbar.add(this.deleteLayer = new JButton("Delete Layer"));
		this.toolbar.add(this.addLayer = new JButton("Add Layer"));
		this.toolbar.add(this.editLayer = new JButton("Edit Layer"));
		this.toolbar.add(this.properties = new JButton("Network Properties"));

		this.addLayer.addActionListener(this);
		this.editLayer.addActionListener(this);
		this.deleteLayer.addActionListener(this);
		this.properties.addActionListener(this);

		content.add(this.toolbar, BorderLayout.PAGE_START);
		this.scroll = new JScrollPane(networkDiagram = new NetworkDiagram(this));
		content.add(this.scroll, BorderLayout.CENTER);
		
		content.add(this.networkToolbar, BorderLayout.WEST);

		setTitle("Edit Neural Network");



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
	

	
	
}
