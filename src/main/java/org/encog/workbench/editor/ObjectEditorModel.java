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
