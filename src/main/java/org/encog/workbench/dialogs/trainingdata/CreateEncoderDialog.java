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
package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;


public class CreateEncoderDialog extends EncogPropertiesDialog {
	private IntegerField inputOutputCount;
	private CheckField complement;
	private DoubleField inputLow;
	private DoubleField inputHigh;
	private DoubleField idealLow;
	private DoubleField idealHigh;
	
	public CreateEncoderDialog(Frame owner) {
		super(owner);
		setTitle("Encoder Training Data");
		setSize(400,200);
		addProperty(this.inputOutputCount = new IntegerField("input output count","Input/Output Count",true,2,1000));
		addProperty(this.complement = new CheckField("complement","Complement"));
		addProperty(this.inputLow = new DoubleField("input low","Input Low Value",true, 0, -1));
		addProperty(this.inputHigh = new DoubleField("input high","Input High Value",true, 0, -1));
		addProperty(this.idealLow = new DoubleField("ideal low","Ideal Low Value",true, 0, -1));
		addProperty(this.idealHigh = new DoubleField("ideal high","Ideal High Value",true, 0, -1));
		render();
	}

	/**
	 * @return the complement
	 */
	public CheckField getComplement() {
		return complement;
	}

	/**
	 * @return the inputLow
	 */
	public DoubleField getInputLow() {
		return inputLow;
	}

	/**
	 * @return the inputHigh
	 */
	public DoubleField getInputHigh() {
		return inputHigh;
	}

	/**
	 * @return the idealLow
	 */
	public DoubleField getIdealLow() {
		return idealLow;
	}

	/**
	 * @return the idealHigh
	 */
	public DoubleField getIdealHigh() {
		return idealHigh;
	}

	/**
	 * @return the inputOutputCount
	 */
	public IntegerField getInputOutputCount() {
		return inputOutputCount;
	}

	

}
