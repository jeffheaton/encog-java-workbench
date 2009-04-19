package org.encog.workbench.dialogs.layers;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public class EditBasicLayer extends EncogCommonDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3593792259701670934L;
	JTextField txtNeuronCount;
	JCheckBox cbThresholds;
	
	public EditBasicLayer(Frame owner) {
		super(owner);
		
		setTitle("Edit Basic Layer");

		this.setSize(300, 240);
		this.setLocation(200, 100);

		final Container content = getBodyPanel();

		content.setLayout(new GridLayout(3, 2, 10, 10));
		
		this.txtNeuronCount = new JTextField();
		this.cbThresholds = new JCheckBox();
				
		content.add(new JLabel("Neuron Count"));
		content.add(this.txtNeuronCount);

		content.add(new JLabel("Use Thresholds"));
		content.add(this.cbThresholds);
		
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
