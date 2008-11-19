package org.encog.workbench.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;

/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
public class GenerateCode extends JDialog implements ActionListener {

	private List<String> trainingSets = new ArrayList<String>();
	private List<String> networks = new ArrayList<String>();

	// Variables declaration
	private JButton btnSubmit;
	public JComboBox cboneuralNetworkName;
	public JComboBox cbotrainingDataName;
	public JComboBox cbLanguage;
	public JComboBox cbTraining;
	public JComboBox cbSaveEG;

	// End of variables declaration
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new form UsersInput */
    public GenerateCode(Frame owner) {
    	super(owner, true);
    	findData();
    	this.setTitle("Generate Code");
    	this.setSize(300, 240);
    	this.setLocation(200, 100);

         cbSaveEG = new JComboBox();
         cbLanguage = new JComboBox();
         cbTraining = new JComboBox();
         btnSubmit = new JButton();
         JButton btnCancel = new JButton();
         cbotrainingDataName = new JComboBox();
         cboneuralNetworkName = new JComboBox();
         setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         btnSubmit.setText("Generate");
         btnCancel.setText("Cancel");
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
         cbotrainingDataName.setModel(new DefaultComboBoxModel(this.trainingSets.toArray()));
         cboneuralNetworkName.setModel(new DefaultComboBoxModel(this.networks.toArray()));
         String[] languages = {"Java","C#","VB.Net"};
         cbLanguage.setModel(new DefaultComboBoxModel(languages));
         String[] training = {"Backpropagation","Genetic Algorithm", "Simulated Annealing"};
         cbTraining.setModel(new DefaultComboBoxModel(training));
         String[] save = {"Yes","No"};
         cbSaveEG.setModel(new DefaultComboBoxModel(save));
         
         Container content = this.getContentPane();
         
         JPanel jp = new JPanel();
         jp.setLayout(new GridLayout(6,1,10,10));

         jp.add(new JLabel("Language"));
         jp.add(cbLanguage); 
         
         jp.add(new JLabel("Training Method"));
         jp.add(cbTraining);
         
         jp.add(new JLabel("Save Network"));
         jp.add(cbSaveEG); 
         
         jp.add(new JLabel("Network Name"));
         jp.add(cbotrainingDataName);
         
         jp.add(new JLabel("Training Set Name"));
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
    	    	
    }
    
    public void findData()
    {
    	for(EncogPersistedObject obj:EncogWorkBench.getInstance().getCurrentFile().getList())
    	{
    		if( obj instanceof BasicNetwork )
    		{
    			this.networks.add(obj.getName());
    		}
    		else if( obj instanceof BasicNeuralDataSet )
    		{
    			this.trainingSets.add(obj.getName());
    		}
    	}
    }
   
		
	
}
