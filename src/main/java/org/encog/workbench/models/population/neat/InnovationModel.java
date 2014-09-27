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

import java.util.HashSet;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.training.NEATInnovation;
import org.encog.util.Format;

public class InnovationModel implements TableModel {

	private NEATPopulation population;
	public static String[] COLUMNS = { "Innovation ID", "Info" };
	private Object[] keys;
	
	
	public InnovationModel(NEATPopulation population)
	{
		this.population = population;
		this.keys = population.getInnovations().getInnovations().keySet().toArray();
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
		switch(arg1)
		{
			case 0:
				return ""+arg0;
			case 1:
				return this.keys[arg0];
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
