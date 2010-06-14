package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class CreateSVMDialog extends EncogPropertiesDialog {

	private final IntegerField inputCount;
	private final IntegerField outputCount;
	
	public CreateSVMDialog(Frame owner) {
		super(owner);
		setTitle("Create BAM Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.inputCount = new IntegerField("input-count","Input Count",true,1,100000));
		addProperty(this.outputCount = new IntegerField("output-count","Output Count",true,1,100000));
		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getOutputCount() {
		return outputCount;
	}

	

}

