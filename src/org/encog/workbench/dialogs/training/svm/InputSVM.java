package org.encog.workbench.dialogs.training.svm;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class InputSVM  extends NetworkAndTrainingDialog {
	
	private final DoubleField gamma;
	private final DoubleField c;
	
	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputSVM(final Frame owner) {
		super(owner);
		setTitle("Support Vector Machine (SVM)");
		addProperty(this.gamma = new DoubleField("gamma","Gamma (0 for 1/#input)",true,-1,-1));
		addProperty(this.c = new DoubleField("c","C",true,-1,-1));

		render();	
	}

	public DoubleField getGamma() {
		return gamma;
	}

	public DoubleField getC() {
		return c;
	}
	
	
	
}