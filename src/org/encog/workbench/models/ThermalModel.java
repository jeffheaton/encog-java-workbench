package org.encog.workbench.models;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.encog.engine.util.Format;
import org.encog.ml.genetic.genome.Genome;
import org.encog.neural.thermal.ThermalNetwork;

public class ThermalModel extends DefaultTableModel {
	
	private ThermalNetwork network;
	
	public ThermalModel(ThermalNetwork network) {
		super();
		this.network = network;
	}
	
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		if( this.network==null )
			return 0;
		else
			return this.network.getNeuronCount();
	}

	public String getColumnName(int columnIndex) {
		return ""+columnIndex;
	}

	public int getRowCount() {
		if( this.network==null )
			return 0;
		else
			return this.network.getNeuronCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return "" + this.network.getWeight(rowIndex, columnIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}
}
