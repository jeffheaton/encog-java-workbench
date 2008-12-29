package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.neural.data.PropertyData;
import org.encog.workbench.frames.manager.EncogCommonFrame;

public class PropertyDataFrame extends EncogCommonFrame {

	private JPanel panelButtons;
	private JScrollPane scroll;
	private JTable table;
	private JButton btnAdd;
	
	public PropertyDataFrame(PropertyData data)
	{
		super();
		Container content  = this.getContentPane();
		content.setLayout(new BorderLayout());
		this.panelButtons = new JPanel();
		content.add(this.panelButtons,BorderLayout.NORTH);
		this.table = new JTable();
		this.scroll = new JScrollPane(this.table);
		content.add(this.scroll,BorderLayout.CENTER);
	}
	
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
