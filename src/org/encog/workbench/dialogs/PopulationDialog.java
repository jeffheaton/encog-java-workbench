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
package org.encog.workbench.dialogs;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class PopulationDialog extends EncogPropertiesDialog {

	private final IntegerField populationSize;
	private final IntegerField inputNeurons;
	private final IntegerField outputNeurons;
	
	public PopulationDialog(Frame owner) {
		super(owner);
		this.setSize(320, 200);
		this.setTitle("Create NEAT Population");
		
		addProperty(this.populationSize = new IntegerField("population size","Population Size",true,1,-1));
		addProperty(this.inputNeurons = new IntegerField("input size","Input Neurons",true,1,-1));
		addProperty(this.outputNeurons = new IntegerField("output size","output Neurons",true,1,-1));

		render();
	}

	public IntegerField getPopulationSize() {
		return populationSize;
	}

	public IntegerField getInputNeurons() {
		return inputNeurons;
	}

	public IntegerField getOutputNeurons() {
		return outputNeurons;
	}
	
	

}
