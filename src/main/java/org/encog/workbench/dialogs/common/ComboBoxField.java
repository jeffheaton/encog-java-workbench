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

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboBoxField extends PropertiesField {
	
	private List<?> list;

	public ComboBoxField(String name, String label, boolean required, List<?> list) {
		super(name, label, required);
		this.list = list;
	}

	@Override
	public void collect() throws ValidationException {
		
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		JComboBox combo = new JComboBox(this.list.toArray());
		combo.setLocation(x, y);
		combo.setSize(combo.getPreferredSize());
		combo.setSize(width,combo.getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(combo);
		
		setField(combo);
		return y+combo.getHeight();
	}
	
	public Object getSelectedValue()
	{
		return ((JComboBox)this.getField()).getSelectedItem();
	}

	public int getSelectedIndex() {
		return ((JComboBox)this.getField()).getSelectedIndex();
	}

}
