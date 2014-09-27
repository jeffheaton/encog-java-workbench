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

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class InputAndIdealDialog extends EncogPropertiesDialog {

	private final IntegerField inputCount;
	private final IntegerField idealCount;
	
	public InputAndIdealDialog(Frame owner) {
		super(owner);

		setTitle("Create Training");
		setSize(500,150);
		addProperty(this.inputCount = new IntegerField("input count","Input Count",true,1,10000));
		addProperty(this.idealCount = new IntegerField("ideal count","Ideal Count (0=unsupervised)",true,0,10000));
		render();		
	}

	/**
	 * @return the inputCount
	 */
	public IntegerField getInputCount() {
		return inputCount;
	}

	/**
	 * @return the idealCount
	 */
	public IntegerField getIdealCount() {
		return idealCount;
	}
	
	

}
