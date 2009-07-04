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
			return GenerateLanguage.Java;
		else if( this.comboLanguage.getSelectedIndex()==2 )
			return GenerateLanguage.Java;
		else
			return null;
	}


}
