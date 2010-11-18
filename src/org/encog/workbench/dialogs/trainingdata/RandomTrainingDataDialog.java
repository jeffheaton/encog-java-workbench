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
package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;


public class RandomTrainingDataDialog extends EncogPropertiesDialog {
	
	private IntegerField elements;
	private IntegerField input;
	private IntegerField ideal;
	private DoubleField low;
	private DoubleField high;
	
	public RandomTrainingDataDialog(Frame owner) {
		super(owner);
		setTitle("Random Training Data");
		setSize(400,200);
		addProperty(this.elements = new IntegerField("elements","Training Set Elements",true, 0, 1000000));
		addProperty(this.input = new IntegerField("input","Input Field Count",true, 1, 1000));
		addProperty(this.ideal = new IntegerField("ideal","Output Field Count",true, 0, 1000));
		addProperty(this.low = new DoubleField("low","Random Low Value",true, 0, -1));
		addProperty(this.high = new DoubleField("high","Random High Value",true, 0, -1));
		render();
	}

	public IntegerField getElements() {
		return elements;
	}

	public IntegerField getInput() {
		return input;
	}

	public IntegerField getIdeal() {
		return ideal;
	}

	public DoubleField getLow() {
		return low;
	}

	public DoubleField getHigh() {
		return high;
	}
	
	

}
