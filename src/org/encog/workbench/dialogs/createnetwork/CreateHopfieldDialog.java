package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.PropertyType;

public class CreateHopfieldDialog extends EncogPropertiesDialog {

	private IntegerField neuronCount;
	
	public CreateHopfieldDialog(Frame owner) {
		super(owner);
		setTitle("Create Hopfield Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.neuronCount = new IntegerField("neurons","Neuron Count",true,1,-1));
		render();
	}

	public IntegerField getNeuronCount() {
		return neuronCount;
	}
	
	

	

}
