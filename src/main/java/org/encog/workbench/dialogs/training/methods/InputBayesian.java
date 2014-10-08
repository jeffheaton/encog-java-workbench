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
package org.encog.workbench.dialogs.training.methods;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class InputBayesian extends EncogPropertiesDialog {

	private final ComboBoxField initOptions;
	private final ComboBoxField searchMethod;
	private final ComboBoxField estimateMethod;
	private final IntegerField maxParents;
	
	
	public InputBayesian() {
		super(EncogWorkBench.getInstance().getMainWindow());
		
		List<String> initOptions = new ArrayList<String>();
		initOptions.add("No Change");
		initOptions.add("Empty");
		initOptions.add("Naive Bayes");
		List<String> searchOptions = new ArrayList<String>();
		searchOptions.add("None");
		searchOptions.add("K2");
		List<String> estimationOptions = new ArrayList<String>();
		estimationOptions.add("None");
		estimationOptions.add("Simple");
		
		setTitle("Train Bayesian");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.initOptions = new ComboBoxField("init method","Init Method",true,initOptions));
		addProperty(this.searchMethod = new ComboBoxField("search method","Search Method",true,searchOptions));
		addProperty(this.estimateMethod = new ComboBoxField("estimate method","Probability Estimate",true,estimationOptions));
		addProperty(this.maxParents = new IntegerField("max parents","Maximum Parents",true,1,10000));
		
		render();
		this.maxParents.setValue(1);
		((JComboBox)this.searchMethod.getField()).setSelectedIndex(1);
		((JComboBox)this.estimateMethod.getField()).setSelectedIndex(1);
		((JComboBox)this.initOptions.getField()).setSelectedIndex(2);
	}

	/**
	 * @return the searchMethod
	 */
	public ComboBoxField getSearchMethod() {
		return searchMethod;
	}

	/**
	 * @return the estimateMethod
	 */
	public ComboBoxField getEstimateMethod() {
		return estimateMethod;
	}

	/**
	 * @return the maxParents
	 */
	public IntegerField getMaxParents() {
		return maxParents;
	}

	/**
	 * @return the initOptions
	 */
	public ComboBoxField getInitOptions() {
		return initOptions;
	}



	

}
