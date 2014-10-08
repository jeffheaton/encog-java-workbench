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

}
