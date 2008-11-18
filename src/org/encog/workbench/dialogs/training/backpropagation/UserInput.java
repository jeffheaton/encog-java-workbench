package org.encog.workbench.dialogs.training.backpropagation;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.training.RunBackpropagation;
import org.encog.workbench.training.TrainingInput;


public class UserInput extends JDialog implements ActionListener  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new form UsersInput */
    public UserInput(Frame owner) {
    	super(owner, true);
    	this.setSize(300, 240);
    	this.setLocation(200, 100);
    	 lblmomentum = new JLabel();
         lbllearningRate = new JLabel();
         lblmaximumError = new JLabel();
         lbltrainingDataName = new JLabel();
         lblneuralNetworkName = new JLabel();
         txtmomentum = new JTextField();
         txtlearningRate = new JTextField();
         txtmaximumError = new JTextField();
         btnSubmit = new JButton();
         JButton btnCancel = new JButton();
         cbotrainingDataName = new JComboBox();
         cboneuralNetworkName = new JComboBox();
         lbllearningRate1 = new JLabel();
         setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         lblmomentum.setText("Momentum");
         lbllearningRate.setText("LearningRate");
         lblmaximumError.setText("MaximumError");
         lbltrainingDataName.setText("TrainingDataName");
         lblneuralNetworkName.setText("NeuralNetworkName");
         btnSubmit.setText("Submit");
         btnCancel.setText("Close");
         btnSubmit.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
                 btnSubmitActionPerformed(evt);
             }
         });
         btnCancel.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
                 btnCancelActionPerformed(evt);
             }
         });
         cbotrainingDataName.setModel(new DefaultComboBoxModel(new String[] { "data-1" }));
         cboneuralNetworkName.setModel(new DefaultComboBoxModel(new String[] { "network-1" }));
         lbllearningRate1.setText("LearningRate");
         Container content = this.getContentPane();
         
         JPanel jp = new JPanel();
         jp.setLayout(new GridLayout(6,1,10,10));

         jp.add(lblmomentum);
         jp.add(txtmomentum); 
         
         jp.add(lbllearningRate);
         jp.add(txtlearningRate);
         
         jp.add(lblmaximumError);
         jp.add(txtmaximumError); 
         
         jp.add(lbltrainingDataName);
         jp.add(cbotrainingDataName);
         
         jp.add(lblneuralNetworkName);
         jp.add(cboneuralNetworkName);
         
         
         jp.add(btnSubmit);
         jp.add(btnCancel);
         
         content.add(jp, BorderLayout.CENTER);
         

    }

  private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)
	  {
	  	this.dispose();
	  }
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {
    	TrainingInput trainingInput = new TrainingInput();
    	trainingInput.setlearningRate(Double.parseDouble(txtlearningRate.getText()));
    	trainingInput.setmaximumError(Double.parseDouble(txtmaximumError.getText()));
    	trainingInput.setmomentum(Double.parseDouble(txtmomentum.getText()));
    	trainingInput.setneuralNetworkName(cboneuralNetworkName.getSelectedItem().toString());
    	trainingInput.settrainingDataName(cbotrainingDataName.getSelectedItem().toString());
    	//RunBackpropagation train = new RunBackpropagation();
		//train.begin(trainingInput);
    	dispose();
    	
    	String nameNetwork = cboneuralNetworkName.getSelectedItem().toString();
    	String nameTraining = cbotrainingDataName.getSelectedItem().toString();
    	
    	BasicNetwork network = (BasicNetwork)EncogWorkBench.getInstance().getCurrentFile().find(nameNetwork);
    	NeuralDataSet training = (NeuralDataSet)EncogWorkBench.getInstance().getCurrentFile().find(nameTraining);
    	    	
    	ProgressBackpropagation train = new ProgressBackpropagation(
    			EncogWorkBench.getInstance().getMainWindow(),
    			network,
    			training,
    	    	Double.parseDouble(txtlearningRate.getText()),
    	    	Double.parseDouble(txtmaximumError.getText()),
    	    	Double.parseDouble(txtmomentum.getText()));

    			
    	train.setVisible(true);
    	
    }
   
    // Variables declaration 
    private JButton btnSubmit;
    public JComboBox cboneuralNetworkName;
    public JComboBox cbotrainingDataName;
    private JLabel lbllearningRate;
    private JLabel lbllearningRate1;
    private JLabel lblmaximumError;
    private JLabel lblmomentum;
    private JLabel lblneuralNetworkName;
    private JLabel lbltrainingDataName;
    public  JTextField txtlearningRate;
    public JTextField txtmaximumError;
    public JTextField txtmomentum;
    // End of variables declaration
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
