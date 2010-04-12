package org.encog.workbench.dialogs.training.lma;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class InputLMA  extends BasicTrainingInput {
	
	private CheckField useBayesian;
	
	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputLMA(final Frame owner) {
		super(owner);
		setTitle("Levenberg Marquardt Training");
		addProperty(this.useBayesian = new CheckField("baysean","Bayesian Regularization"));
		render();	
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
	}

	public CheckField getUseBayesian() {
		return useBayesian;
	}
	
	
}
