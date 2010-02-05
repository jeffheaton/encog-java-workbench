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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.models.TrainingSetTableModel;
import org.encog.workbench.process.ImportExport;
import org.encog.workbench.util.ExcelAdapter;

public class TrainingDataFrame extends EncogCommonFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TrainingSetTableModel model;
	private JToolBar toolbar;
	private JTable table;

	private JButton addInputColumn;
	private JButton delColumn;
	private JButton addIdealColumn;
	private JButton addRow;
	private JButton delRow;
	private JButton export;

	public TrainingDataFrame(final BasicNeuralDataSet data) {
		setEncogObject(data);
		addWindowListener(this);
	}

	public void actionPerformed(final ActionEvent action) {
		final int row = this.table.getSelectedRow();
		final int col = this.table.getSelectedColumn();

		if (action.getSource() == this.addInputColumn) {
			this.model.addInputColumn();
		} else if (action.getSource() == this.delColumn) {
			if (col == -1) {
				JOptionPane.showMessageDialog(this,
						"Please move to the column you wish to delete.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else if (col < getData().getInputSize()
					&& getData().getInputSize() <= 1) {
				JOptionPane.showMessageDialog(this,
						"There must be at least one input column.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				this.model.delColumn(col);
			}
		} else if (action.getSource() == this.addIdealColumn) {
			this.model.addIdealColumn();
		} else if (action.getSource() == this.addRow) {
			this.model.addRow(row);
		} else if (action.getSource() == this.delRow) {
			if (row == -1) {
				JOptionPane.showMessageDialog(this,
						"Please move to the row you wish to delete.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				this.model.delRow(row);
			}
		} else if(action.getSource()==this.export)
		{
			ImportExport.performExport(this.getEncogObject());
		}

	}

	public BasicNeuralDataSet getData() {
		return (BasicNeuralDataSet) getEncogObject();
	}

	public void mouseClicked(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(final WindowEvent e) {
		setSize(640, 480);
		final Container content = getContentPane();
		content.setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.addInputColumn = new JButton("Add Input Column"));
		this.toolbar.add(this.delColumn = new JButton("Delete Column"));
		this.toolbar.add(this.addIdealColumn = new JButton("Add Ideal Column"));
		this.toolbar.add(this.addRow = new JButton("Add Row"));
		this.toolbar.add(this.delRow = new JButton("Delete Row"));
		this.toolbar.add(this.export = new JButton("Export"));
		this.addInputColumn.addActionListener(this);
		this.delColumn.addActionListener(this);
		this.addIdealColumn.addActionListener(this);
		this.addRow.addActionListener(this);
		this.delRow.addActionListener(this);
		this.export.addActionListener(this);
		content.add(this.toolbar, BorderLayout.PAGE_START);
		this.model = new TrainingSetTableModel(getData());
		this.table = new JTable(this.model);
		content.add(new JScrollPane(this.table), BorderLayout.CENTER);
		//
		setTitle("Edit Training Data");
		new ExcelAdapter( this.table );
	}

}
