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

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.matrix.Matrix;
import org.encog.neural.networks.BasicNetwork;
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

	public MatrixFrame(final BasicNetwork data, final Matrix matrix) {
		setSize(640, 480);
		setTitle("Weight Matrix for " + data.getName());
		setEncogObject(matrix);
		this.model = new MatrixTableModel(matrix);
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

}
