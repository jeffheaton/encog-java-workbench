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

import javax.swing.JComboBox;

import org.encog.ml.svm.KernelType;
import org.encog.ml.svm.SVMType;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class CreateSVMDialog extends EncogPropertiesDialog {

	private final IntegerField inputCount;
	private final IntegerField outputCount;

	private final List<String> svmTypeNames;
	private final List<String> kernelTypeNames;

	private final ComboBoxField svmType;
	private final ComboBoxField kernelType;

	public CreateSVMDialog(Frame owner) {
		super(owner);

		svmTypeNames = new ArrayList<String>();
		kernelTypeNames = new ArrayList<String>();

		svmTypeNames.add("Support Vector (Classification)");
		svmTypeNames.add("New Support Vector (Classification)");
		svmTypeNames.add("Support Vector (One Class)");
		svmTypeNames.add("Epsilon Support Vector (Regression)");
		svmTypeNames.add("New Support Vector (Regression)");

		kernelTypeNames.add("Linear");
		kernelTypeNames.add("Poly");
		kernelTypeNames.add("RadialBasisFunction");
		kernelTypeNames.add("Sigmoid");
		kernelTypeNames.add("Precomputed");

		setTitle("Create SVM Network");
		setSize(400, 400);
		setLocation(200, 200);
		addProperty(this.inputCount = new IntegerField("input-count",
				"Input Count", true, 1, 100000));
		addProperty(this.outputCount = new IntegerField("output-count",
				"Output Count", true, 1, 100000));
		addProperty(this.svmType = new ComboBoxField("SVM type", "SVM Type",
				true, svmTypeNames));
		addProperty(this.kernelType = new ComboBoxField("Kernel type",
				"Kernel Type", true, kernelTypeNames));

		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getOutputCount() {
		return outputCount;
	}

	public SVMType getSVMType() {
		switch (((JComboBox) this.svmType.getField()).getSelectedIndex()) {
		case 0:
			return SVMType.SupportVectorClassification;
		case 1:
			return SVMType.NewSupportVectorClassification;
		case 2:
			return SVMType.SupportVectorOneClass;
		case 3:
			return SVMType.EpsilonSupportVectorRegression;
		case 4:
			return SVMType.NewSupportVectorRegression;
		default:
			return null;
		}
	}

	public void setSVMType(SVMType type) {
		JComboBox combo = (JComboBox) this.svmType.getField();

		switch (type) {
		case SupportVectorClassification:
			combo.setSelectedIndex(0);
			break;
		case NewSupportVectorClassification:
			combo.setSelectedIndex(1);
			break;
		case SupportVectorOneClass:
			combo.setSelectedIndex(2);
			break;
		case EpsilonSupportVectorRegression:
			combo.setSelectedIndex(3);
			break;
		case NewSupportVectorRegression:
			combo.setSelectedIndex(4);
			break;
		}
	}

	public KernelType getKernelType() {
		switch (((JComboBox) this.kernelType.getField()).getSelectedIndex()) {
		case 0:
			return KernelType.Linear;
		case 1:
			return KernelType.Poly;
		case 2:
			return KernelType.RadialBasisFunction;
		case 3:
			return KernelType.Sigmoid;
		case 4:
			return KernelType.Precomputed;
		default:
			return null;
		}
	}

	public void setKernelType(KernelType type) {
		JComboBox combo = (JComboBox) this.kernelType.getField();

		switch (type) {
		case Linear:
			combo.setSelectedIndex(0);
			break;
		case Poly:
			combo.setSelectedIndex(1);
			break;
		case RadialBasisFunction:
			combo.setSelectedIndex(2);
			break;
		case Sigmoid:
			combo.setSelectedIndex(3);
			break;
		case Precomputed:
			combo.setSelectedIndex(4);
			break;
		}

	}

}
