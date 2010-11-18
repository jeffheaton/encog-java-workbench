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
package org.encog.workbench.dialogs.training.outstar;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

/**
 * A dialog box that inputs for the parameters to use with
 * the adaline training method.
 * @author jheaton
 *
 */
public class InputOutstar extends BasicTrainingInput {


	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private DoubleField learningRate;


	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputOutstar(final Frame owner) {
		super(owner);
		setTitle("Train Outstar");

		addProperty(this.learningRate = new DoubleField("learning rate","Learning Rate",true,-1,-1));

		render();
		this.learningRate.setValue(0.7);

		this.getMaxError().setValue(0.01);
	}


	public DoubleField getLearningRate() {
		return learningRate;
	}
}
