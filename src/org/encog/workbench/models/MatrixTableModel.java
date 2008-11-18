package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.encog.matrix.Matrix;

public class MatrixTableModel implements TableModel {

	private List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private Matrix matrix;
	
	public MatrixTableModel(Matrix matrix)
	{
		this.matrix = matrix;
	}
	
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);		
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return matrix.getCols()+1;
	}

	public String getColumnName(int columnIndex) {
		if( columnIndex == 0)
			return "";
		else
		return "Next Layer: " + columnIndex;
	}

	public int getRowCount() {
		return matrix.getRows();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if( columnIndex==0 )
		{
			if( rowIndex==(this.matrix.getRows()-1) )
				return "Threshold: ";
			else
				return "This Layer: " + (rowIndex+1);
		}
		else
			return ""+matrix.get(rowIndex,columnIndex-1);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if( columnIndex==0 )
			return false;
		else
			return true;
	}

	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);		
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		matrix.set(rowIndex, columnIndex-1, Double.parseDouble((String)value));		
	}
}
