package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.PropertyType;

public class CreateHopfieldDialog extends EncogPropertiesDialog {

	private JTextField txtNeuronCount;
	
	public CreateHopfieldDialog(Frame owner) {
		super(owner);
		addProperty(new PropertiesField(PropertyType.integer,"neurons","Neuron Count",1,10000));
	}

	

}
