package org.encog.workbench.models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.solve.genetic.innovation.Innovation;
import org.encog.solve.genetic.population.Population;
import org.encog.util.Format;

public class InnovationModel implements TableModel {

	private Population population;
	public static String[] COLUMNS = { "Innovation ID", "Info" };
	
	public InnovationModel(Population population)
	{
		this.population = population;
	}
	
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	public int getColumnCount() {
		return COLUMNS.length;
	}

	public String getColumnName(int arg0) {
		return COLUMNS[arg0];
	}

	public int getRowCount() {
		if( this.population.getInnovations()==null)
			return 0;
		return this.population.getInnovations().getInnovations().size();
	}

	public Object getValueAt(int arg0, int arg1) {
		Innovation innovation = this.population.getInnovations().get(arg0);

		switch(arg1)
		{
			case 0:
				return Format.formatInteger((int)innovation.getInnovationID());
			case 1:
				return innovation.toString();
			default:
				return "";
		}
				
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
