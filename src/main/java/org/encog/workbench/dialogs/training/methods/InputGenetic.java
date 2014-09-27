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
package org.encog.workbench.dialogs.training.methods;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.training.DialogMaxError;

/**
 * Dialog box to input the parameters for genetic training.
 */
public class InputGenetic extends DialogMaxError {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;

	private final IntegerField populationSize;
	private final DoubleField mutationPercent;
	private final DoubleField percentToMate;
	

	/**
	 * Construct the dialog.
	 * @param owner The owner.
	 */
	public InputGenetic() {
		super(false);
		setTitle("Train Simulated Annealing");
		
		addProperty(this.populationSize = new IntegerField("population size","Learning Rate",true,1,-1));
		addProperty(this.mutationPercent = new DoubleField("mutation percent","Mutation Percent",true,0,1));
		addProperty(this.percentToMate = new DoubleField("percent to mate","Percent to Mate",true,0,1));
		render();
		this.populationSize.setValue(5000);
		this.mutationPercent.setValue(0.1);
		this.percentToMate.setValue(0.25);
	}


	public IntegerField getPopulationSize() {
		return populationSize;
	}


	public DoubleField getMutationPercent() {
		return mutationPercent;
	}


	public DoubleField getPercentToMate() {
		return percentToMate;
	}
	
	


}
