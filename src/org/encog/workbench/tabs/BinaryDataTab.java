package org.encog.workbench.tabs;

import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.encog.EncogError;
import org.encog.mathutil.libsvm.svm_model;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.data.buffer.BufferedNeuralDataSet;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.models.BufferedDataSetTableModel;
import org.encog.workbench.models.TrainingSetTableModel;
import org.encog.workbench.process.ImportExport;
import org.encog.workbench.util.EncogFonts;
import org.encog.workbench.util.ExcelAdapter;
import org.encog.workbench.util.TaskComplete;

public class BinaryDataTab extends EncogCommonTab implements ActionListener {

	private BufferedNeuralDataSet object;
	private BufferedDataSetTableModel model;
	private JToolBar toolbar;
	private JTable table;

	private JButton addInputColumn;
	private JButton delColumn;
	private JButton addIdealColumn;
	private JButton addRow;
	private JButton delRow;
	private JButton export;
	
	public BinaryDataTab(EncogPersistedObject encogObject) {
		super(encogObject);
		this.object = (BufferedNeuralDataSet)encogObject;
		setLayout(new BorderLayout());
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
		add(this.toolbar, BorderLayout.PAGE_START);
		this.model = new BufferedDataSetTableModel(getData());
		this.table = new JTable(this.model);
		add(new JScrollPane(this.table), BorderLayout.CENTER);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//new ExcelAdapter( this.table );
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
			Object[] list = new Object[2];
			list[0] = this.getData().getFile();
			list[1] = null;
			
			ImportExport.performBin2External(this.getData().getFile(), null);
		}

	}
	
	public void dispose()
	{
		super.dispose();
		this.object.open();
	}

	public BufferedNeuralDataSet getData() {
		return (BufferedNeuralDataSet) getEncogObject();
	}

}
