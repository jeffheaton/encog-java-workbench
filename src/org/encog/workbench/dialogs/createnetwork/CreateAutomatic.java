/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.data.NeuralDataSet;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.BuildingListListener;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;

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
	private final List<String> trainingSets = new ArrayList<String>();

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
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_TRAINING)) {
				this.trainingSets.add(obj.getName());
			}
		}
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
	public NeuralDataSet getTraining() {
		String trainingName = (String) this.comboTraining.getSelectedValue();
		return (NeuralDataSet) EncogWorkBench.getInstance().getCurrentFile()
				.find(trainingName);
	}

	public IntegerField getWeightTries() {
		return weightTries;
	}

	public IntegerField getWindowSize() {
		return windowSize;
	}
	
	

}
