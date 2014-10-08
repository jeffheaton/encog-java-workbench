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

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class PropertiesField {

	private final String label;
	private final String name;
	private final boolean required;
	private JComponent field;
	private JLabel labelControl;
	private EncogPropertiesDialog owner;
	
	public PropertiesField(String name, String label,boolean required)
	{
		this.name = name;
		this.label = label;
		this.required = required;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}

	public JComponent getField() {
		return field;
	}

	public void setField(JComponent field) {
		this.field = field;
	}	
	
	public abstract void collect() throws ValidationException;
	
	public JLabel createLabel() 
	{
		if(this.labelControl!=null)
		{
			return this.labelControl;
		}
		
		this.labelControl = new JLabel(this.label);
		this.labelControl.setSize(this.labelControl.getPreferredSize());
		return this.labelControl;
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.field = new JTextField();
		this.field.setLocation(x, y);
		this.field.setSize(this.field.getPreferredSize());
		this.field.setSize(width,this.field.getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.field);
		
		return y+this.field.getHeight();
	}

	public EncogPropertiesDialog getOwner() {
		return owner;
	}

	public void setOwner(EncogPropertiesDialog owner) {
		this.owner = owner;
	}
	
	public void enable(boolean e)
	{
		this.field.setEnabled(e);
	}
	
	public void setVisable(boolean v)
	{
		this.field.setVisible(v);
	}
	
	public void repaint()
	{
		this.field.repaint();
	}

	public JLabel getLabelControl() {
		return labelControl;
	}

	public void setLabelControl(JLabel labelControl) {
		this.labelControl = labelControl;
	}
	
	
	
}
