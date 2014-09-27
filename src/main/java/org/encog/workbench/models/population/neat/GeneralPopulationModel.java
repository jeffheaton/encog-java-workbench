/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
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
package org.encog.workbench.models.population.neat;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.ml.ea.genome.Genome;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.util.Format;

public class GeneralPopulationModel implements TableModel {

	private NEATPopulation population;
	private int maxGeneration;
	private List<Genome> list;
	public static String[] COLUMNS = { "Neurons", "Links", "Score", "Age", "Birth Generation" };
	
	
	public GeneralPopulationModel(NEATPopulation population)
	{
		this.population = population;
		this.maxGeneration = 0;
		this.list = this.population.flatten();
		for(Genome g : list) {
			NEATGenome genome = (NEATGenome)g;
			this.maxGeneration = Math.max(this.maxGeneration, genome.getBirthGeneration());
		}
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
		return this.list.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		NEATGenome genome = (NEATGenome)this.list.get(rowIndex);
		
		switch(columnIndex)
		{
			case 0:
				return Format.formatInteger(genome.getNeuronsChromosome().size());
			case 1:
				return Format.formatInteger(genome.getLinksChromosome().size());
			case 2:
				return Format.formatDouble(genome.getScore(),4);
			case 3:
				return Format.formatInteger(this.maxGeneration - genome.getBirthGeneration());
			case 4:
				return Format.formatInteger(genome.getBirthGeneration());
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
