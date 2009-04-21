package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.PropertyType;

public class CreateHopfieldDialog extends EncogPropertiesDialog {

	private PropertiesField neuronCount;
	
	public CreateHopfieldDialog(Frame owner) {
		super(owner);
		setTitle("Create Hopfield Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.neuronCount = new PropertiesField(PropertyType.integer,"neurons","Neuron Count",1,-1,true));
		render();
	}
	
	public int getNeuronCount()
	{
		return neuronCount.getValueInt();
	}
	
	public void setNeuronCount(int count)
	{
		neuronCount.setValueInt(count);
	}

	

}
