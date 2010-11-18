/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.engine.util.Format;
import org.encog.solve.genetic.genome.Genome;
import org.encog.solve.genetic.population.Population;

public class GeneralPopulationModel implements TableModel {

	private Population population;
	
	public static String[] COLUMNS = { "Genome ID", "Chromosomes", "Spawn Size", "Score" };
	
	public GeneralPopulationModel(Population population)
	{
		this.population = population;
	}
	
	public void addTableModelListener(TableModelListener l) {
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
		return this.population.getGenomes().size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Genome genome = this.population.getGenomes().get(rowIndex);
		
		switch(columnIndex)
		{
			case 0:
				return Format.formatInteger((int)genome.getGenomeID());
			case 1:
				return Format.formatInteger(genome.getChromosomes().size());
			case 2:
				return Format.formatDouble(genome.getAmountToSpawn(),2);
			case 3:
				return Format.formatDouble(genome.getScore(),4);
			default:
				return "";
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

}
