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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class TextAreaField extends PropertiesField {

	private String value;
	private JTextArea textArea;
	
	public TextAreaField(String name, String label, boolean required) {
		super(name, label, required);
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.textArea = new JTextArea(10,10);
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(this.textArea);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setLocation(x, y);
		scroll.setSize(scroll.getPreferredSize());
		scroll.setSize(width,scroll.getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(scroll);
		
		
		setField(scroll);
		
		return y+scroll.getHeight();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		this.textArea.setText(value);
	}

	@Override
	public void collect() throws ValidationException {
		this.value = this.textArea.getText();
		if( this.value.length()<1 && this.isRequired())
		{
			throw new ValidationException("The field " + this.getName() + " is required.");
		}
	}
	
	

}
