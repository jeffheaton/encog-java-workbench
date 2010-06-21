package org.encog.workbench.dialogs.training.svm;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class ImputSearchSVM  extends BasicTrainingInput {
	
	private final DoubleField beginningGamma;
	private final DoubleField endingGamma;
	private final DoubleField stepGamma;
	private final DoubleField beginningC;
	private final DoubleField endingC;
	private final DoubleField stepC;

	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public ImputSearchSVM(final Frame owner) {
		super(owner);
		setTitle("Support Vector Machine (SVM) Cross Validation");
		addProperty(this.beginningGamma = new DoubleField("gamma","Gamma Begin",true,-1,-1));
		addProperty(this.endingGamma = new DoubleField("gamma","Gamma End",true,-1,-1));
		addProperty(this.stepGamma = new DoubleField("gamma","Gamma Step",true,-1,-1));
		addProperty(this.beginningC = new DoubleField("gamma","C Begin",true,-1,-1));
		addProperty(this.endingC = new DoubleField("gamma","C End",true,-1,-1));
		addProperty(this.stepC = new DoubleField("gamma","C Step",true,-1,-1));
		render();	
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
	}

	public DoubleField getBeginningGamma() {
		return beginningGamma;
	}

	public DoubleField getEndingGamma() {
		return endingGamma;
	}

	public DoubleField getStepGamma() {
		return stepGamma;
	}

	public DoubleField getBeginningC() {
		return beginningC;
	}

	public DoubleField getEndingC() {
		return endingC;
	}

	public DoubleField getStepC() {
		return stepC;
	}
	
	
	
}