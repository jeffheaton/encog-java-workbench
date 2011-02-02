package org.encog.workbench.dialogs.training;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;

public class TrainDialog extends NetworkAndTrainingDialog {

	private final DoubleField maxError;
	private final CheckField loadToMemory;
	private final CheckField useDecimalComma;
	private final CheckField headers;
	
	public TrainDialog(Frame owner) {
		super(owner);
		setSize(600,250);
		addProperty(this.maxError = new DoubleField("max error",
				"Maximum Error Percent(0-100)", true, 0, 100));
		addProperty(this.loadToMemory = new CheckField("load to memory",
				"Load to Memory (better performance)"));
		addProperty(this.useDecimalComma = new CheckField("decimal comma",
		"Use decimal-comma and ; Separator(for CSV's)"));
		addProperty(this.headers = new CheckField("CSV headers",
		"CSV has headers"));
		
		render();
		this.loadToMemory.setValue(true);
		this.maxError.setValue(5);
	}

	/**
	 * @return the maxError
	 */
	public DoubleField getMaxError() {
		return maxError;
	}

	/**
	 * @return the loadToMemory
	 */
	public CheckField getLoadToMemory() {
		return loadToMemory;
	}

	/**
	 * @return the useDecimalComma
	 */
	public CheckField getUseDecimalComma() {
		return useDecimalComma;
	}

	/**
	 * @return the headers
	 */
	public CheckField getHeaders() {
		return headers;
	}
	
}
