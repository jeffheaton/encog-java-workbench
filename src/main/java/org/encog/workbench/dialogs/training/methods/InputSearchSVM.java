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

import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.training.DialogMaxError;

public class InputSearchSVM  extends DialogMaxError {
	
	private final DoubleField beginningGamma;
	private final DoubleField endingGamma;
	private final DoubleField stepGamma;
	private final DoubleField beginningC;
	private final DoubleField endingC;
	private final DoubleField stepC;
	private final IntegerField threadCount;

	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputSearchSVM() {
		super(false);
		setSize(400,400);
		setTitle("Support Vector Machine (SVM) Cross Validation");
		addProperty(this.beginningGamma = new DoubleField("gamma begin","Gamma Begin",true,-1,-1));
		addProperty(this.endingGamma = new DoubleField("gamma end","Gamma End",true,-1,-1));
		addProperty(this.stepGamma = new DoubleField("gamma step","Gamma Step",true,-1,-1));
		addProperty(this.beginningC = new DoubleField("c begin","C Begin",true,-1,-1));
		addProperty(this.endingC = new DoubleField("c end","C End",true,-1,-1));
		addProperty(this.stepC = new DoubleField("c step","C Step",true,-1,-1));
		addProperty(this.threadCount = new IntegerField("thread count","Threads",true,1,20));
		render();	
		this.threadCount.setValue(1);
	}

	public DoubleField getBeginningGamma() {
		return beginningGamma;
	}

	public DoubleField getEndingGamma() {
		return endingGamma;
	}

	public DoubleField getStepGamma() {
		return stepGamma;
	}

	public DoubleField getBeginningC() {
		return beginningC;
	}

	public DoubleField getEndingC() {
		return endingC;
	}

	public DoubleField getStepC() {
		return stepC;
	}

	/**
	 * @return the threadCount
	 */
	public IntegerField getThreadCount() {
		return threadCount;
	}
}
