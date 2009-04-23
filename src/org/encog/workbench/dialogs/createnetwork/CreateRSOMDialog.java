package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class CreateRSOMDialog extends EncogPropertiesDialog {

	private IntegerField inputCount;
	private IntegerField outputCount;
	
	public CreateRSOMDialog(Frame owner) {
		super(owner);
		setTitle("Create RSOM Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.inputCount = new IntegerField("input neurons","Input Neuron Count",true,1,-1));
		addProperty(this.outputCount = new IntegerField("input neurons","Output Neuron Count",true,1,-1));
		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getOutputCount() {
		return outputCount;
	}	

}
