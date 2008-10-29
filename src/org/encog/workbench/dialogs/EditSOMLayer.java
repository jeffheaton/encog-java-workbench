package org.encog.workbench.dialogs;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationLinear;
import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.activation.ActivationTANH;
import org.encog.util.NormalizeInput;

public class EditSOMLayer extends JDialog implements WindowListener, ActionListener {

	public enum Command
	{
		OK,
		CANCEL
	};
	
	private JTextField ctrlName;
	private JTextField ctrlDescription;
	private JTextField ctrlNeuronCount;
	private JComboBox ctrlNormalization;
	private JButton ctrlOK;
	private JButton ctrlCancel;
	private String resultName;
	private String resultDescription;
	private NormalizeInput.NormalizationType resultNormalization;
	private int resultNeuronCount;
	private Command command;
	
	public EditSOMLayer(Frame owner) {
		super(owner, true);
		
		this.setLocation(200, 100);
		setTitle("Self Organizing Map (SOM) Layer");
		
		Container content = this.getContentPane();		
		content.setLayout(new GridLayout(5,1,10,10));
		content.add(new JLabel("Name"));
		content.add(this.ctrlName = new JTextField());
		content.add(new JLabel("Description"));
		content.add(this.ctrlDescription = new JTextField());
		content.add(new JLabel("Normalization"));
		content.add(ctrlNormalization = new JComboBox());
		content.add(new JLabel("Neuron Count"));
		content.add(this.ctrlNeuronCount = new JTextField());
		content.add(ctrlOK = new JButton("OK"));
		content.add(ctrlCancel = new JButton("Cancel"));
				
		ctrlNormalization.setModel(new DefaultComboBoxModel(new String[] { "Multiplicative", "Z-Axis" }));
		
		this.setModal(true);
		this.setResizable(false);
		
		pack();
		
		this.ctrlOK.addActionListener(this);
		this.ctrlCancel.addActionListener(this);
		this.addWindowListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()== this.ctrlOK )
		{
			this.resultName = this.ctrlName.getText();
			this.resultDescription = this.ctrlDescription.getText();
			
			try
			{
				this.resultNeuronCount = Integer.parseInt(this.ctrlNeuronCount.getText());
			}
			catch(NumberFormatException ex)
			{
				// just ignore it
			}
			
			switch( this.ctrlNormalization.getSelectedIndex() )
			{
				case 0:
					this.resultNormalization = NormalizeInput.NormalizationType.MULTIPLICATIVE;
					break;
				case 1:
					this.resultNormalization = NormalizeInput.NormalizationType.Z_AXIS;
					break;
			}			
			
			this.command = Command.OK;
			this.dispose();
		}
		else if( e.getSource()== this.ctrlCancel )
		{
			this.command = Command.CANCEL;
			this.dispose();
		}
		
	}

	/**
	 * @return the resultName
	 */
	public String getResultName() {
		return resultName;
	}

	/**
	 * @param resultName the resultName to set
	 */
	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	/**
	 * @return the resultDescription
	 */
	public String getResultDescription() {
		return resultDescription;
	}

	/**
	 * @param resultDescription the resultDescription to set
	 */
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	/**
	 * @return the resultNeuronCount
	 */
	public int getResultNeuronCount() {
		return resultNeuronCount;
	}

	/**
	 * @param resultNeuronCount the resultNeuronCount to set
	 */
	public void setResultNeuronCount(int resultNeuronCount) {
		this.resultNeuronCount = resultNeuronCount;
	}

	public void windowActivated(WindowEvent e) {
		this.ctrlName.setText(this.resultName);
		this.ctrlDescription.setText(this.resultDescription);
		this.ctrlNeuronCount.setText(""+this.resultNeuronCount);
		if(this.resultNormalization == NormalizeInput.NormalizationType.MULTIPLICATIVE )
		{
			this.ctrlNormalization.setSelectedIndex(0);
		}
		else if(this.resultNormalization == NormalizeInput.NormalizationType.Z_AXIS )
		{
			this.ctrlNormalization.setSelectedIndex(1);
		}		
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {

		
	}

	/**
	 * @return the command
	 */
	public Command getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(Command command) {
		this.command = command;
	}

	/**
	 * @return the resultNormalization
	 */
	public NormalizeInput.NormalizationType getResultNormalization() {
		return resultNormalization;
	}

	/**
	 * @param resultNormalization the resultNormalization to set
	 */
	public void setResultNormalization(
			NormalizeInput.NormalizationType resultNormalization) {
		this.resultNormalization = resultNormalization;
	}


	
	
	

}
