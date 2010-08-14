/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.engine.util.Format;
import org.encog.solve.genetic.population.Population;
import org.encog.solve.genetic.species.Species;

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
