package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.PropertyType;

public class CreateElmanDialog extends EncogPropertiesDialog {

	private PropertiesField inputCount;
	private PropertiesField hiddenCount;
	private PropertiesField outputCount;
	
	public CreateElmanDialog(Frame owner) {
		super(owner);
		setTitle("Create Elman Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.inputCount = new PropertiesField(PropertyType.integer,"input neurons","Input Neuron Count",1,-1,true));
		addProperty(this.hiddenCount = new PropertiesField(PropertyType.integer,"input neurons","Hidden Neuron Count",1,-1,true));
		addProperty(this.outputCount = new PropertiesField(PropertyType.integer,"input neurons","Output Neuron Count",1,-1,true));
		render();
	}
	
	public int getInputCount()
	{
		return inputCount.getValueInt();
	}
	
	public void setInputCount(int count)
	{
		inputCount.setValueInt(count);
	}
	
	public int getHiddenCount()
	{
		return hiddenCount.getValueInt();
	}
	
	public void setHiddenCount(int count)
	{
		hiddenCount.setValueInt(count);
	}
	
	public int getOutputCount()
	{
		return outputCount.getValueInt();
	}
	
	public void setOutputCount(int count)
	{
		outputCount.setValueInt(count);
	}

}
