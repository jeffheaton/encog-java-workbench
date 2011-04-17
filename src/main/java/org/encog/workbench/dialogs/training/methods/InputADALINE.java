package org.encog.workbench.dialogs.training.methods;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.DialogMaxError;

public class InputADALINE extends DialogMaxError {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private DoubleField learningRate;
	

	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputADALINE() {
		super(false);
		setTitle("Train ADALINE");
		
		addProperty(this.learningRate = new DoubleField("learning rate","Learning Rate",true,0,-1));
		render();
		this.learningRate.setValue(0.01);
	}


	public DoubleField getLearningRate() {
		return learningRate;
	}

	

	
}
