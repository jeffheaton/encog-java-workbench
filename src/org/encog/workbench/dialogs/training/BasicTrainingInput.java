package org.encog.workbench.dialogs.training;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public abstract class BasicTrainingInput extends NetworkAndTrainingDialog {

	private JTextField txtmaximumError;
	private double maxError;
	
	public BasicTrainingInput(Frame owner) {
		super(owner);
		
		JPanel content = this.getBodyPanel();
		
		content.add(new JLabel("Maximum Error"));
		content.add(txtmaximumError = new JTextField());
		
		this.txtmaximumError.setText("0.01");
	}
	

	/**
	 * @return the maxError
	 */
	public double getMaxError() {
		return maxError;
	}
	

	
	public void collectFields() throws ValidationException {
		super.collectFields();
		this.maxError = this.validateFieldNumeric("maximum error", this.txtmaximumError);
	}

}
