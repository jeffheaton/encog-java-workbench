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
import org.encog.workbench.dialogs.common.ValidationException;

public abstract class BasicTrainingInput extends EncogCommonDialog {

	private JComboBox cboneuralNetworkName;
	private JComboBox cbotrainingDataName;
	private JTextField txtmaximumError;
	private String network;
	private String trainingSet;
	private double maxError;
	private List<String> trainingSets = new ArrayList<String>();
	private List<String> networks = new ArrayList<String>();
	
	public BasicTrainingInput(Frame owner) {
		super(owner);
		
		findData();
		
		cbotrainingDataName = new JComboBox();
		cboneuralNetworkName = new JComboBox();

		cbotrainingDataName.setModel(new DefaultComboBoxModel(this.trainingSets
				.toArray()));
		cboneuralNetworkName.setModel(new DefaultComboBoxModel(this.networks
				.toArray()));
		
		JPanel content = this.getBodyPanel();
		
		content.add(new JLabel("Training Set"));
		content.add(cbotrainingDataName);

		content.add(new JLabel("Network Name"));
		content.add(cboneuralNetworkName);	
		
		content.add(new JLabel("Maximum Error"));
		content.add(txtmaximumError = new JTextField());
		
		this.txtmaximumError.setText("0.01");
	}
	
	public void findData() {
		for (EncogPersistedObject obj : EncogWorkBench.getInstance()
				.getCurrentFile().getList()) {
			if (obj instanceof BasicNetwork) {
				this.networks.add(obj.getName());
			} else if (obj instanceof BasicNeuralDataSet) {
				this.trainingSets.add(obj.getName());
			}
		}
	}

	/**
	 * @return the maxError
	 */
	public double getMaxError() {
		return maxError;
	}
	
	/**
	 * @return the network
	 */
	public String getNetwork() {
		return network;
	}

	/**
	 * @return the trainingSet
	 */
	public String getTrainingSet() {
		return trainingSet;
	}
	
	public void collectFields() throws ValidationException {
		this.network = validateFieldString("network",this.cboneuralNetworkName,true);
		this.trainingSet = validateFieldString("training set", this.cbotrainingDataName,true);
		this.maxError = this.validateFieldNumeric("maximum error", this.txtmaximumError);
	}

}
