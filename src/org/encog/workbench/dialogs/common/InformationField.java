/*
 * Encog(tm) Workbench v2.5
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

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InformationField extends PropertiesField {

	private String value;
	private int height;
	
	public InformationField(int height, String label) {
		super(null, label, false);
		this.height = height;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		((JTextField)this.getField()).setText(value);
	}
	
	public JLabel createLabel() 
	{
		return null;
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		JTextArea l = new JTextArea(this.getLabel());
		l.setEditable(false);
		l.setLineWrap(true);
		l.setBackground(this.getOwner().getBackground());
		l.setWrapStyleWord(true);
		l.setSize(new Dimension(this.getOwner().getWidth(),this.height*(l.getFontMetrics(l.getFont())).getHeight()));
		this.setLabelControl(null);
		l.setLocation(new Point(0,y));
		panel.add(l);
		
		return y+100;
	}


	@Override
	public void collect() throws ValidationException {
	}

}
