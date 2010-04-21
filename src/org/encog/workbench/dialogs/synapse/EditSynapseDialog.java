package org.encog.workbench.dialogs.synapse;

import java.awt.Frame;

import org.encog.neural.networks.synapse.Synapse;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TableField;

public class EditSynapseDialog extends EncogPropertiesDialog {

	private final Synapse synapse;
	private final MatrixTableField matrixTable;
	
	public EditSynapseDialog(Frame owner, Synapse synapse) {
		super(owner);

		setTitle("Edit Synapse");
		setSize(600,500);
		setLocation(20,20);
		this.synapse = synapse;
		addProperty(matrixTable = new MatrixTableField("weight matrix","Weight Matrix",synapse));
		render();
	}

	public Synapse getSynapse() {
		return synapse;
	}

	public MatrixTableField getMatrixTable() {
		return matrixTable;
	}
	
	
	
}
