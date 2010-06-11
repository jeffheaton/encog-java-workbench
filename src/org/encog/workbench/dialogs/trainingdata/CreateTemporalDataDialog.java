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

package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class CreateTemporalDataDialog  extends EncogPropertiesDialog {

	private final IntegerField inputWindow;
	private final IntegerField outputWindow;
	private final ComboBoxField normalizationType;

	
	public CreateTemporalDataDialog(Frame owner) {
		super(owner);
		
		setTitle("Temporal Training Data");
		setSize(400,200);
		addProperty(this.inputWindow = new IntegerField("input window","Input Window",true,0,1000));
		addProperty(this.outputWindow = new IntegerField("output window","Output Window",true, 0, 1000));

		List<String> list = new ArrayList<String>();
		list.add("Raw");
		list.add("Percent Change");
		list.add("Delta Change");
		addProperty(this.normalizationType = new ComboBoxField("normalization type", "Normalization Type",true,list) );
		
		
		render();
	}


	public IntegerField getInputWindow() {
		return inputWindow;
	}


	public IntegerField getOutputWindow() {
		return outputWindow;
	}


	public ComboBoxField getNormalizationType() {
		return normalizationType;
	}
	
	

	
}
