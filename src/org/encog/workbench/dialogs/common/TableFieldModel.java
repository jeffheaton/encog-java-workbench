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
