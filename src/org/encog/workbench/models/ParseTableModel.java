package org.encog.workbench.models;

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

public class ParseTableModel implements TableModel {
	public static final String[] BOOLEAN_VALUES = {"True","False"};
	private Object data;
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private List<Field> fields = new ArrayList<Field>();
	private List<String> fieldNames = new ArrayList<String>();

	public void addTableModelListener(TableModelListener listener) {
		this.listeners.add(listener);
	}

	public Class<?> getColumnClass(int arg0) {
		return String.class;
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
		return this.fields.size();
	}

	public Object getValueAt(int row, int col) {
		if (this.data == null)
			return null;
		else {
			if (col == 0) {
				return fieldNames.get(row);
			} else {
				try {
					return fields.get(row).get(this.data);
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
				Field field = fields.get(row);
				Class<?> type = field.getType();

				if (type == byte.class) { 
					field.setByte(this.data, Byte.parseByte((String)value));
				}
				else if (type == short.class) {
					field.setShort(this.data, Short.parseShort((String)value));
				}
				else if (type == int.class ) {
					field.setInt(this.data, Integer.parseInt((String)value));
				}
				else if (type == long.class) {
					field.setLong(this.data, Long.parseLong((String)value));
				} else if (type == String.class) {
					field.set(this.data, value);
				} else if (type == double.class) {
					field.setDouble(this.data, Double.parseDouble((String)value));
				} else if (type == float.class) {
					field.setFloat(this.data, Float.parseFloat((String)value));
				}
			} catch (IllegalArgumentException e) {
				EncogWorkBench.displayError("Error Setting Value:" + this.fieldNames.get(row), e
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

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		// first find the table used with this
		for (TableModelListener listener : this.listeners) {
			System.out.println(listener);
		}
		
		//
		this.data = data;
		this.fields.clear();
		this.fieldNames.clear();
		Field[] f = data.getClass().getDeclaredFields();
		for (int i = 0; i < f.length; i++) {
			if ((f[i].getModifiers() & Modifier.FINAL) == 0) {
				Class c = f[i].getType();
				if (isDirectEditableType(c)) {
					f[i].setAccessible(true);
					this.fields.add(f[i]);
					this.fieldNames.add(f[i].getName());
				}
			}
		}
		updateListeners();
	}

	private boolean isDirectEditableType(Class<?> c) {
		if ((c == int.class) || (c == short.class)
				|| (c == char.class) || (c == long.class)
				|| (c == byte.class) || (c == String.class)) {
			return true;
		}
		return false;
	}

	public void updateListeners() {
		TableModelEvent event = new TableModelEvent(this);

		for (TableModelListener listener : this.listeners) {
			listener.tableChanged(event);
		}
	}
}
