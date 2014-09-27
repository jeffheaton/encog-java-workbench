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
package org.encog.workbench.dialogs.common;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TableFieldModel implements TableModel {

	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private Object[][] data;
	private Class<?> type;
	private String[] heads;
	private boolean[] editable;
	
	public TableFieldModel(Class<?> c,int rows, int columns)
	{
		this.type = c;
		this.data = new Object[rows][columns];
		this.editable = new boolean[columns];
		for(int i=0;i<this.editable.length;i++)
			this.editable[i] = true;
	}
	
	public TableFieldModel(Class<?> c,String[] heads, int rows)
	{
		this.type = c;
		this.data = new Object[rows][heads.length];
		this.heads = heads;
		this.editable = new boolean[heads.length];
		for(int i=0;i<this.editable.length;i++)
			this.editable[i] = true;
	}
	
	public void addTableModelListener(TableModelListener l) {
		this.listeners.add(l);
		
	}

	public Class<?> getColumnClass(int columnIndex) {
		return type;
	}

	public int getColumnCount() {
		return this.data[0].length;
	}

	public String getColumnName(int columnIndex) {
		if( this.heads!=null)
			return this.heads[columnIndex];
		else
			return null;
	}

	public int getRowCount() {
		return this.data.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.data[rowIndex][columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return this.editable[columnIndex];
	}

	public void removeTableModelListener(TableModelListener l) {
		this.listeners.remove(l);
		
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		this.data[rowIndex][columnIndex] = value;
	}
	
	public void setEditable(int index,boolean b)
	{
		this.editable[index] = b;
	}

}
