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

import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.BufferedNeuralDataSet;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.frames.document.tree.ProjectTraining;

public class TrainDialog extends NetworkAndTrainingDialog {

	private final CheckField loadToMemory;
	
	public TrainDialog(Frame owner) {
		super(owner);
		setSize(600,250);
		addProperty(this.loadToMemory = new CheckField("load to memory",
				"Load to Memory (better performance)"));
		
		render();
		this.loadToMemory.setValue(true);
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
		BufferedNeuralDataSet result = new BufferedNeuralDataSet(file);
		if( this.loadToMemory.getValue())
			return result.loadToMemory();
		else
			return result;
	}
}
