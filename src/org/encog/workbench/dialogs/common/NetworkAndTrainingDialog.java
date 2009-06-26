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
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;

/**
 * Basic dialog box that displays two combo boxes used to select
 * the training set and network to be used.  Subclasses can
 * add additional fields.  This class is based on the Encog
 * common dialog box.
 * @author jheaton
 */
public abstract class NetworkAndTrainingDialog extends EncogPropertiesDialog {

	private ComboBoxField comboTraining;
	private ComboBoxField comboNetwork;
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;

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
		setTitle("Network and Training Set");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.comboTraining = new ComboBoxField("training set","Training Set",true,this.trainingSets));
		addProperty(this.comboNetwork = new ComboBoxField("network","Neural Network",true,this.networks));
	}



	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData() {
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_BASIC_NET) ) {
				this.networks.add(obj.getName());
			} else if (obj.getType().equals(EncogPersistedCollection.TYPE_TRAINING) ) {
				this.trainingSets.add(obj.getName());
			}
		}
	}

	/**
	 * @return The network that the user chose.
	 */
	public BasicNetwork getNetwork() {
		String networkName = (String)this.comboNetwork.getSelectedValue();
		return (BasicNetwork)EncogWorkBench.getInstance().getCurrentFile().find(networkName);
	}

	/**
	 * @return The training set that the user chose.
	 */
	public NeuralDataSet getTrainingSet() {
		String trainingName = (String)this.comboTraining.getSelectedValue();
		return (NeuralDataSet)EncogWorkBench.getInstance().getCurrentFile().find(trainingName);
	}

}
