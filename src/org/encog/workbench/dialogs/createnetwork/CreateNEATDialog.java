/*
 * Encog(tm) Workbench v2.4
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

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationSigmoid;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.BuildingListListener;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;

public class CreateNEATDialog extends EncogPropertiesDialog implements
		BuildingListListener, PopupListener {

	private IntegerField inputCount;
	private IntegerField outputCount;
	private PopupField outputActivationField;
	private PopupField neatActivationField;
	private ActivationFunction outputActivationFunction;
	private ActivationFunction neatActivationFunction;

	public CreateNEATDialog(Frame owner) {
		super(owner);
		setTitle("Create Feedforward Network");
		setSize(600, 400);
		setLocation(200, 200);
		addProperty(this.inputCount = new IntegerField("input neurons",
				"Input Neuron Count", true, 1, -1));
		addProperty(this.outputCount = new IntegerField("output neurons",
				"Output Neuron Count", true, 1, -1));
		addProperty(this.outputActivationField = new PopupField("activation",
				"Activation Function", true));
		addProperty(this.neatActivationField = new PopupField("activation",
				"Activation Function", true));
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
		else {
			try {
				str = "Hidden Layer " + layer + ": "
						+ Integer.parseInt(str.trim()) + " neurons";
			} catch (NumberFormatException e) {
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
			String str = askNeurons(index + 1);
			if (str != null) {
				list.getModel().remove(index);
				list.getModel().add(index, str);
			}
		}
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

	public void setNeatActivationFunction(ActivationSigmoid activationFunction) {
		this.neatActivationFunction = activationFunction;
		this.neatActivationField.setValue(activationFunction.getClass()
				.getSimpleName());
		
	}

}
