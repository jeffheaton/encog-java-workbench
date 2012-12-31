/*
 * Encog(tm) Workbench v3.2
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
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
package org.encog.workbench.models.population.epl;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.ml.prg.EncogProgram;
import org.encog.ml.prg.train.PrgPopulation;
import org.encog.parse.expression.common.RenderCommonExpression;
import org.encog.util.Format;

public class EPLPopulationModel implements TableModel {

	private PrgPopulation population;
	
	public static String[] COLUMNS = { "#", "Length", "Score", "Adj. Score", "Expression" };
	
	public EPLPopulationModel(PrgPopulation population)
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
		
		EncogProgram genome = (EncogProgram)this.population.getGenomes().get(rowIndex);
		RenderCommonExpression render = new RenderCommonExpression();
		
		switch(columnIndex)
		{
			case 0:
				return ""+rowIndex;
			case 1:
				return Format.formatInteger(genome.getProgramLength());
			case 2:
				if( Double.isNaN(genome.getScore()) || Double.isInfinite(genome.getScore()) ) {
					return "NaN";
				} else {
					return Format.formatDouble(genome.getScore(),4);
				}
			case 3:
				if( Double.isNaN(genome.getAdjustedScore()) || Double.isInfinite(genome.getAdjustedScore()) ) {
					return "NaN";
				} else {
					return Format.formatDouble(genome.getAdjustedScore(),4);
				}
			case 4:
				return render.render(genome);
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
