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
package org.encog.workbench.dialogs.binary;

import java.io.File;
import java.util.List;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TextField;
import org.encog.workbench.frames.document.tree.ProjectTraining;

/**
 * Basic dialog box that displays two combo boxes used to select
 * the training set and network to be used.  Subclasses can
 * add additional fields.  This class is based on the Encog
 * common dialog box.
 * @author jheaton
 */
public class DialogNoise extends EncogPropertiesDialog {

	private final ComboBoxField sourceFile;
	private final TextField targetFile;
	private final DoubleField inputNoise;
	private final DoubleField idealNoise;
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;

	/**
	 * All available training sets to display in the combo box.
	 */
	private List<ProjectTraining> trainingSets;

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public DialogNoise() {		
		super(EncogWorkBench.getInstance().getMainWindow());
		
		setTitle("Add Noise to EGB File");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.sourceFile = new ComboBoxField("source egb file","Source EGB File",true,EncogWorkBench.getInstance().getTrainingData()));
		addProperty(this.targetFile = new TextField("target egb file", "Target EGB File", true));
		addProperty(this.inputNoise = new DoubleField("input noise","Input Noise(percent 0.1=10%)",true,0,100));
		addProperty(this.idealNoise = new DoubleField("ideal noise","Ideal Noise(percent 0.1=10%)",true,0,100));
		render();		
	}

	/**
	 * @return The training set that the user chose.
	 */
	public File getSourceFile() {
		if( this.sourceFile.getSelectedValue()==null )			
			return null;
		File result = ((ProjectTraining)this.sourceFile.getSelectedValue()).getFile();
		return result;
	}

	public List<ProjectTraining> getTrainingSets() {
		return trainingSets;
	}

	public void setTrainingSets(List<ProjectTraining> trainingSets) {
		this.trainingSets = trainingSets;
	}

	public TextField getTargetFile() {
		return targetFile;
	}

	public DoubleField getInputNoise() {
		return inputNoise;
	}

	public DoubleField getIdealNoise() {
		return idealNoise;
	}
	
	

}
