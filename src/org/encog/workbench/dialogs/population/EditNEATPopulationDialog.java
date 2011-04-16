package org.encog.workbench.dialogs.population;

import org.encog.neural.activation.ActivationFunction;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;

public class EditNEATPopulationDialog extends EditPopulationDialog implements PopupListener {

	private PopupField outputActivationField;
	private PopupField neatActivationField;
	private ActivationFunction outputActivationFunction;
	private ActivationFunction neatActivationFunction;
	
	public EditNEATPopulationDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
				
		addProperty(this.outputActivationField = new PopupField("output activation",
				"Output Activation Function", true));
		addProperty(this.neatActivationField = new PopupField("NEAT activation",
				"NEAT Activation Function", true));
		
		render();
	}

	public String popup(PopupField field) {
		if (field == this.outputActivationField) {
			ActivationDialog dialog = new ActivationDialog(EncogWorkBench
					.getInstance().getMainWindow());
			dialog.setActivation(this.outputActivationFunction);
			if (!dialog.process())
				return null;
			else {
				this.outputActivationFunction = dialog.getActivation();
				return dialog.getActivation().getClass().getSimpleName();
			}
		} else if (field == this.neatActivationField) {
			ActivationDialog dialog = new ActivationDialog(EncogWorkBench
					.getInstance().getMainWindow());
			dialog.setActivation(this.neatActivationFunction);
			if (!dialog.process())
				return null;
			else {
				this.neatActivationFunction = dialog.getActivation();
				return dialog.getActivation().getClass().getSimpleName();
			}
		} else
			return null;
	}

	public PopupField getOutputActivationField() {
		return outputActivationField;
	}

	public PopupField getNeatActivationField() {
		return neatActivationField;
	}

	public void setOutputActivationFunction(
			ActivationFunction activationFunction) {
		this.outputActivationFunction = activationFunction;
		this.outputActivationField.setValue(activationFunction.getClass()
				.getSimpleName());
	}

	public ActivationFunction getOutputActivationFunction() {
		return outputActivationFunction;
	}

	public ActivationFunction getNeatActivationFunction() {
		return neatActivationFunction;
	}

	public void setNeatActivationFunction(ActivationFunction activationFunction) {
		this.neatActivationFunction = activationFunction;
		this.neatActivationField.setValue(activationFunction.getClass()
				.getSimpleName());
		
	}	
}
