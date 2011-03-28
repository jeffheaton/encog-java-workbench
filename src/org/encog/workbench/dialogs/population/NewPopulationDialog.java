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
package org.encog.workbench.dialogs.population;

import java.awt.Frame;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;

public class NewPopulationDialog extends EncogPropertiesDialog implements PopupListener {

	private final IntegerField populationSize;
	private final IntegerField inputNeurons;
	private final IntegerField outputNeurons;
	private PopupField outputActivationField;
	private PopupField neatActivationField;
	private ActivationFunction outputActivationFunction;
	private ActivationFunction neatActivationFunction;
	private CheckField snapshot;
	
	public NewPopulationDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		this.setSize(400, 200);
		this.setTitle("Create NEAT Population");
		
		addProperty(this.populationSize = new IntegerField("population size","Population Size",true,1,-1));
		addProperty(this.inputNeurons = new IntegerField("input size","Input Neurons",true,1,-1));
		addProperty(this.outputNeurons = new IntegerField("output size","output Neurons",true,1,-1));
		addProperty(this.snapshot = new CheckField("snapshot","Snapshot"));
		
		addProperty(this.outputActivationField = new PopupField("output activation",
				"Output Activation Function", true));
		addProperty(this.neatActivationField = new PopupField("NEAT activation",
				"NEAT Activation Function", true));


		render();
		this.setNeatActivationFunction(new ActivationSigmoid());
		this.setOutputActivationFunction(new ActivationLinear());
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
	
	public String popup(PopupField field) {
		if (field == this.outputActivationField) {
			ActivationDialog dialog = new ActivationDialog(EncogWorkBench
					.getInstance().getMainWindow());
			dialog.setActivation(this.outputActivationFunction);
			if (!dialog.process())
				return null;
			else {
				this.outputActivationFunction = dialog.getActivation();
				return dialog.getActivation().getClass().getSimpleName();
			}
		} else if (field == this.neatActivationField) {
			ActivationDialog dialog = new ActivationDialog(EncogWorkBench
					.getInstance().getMainWindow());
			dialog.setActivation(this.neatActivationFunction);
			if (!dialog.process())
				return null;
			else {
				this.neatActivationFunction = dialog.getActivation();
				return dialog.getActivation().getClass().getSimpleName();
			}
		} else
			return null;
	}

	public PopupField getOutputActivationField() {
		return outputActivationField;
	}

	public PopupField getNeatActivationField() {
		return neatActivationField;
	}

	public void setOutputActivationFunction(
			ActivationFunction activationFunction) {
		this.outputActivationFunction = activationFunction;
		this.outputActivationField.setValue(activationFunction.getClass()
				.getSimpleName());
	}

	public ActivationFunction getOutputActivationFunction() {
		return outputActivationFunction;
	}

	public ActivationFunction getNeatActivationFunction() {
		return neatActivationFunction;
	}

	public void setNeatActivationFunction(ActivationFunction activationFunction) {
		this.neatActivationFunction = activationFunction;
		this.neatActivationField.setValue(activationFunction.getClass()
				.getSimpleName());
		
	}

	/**
	 * @return the snapshot
	 */
	public CheckField getSnapshot() {
		return snapshot;
	}

	/**
	 * @param snapshot the snapshot to set
	 */
	public void setSnapshot(CheckField snapshot) {
		this.snapshot = snapshot;
	}
}
