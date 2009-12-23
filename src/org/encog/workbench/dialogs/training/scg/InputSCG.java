package org.encog.workbench.dialogs.training.scg;

import java.awt.Frame;

import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class InputSCG  extends BasicTrainingInput {
	
	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputSCG(final Frame owner) {
		super(owner);
		setTitle("Train Levenberg-Marquardt(SCG)");
		render();	
		this.getMaxError().setValue(0.01);
	}
	
}
