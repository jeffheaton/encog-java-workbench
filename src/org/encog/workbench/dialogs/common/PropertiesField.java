/*
 * Encog(tm) Workbench v2.4
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
	
}
