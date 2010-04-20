package org.encog.workbench.dialogs;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.solve.genetic.species.Species;
import org.encog.util.identity.BasicGenerateID;
import org.encog.util.identity.GenerateID;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class EditPopulationDialog  extends EncogPropertiesDialog {
	
	private final DoubleField oldAgePenalty;
	private final IntegerField oldAgeThreshold;
	private final IntegerField populationSize;
	private final DoubleField survivalRate;
	private final IntegerField youngBonusAgeThreshold;
	private final DoubleField youngScoreBonus;
	
	
	public EditPopulationDialog(Frame owner) {
		super(owner);

		setTitle("Extract Top Genomes");
		setSize(400,200);
		setLocation(200,200);
		
		addProperty(oldAgePenalty = new DoubleField("old penalty","Old Age Penalty",true,0,1));
		addProperty(oldAgeThreshold = new IntegerField("old threshold","Old Age Threshold",true,0,Integer.MAX_VALUE));
		addProperty(populationSize = new IntegerField("max population","Max Population Size",true,0,Integer.MAX_VALUE));
		addProperty(survivalRate = new DoubleField("survival rate","Survival Rate",true,0,Integer.MAX_VALUE));
		addProperty(youngBonusAgeThreshold = new IntegerField("youth bonus","Youth Bonus",true,0,Integer.MAX_VALUE));
		addProperty(youngScoreBonus = new DoubleField("youth threshold","Youth Threshold",true,0,Integer.MAX_VALUE));		
		render();
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
