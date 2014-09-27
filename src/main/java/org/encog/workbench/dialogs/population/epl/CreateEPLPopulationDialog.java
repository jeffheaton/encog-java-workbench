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
package org.encog.workbench.dialogs.population.epl;

import java.io.File;
import java.util.List;

import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.BuildingListListener;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.frames.document.tree.ProjectTraining;

public class CreateEPLPopulationDialog extends EncogPropertiesDialog implements BuildingListListener {

	private final ComboBoxField comboTraining;
	private final BuildingListField inputVariables;
	private final IntegerField populationSize;
	private final IntegerField maxDepth;
	
	/**
	 * All available training sets to display in the combo box.
	 */
	private List<ProjectTraining> trainingSets;
	
	public CreateEPLPopulationDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		findData();
		
		this.setSize(500, 400);
		this.setTitle("Create EPL Population");
		
		addProperty(this.comboTraining = new ComboBoxField("training set","Training Set (optinal)",false,this.trainingSets));
		addProperty(this.populationSize = new IntegerField("population size","Population Size",true,1,-1));
		addProperty(this.maxDepth = new IntegerField("max depth","Maximum Depth",true,3,Integer.MAX_VALUE));
		addProperty(this.inputVariables = new BuildingListField("input variables",
				"Input Variables"));


		render();
		this.maxDepth.setValue(5);
	}

	public IntegerField getPopulationSize() {
		return populationSize;
	}


	
	
	
	/**
	 * @return the inputVariables
	 */
	public BuildingListField getInputVariables() {
		return inputVariables;
	}



	/**
	 * @return the maxDepth
	 */
	public IntegerField getMaxDepth() {
		return maxDepth;
	}

	/**
	 * @return the comboTraining
	 */
	public ComboBoxField getComboTraining() {
		return comboTraining;
	}

	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData() {
		this.trainingSets = EncogWorkBench.getInstance().getTrainingData();
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

	@Override
	public void add(BuildingListField list, int index) {
		String str = EncogWorkBench.displayInput("Variable?");
		if (str != null) {
			list.getModel().addElement(str);
		}
	}

	@Override
	public void edit(BuildingListField list, int index) {
		if (index != -1) {
			String str = EncogWorkBench.displayInput("Variable?");
			if (str != null) {
				list.getModel().remove(index);
				list.getModel().add(index, str);
			}
		}
	}

	@Override
	public void del(BuildingListField list, int index) {
		if (index != -1) {
			list.getModel().remove(index);
		}
	}


}
