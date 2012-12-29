/*
 * Encog(tm) Workbench v3.2
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
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

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class CreateEPLPopulationDialog extends EncogPropertiesDialog {

	private final IntegerField populationSize;
	private final IntegerField inputSize;
	
	public CreateEPLPopulationDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		this.setSize(400, 200);
		this.setTitle("Create EPL Population");
		
		addProperty(this.populationSize = new IntegerField("population size","Population Size",true,1,-1));
		addProperty(this.inputSize = new IntegerField("input size","Input Variables",true,1,-1));


		render();
		this.populationSize.setValue(1000);
		this.inputSize.setValue(1);
	}

	public IntegerField getPopulationSize() {
		return populationSize;
	}

	/**
	 * @return the inputSize
	 */
	public IntegerField getInputSize() {
		return inputSize;
	}

}
