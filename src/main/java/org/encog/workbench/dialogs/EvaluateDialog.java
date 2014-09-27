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
package org.encog.workbench.dialogs;

import java.io.File;

import javax.swing.JComboBox;

import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.training.MethodAndTrainingDialog;
import org.encog.workbench.frames.document.tree.ProjectTraining;

public class EvaluateDialog extends MethodAndTrainingDialog {

	private ComboBoxField comboValidation;
	
	public EvaluateDialog() {
		super(false);
		setTitle("Evaluate Training Set");
		addProperty(this.comboValidation = new ComboBoxField("validation set","Validation Set",false,this.getTrainingSets()));
		render();
		((JComboBox)this.comboValidation.getField()).setSelectedIndex(-1);
	}

	public MLDataSet getValidationSet() {
		if( this.comboValidation.getSelectedValue()==null )			
			return null;
		File file = ((ProjectTraining)this.comboValidation.getSelectedValue()).getFile();
		BufferedMLDataSet result = new BufferedMLDataSet(file);
		return result;
	}

}
