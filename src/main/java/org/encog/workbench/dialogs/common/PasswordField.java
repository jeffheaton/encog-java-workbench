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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PasswordField extends PropertiesField {
	private String value;
	
	public PasswordField(String name, String label, boolean required) {
		super(name, label, required);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		((JTextField)this.getField()).setText(value);
	}

	@Override
	public void collect() throws ValidationException {
		this.value = ((JTextField)this.getField()).getText();
		if( this.value.length()<1 && this.isRequired())
		{
			throw new ValidationException("The field " + this.getName() + " is required.");
		}
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		JPasswordField field = new JPasswordField();
		
		this.setField(field);
		field.setLocation(x, y);
		field.setSize(field.getPreferredSize());
		field.setSize(width,field.getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(field);
		
		return y+field.getHeight();
	}
	

}
