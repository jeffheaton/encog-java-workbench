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
		
		JComboBox combo = ((JComboBox)this.getField());
		String str = (String)combo.getSelectedItem();
		if( str==null )
			str = "";
		
		if( combo.getSelectedIndex()==-1  || str.length()==0 )
		{
			throw new ValidationException("The field " + this.getName() + " is required.");
		}		
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
