package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class CreateEmptyTrainingDialog extends EncogPropertiesDialog {
	
	private IntegerField input;
	private IntegerField ideal;
	
	public CreateEmptyTrainingDialog(Frame owner) {
		super(owner);
		setTitle("Empty Training Data");
		setSize(400,200);
		addProperty(this.input = new IntegerField("input","Input Field Count",true, 1, 1000));
		addProperty(this.ideal = new IntegerField("ideal","Output Field Count",true, 0, 1000));
		render();
	}

	public IntegerField getInput() {
		return input;
	}

	public IntegerField getIdeal() {
		return ideal;
	}

	
}
