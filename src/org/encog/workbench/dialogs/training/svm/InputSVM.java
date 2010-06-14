package org.encog.workbench.dialogs.training.svm;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class InputSVM  extends BasicTrainingInput {
	
	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputSVM(final Frame owner) {
		super(owner);
		setTitle("Support Vector Machine (SVM)");
		render();	
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
	}
	
}