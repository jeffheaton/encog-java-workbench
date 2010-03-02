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

import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EncogPropertiesDialog extends EncogCommonDialog {

	List<PropertiesField> properties = new ArrayList<PropertiesField>();
	
	public EncogPropertiesDialog(Frame owner) {
		super(owner);
	}
	
	public EncogPropertiesDialog(JDialog owner) {
		super(owner);
	}
	
	public void render()
	{
		int y = 0;
		int maxLabelWidth = 0;
		int dialogWidth = getWidth();
		int labelHeight=0;
		
		JPanel contents = this.getBodyPanel();
		contents.setLayout(null);
		
		// create the labels
		
		for(PropertiesField field: this.properties)
		{
			JLabel label = field.createLabel();
			maxLabelWidth = Math.max(maxLabelWidth, label.getWidth());
		}
		
		y=0;
		// create the text fields
		for(PropertiesField field: this.properties)
		{
			y = field.createField(contents, maxLabelWidth+30, y, dialogWidth-maxLabelWidth-50 );
		}
	}

	@Override
	public void collectFields() throws ValidationException {
		for(PropertiesField field: this.properties)
		{
			field.collect();
		}
	}

	@Override
	public void setFields() {
		// nothing to do here
		
	}
	
	public void addProperty(PropertiesField field) {
		this.properties.add(field);
		field.setOwner(this);
		
	}

}
