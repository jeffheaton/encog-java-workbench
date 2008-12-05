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
package org.encog.workbench.frames.visualize;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;

import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.frames.manager.EncogCommonFrame;

public class NetworkVisualizeFrame extends EncogCommonFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BasicNetwork data;
	private final JScrollPane scroll;
	private final NetworkVisualizePanel visualize;

	public NetworkVisualizeFrame(final BasicNetwork data) {
		this.data = data;
		addWindowListener(this);
		setSize(320, 200);
		setTitle("Visualize " + this.data.getName());
		this.visualize = new NetworkVisualizePanel(this.data);
		this.scroll = new JScrollPane(this.visualize);
		// this.scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(this.scroll, BorderLayout.CENTER);

	}

	public void actionPerformed(final ActionEvent e) {

	}

	public void mouseClicked(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(final WindowEvent e) {

	}

}
