/*
 * Encog(tm) Workbench v2.3
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckField extends PropertiesField implements ActionListener {

	private boolean value;
	private CheckListener listener;
	
	public CheckField(String name, String label) {
		super(name, label, true);
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.setField(new JCheckBox());
		this.getField().setLocation(x, y);
		this.getField().setSize(this.getField().getPreferredSize());
		this.getField().setSize(width,this.getField().getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.getField());
		((JCheckBox)this.getField()).addActionListener(this);
		
		return y+this.getField().getHeight();
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == getField() )
			if( this.listener!=null )
		this.listener.check(this);
		
	}

	public CheckListener getListener() {
		return listener;
	}

	public void setListener(CheckListener listener) {
		
			this.listener = listener;
	}

	public boolean getValue() {
		return ((JCheckBox)this.getField()).isSelected();
	}

	public void setValue(boolean b) {
		((JCheckBox)this.getField()).setSelected(b);
		
	}
	
	
	
	

}
