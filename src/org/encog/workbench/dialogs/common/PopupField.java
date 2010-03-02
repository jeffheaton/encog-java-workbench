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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PopupField extends PropertiesField implements ActionListener {

	private String value;
	private JButton button;
	private JLabel label;
	
	public PopupField(String name, String label, boolean required) {
		super(name, label, required);
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		JPanel p = new JPanel();
		this.setField(p);
		p.setLocation(x, y);

		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		
		p.setSize(width,label.getHeight());
		
		p.setLayout(new BorderLayout());
		p.add(this.label = new JLabel("test"),BorderLayout.CENTER);
		p.add(this.button = new JButton("..."),BorderLayout.EAST);
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		panel.add(label);
		panel.add(p);
		
		this.button.addActionListener(this);
		
		return y+p.getHeight();
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.button)
		{
			String str = ((PopupListener)this.getOwner()).popup(this);
			if( str!=null )
				this.value = str;
			this.label.setText(this.value);
		}
		
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.label.setText(value);
		this.value = value;
	}
	
	

}
