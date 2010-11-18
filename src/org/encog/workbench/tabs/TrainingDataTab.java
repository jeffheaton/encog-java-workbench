/*
 * Encog(tm) Workbench v2.6 
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
package org.encog.workbench.tabs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.models.TrainingSetTableModel;
import org.encog.workbench.process.ImportExport;
import org.encog.workbench.util.ExcelAdapter;

public class TrainingDataTab extends EncogCommonTab implements ActionListener {

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

	public TrainingDataTab(final BasicNeuralDataSet data) {
		super(data);
		
		setSize(640, 480);
		setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.addInputColumn = new JButton("Add Input Column"));
		this.toolbar.add(this.delColumn = new JButton("Delete Column"));
		this.toolbar.add(this.addIdealColumn = new JButton("Add Ideal Column"));
		this.toolbar.add(this.addRow = new JButton("Add Row"));
		this.toolbar.add(this.delRow = new JButton("Delete Row"));
		//this.toolbar.add(this.export = new JButton("Export"));
		this.addInputColumn.addActionListener(this);
		this.delColumn.addActionListener(this);
		this.addIdealColumn.addActionListener(this);
		this.addRow.addActionListener(this);
		this.delRow.addActionListener(this);
		this.export.addActionListener(this);
		add(this.toolbar, BorderLayout.PAGE_START);
		this.model = new TrainingSetTableModel(getData());
		this.table = new JTable(this.model);
		add(new JScrollPane(this.table), BorderLayout.CENTER);
		//
		new ExcelAdapter( this.table );
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
			//ImportExport.performExport(this.getEncogObject());
		}

	}

	public BasicNeuralDataSet getData() {
		return (BasicNeuralDataSet) getEncogObject();
	}


}
