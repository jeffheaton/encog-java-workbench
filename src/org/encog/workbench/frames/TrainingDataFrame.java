package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.models.TrainingSetTableModel;

public class TrainingDataFrame extends JFrame implements WindowListener, ActionListener {

	private BasicNeuralDataSet data;
	private TrainingSetTableModel model;
	private JToolBar toolbar;
	private JTable table;
	
	private JButton addInputColumn;
	private JButton delColumn;
	private JButton addIdealColumn;
	private JButton addRow;
	private JButton delRow;
	private JButton properties;
	
	public TrainingDataFrame(BasicNeuralDataSet data) {
		this.data = data;
		this.addWindowListener(this);
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		
		
	}

	public void windowClosing(WindowEvent e) {
		EncogWorkBench.getInstance().getMainWindow().closeSubWindow(this);
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void windowOpened(WindowEvent e) {
		setSize(640,480);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.addInputColumn = new JButton("Add Input Column"));
		this.toolbar.add(this.delColumn = new JButton("Delete Column"));
		this.toolbar.add(this.addIdealColumn = new JButton("Add Ideal Column"));
		this.toolbar.add(this.addRow = new JButton("Add Row"));
		this.toolbar.add(this.delRow = new JButton("Delete Row"));
		this.toolbar.add(this.properties = new JButton("Data Set Properties"));
		this.addInputColumn.addActionListener(this);
		this.delColumn.addActionListener(this);
		this.addIdealColumn.addActionListener(this);
		this.addRow.addActionListener(this);
		this.delRow.addActionListener(this);
		this.properties.addActionListener(this);
		content.add(this.toolbar,BorderLayout.PAGE_START);
		this.model = new TrainingSetTableModel(this.data);
		this.table = new JTable(model);		
		content.add(new JScrollPane(table),BorderLayout.CENTER);
		//
		setTitle("Edit Training Data");
	}

	public void actionPerformed(ActionEvent action) {
		int row = this.table.getSelectedRow();
		int col = this.table.getSelectedColumn();
		
		if( action.getSource() == this.addInputColumn )
		{
			this.model.addInputColumn();
		}
		else if( action.getSource() == this.delColumn )
		{
			if( col==-1 )
			{
				JOptionPane.showMessageDialog(this, "Please move to the column you wish to delete.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if( col< this.data.getInputSize() && this.data.getInputSize()<=1 ){
				JOptionPane.showMessageDialog(this, "There must be at least one input column.", "Error", JOptionPane.ERROR_MESSAGE);				
			}
			else
				this.model.delColumn(col);
		}
		else if( action.getSource() == this.addIdealColumn )
		{
			this.model.addIdealColumn();
		}
		else if( action.getSource() == this.addRow )
		{
			this.model.addRow(row);
		}
		else if( action.getSource() == this.delRow )
		{
			if( row==-1 )
			{
				JOptionPane.showMessageDialog(this, "Please move to the row you wish to delete.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
				this.model.delRow(row);
		}
		else if (action.getSource()==this.properties)
		{
			EditEncogObjectProperties dialog = new EditEncogObjectProperties(this,this.data);
			dialog.process();
		}
		
	}
	
	public BasicNeuralDataSet getData()
	{
		return this.data;
	}

}
