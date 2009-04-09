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

/**
 * Basic dialog box that displays two combo boxes used to select
 * the training set and network to be used.  Subclasses can
 * add additional fields.  This class is based on the Encog
 * common dialog box.
 * @author jheaton
 */
public abstract class NetworkAndTrainingDialog extends EncogCommonDialog {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;

	/**
	 * The network combo box.
	 */
	private final JComboBox cboneuralNetworkName;
	
	/**
	 * The training set combo box.
	 */
	private final JComboBox cbotrainingDataName;
	
	/**
	 * The network that was chosen.
	 */
	private BasicNetwork network;
	
	/**
	 * The training set that was chosen.
	 */
	private NeuralDataSet trainingSet;

	/**
	 * All available training sets to display in the combo box.
	 */
	private final List<String> trainingSets = new ArrayList<String>();
	
	/**
	 * All available networks to display in the combo box.
	 */
	private final List<String> networks = new ArrayList<String>();

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
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

	/**
	 * Collect the network and training set fields.
	 */
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

	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData() {
		/*for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj instanceof BasicNetwork) {
				this.networks.add(obj.getName());
			} else if (obj instanceof BasicNeuralDataSet) {
				this.trainingSets.add(obj.getName());
			}
		}*/
	}

	/**
	 * @return The network that the user chose.
	 */
	public BasicNetwork getNetwork() {
		return this.network;
	}

	/**
	 * @return The training set that the user chose.
	 */
	public NeuralDataSet getTrainingSet() {
		return this.trainingSet;
	}

}
