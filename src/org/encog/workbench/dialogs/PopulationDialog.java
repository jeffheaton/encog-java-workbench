package org.encog.workbench.dialogs;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class PopulationDialog extends EncogPropertiesDialog {

	private final IntegerField populationSize;
	private final IntegerField inputNeurons;
	private final IntegerField outputNeurons;
	
	public PopulationDialog(Frame owner) {
		super(owner);
		this.setSize(320, 200);
		this.setTitle("Create NEAT Population");
		
		addProperty(this.populationSize = new IntegerField("population size","Population Size",true,1,-1));
		addProperty(this.inputNeurons = new IntegerField("input size","Input Neurons",true,1,-1));
		addProperty(this.outputNeurons = new IntegerField("output size","output Neurons",true,1,-1));

		render();
	}

	public IntegerField getPopulationSize() {
		return populationSize;
	}

	public IntegerField getInputNeurons() {
		return inputNeurons;
	}

	public IntegerField getOutputNeurons() {
		return outputNeurons;
	}
	
	

}
