package org.encog.workbench.dialogs;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;

public class EvaluateDialog extends NetworkAndTrainingDialog {

	public EvaluateDialog(Frame owner) {
		super(owner);
		setTitle("Evaluate Training Set");
		render();
	}

}
