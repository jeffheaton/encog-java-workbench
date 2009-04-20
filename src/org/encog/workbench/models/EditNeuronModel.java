package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.workbench.dialogs.layers.EditBasicLayer;

public class EditNeuronModel implements TableModel {

	private EditBasicLayer owner;
	private List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	
	public EditNeuronModel(EditBasicLayer owner) {
		this.owner = owner;
	}

	public void addTableModelListener(TableModelListener l) {
		this.listeners.add(l);
		
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnName(int columnIndex) {
		if(columnIndex==0)
		{
			return "Neuron #";
		}
		else if(columnIndex==1)
		{
			return "Threshold";
		}
		else
		return null;
	}

	public int getRowCount() {
		return this.owner.getNeuronCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex==0)
		{
			return ""+(rowIndex+1);
		}
		else
		{
			if( this.owner.getThresholds()==null)
				return "N/A";
			else
				return this.owner.getThresholds()[rowIndex];
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if( columnIndex>0 )
		{
			if( this.owner.getThresholds()!=null)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public void removeTableModelListener(TableModelListener l) {
		this.listeners.remove(l);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		for(TableModelListener l: this.listeners)
		{
			l.tableChanged(null);
		}
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

}
