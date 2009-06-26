/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
 */

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
