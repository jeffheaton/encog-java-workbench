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
package org.encog.workbench.dialogs;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class RandomizeNetworkDialog extends EncogPropertiesDialog  {
	private final DoubleField high;
	private final DoubleField low;
	private final DoubleField mean;
	private final DoubleField deviation;
	private final ComboBoxField theType;
	private final DoubleField perturbPercent;
	private final IntegerField seedValue;
	private final DoubleField constantValue;
	private final DoubleField constHigh;
	private final DoubleField constLow;
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;


	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public RandomizeNetworkDialog(final Frame owner) {
		
		super(owner);

		setTitle("Randomize Network");
		setSize(400,400);
		setLocation(200,200);
		
		List<String> types = new ArrayList<String>();
		types.add("Random");
		types.add("Nguyen-Widrow");
		types.add("Fan-In");
		
		this.setCollectCurrentTabOnly(true);
				
		this.beginTab("Randomize");
		addProperty(this.high = new DoubleField("high","High Range",true,0,-1));
		addProperty(this.low = new DoubleField("low","Low Range",true,0,-1));
		addProperty(this.theType = new ComboBoxField("type","Type",true,types));
		this.beginTab("Perturb");
		addProperty(this.perturbPercent = new DoubleField("perturb percent","Perturb Percent",true,0,-1));
		this.beginTab("Gaussian");
		addProperty(this.mean = new DoubleField("mean","Mean",true,0,-1));
		addProperty(this.deviation = new DoubleField("standard deviation","Standard Deviation",true,0,-1));
		this.beginTab("Consistent");
		addProperty(this.seedValue = new IntegerField("seed value","Seed Value",true,0,-1));
		addProperty(this.constHigh = new DoubleField("high","High Range",true,0,-1));
		addProperty(this.constLow = new DoubleField("low","Low Range",true,0,-1));		
		this.beginTab("Constant");
		addProperty(this.constantValue = new DoubleField("constant value","Constant Value",true,0,-1));
		render();
	}


	public DoubleField getHigh() {
		return high;
	}


	public DoubleField getLow() {
		return low;
	}


	public ComboBoxField getTheType() {
		return theType;
	}


	public DoubleField getPerturbPercent() {
		return perturbPercent;
	}


	public IntegerField getSeedValue() {
		return seedValue;
	}


	public DoubleField getConstantValue() {
		return constantValue;
	}


	public DoubleField getConstHigh() {
		return constHigh;
	}


	public DoubleField getConstLow() {
		return constLow;
	}


	public DoubleField getMean() {
		return mean;
	}


	public DoubleField getDeviation() {
		return deviation;
	}

	

	


}
