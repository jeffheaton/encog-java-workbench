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

import java.util.List;

import javax.swing.JComboBox;

import org.encog.ml.MLMethod;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectFile;

/**
 * Basic dialog box that allows the user to choose a neural network.
 * @author jheaton
 */
public class NetworkDialog extends EncogPropertiesDialog {

	private ComboBoxField comboNetwork;
		
	/**
	 * All available networks to display in the combo box.
	 */
	private List<ProjectEGFile> networks;

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public NetworkDialog(boolean includePop) {
		
		super(EncogWorkBench.getInstance().getMainWindow());
		findData(includePop);
		setTitle("Choose a Neural Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.comboNetwork = new ComboBoxField("network","Neural Network",true,this.networks));
		render();
	}



	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData(boolean includePop) {
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
	
	public ComboBoxField getComboNetwork() {
		return this.comboNetwork;
	}


}
