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
package org.encog.workbench.dialogs.wizard.analyst;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import org.encog.app.analyst.AnalystFileFormat;
import org.encog.app.analyst.AnalystGoal;
import org.encog.app.analyst.wizard.NormalizeRange;
import org.encog.app.analyst.wizard.WizardMethodType;
import org.encog.app.generate.TargetLanguage;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.FileField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;
import org.encog.workbench.frames.document.EncogDocumentFrame;

public class AnalystWizardDialog extends EncogPropertiesDialog {
	
	private final FileField rawFile;
	private final ComboBoxField method;
	private final ComboBoxField format;
	private final ComboBoxField range;
	private final ComboBoxField goal;
	private final ComboBoxField missing;
	private final TextField targetField;
	private final CheckField headers;
	private final IntegerField lagCount;
	private final IntegerField leadCount;
	private final CheckField includeTarget;
	private final CheckField normalize;
	private final CheckField segregate;
	private final CheckField randomize;
	private final CheckField balance;
	private final CheckField cluster;
	private final DoubleField maxError;
	private final ComboBoxField generationTarget;
	private final CheckField embedData;
	
	/**
	 * @return the cluster
	 */
	public CheckField getCluster() {
		return cluster;
	}

	private final List<String> methods = new ArrayList<String>();
	
	public AnalystWizardDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		
		List<String> list = new ArrayList<String>();
		list.add("CSV");
		
		List<String> csvFormat = new ArrayList<String>();
		csvFormat.add("Decimal Point (USA/English) & Comma Separator");
		csvFormat.add("Decimal Point (USA/English) & Space Separator");
		csvFormat.add("Decimal Point (USA/English) & Semicolon Separator");
		csvFormat.add("Decimal Comma (Non-USA/English) & Space Separator");
		csvFormat.add("Decimal Comma (Non-USA/English) & Semicolon Separator");
		
		List<String> goalList = new ArrayList<String>();
		goalList.add("Classification");
		goalList.add("Regression");
		
		List<String> rangeList = new ArrayList<String>();
		rangeList.add("-1 to 1");
		rangeList.add("0 to 1");
		
		List<String> missingList = new ArrayList<String>();
		missingList.add("DiscardMissing");
		missingList.add("MeanAndModeMissing");
		missingList.add("NegateMissing");
		
		List<String> targetLanguages = new ArrayList<String>();
		targetLanguages.add("No Generation");				
		targetLanguages.add("Java");
		targetLanguages.add("JavaScript");
		targetLanguages.add("CSharp");
		targetLanguages.add("MQL4");
		targetLanguages.add("NinjaScript");

		methods.add("Bayesian Network");
		methods.add("Encog Program (GP)");
		methods.add("Feedforward Network");
		methods.add("NEAT Network");
		methods.add("RBF Network");
		methods.add("PNN/GRNN Network");
		methods.add("Self Organizing Map (SOM)");
		methods.add("Support Vector Machine");		
				
		this.setSize(640, 360);
		this.setTitle("Setup Encog Analyst Wizard");
		
		beginTab("General");
		addProperty(this.rawFile = new FileField("source file","Source CSV File(*.csv)",true,false,EncogDocumentFrame.CSV_FILTER));
		addProperty(this.format = new ComboBoxField("format", "File Format", true, csvFormat));
		addProperty(this.method = new ComboBoxField("method", "Machine Learning", true, methods));
		addProperty(this.goal = new ComboBoxField("goal", "Goal", true, goalList));
		addProperty(this.targetField = new TextField("target field", "Target Field(blank for auto)", false));
		addProperty(this.headers = new CheckField("headers","CSV File Headers"));
		addProperty(this.range = new ComboBoxField("normalization range", "Normalization Range", true, rangeList));
		addProperty(this.missing = new ComboBoxField("missing values", "Missing Values", true, missingList));
		addProperty(this.maxError = new DoubleField("max error",
				"Maximum Error Percent(0-100)", true, 0, 100));
		beginTab("Time Series");
		addProperty(this.lagCount = new IntegerField("lag count","Lag Count",true,0,1000));
		addProperty(this.leadCount = new IntegerField("lead count","Lead Count",true,0,1000));
		addProperty(this.includeTarget = new CheckField("include target","Include Target in Input"));
		beginTab("Tasks");
		addProperty(this.normalize = new CheckField("normalize","Normalize"));
		addProperty(this.randomize = new CheckField("randomize","Randomize"));
		addProperty(this.segregate = new CheckField("segregate","Segregate"));
		addProperty(this.balance = new CheckField("balance","Balance"));
		addProperty(this.cluster = new CheckField("cluster","Cluster"));
		beginTab("Code Generation");
		addProperty(this.generationTarget = new ComboBoxField("language", "Generation Language", true, targetLanguages));
		addProperty(this.embedData = new CheckField("embed","Embed Data"));
		
		render();
		
		this.lagCount.setValue(0);
		this.leadCount.setValue(0);
		
		this.randomize.setValue(true);
		this.segregate.setValue(true);
		this.normalize.setValue(true);
		this.balance.setValue(false);
		this.cluster.setValue(true);
		((JComboBox)this.method.getField()).setSelectedIndex(2);
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
	
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
				return WizardMethodType.BayesianNetwork;
			case 1:
				return WizardMethodType.EPL;
			case 2:
				return WizardMethodType.FeedForward;
			case 3:
				return WizardMethodType.NEAT;
			case 4:
				return WizardMethodType.RBF;
			case 5:
				return WizardMethodType.PNN;
			case 6:
				return WizardMethodType.SOM;
			case 7:
				return WizardMethodType.SVM;
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
				return AnalystGoal.Regression;
			default:
				return null;
		}
	}
	
	public NormalizeRange getRange() {
		int idx = this.range.getSelectedIndex();
		switch(idx) {
			case 0:
				return NormalizeRange.NegOne2One;
			case 1:
				return NormalizeRange.Zero2One;
			default:
				return null;
		}
	}
	
	public String getTargetField() {
		return this.targetField.getValue();
	}

	/**
	 * @return the lagCount
	 */
	public IntegerField getLagCount() {
		return lagCount;
	}

	/**
	 * @return the leadCount
	 */
	public IntegerField getLeadCount() {
		return leadCount;
	}

	/**
	 * @return the includeTarget
	 */
	public CheckField getIncludeTarget() {
		return includeTarget;
	}

	/**
	 * @return the segregate
	 */
	public CheckField getSegregate() {
		return segregate;
	}

	/**
	 * @return the randomize
	 */
	public CheckField getRandomize() {
		return randomize;
	}

	/**
	 * @return the normalize
	 */
	public CheckField getNormalize() {
		return normalize;
	}

	/**
	 * @return the balance
	 */
	public CheckField getBalance() {
		return balance;
	}

	/**
	 * @return the missing
	 */
	public ComboBoxField getMissing() {
		return missing;
	}	
	
	public DoubleField getMaxError() {
		return this.maxError;
	}

	public ComboBoxField getGenerationTarget() {
		return generationTarget;
	}

	public CheckField getEmbedData() {
		return embedData;
	}
	
	public TargetLanguage getGenerationTargetLanguage() {
		switch(this.generationTarget.getSelectedIndex()) {
			case 0:
				return TargetLanguage.NoGeneration;
			case 1:
				return TargetLanguage.Java;
			case 2:
				return TargetLanguage.JavaScript;
			case 3:
				return TargetLanguage.CSharp;
			case 4:
				return TargetLanguage.MQL4;
			case 5:
				return TargetLanguage.NinjaScript;
			default:
				return TargetLanguage.NoGeneration;
		}
	}
	
	
}
