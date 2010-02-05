/*
 * Encog(tm) Workbench v2.3
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

package org.encog.workbench.frames;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.matrix.Matrix;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.synapse.Synapse;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.models.MatrixTableModel;

public class MatrixFrame extends EncogCommonFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JScrollPane scroll;
	private final JTable table;
	private final MatrixTableModel model;

	public MatrixFrame(final BasicNetwork data, final Synapse synapse) {
		setSize(640, 480);
		setTitle("Weight Matrix for " + data.getName());
		setEncogObject(synapse);
		this.model = new MatrixTableModel(synapse);
		this.table = new JTable(this.model);
		this.scroll = new JScrollPane(this.table);
		getContentPane().add(this.scroll);
	}

	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}
	
public void windowClosing(final WindowEvent e) {
		
		// just let this window close, no need to ask anything
	// we can't roll back anyway

	}

}
