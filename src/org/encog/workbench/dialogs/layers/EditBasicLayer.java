package org.encog.workbench.dialogs.layers;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.models.EditNeuronModel;
import org.encog.workbench.models.MatrixTableModel;

public class EditBasicLayer extends EncogCommonDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3593792259701670934L;
	private JTextField txtNeuronCount;
	private JCheckBox cbThresholds;
	private final JScrollPane scroll;
	private final JTable table;
	private final EditNeuronModel model;
	
	private int neuronCount;
	private double[] thresholds;
	
	
	public EditBasicLayer(Frame owner) {
		super(owner);
		
		setTitle("Edit Basic Layer");

		this.setSize(300, 240);
		this.setLocation(200, 100);

		final Container content = getBodyPanel();

		content.setLayout(new GridLayout(3, 2, 10, 10));
		
		this.txtNeuronCount = new JTextField();
		this.cbThresholds = new JCheckBox();
		
		this.model = new EditNeuronModel(this);
		this.table = new JTable(this.model);
		this.scroll = new JScrollPane(this.table);
		this.cbThresholds.addActionListener(this);
				
		content.add(new JLabel("Neuron Count"));
		content.add(this.txtNeuronCount);

		content.add(new JLabel("Use Thresholds"));
		content.add(this.cbThresholds);
		
		content.add(new JLabel("Threshold Values"));
		content.add(this.scroll);
		
	}

	@Override
	public void collectFields() throws ValidationException {
		this.neuronCount = (int)this.validateFieldNumeric("neuron count", this.txtNeuronCount, 1, 10000);
	}

	@Override
	public void setFields() {
		this.txtNeuronCount.setText(""+this.neuronCount);
		this.cbThresholds.setSelected(this.thresholds!=null);		
	}

	public int getNeuronCount() {
		return neuronCount;
	}

	public void setNeuronCount(int neuronCount) {
		this.neuronCount = neuronCount;
	}

	public double[] getThresholds() {
		return thresholds;
	}

	public void setThresholds(double[] thresholds) {
		this.thresholds = thresholds;
	}
	
	public void actionPerformed(final ActionEvent e) {
		super.actionPerformed(e);
		if(this.cbThresholds.isSelected())
		{
			try {
				this.neuronCount = (int)this.validateFieldNumeric("neuron count", this.txtNeuronCount, 1, 10000);
				this.thresholds = new double[this.neuronCount];
			} catch (ValidationException e1) {
				EncogWorkBench.displayError("Field Error", e1.getMessage());
				this.cbThresholds.setSelected(false);
			}
			this.model.update();
			this.table.repaint();
		}
		else
		{
			this.thresholds = null;
			this.model.update();
			this.table.repaint();
		}
	}

}
