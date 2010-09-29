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
package org.encog.workbench.dialogs.layers;

import java.awt.Frame;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.CheckListener;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;
import org.encog.workbench.dialogs.common.TableField;

public class EditContextLayer extends EditLayerDialog implements
		PopupListener, CheckListener {

	public final static String[] COLUMN_HEADS = { "Neuron", "Bias",
			"Context" };

	private PopupField activationField;
	private CheckField useBias;
	private TableField thresholdTable;
	private ContextLayer contextLayer;
	private ActivationFunction activationFunction;
	private DoubleField biasActivation;

	public EditContextLayer(Frame owner, ContextLayer layer) {
		super(owner);
		setTitle("Edit Context Layer");
		setSize(600, 400);
		setLocation(200, 200);

		addProperty(this.activationField = new PopupField("activation",
				"Activation Function", true));
		addProperty(this.useBias = new CheckField("use bias",
				"Use Bias Values"));
		addProperty(this.biasActivation = new DoubleField("Bias Activation",
				"Bias Activation", true, 1,-1));		
		addProperty(this.thresholdTable = new TableField("threshold values",
				"Threshold Values", true, 100, layer.getNeuronCount(),
				COLUMN_HEADS));
		this.useBias.setListener(this);
		this.contextLayer = contextLayer;
		render();
		setTableEditable();
	}

	public String popup(PopupField field) {
		ActivationDialog dialog = new ActivationDialog(this);
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

	public TableField getThresholdTable() {
		return thresholdTable;
	}

	public void check(CheckField check) {
		generateThresholdValues();
	}

	public CheckField getUseBias() {
		return useBias;
	}

	public void generateThresholdValues() {
		setTableEditable();

		for (int i = 0; i < this.getNeuronCount().getValue(); i++) {
			this.thresholdTable.setValue(i, 0, "#" + (i + 1));
			if (this.useBias.getValue())
				this.thresholdTable.setValue(i, 1, "0.0");
			else
				this.thresholdTable.setValue(i, 1, "N/A");
		}

		this.thresholdTable.repaint();
	}

	public void setTableEditable() {
		this.thresholdTable.getModel().setEditable(0, false);
		this.thresholdTable.getModel().setEditable(1,
				this.useBias.getValue());
		this.thresholdTable.getModel().setEditable(2, true);
	}
	
	/**
	 * @return the biasActivation
	 */
	public DoubleField getBiasActivation() {
		return biasActivation;
	}

}
