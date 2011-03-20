package org.encog.workbench.dialogs.training;

import java.awt.Frame;
import java.io.File;

import org.encog.ml.MLMethod;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.buffer.BufferedNeuralDataSet;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.frames.document.tree.ProjectTraining;

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
	
	/**
	 * @return The training set that the user chose.
	 */
	public NeuralDataSet getTrainingSet() {
		if( this.getComboTraining().getSelectedValue()==null )			
			return null;
		File file = ((ProjectTraining)this.getComboTraining().getSelectedValue()).getFile();
		BufferedNeuralDataSet result = new BufferedNeuralDataSet(file);
		if( this.loadToMemory.getValue())
			return result.loadToMemory();
		else
			return result;
	}
}
