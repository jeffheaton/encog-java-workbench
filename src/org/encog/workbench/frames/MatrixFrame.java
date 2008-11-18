package org.encog.workbench.frames;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.matrix.Matrix;
import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.models.MatrixTableModel;

public class MatrixFrame extends EncogCommonFrame {

	private BasicNetwork data;
	private Matrix matrix;
	private JScrollPane scroll;
	private JTable table;
	private MatrixTableModel model;
	
	public MatrixFrame(BasicNetwork data,Matrix matrix)
	{
		setSize(640,480);
		this.setTitle("Weight Matrix for " + data.getName());
		this.data = data;
		this.matrix = matrix;
		this.model = new MatrixTableModel(this.matrix);
		this.table = new JTable(this.model);
		this.scroll = new JScrollPane(this.table);
		this.getContentPane().add(scroll);
	}
	
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
