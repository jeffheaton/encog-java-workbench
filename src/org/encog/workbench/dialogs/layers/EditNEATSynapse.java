package org.encog.workbench.dialogs.layers;

import java.awt.Frame;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.networks.layers.Layer;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;
import org.encog.workbench.dialogs.common.TableField;

public class EditNEATSynapse  extends EncogPropertiesDialog implements PopupListener {
	private PopupField activationField;
	private CheckField snapshot;
	
	private ActivationFunction activationFunction;

	public EditNEATSynapse(Frame owner) {
		super(owner);
		setTitle("Edit NEAT Synapse");
		setSize(400, 400);
		setLocation(200, 200);
		addProperty(this.activationField = new PopupField("activation",
				"Activation Function", true));
		addProperty(this.snapshot = new CheckField("snapshot",
				"Calculate with Snapshot"));

		render();
	}

	public String popup(PopupField field) {
		ActivationDialog dialog = new ActivationDialog(this);
		dialog.setActivation(this.activationFunction);
		if( !dialog.process()  )
			return null;
		else
		{
			this.activationFunction = dialog.getActivation();
			return dialog.getActivation().getClass().getSimpleName();
		}
	}

	public PopupField getActivationField() {
		return activationField;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
		this.activationField.setValue(this.activationFunction.getClass().getSimpleName());
	}

	public CheckField getSnapshot() {
		return snapshot;
	}


	
	
}
