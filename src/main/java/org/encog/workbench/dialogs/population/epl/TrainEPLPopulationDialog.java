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

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.training.DialogMaxError;

/**
 * A dialog box that inputs for the parameters to use with
 * the backpropagation training method.
 * @author jheaton
 *
 */
public class TrainEPLPopulationDialog extends DialogMaxError {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private final IntegerField complexityPenaltyThreshold;
	private final DoubleField complexityPenalty;
	private final IntegerField complexityPentaltyFullThreshold;
	private final DoubleField complexityFullPenalty;
	private final CheckField simplify;
	private final DoubleField mutateProbability;
	private final DoubleField constMutateProbability;
	private final DoubleField crossoverProbability;
	
	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public TrainEPLPopulationDialog() {
		super(true);
		setTitle("Train EPL Population");
		
		addProperty(this.complexityPenaltyThreshold= new IntegerField("complexity penalty threshold","Complexity Penalty Threshold",true,-1,-1));
		addProperty(this.complexityPenalty = new DoubleField("complexity penalty","Complexity Penalty",true,-1,-1));
		addProperty(this.complexityPentaltyFullThreshold = new IntegerField("complexity pentalty full threshold","Complexity Pentalty Full Threshold",true,-1,-1));
		addProperty(this.complexityFullPenalty = new DoubleField("complexity full penalty","Complexity Full Penalty",true,-1,-1));
		addProperty(this.simplify = new CheckField("simplify","Simplify"));
		addProperty(this.mutateProbability = new DoubleField("mutation probability","Struct Mutation Probability",true,0,1));
		addProperty(this.constMutateProbability = new DoubleField("mutation probability","Const Mutation Probability",true,0,1));
		addProperty(this.crossoverProbability = new DoubleField("crossover probability","Crossover Probability",true,0,1));
		

		render();
		this.complexityPenaltyThreshold.setValue(10);
		this.complexityPenalty.setValue(0.2);
		this.complexityPentaltyFullThreshold.setValue(50);
		this.complexityFullPenalty.setValue(2.0);
		this.simplify.setValue(true);
		this.mutateProbability.setValue(0.1);
		this.constMutateProbability.setValue(0.1);
		this.crossoverProbability.setValue(0.8);

		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
	}

	/**
	 * @return the complexityPenaltyThreshold
	 */
	public IntegerField getComplexityPenaltyThreshold() {
		return complexityPenaltyThreshold;
	}

	/**
	 * @return the complexityPenalty
	 */
	public DoubleField getComplexityPenalty() {
		return complexityPenalty;
	}

	/**
	 * @return the complexityPentaltyFullThreshold
	 */
	public IntegerField getComplexityPentaltyFullThreshold() {
		return complexityPentaltyFullThreshold;
	}

	/**
	 * @return the complexityFullPenalty
	 */
	public DoubleField getComplexityFullPenalty() {
		return complexityFullPenalty;
	}

	/**
	 * @return the simplify
	 */
	public CheckField getSimplify() {
		return simplify;
	}

	/**
	 * @return the mutateProbability
	 */
	public DoubleField getMutateProbability() {
		return mutateProbability;
	}

	/**
	 * @return the crossoverProbability
	 */
	public DoubleField getCrossoverProbability() {
		return crossoverProbability;
	}

	/**
	 * @return the constMutateProbability
	 */
	public DoubleField getConstMutateProbability() {
		return constMutateProbability;
	}
}
