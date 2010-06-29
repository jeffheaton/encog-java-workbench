package org.encog.workbench.dialogs;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class InputAndIdealDialog extends EncogPropertiesDialog {

	private final IntegerField inputCount;
	private final IntegerField idealCount;
	
	public InputAndIdealDialog(Frame owner) {
		super(owner);

		setTitle("Create Training");
		setSize(500,150);
		addProperty(this.inputCount = new IntegerField("input count","Input Count",true,1,10000));
		addProperty(this.idealCount = new IntegerField("ideal count","Ideal Count (0=unsupervised)",true,0,10000));
		render();		
	}

	/**
	 * @return the inputCount
	 */
	public IntegerField getInputCount() {
		return inputCount;
	}

	/**
	 * @return the idealCount
	 */
	public IntegerField getIdealCount() {
		return idealCount;
	}
	
	

}
