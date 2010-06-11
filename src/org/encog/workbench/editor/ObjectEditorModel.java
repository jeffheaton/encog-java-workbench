/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.editor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;

public class ObjectEditorModel implements TableModel {

	private PropertyCollection data;
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();

	public ObjectEditorModel(PropertyCollection data)
	{
		this.data = data;
	}

	public void addTableModelListener(TableModelListener listener) {
		this.listeners.add(listener);
	}

	public Class<?> getColumnClass(int arg0) {
		return Object.class;
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnName(int col) {
		if (col == 0)
			return "Name";
		else
			return "Value";
	}

	public int getRowCount() {
		if (this.data == null)
			return 0;
		return this.data.size();
	}

	public Object getValueAt(int row, int col) {
		if (this.data == null)
			return null;
		else {
			if (col == 0) {
				return this.data.getFieldName(row);
			} else {
				try {
					Object value = data.getField(row).get(this.data.getData());
					return value;
				} catch (IllegalArgumentException e) {
					throw new WorkBenchError(e);
				} catch (IllegalAccessException e) {
					throw new WorkBenchError(e);
				}
			}

		}
	}

	public boolean isCellEditable(int row, int col) {
		if (col == 0)
			return false;
		else
			return true;
	}

	public void removeTableModelListener(TableModelListener listener) {
		this.listeners.remove(listener);

	}

	public void setValueAt(Object value, int row, int col) {
		if (col == 1) {
			try {
				Field field = this.data.getField(row);
				Class<?> type = field.getType();

				if (type == byte.class) { 
					field.setByte(this.data.getData(), Byte.parseByte((String)value));
				}
				else if (type == short.class) {
					field.setShort(this.data.getData(), Short.parseShort((String)value));
				}
				else if (type == int.class ) {
					field.setInt(this.data.getData(), Integer.parseInt((String)value));
				}
				else if (type == long.class) {
					field.setLong(this.data.getData(), Long.parseLong((String)value));
				} else if (type == String.class) {
					field.set(this.data.getData(), value);
				} else if (type == double.class) {
					field.setDouble(this.data.getData(), Double.parseDouble((String)value));
				} else if (type == float.class) {
					field.setFloat(this.data.getData(), Float.parseFloat((String)value));
				} else if (type == boolean.class) {
					field.setBoolean(this.data.getData(), (Boolean)value);
				}
			} catch (IllegalArgumentException e) {
				EncogWorkBench.displayError("Error Setting Value: " + this.data.getFieldName(row), e
						.getMessage());
			} catch (IllegalAccessException e) {
				throw new WorkBenchError(e);
			} 

		}

	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}


	public void updateListeners() {
		TableModelEvent event = new TableModelEvent(this);

		for (TableModelListener listener : this.listeners) {
			listener.tableChanged(event);
		}
	}
}
