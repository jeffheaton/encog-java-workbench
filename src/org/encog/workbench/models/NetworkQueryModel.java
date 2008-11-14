package org.encog.workbench.models;

import javax.swing.table.DefaultTableModel;

public class NetworkQueryModel extends DefaultTableModel {

	public NetworkQueryModel(int rows, int cols) {
		super(rows,cols);
	}
	
	public boolean isCellEditable(int row, int column)
	{
		if( column==0 )
			return false;
		else 
			return true;
	}

}
