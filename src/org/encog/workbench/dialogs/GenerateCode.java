/*
 * Encog(tm) Workbench v2.6 
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
package org.encog.workbench.dialogs;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.process.generate.Generate.GenerateLanguage;
import org.encog.workbench.process.generate.Generate.TrainingMethod;

public class GenerateCode extends EncogPropertiesDialog {

	private ComboBoxField comboNetwork;
	private ComboBoxField comboLanguage;
	
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
	 * All available languages to display in the combo box.
	 */
	private final List<String> languages = new ArrayList<String>();

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public GenerateCode(final Frame owner) {
		
		super(owner);
		findData();
		setTitle("Generate Code");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.comboNetwork = new ComboBoxField("network","Neural Network",true,this.networks));
		addProperty(this.comboLanguage = new ComboBoxField("language","Generation Language",true,this.languages));
		render();
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
		
		this.languages.clear();
		this.languages.add("Java");
		this.languages.add("C#");
		this.languages.add("Visual Basic");
	}

	/**
	 * @return The network that the user chose.
	 */
	public BasicNetwork getNetwork() {
		String networkName = (String)this.comboNetwork.getSelectedValue();
		return (BasicNetwork)EncogWorkBench.getInstance().getCurrentFile().find(networkName);
	}


	public GenerateLanguage getLanguage() {
		if( this.comboLanguage.getSelectedIndex()==0 )
			return GenerateLanguage.Java;
		else if( this.comboLanguage.getSelectedIndex()==1 )
			return GenerateLanguage.CS;
		else if( this.comboLanguage.getSelectedIndex()==2 )
			return GenerateLanguage.VB;
		else
			return null;
	}


}
