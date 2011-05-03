/*
 * Encog(tm) Workbench v3.0
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2011 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.training;

import java.awt.Frame;
import java.io.File;
import java.util.List;

import javax.swing.JComboBox;

import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.BufferedNeuralDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.frames.document.tree.ProjectTraining;

/**
 * Basic dialog box that displays two combo boxes used to select
 * the training set and network to be used.  Subclasses can
 * add additional fields.  This class is based on the Encog
 * common dialog box.
 * @author jheaton
 */
public class NetworkAndTrainingDialog extends EncogPropertiesDialog {

	private ComboBoxField comboTraining;
	private ComboBoxField comboNetwork;
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;

	/**
	 * All available training sets to display in the combo box.
	 */
	private List<ProjectTraining> trainingSets;
	
	/**
	 * All available networks to display in the combo box.
	 */
	private List<ProjectEGFile> networks;

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
		this.trainingSets = EncogWorkBench.getInstance().getTrainingData();
		this.networks = EncogWorkBench.getInstance().getMLMethods();
	}

	/**
	 * @return The network that the user chose.
	 */
	public MLMethod getNetwork() {
		if( this.comboNetwork.getSelectedValue()==null )
			return null;
		
		return (MLMethod)(((ProjectEGFile)this.comboNetwork.getSelectedValue()).getObject());
	}
	

	public void setMethod(ProjectFile method) {
		((JComboBox)this.comboNetwork.getField()).setSelectedItem(method);
	}

	/**
	 * @return The training set that the user chose.
	 */
	public MLDataSet getTrainingSet() {
		if( this.comboTraining.getSelectedValue()==null )			
			return null;
		File file = ((ProjectTraining)this.comboTraining.getSelectedValue()).getFile();
		BufferedNeuralDataSet result = new BufferedNeuralDataSet(file);
		return result;
	}
	
	public ComboBoxField getComboTraining() {
		return this.comboTraining;
	}
	
	public ComboBoxField getComboNetwork() {
		return this.comboNetwork;
	}


}
