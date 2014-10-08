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

import org.encog.neural.pnn.PNNKernelType;
import org.encog.neural.pnn.PNNOutputMode;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class CreatePNN extends EncogPropertiesDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1916684369370397010L;
	
	private IntegerField inputCount;
	private IntegerField outputCount;
	private ComboBoxField kernelType;
	private ComboBoxField outputModel;
	
	public CreatePNN() {
		super(EncogWorkBench.getInstance().getMainWindow());
		List<String> outputModelList = new ArrayList<String>();
		outputModelList.add("Regression");
		outputModelList.add("Classification");
		outputModelList.add("Unsupercised");
		
		List<String> kernelTypeList = new ArrayList<String>();
		kernelTypeList.add("Gaussian");
		kernelTypeList.add("Reciprocal");		
		
		setTitle("Create GRNN/PNN Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.inputCount = new IntegerField("input neurons","Input Neuron Count",true,1,100000));
		addProperty(this.outputCount = new IntegerField("input neurons","Output Neuron Count",true,1,100000));
		addProperty(this.kernelType = new ComboBoxField("kernel type","Kernel Type",true,kernelTypeList));
		addProperty(this.outputModel = new ComboBoxField("output model","Output Model",true,outputModelList));
		
		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getOutputCount() {
		return outputCount;
	}	
	
	public PNNKernelType getKernelType() {
		switch( this.kernelType.getSelectedIndex()) {
			case 0:
				return PNNKernelType.Gaussian;
			case 1:
				return PNNKernelType.Reciprocal;
			default:
				return null;
		}
	}
	
	public PNNOutputMode getOutputModel() {
		switch( this.kernelType.getSelectedIndex()) {
			case 0:
				return PNNOutputMode.Regression;
			case 1:
				return PNNOutputMode.Classification;
			case 2:
				return PNNOutputMode.Unsupervised;				
			default:
				return null;
		}
	}

}
