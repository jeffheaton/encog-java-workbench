package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.PropertyType;

public class CreateJordanDialog extends EncogPropertiesDialog {

	private IntegerField inputCount;
	private IntegerField hiddenCount;
	private IntegerField outputCount;
	
	public CreateJordanDialog(Frame owner) {
		super(owner);
		setTitle("Create Jordan Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.inputCount = new IntegerField("input neurons","Input Neuron Count",true,1,-1));
		addProperty(this.hiddenCount = new IntegerField("input neurons","Hidden Neuron Count",true,1,-1));
		addProperty(this.outputCount = new IntegerField("input neurons","Output Neuron Count",true,1,-1));
		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getHiddenCount() {
		return hiddenCount;
	}

	public IntegerField getOutputCount() {
		return outputCount;
	}
	
	

}
