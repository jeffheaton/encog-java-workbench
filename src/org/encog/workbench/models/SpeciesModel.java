package org.encog.workbench.models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.solve.genetic.population.Population;
import org.encog.solve.genetic.species.Species;
import org.encog.util.Format;

public class SpeciesModel implements TableModel {

	private Population population;
	
	public static String[] COLUMNS = { "Species ID", "Age", "Best Score", "Stagnant" , "Leader ID", "Members" };
	
	public SpeciesModel(Population population)
	{
		this.population = population;
	}
	
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return COLUMNS.length;
	}

	public String getColumnName(int columnIndex) {
		return COLUMNS[columnIndex];
	}

	public int getRowCount() {
		return this.population.getSpecies().size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Species species = this.population.getSpecies().get(rowIndex);
		
		String leader = "none";
		
		if( species.getLeader()!=null )
			leader = Format.formatInteger((int)species.getLeader().getGenomeID());
		
		switch(columnIndex)
		{
			case 0:
				return Format.formatInteger((int)species.getSpeciesID());
			case 1:
				return Format.formatInteger(species.getAge());
			case 2:
				return Format.formatDouble(species.getBestScore(),4);
			case 3:
				return Format.formatInteger(species.getGensNoImprovement());
			case 4:
				return leader;
			case 5:
				return Format.formatInteger(species.getMembers().size());
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
