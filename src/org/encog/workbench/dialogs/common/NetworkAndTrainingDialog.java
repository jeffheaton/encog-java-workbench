/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
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
package org.encog.workbench.dialogs.common;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;

public abstract class NetworkAndTrainingDialog extends EncogCommonDialog {

	private final JComboBox cboneuralNetworkName;
	private final JComboBox cbotrainingDataName;
	private BasicNetwork network;
	private NeuralDataSet trainingSet;

	private final List<String> trainingSets = new ArrayList<String>();
	private final List<String> networks = new ArrayList<String>();

	public NetworkAndTrainingDialog(final Frame owner) {
		super(owner);

		findData();

		this.cbotrainingDataName = new JComboBox();
		this.cboneuralNetworkName = new JComboBox();

		this.cbotrainingDataName.setModel(new DefaultComboBoxModel(
				this.trainingSets.toArray()));
		this.cboneuralNetworkName.setModel(new DefaultComboBoxModel(
				this.networks.toArray()));

		final JPanel content = getBodyPanel();

		content.add(new JLabel("Training Set"));
		content.add(this.cbotrainingDataName);

		content.add(new JLabel("Network Name"));
		content.add(this.cboneuralNetworkName);

	}

	public void collectFields() throws ValidationException {
		final String networkName = validateFieldString("network",
				this.cboneuralNetworkName, true);
		final String trainingSetName = validateFieldString("training set",
				this.cbotrainingDataName, true);

		this.network = (BasicNetwork) EncogWorkBench.getInstance()
				.getCurrentFile().find(networkName);
		this.trainingSet = (NeuralDataSet) EncogWorkBench.getInstance()
				.getCurrentFile().find(trainingSetName);
	}

	public void findData() {
		for (final EncogPersistedObject obj : EncogWorkBench.getInstance()
				.getCurrentFile().getList()) {
			if (obj instanceof BasicNetwork) {
				this.networks.add(obj.getName());
			} else if (obj instanceof BasicNeuralDataSet) {
				this.trainingSets.add(obj.getName());
			}
		}
	}

	/**
	 * @return the network
	 */
	public BasicNetwork getNetwork() {
		return this.network;
	}

	/**
	 * @return the trainingSet
	 */
	public NeuralDataSet getTrainingSet() {
		return this.trainingSet;
	}

}
