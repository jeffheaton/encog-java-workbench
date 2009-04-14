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
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.EncogListFrame;
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
	
	private NetworkToolbar networkToolbar;

	public NetworkFrame(final BasicNetwork data) {
		setEncogObject(data);
		addWindowListener(this);
		this.networkToolbar = new NetworkToolbar();
	}

	public void actionPerformed(final ActionEvent action) {
		if (action.getSource() == this.editLayer
				|| action.getSource() == this.popupNetworkLayerEdit) {
			performEditLayer();
		} else if (action.getSource() == this.deleteLayer
				|| action.getSource() == this.popupNetworkLayerDelete) {
			//performDeleteLayer();
		} else if (action.getSource() == this.addLayer) {
			performAddLayer();
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
	}

	private void performAddLayer() {

	}



	private void performEditLayer() {
		final Object item = this.contents.getSelectedValue();
		openItem(item);
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
		
		content.add(this.networkToolbar, BorderLayout.WEST);

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
