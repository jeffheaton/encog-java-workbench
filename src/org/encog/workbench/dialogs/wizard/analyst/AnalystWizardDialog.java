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

import org.encog.app.analyst.AnalystFileFormat;
import org.encog.app.analyst.AnalystGoal;
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
	private final ComboBoxField format;
	private final ComboBoxField goal;
	private final IntegerField targetField;
	private final CheckField headers;
	
	private final List<String> methods = new ArrayList<String>();
	
	public AnalystWizardDialog(Frame owner) {
		super(owner);
		
		List<String> list = new ArrayList<String>();
		list.add("CSV");
		list.add("Excel (*.xlsx)");
		
		List<String> csvFormat = new ArrayList<String>();
		csvFormat.add("Decimal Point (USA/English) & Comma Separator");
		csvFormat.add("Decimal Point (USA/English) & Space Separator");
		csvFormat.add("Decimal Point (USA/English) & Semicolon Separator");
		csvFormat.add("Decimal Comma (Non-USA/English) & Space Separator");
		csvFormat.add("Decimal Comma (Non-USA/English) & Semicolon Separator");
		
		List<String> goalList = new ArrayList<String>();
		goalList.add("Classification");
		goalList.add("Regression");

		
		methods.add("Feedforward Network");
		methods.add("RBF Network");
		methods.add("Support Vector Machine");
		methods.add("PNN/GRNN Network");
		
		this.setSize(640, 220);
		this.setTitle("Setup Encog Analyst Wizard");
		
		addProperty(this.rawFile = new FileField("source file","Source CSV File(*.csv)",true,false,EncogDocumentFrame.CSV_FILTER));
		addProperty(this.format = new ComboBoxField("format", "File Format", true, csvFormat));
		addProperty(this.method = new ComboBoxField("method", "Machine Learning", true, methods));
		addProperty(this.goal = new ComboBoxField("goal", "Goal", true, goalList));
		addProperty(this.targetField = new IntegerField("target field", "Target Field(-1 for auto)", true, -1, 1000));
		addProperty(this.headers = new CheckField("headers","CSV File Headers"));

		render();
		
		this.targetField.setValue(-1);
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
	
	public AnalystFileFormat getFormat() {
		int idx = this.format.getSelectedIndex(); 
		switch( idx ) {
			case 0:
				return AnalystFileFormat.DECPNT_COMMA;
			case 1:
				return AnalystFileFormat.DECPNT_SPACE;
			case 2:
				return AnalystFileFormat.DECPNT_SEMI;
			case 3:
				return AnalystFileFormat.DECCOMMA_SPACE;
			case 4:
				return AnalystFileFormat.DECCOMMA_SEMI;
			default:
				return null;
		}
	}
	
	public AnalystGoal getGoal() {
		int idx = this.goal.getSelectedIndex();
		switch(idx) {
			case 0:
				return AnalystGoal.Classification;
			case 1:
				return AnalystGoal.Classification;
			default:
				return null;
		}
	}
	
	public int getTargetField() {
		return this.targetField.getValue();
	}
}
