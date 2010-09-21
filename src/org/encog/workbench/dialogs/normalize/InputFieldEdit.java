/*
 * Encog(tm) Workbench v2.5
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
package org.encog.workbench.dialogs.normalize;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class InputFieldEdit extends EncogPropertiesDialog {
	
	private ComboBoxField comboSourceData;
	private IntegerField inputFieldIndex;
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;

	/**
	 * All available training sets to display in the combo box.
	 */
	private final List<String> trainingSets = new ArrayList<String>();
	

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public InputFieldEdit(final Frame owner) {
		
		super(owner);
		findData();
		setTitle("Normalization Input");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.comboSourceData = new ComboBoxField("source data","Source Data",true,this.trainingSets));
		addProperty(this.inputFieldIndex = new IntegerField("field index","Field Index(in source data)",true,0,10000));
		render();
	}



	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData() {
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_BINARY) ) {
				this.trainingSets.add(obj.getName());
			} else if (obj.getType().equals(EncogPersistedCollection.TYPE_TRAINING) ||
					obj.getType().equals(EncogPersistedCollection.TYPE_BINARY)) {
				this.trainingSets.add(obj.getName());
			}
		}
	}



	/**
	 * @return the comboSourceData
	 */
	public ComboBoxField getComboSourceData() {
		return comboSourceData;
	}



	/**
	 * @return the inputFieldIndex
	 */
	public IntegerField getInputFieldIndex() {
		return inputFieldIndex;
	}

	

}
