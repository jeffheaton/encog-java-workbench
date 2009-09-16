/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
 */

package org.encog.workbench.dialogs.validate;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;

public class InputValidationChart extends EncogPropertiesDialog {
	private static final long serialVersionUID = 3377283752032159041L;

	private ComboBoxField comboNetwork;
	private ComboBoxField comboValidation;

	/**
	 * All available networks to display in the combo box.
	 */
	private final List<String> networks = new ArrayList<String>();

	/**
	 * All available training sets to display in the combo box.
	 */
	private final List<String> trainingSets = new ArrayList<String>();

	/**
	 * Construct the dialog box.
	 * 
	 * @param owner
	 */
	public InputValidationChart(final Frame owner) {
		super(owner);
		findData();
		setTitle("Validation Chart");
		setSize(400, 400);
		setLocation(200, 200);
		addProperty(this.comboNetwork = new ComboBoxField("network",
				"Neural Network", true, this.networks));
		addProperty(this.comboValidation = new ComboBoxField("validation set",
				"Validation Set", true, this.trainingSets));
		render();
	}

	/**
	 * Obtain the data needed to fill in the network and training set combo
	 * boxes.
	 */
	private void findData() {
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_BASIC_NET)) {
				this.networks.add(obj.getName());
			} else if (obj.getType().equals(
					EncogPersistedCollection.TYPE_TRAINING)) {
				this.trainingSets.add(obj.getName());
			}
		}
	}

	/**
	 * @return The network that the user chose.
	 */
	public BasicNetwork getNetwork() {
		String networkName = (String) this.comboNetwork.getSelectedValue();
		return (BasicNetwork) EncogWorkBench.getInstance().getCurrentFile()
				.find(networkName);
	}

	/**
	 * @return The training set that the user chose.
	 */
	public NeuralDataSet getValidationSet() {
		String trainingName = (String) this.comboValidation.getSelectedValue();
		return (NeuralDataSet) EncogWorkBench.getInstance().getCurrentFile()
				.find(trainingName);
	}
}