/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.wizard.analyst;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.app.analyst.wizard.WizardMethodType;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.FileField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.frames.document.EncogDocumentFrame;

public class AnalystWizardDialog extends EncogPropertiesDialog {
	
	private final FileField rawFile;
	private final ComboBoxField method;
	private final CheckField headers;
	private final CheckField decimalComma;
	
	private final List<String> methods = new ArrayList<String>();
	
	public AnalystWizardDialog(Frame owner) {
		super(owner);
		
		List<String> list = new ArrayList<String>();
		list.add("CSV");
		list.add("Excel (*.xlsx)");
		
		methods.add("Feedforward Network");
		methods.add("RBF Network");
		methods.add("Support Vector Machine");
		methods.add("PNN/GRNN Network");
		
		this.setSize(640, 200);
		this.setTitle("Setup Encog Analyst Wizard");
		
		addProperty(this.rawFile = new FileField("source file","Source CSV File(*.csv)",true,false,EncogDocumentFrame.CSV_FILTER));
		addProperty(this.method = new ComboBoxField("method", "Machine Learning", true, methods));
		addProperty(this.headers = new CheckField("headers","CSV File Headers"));
		addProperty(this.decimalComma = new CheckField("decimal comma","Decimal Comma (instead of decimal point)"));

		render();
	}

	/**
	 * @return the rawFile
	 */
	public FileField getRawFile() {
		return rawFile;
	}

	/**
	 * @return the headers
	 */
	public CheckField getHeaders() {
		return headers;
	}

	/**
	 * @return the decimalComma
	 */
	public CheckField getDecimalComma() {
		return decimalComma;
	}
	
	public WizardMethodType getMethodType()
	{
		switch(this.method.getSelectedIndex()) {
			case 0:
				return WizardMethodType.FeedForward;
			case 1:
				return WizardMethodType.RBF;
			case 2:
				return WizardMethodType.SVM;
			case 3:
				return WizardMethodType.PNN;
			default:
				return null;
		}
	}


}
