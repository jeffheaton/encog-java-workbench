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
package org.encog.workbench.dialogs.population.neat;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class EditPopulationDialog  extends EncogPropertiesDialog {
	
	private final DoubleField oldAgePenalty;
	private final IntegerField oldAgeThreshold;
	private final IntegerField populationSize;
	private final DoubleField survivalRate;
	private final IntegerField youngBonusAgeThreshold;
	private final DoubleField youngScoreBonus;
	
	
	public EditPopulationDialog(Frame owner) {
		super(owner);
		setTitle("Edit Population Info");
		setSize(400,300);
		setLocation(200,200);
		
		addProperty(oldAgePenalty = new DoubleField("old penalty","Old Age Penalty",true,0,1));
		addProperty(oldAgeThreshold = new IntegerField("old threshold","Old Age Threshold",true,0,Integer.MAX_VALUE));
		addProperty(populationSize = new IntegerField("max population","Max Population Size",true,0,Integer.MAX_VALUE));
		addProperty(survivalRate = new DoubleField("survival rate","Survival Rate",true,0,Integer.MAX_VALUE));
		addProperty(youngBonusAgeThreshold = new IntegerField("youth bonus","Youth Bonus",true,0,Integer.MAX_VALUE));
		addProperty(youngScoreBonus = new DoubleField("youth threshold","Youth Threshold",true,0,Integer.MAX_VALUE));		

	}


	public DoubleField getOldAgePenalty() {
		return oldAgePenalty;
	}


	public IntegerField getOldAgeThreshold() {
		return oldAgeThreshold;
	}


	public IntegerField getPopulationSize() {
		return populationSize;
	}


	public DoubleField getSurvivalRate() {
		return survivalRate;
	}


	public IntegerField getYoungBonusAgeThreshold() {
		return youngBonusAgeThreshold;
	}


	public DoubleField getYoungScoreBonus() {
		return youngScoreBonus;
	}
	
	

	
}
