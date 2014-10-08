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
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class MapDataModel  implements TableModel {
	
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private Map<String,String> data;
	private String[] properties;
	
	public MapDataModel(Map<String,String> data)
	{
		this.data = data;
		updateProperties();
	}

	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
		
	}

	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnName(int c) {
		if(c==1)
			return "Value";
		else
			return "Name";
	}

	public int getRowCount() {
		return this.properties.length;
	}

	public Object getValueAt(int row, int col) {
		
		String key = this.properties[row]; 
		
		if( col==0 )
			return key;
		else
			return this.data.get(key);
	}

	public boolean isCellEditable(int row, int col) {
		if(col==0)
			return false;
		else
			return true;
	}

	public void removeTableModelListener(TableModelListener listener) {
		this.listeners.remove(listener);
		
	}

	public void setValueAt(Object obj, int row, int col) {

		String key = this.properties[row]; 
		String value = (String)obj;
		
		if( col==1 )
			this.data.put(key,value);
		
	}
	
	public void updateProperties()
	{
		this.properties = new String[data.size()];

		int i=0;
		for(String key: this.data.keySet() )
		{
			this.properties[i++]=key;
		}
		
		updateListeners();
	}
	
	public void updateListeners()
	{
		TableModelEvent event = new TableModelEvent(this);
		
		for(TableModelListener listener: this.listeners)
		{
			listener.tableChanged(event);
		}
	}

	public void addProperty(String input) {
		this.data.put(input, "");
		updateProperties();
		
	}

	public void deleteRow(int row) {
		String key = this.properties[row];
		this.data.remove(key);
		updateProperties();
		
	}

}
