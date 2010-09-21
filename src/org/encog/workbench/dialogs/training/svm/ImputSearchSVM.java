/*
 * Encog(tm) Workbench v2.5
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
package org.encog.workbench.dialogs.training.svm;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class ImputSearchSVM  extends BasicTrainingInput {
	
	private final DoubleField beginningGamma;
	private final DoubleField endingGamma;
	private final DoubleField stepGamma;
	private final DoubleField beginningC;
	private final DoubleField endingC;
	private final DoubleField stepC;

	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public ImputSearchSVM(final Frame owner) {
		super(owner);
		setTitle("Support Vector Machine (SVM) Cross Validation");
		addProperty(this.beginningGamma = new DoubleField("gamma","Gamma Begin",true,-1,-1));
		addProperty(this.endingGamma = new DoubleField("gamma","Gamma End",true,-1,-1));
		addProperty(this.stepGamma = new DoubleField("gamma","Gamma Step",true,-1,-1));
		addProperty(this.beginningC = new DoubleField("gamma","C Begin",true,-1,-1));
		addProperty(this.endingC = new DoubleField("gamma","C End",true,-1,-1));
		addProperty(this.stepC = new DoubleField("gamma","C Step",true,-1,-1));
		render();	
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
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
	
	
	
}
