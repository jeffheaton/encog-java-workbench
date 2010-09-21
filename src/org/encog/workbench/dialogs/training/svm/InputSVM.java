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
import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class InputSVM  extends NetworkAndTrainingDialog {
	
	private final DoubleField gamma;
	private final DoubleField c;
	
	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputSVM(final Frame owner) {
		super(owner);
		setTitle("Support Vector Machine (SVM)");
		addProperty(this.gamma = new DoubleField("gamma","Gamma (0 for 1/#input)",true,-1,-1));
		addProperty(this.c = new DoubleField("c","C",true,-1,-1));

		render();	
	}

	public DoubleField getGamma() {
		return gamma;
	}

	public DoubleField getC() {
		return c;
	}
	
	
	
}
