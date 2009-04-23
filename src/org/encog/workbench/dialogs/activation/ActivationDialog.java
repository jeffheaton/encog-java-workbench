package org.encog.workbench.dialogs.activation;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JPanel;

import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public class ActivationDialog extends EncogCommonDialog {

	public ActivationDialog(Frame owner) {
		super(owner);
		this.setSize(600,300);
		JPanel contents = this.getBodyPanel();
		contents.setLayout(new BorderLayout());
		contents.add(new EquationPanel(),BorderLayout.CENTER);
	}

	@Override
	public void collectFields() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		
	}

}
