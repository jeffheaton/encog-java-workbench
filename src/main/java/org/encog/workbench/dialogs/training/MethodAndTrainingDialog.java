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
package org.encog.workbench.dialogs.training;

import java.io.File;
import java.util.List;

import javax.swing.JComboBox;

import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
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
public class MethodAndTrainingDialog extends EncogPropertiesDialog {

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
	public MethodAndTrainingDialog(boolean includePop) {
		
		super(EncogWorkBench.getInstance().getMainWindow());
		findData(includePop);
		setTitle("ML Method and Training Set");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.comboTraining = new ComboBoxField("training set","Training Set",true,this.trainingSets));
		addProperty(this.comboNetwork = new ComboBoxField("ML method","ML Method",true,this.networks));
	}



	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData(boolean includePop) {
		this.trainingSets = EncogWorkBench.getInstance().getTrainingData("_train");
		this.networks = EncogWorkBench.getInstance().getMLMethods(includePop);
	}
	
	public Object getMethodOrPopulation() {
		if( this.comboNetwork.getSelectedValue()==null )
			return null;
		
		return (((ProjectEGFile)this.comboNetwork.getSelectedValue()).getObject());
	}

	/**
	 * @return The network that the user chose.
	 */
	public MLMethod getNetwork() {
		Object obj = getMethodOrPopulation();
		
		if( !(obj instanceof MLMethod) ) {
			throw new WorkBenchError("This operation requires a MLMethod, not a \n" + obj.getClass().getName());
		}
		
		return (MLMethod)obj;
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
		BufferedMLDataSet result = new BufferedMLDataSet(file);
		return result;
	}
	
	public ComboBoxField getComboTraining() {
		return this.comboTraining;
	}
	
	public ComboBoxField getComboNetwork() {
		return this.comboNetwork;
	}



	public List<ProjectTraining> getTrainingSets() {
		return trainingSets;
	}
	
	


}
