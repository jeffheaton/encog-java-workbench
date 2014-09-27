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

import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.frames.document.tree.ProjectTraining;
import org.encog.workbench.util.FileUtil;

public class TrainDialog extends MethodAndTrainingDialog {

	private final CheckField loadToMemory;
	private ComboBoxField comboValidation;
	
	public TrainDialog(boolean includePop) {
		super(includePop);
		setSize(600,250);
		List<ProjectTraining> list = EncogWorkBench.getInstance().getTrainingData("_eval");
		
		addProperty(this.comboValidation = new ComboBoxField("validation set","Validation Set (optional)",false,list));
		addProperty(this.loadToMemory = new CheckField("load to memory",
				"Load to Memory (better performance)"));
		
		
		render();
		this.loadToMemory.setValue(true);
		ensureNonEvalUnselection();
	}

	/**
	 * @return the loadToMemory
	 */
	public CheckField getLoadToMemory() {
		return loadToMemory;
	}
	
	/**
	 * @return The training set that the user chose.
	 */
	public MLDataSet getTrainingSet() {
		if( this.getComboTraining().getSelectedValue()==null )			
			return null;
		File file = ((ProjectTraining)this.getComboTraining().getSelectedValue()).getFile();
		BufferedMLDataSet result = new BufferedMLDataSet(file);
		if( this.loadToMemory.getValue())
			return result.loadToMemory();
		else
			return result;
	}

	public MLDataSet getValidationSet() {
		if( this.comboValidation.getSelectedValue()==null )			
			return null;
		File file = ((ProjectTraining)this.comboValidation.getSelectedValue()).getFile();
		BufferedMLDataSet result = new BufferedMLDataSet(file);
		if( this.loadToMemory.getValue())
			return result.loadToMemory();
		else
			return result;
	}
	
    private void ensureNonEvalUnselection()
    {
        JComboBox comboValidation = (JComboBox) this.comboValidation.getField();
        ProjectTraining selectedItem = (ProjectTraining) comboValidation.getSelectedItem();

        if (selectedItem == null || !FileUtil.getFileName(selectedItem.getFile()).endsWith("_eval"))
        {
            comboValidation.setSelectedIndex(-1);
        }
    }
}
