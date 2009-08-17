package org.encog.workbench.dialogs.training.instar;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

/**
 * A dialog box that inputs for the parameters to use with
 * the instar training method.
 * @author jheaton
 *
 */
public class InputInstar extends BasicTrainingInput {


	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private DoubleField learningRate;


	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputInstar(final Frame owner) {
		super(owner);
		setTitle("Train Instar");

		addProperty(this.learningRate = new DoubleField("learning rate","Learning Rate",true,-1,-1));

		render();
		this.learningRate.setValue(0.7);

		this.getMaxError().setValue(0.01);
	}


	public DoubleField getLearningRate() {
		return learningRate;
	}
}
