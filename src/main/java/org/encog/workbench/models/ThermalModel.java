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
package org.encog.workbench.models;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

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
