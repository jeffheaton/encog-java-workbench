package org.encog.workbench.dialogs.synapse;

import java.awt.Frame;

import org.encog.neural.networks.synapse.Synapse;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TableField;

public class EditSynapseDialog extends EncogPropertiesDialog {

	private TableField weightMatrix;
	private Synapse synapse;
	private MatrixTableField matrixTable;
	
	public EditSynapseDialog(Frame owner, Synapse synapse) {
		super(owner);

		setTitle("Edit Synapse");
		setSize(400,200);
		setLocation(200,200);
		this.synapse = synapse;
		addProperty(matrixTable = new MatrixTableField("weight matrix","Weight Matrix",synapse));
		render();
	}
	
}
