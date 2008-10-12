package org.encog.workbench.dialogs;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;



/*
 * UsersInput.java
 *
 * Created on October 10, 2008, 11:07 PM
 */



/**
 *
 * @author  manoj.talreja
 */
import org.encog.workbench.training.RunBackpropagation;
import org.encog.workbench.training.TrainingInput;
import org.encog.workbench.util.StringConst;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import sun.io.Converters;

public class UserInput extends JDialog implements ActionListener  {
    /** Creates new form UsersInput */
    public UserInput(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {

        lblmomentum = new javax.swing.JLabel();
        lbllearningRate = new javax.swing.JLabel();
        lblmaximumError = new javax.swing.JLabel();
        lbltrainingDataName = new javax.swing.JLabel();
        lblneuralNetworkName = new javax.swing.JLabel();
        txtmomentum = new javax.swing.JTextField();
        txtlearningRate = new javax.swing.JTextField();
        txtmaximumError = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        cbotrainingDataName = new javax.swing.JComboBox();
        cboneuralNetworkName = new javax.swing.JComboBox();
        lbllearningRate1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblmomentum.setText("Momentum");

        lbllearningRate.setText("LearningRate");

        lblmaximumError.setText("MaximumError");

        lbltrainingDataName.setText("TrainingDataName");

        lblneuralNetworkName.setText("NeuralNetworkName");

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        cbotrainingDataName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "data-1" }));

        cboneuralNetworkName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "network-1" }));

        lbllearningRate1.setText("LearningRate");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.LEADING)
              .add(layout.createSequentialGroup()
              .add(25, 25, 25)
              .add(layout.createParallelGroup(GroupLayout.TRAILING)
              .add(lblneuralNetworkName)
              .add(lblmomentum)
              .add(lbltrainingDataName)
                    .add(lblmaximumError)
                    .add(lbllearningRate))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(GroupLayout.LEADING, false)
                    .add(btnSubmit)
                    .add(txtmomentum, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .add(txtmaximumError, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .add(txtlearningRate, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .add(cbotrainingDataName, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(cboneuralNetworkName, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblmomentum)
                    .add(txtmomentum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .add(21, 21, 21)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(txtlearningRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(lbllearningRate)))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblmaximumError)
                    .add(txtmaximumError, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lbltrainingDataName)
                    .add(cbotrainingDataName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblneuralNetworkName)
                    .add(cboneuralNetworkName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.UNRELATED)
                .add(btnSubmit)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {
    	TrainingInput trainingInput = new TrainingInput();
    	trainingInput.setlearningRate(Double.parseDouble(txtlearningRate.getText()));
    	trainingInput.setmaximumError(Double.parseDouble(txtmaximumError.getText()));
    	trainingInput.setmomentum(Double.parseDouble(txtmomentum.getText()));
    	trainingInput.setneuralNetworkName(cboneuralNetworkName.getSelectedItem().toString());
    	trainingInput.settrainingDataName(cbotrainingDataName.getSelectedItem().toString());
    	RunBackpropagation train = new RunBackpropagation();
		train.begin(trainingInput);
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
