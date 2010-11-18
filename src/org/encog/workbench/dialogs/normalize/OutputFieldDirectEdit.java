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
package org.encog.workbench.dialogs.normalize;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.normalize.DataNormalization;
import org.encog.normalize.input.InputField;
import org.encog.normalize.input.InputFieldEncogCollection;
import org.encog.normalize.output.OutputField;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class OutputFieldDirectEdit  extends EncogPropertiesDialog {
	
	private ComboBoxField comboSourceField;
	private DataNormalization norm;
	private List<InputField> inputFields = new ArrayList<InputField>();

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public OutputFieldDirectEdit(final Frame owner, DataNormalization norm) {
		
		super(owner);
		
		this.norm = norm;
		
		findData();
		setTitle("Normalization Direct Output");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.comboSourceField = new ComboBoxField("source field","Source Field",true,this.inputFields));
		render();
	}



	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData() {
		
		for( InputField field : this.norm.getInputFields() )
		{
			if( field instanceof  InputFieldEncogCollection)
			{
				this.inputFields.add(field);
			}
		}
	}



	public InputField getInputField() {
		return (InputField)this.comboSourceField.getSelectedValue();
	}

}
