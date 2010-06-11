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
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class CreateMarketTrainingDialog  extends EncogPropertiesDialog {
	
	private final TextField ticker;
	private final IntegerField fromMonth;
	private final IntegerField fromDay;
	private final IntegerField fromYear;
	private final IntegerField toMonth;
	private final IntegerField toDay;
	private final IntegerField toYear;
	private final IntegerField inputWindow;
	private final IntegerField outputWindow;
	private final ComboBoxField normalizationType;

	
	public CreateMarketTrainingDialog(Frame owner) {
		super(owner);
		
		GregorianCalendar gc = new GregorianCalendar();
		int year = gc.get(Calendar.YEAR);
		
		setTitle("Market Training Data");
		setSize(500,400);
		addProperty(this.ticker = new TextField("ticker","Ticker Symbol",true));
		addProperty(this.fromMonth = new IntegerField("begin month","Beginning Month(1-12)",true, 1, 12));
		addProperty(this.fromDay = new IntegerField("begin day","Beginning Day(1-12)",true, 1, 31));
		addProperty(this.fromYear = new IntegerField("begin year","Beginning Year(1-12)",true, 1900, year));
		
		addProperty(this.toMonth = new IntegerField("end month","Ending Month(1-12)",true, 1, 12));
		addProperty(this.toDay = new IntegerField("end day","Ending Day(1-12)",true, 1, 31));
		addProperty(this.toYear = new IntegerField("end year","Ending Year(1-12)",true, 1900, year));
		
		addProperty(this.inputWindow = new IntegerField("input window","Input Window",true,0,1000));
		addProperty(this.outputWindow = new IntegerField("output window","Output Window",true, 0, 1000));

		List<String> list = new ArrayList<String>();
		list.add("Raw");
		list.add("Percent Change");
		list.add("Delta Change");
		addProperty(this.normalizationType = new ComboBoxField("normalization type", "Normalization Type",true,list) );
		
		render();
	}


	public TextField getTicker() {
		return ticker;
	}


	public IntegerField getFromMonth() {
		return fromMonth;
	}


	public IntegerField getFromDay() {
		return fromDay;
	}


	public IntegerField getFromYear() {
		return fromYear;
	}


	public IntegerField getToMonth() {
		return toMonth;
	}


	public IntegerField getToDay() {
		return toDay;
	}


	public IntegerField getToYear() {
		return toYear;
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
