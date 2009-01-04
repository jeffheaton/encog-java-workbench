package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.neural.data.PropertyData;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.models.PropertyDataModel;
import org.encog.workbench.EncogWorkBench;

public class PropertyDataFrame extends EncogCommonFrame {

	private JPanel panelButtons;
	private JScrollPane scroll;
	private JTable table;
	private JButton btnAdd;
	private PropertyDataModel model;
	private PropertyData data;
	private JButton btnDel;
	
	public PropertyDataFrame(PropertyData data)
	{
		super();
		this.data = data;
		setSize(400,400);
		setTitle("Property Data");
		Container content  = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.panelButtons = new JPanel();
		this.panelButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
		content.add(this.panelButtons,BorderLayout.NORTH);
		this.model = new PropertyDataModel(data);
		this.table = new JTable(this.model);
		this.scroll = new JScrollPane(this.table);
		content.add(this.scroll,BorderLayout.CENTER);
		this.btnAdd = new JButton("Add Row");
		this.btnDel = new JButton("Delete Row");
		this.panelButtons.add(this.btnAdd);
		this.btnAdd.addActionListener(this);
		this.panelButtons.add(this.btnDel);
		this.btnDel.addActionListener(this);
		
		
	}
	
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean validate(String str)
	{
		// check for invalid name
		for(int i=0;i<str.length();i++)
		{
			char ch=str.charAt(i);
			if( !Character.isLetterOrDigit(ch))
			{
				EncogWorkBench.displayError("Error", "Name contains an invalid character.");
				return false;
			}
		}
		
		// check for dup
		if( this.data.isDefined(str))
		{
			EncogWorkBench.displayError("Error", "Name already defined.");
			return false;
		}
		
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.btnAdd)
		{
			String input = EncogWorkBench.displayInput("Enter the property to add? (no spaces)");
			if(input!=null)
			{
				if( validate(input))
					this.model.addProperty(input);
			}
		}
		else if( e.getSource()==this.btnDel)
		{
			int row = this.table.getSelectedRow();
			
			if( row == -1 )
			{
				EncogWorkBench.displayError("Error", "Please select the row that you wish to delete.");
				return;
			}
			
			if( EncogWorkBench.askQuestion("Are you sure", "Are you sure you want to delete this property row?"))
			{
				this.model.deleteRow(row);
			}
		}
		
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
