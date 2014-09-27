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
import java.util.ArrayList;
import java.util.List;

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
