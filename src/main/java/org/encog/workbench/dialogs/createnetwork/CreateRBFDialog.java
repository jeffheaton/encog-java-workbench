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
package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.mathutil.rbf.GaussianFunction;
import org.encog.mathutil.rbf.InverseMultiquadricFunction;
import org.encog.mathutil.rbf.MexicanHatFunction;
import org.encog.mathutil.rbf.MultiquadricFunction;
import org.encog.mathutil.rbf.RBFEnum;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.PropertyType;

public class CreateRBFDialog extends EncogPropertiesDialog {

	private IntegerField inputCount;
	private IntegerField hiddenCount;
	private IntegerField outputCount;
	private ComboBoxField rbfTypeCombo;
	
	public CreateRBFDialog(Frame owner) {
		super(owner);
		List<String> rbfTypes = new ArrayList<String>();
		rbfTypes.add(GaussianFunction.class.getSimpleName());
		rbfTypes.add(MultiquadricFunction.class.getSimpleName());
		rbfTypes.add(InverseMultiquadricFunction.class.getSimpleName());
		rbfTypes.add(MexicanHatFunction.class.getSimpleName());
				
		setTitle("Create RBF Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.inputCount = new IntegerField("input neurons","Input Neuron Count",true,1,100000));
		addProperty(this.hiddenCount = new IntegerField("input neurons","Hidden Neuron Count",true,1,100000));
		addProperty(this.outputCount = new IntegerField("input neurons","Output Neuron Count",true,1,100000));
		addProperty(this.rbfTypeCombo = new ComboBoxField("rbf type","RBF Type",true,rbfTypes));
		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getHiddenCount() {
		return hiddenCount;
	}

	public IntegerField getOutputCount() {
		return outputCount;
	}
	

	public RBFEnum getRBFType() {
		switch(this.rbfTypeCombo.getSelectedIndex()) {
			case 0:
				return RBFEnum.Gaussian;
			case 1:
				return RBFEnum.Multiquadric;
			case 2:
				return RBFEnum.InverseMultiquadric;
			case 3:
				return RBFEnum.MexicanHat;
			default:
				return null;
		}
	}

}
