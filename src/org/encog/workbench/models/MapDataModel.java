/*
 * Encog(tm) Workbench v2.4
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.neural.data.PropertyData;

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
			return "Name";
		else
			return "Value";
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
