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

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.tabs.mlmethod.MLMethodTab;

public class WeightsModel implements TableModel {
	
	private BasicNetwork network;
	private int fromLayer = 0;
	private List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private MLMethodTab owner;
	
	public WeightsModel(MLMethodTab theOwner, BasicNetwork theNetwork) {
		super();
		this.network = theNetwork;
		this.owner = theOwner;
	}
	
	public void addTableModelListener(TableModelListener l) {
		this.listeners.add(l);
	}

	public Class<?> getColumnClass(int columnIndex) {
		if( columnIndex==0 ) {
			return String.class;	
		} else {
			return Double.class;
		}
		
	}

	public int getColumnCount() {
		if( this.network==null )
			return 0;
		else {
			return this.network.getLayerNeuronCount(this.fromLayer)+1;
		}
	}

	public String getColumnName(int columnIndex) {
		if( columnIndex==0 ) {
			return "";
		} else {
			String prefix;
			if( this.fromLayer==0 ) {
				prefix = "I:";
			} else {
				prefix = "H" + this.fromLayer + ":";
			}
			return prefix+(columnIndex-1);
		}
	}

	public int getRowCount() {
		if( this.network==null ) {
			return 0;
		} else {
			return this.network.getLayerNeuronCount(this.fromLayer+1);
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if( this.network==null ) {
			return 0;
		} else {
			if( columnIndex==0 ) {
				String prefix;
				if( this.fromLayer==(this.network.getLayerCount()-2) ) {
					prefix = "O:";
				} else {
					prefix = "H" + (this.fromLayer+1) + ":";
				}
				
				return prefix+rowIndex;
			} else {
				return new Double(this.network.getWeight(this.fromLayer, columnIndex, rowIndex));
			}
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex>0;
	}

	public void removeTableModelListener(TableModelListener l) {
		this.listeners.add(l);
		
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		this.owner.setDirty(true);
		this.network.setWeight(this.fromLayer, columnIndex, rowIndex,((Double)value).doubleValue());
	}

	/**
	 * @return the fromLayer
	 */
	public int getFromLayer() {
		return fromLayer;
	}

	/**
	 * @param fromLayer the fromLayer to set
	 */
	public void setFromLayer(int fromLayer) {
		final TableModelEvent tce = new TableModelEvent(this,
				TableModelEvent.ALL_COLUMNS);
		
		this.fromLayer = fromLayer;
		for( TableModelListener lis : this.listeners) {
			lis.tableChanged(tce);
		}
	}
	
	
}
