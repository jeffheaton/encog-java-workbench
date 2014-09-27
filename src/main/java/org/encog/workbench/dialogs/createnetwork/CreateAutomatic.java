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
import java.util.List;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.ml.data.MLDataSet;
import org.encog.util.simple.EncogUtility;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.BuildingListListener;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;
import org.encog.workbench.frames.document.tree.ProjectTraining;

public class CreateAutomatic extends EncogPropertiesDialog implements
		BuildingListListener, PopupListener {

	private final ComboBoxField comboTraining;
	private final BuildingListField hidden;
	private final PopupField activationField;
	private final IntegerField iterations;
	private final IntegerField weightTries;
	private final IntegerField windowSize;

	private ActivationFunction activationFunction;

	/**
	 * All available training sets to display in the combo box.
	 */
	private List<ProjectTraining> trainingSets;

	public CreateAutomatic(Frame owner) {
		super(owner);
		setTitle("Create Network from Training");
		setSize(600, 400);
		setLocation(200, 200);
		findData();

		addProperty(this.comboTraining = new ComboBoxField("training set",
				"Training Set", true, this.trainingSets));
		addProperty(this.hidden = new BuildingListField("hidden neurons",
				"Hidden Layer Counts"));
		addProperty(this.activationField = new PopupField("activation",
				"Activation Function", true));
		addProperty(this.iterations = new IntegerField("iterations",
				"Training Iterations", true, 1, 100000));
		addProperty(this.weightTries = new IntegerField("weight tries",
				"Weights to Try", true, 1, 100000));
		addProperty(this.windowSize = new IntegerField("window size",
				"Number of Top Networks", true, 1, 100000));
		render();
	}

	/**
	 * Obtain the data needed to fill in the network and training set combo
	 * boxes.
	 */
	private void findData() {
		this.trainingSets = EncogWorkBench.getInstance().getTrainingData();
	}

	private String askNeurons(int layer) {

		NeuronRangeDialog dialog = new NeuronRangeDialog(EncogWorkBench
				.getInstance().getMainWindow());

		if (dialog.process()) {
			int high = dialog.getHigh().getValue();
			int low = dialog.getLow().getValue();

			if (low > high) {
				int temp = low;
				high = low;
				low = temp;
			}

			String str = "Hidden Layer " + layer + ": high=" + high + ",low="
					+ low + ".";
			return str;
		}

		return null;

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
			String str = askNeurons(index + 1);
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
		ActivationDialog dialog = new ActivationDialog(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.setActivation(this.activationFunction);
		if (!dialog.process())
			return null;
		else {
			this.activationFunction = dialog.getActivation();
			return dialog.getActivation().getClass().getSimpleName();
		}
	}

	public PopupField getActivationField() {
		return activationField;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
		this.activationField.setValue(this.activationFunction.getClass()
				.getSimpleName());
	}

	public IntegerField getIterations() {
		return iterations;
	}

	/**
	 * @return The training set that the user chose.
	 */
	public MLDataSet getTraining() {
		ProjectTraining training = (ProjectTraining) this.comboTraining.getSelectedValue();
		return EncogUtility.loadEGB2Memory(training.getFile());
	}

	public IntegerField getWeightTries() {
		return weightTries;
	}

	public IntegerField getWindowSize() {
		return windowSize;
	}
	
	

}
