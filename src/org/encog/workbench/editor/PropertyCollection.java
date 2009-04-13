package org.encog.workbench.editor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

import org.encog.persist.EncogPersistedObject;

public class PropertyCollection {

	private ObjectEditorModel model;
	private Object data;
	private List<Field> fields = new ArrayList<Field>();
	private List<String> fieldNames = new ArrayList<String>();
	public final static String[] booleanValues = { "true", "false" };

	public PropertyCollection(Object data) {
		this.data = data;
		this.model = new ObjectEditorModel(this);

	}

	public int size() {
		return this.fields.size();
	}

	public String getFieldName(int field) {
		return this.fieldNames.get(field);
	}

	public Field getField(int field) {
		return this.fields.get(field);
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
		this.data = data;
		this.fields.clear();
		this.fieldNames.clear();

		if (data != null) {

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
		}
		this.model.updateListeners();
	}

	private boolean isDirectEditableType(Class<?> c) {
		if ((c == int.class) || (c == short.class) || (c == char.class)
				|| (c == long.class) || (c == byte.class)
				|| (c == String.class) || (c == boolean.class)) {
			return true;
		}
		return false;
	}

	/**
	 * @return the model
	 */
	public ObjectEditorModel getModel() {
		return model;
	}

}
