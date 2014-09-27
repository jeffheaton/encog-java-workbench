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

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.BuildingListListener;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;

public class CreateFeedforward extends EncogPropertiesDialog implements
		BuildingListListener,PopupListener {

	private IntegerField inputCount;
	private IntegerField outputCount;
	private BuildingListField hidden;
	private PopupField activationFieldHidden;
	private PopupField activationFieldOutput;
	private ActivationFunction activationFunctionHidden;
	private ActivationFunction activationFunctionOutput;
	

	public CreateFeedforward(Frame owner) {
		super(owner);
		setTitle("Create Feedforward Network");
		setSize(600, 400);
		setLocation(200, 200);
		addProperty(this.inputCount = new IntegerField("input neurons",
				"Input Neuron Count", true, 1, 100000));
		addProperty(this.hidden = new BuildingListField("hidden neurons",
				"Hidden Layer Counts"));
		addProperty(this.outputCount = new IntegerField("output neurons",
				"Output Neuron Count", true, 1, 100000));
		addProperty(this.activationFieldHidden = new PopupField("activation hidden",
				"Activation Function Hidden", true));
		addProperty(this.activationFieldOutput = new PopupField("activation output",
				"Activation Function Output", true));
		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getOutputCount() {
		return outputCount;
	}

	private String askNeurons(int layer) {
		String str = EncogWorkBench
				.displayInput("How many neurons for hidden layer " + layer
						+ "?");
		if (str == null)
			return null;
		else
		{
			try
			{
				str = "Hidden Layer " + layer + ": " + Integer.parseInt(str.trim()) + " neurons";	
			}
			catch(NumberFormatException e)
			{
				EncogWorkBench.displayError("Error", "Enter a valid number.");
				return null;
			}
		}
			

		return str;
	}

	public void add(BuildingListField list, int index) {

		String str = askNeurons(list.getModel().size() + 1);
		if (str != null) {
			list.getModel().addElement(str);
		}

	}

	public void del(BuildingListField list, int index) {
		if (index != -1) {
			list.getModel().remove(index);
		}
	}

	public void edit(BuildingListField list, int index) {
		if (index != -1) {
			String str = askNeurons(index+1);
			if (str != null) {
				list.getModel().remove(index);
				list.getModel().add(index, str);
			}
		}
	}

	public BuildingListField getHidden() {
		return hidden;
	}

	public String popup(PopupField field) {
		ActivationDialog dialog = new ActivationDialog(EncogWorkBench.getInstance().getMainWindow());
		
		if( field==this.activationFieldHidden )
		{			
			dialog.setActivation(this.activationFunctionHidden);
		} else if( field==this.activationFieldOutput ) 
		{
			dialog.setActivation(this.activationFunctionOutput);
		}
				
		if( !dialog.process()  )
			return null;
		else if( field==this.activationFieldHidden )
		{			
			this.activationFunctionHidden = dialog.getActivation();			
			return dialog.getActivation().getClass().getSimpleName();
		} else if( field==this.activationFieldOutput )
		{
			this.activationFunctionOutput = dialog.getActivation();
			return dialog.getActivation().getClass().getSimpleName();
		}
		else
			return null;
	}

	public PopupField getActivationHiddenField() {
		return activationFieldHidden;
	}
	

	public ActivationFunction getActivationFunctionHidden() {
		return activationFunctionHidden;
	}

	public void setActivationFunctionHidden(ActivationFunction activationFunction) {
		this.activationFunctionHidden = activationFunction;
		this.activationFieldHidden.setValue(this.activationFunctionHidden.getClass().getSimpleName());
	}

	public ActivationFunction getActivationFunctionOutput() {
		return activationFunctionOutput;
	}

	public void setActivationFunctionOutput(ActivationFunction activationFunction) {
		this.activationFunctionOutput = activationFunction;
		this.activationFieldOutput.setValue(this.activationFunctionOutput.getClass().getSimpleName());
	}
}
