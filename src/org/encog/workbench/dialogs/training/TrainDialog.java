package org.encog.workbench.dialogs.training;

import java.awt.Frame;

import org.encog.ml.MLMethod;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;

public class TrainDialog extends NetworkAndTrainingDialog {

	private final CheckField loadToMemory;
	
	public TrainDialog(Frame owner) {
		super(owner);
		setSize(600,250);
		addProperty(this.loadToMemory = new CheckField("load to memory",
				"Load to Memory (better performance)"));
		
		render();
		this.loadToMemory.setValue(true);
	}

	/**
	 * @return the loadToMemory
	 */
	public CheckField getLoadToMemory() {
		return loadToMemory;
	}

}
