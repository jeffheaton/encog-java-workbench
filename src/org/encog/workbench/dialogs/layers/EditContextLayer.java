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

package org.encog.workbench.dialogs.layers;

import java.awt.Frame;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.CheckListener;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
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
