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
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.process.generate.Generate.GenerateLanguage;

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
public class GenerateCode extends NetworkAndTrainingDialog {

	private JComboBox cbLanguage;
	private JComboBox cbTraining;
	private JComboBox cbCopyTraining;
	
	private GenerateLanguage language;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new form UsersInput */
    public GenerateCode(Frame owner) {
    	super(owner);
    	findData();
    	this.setTitle("Generate Code");
    	this.setSize(300, 240);
    	this.setLocation(200, 100);

    		cbCopyTraining = new JComboBox();
         cbLanguage = new JComboBox();
         cbTraining = new JComboBox();

         setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

         String[] languages = {"Java","C#","VB.Net"};
         cbLanguage.setModel(new DefaultComboBoxModel(languages));
         String[] training = {"Backpropagation","Genetic Algorithm", "Simulated Annealing"};
         cbTraining.setModel(new DefaultComboBoxModel(training));
         String[] save = {"Yes","No"};
         cbCopyTraining.setModel(new DefaultComboBoxModel(save));
        
         
         JPanel jp = this.getBodyPanel();
         jp.setLayout(new GridLayout(6,1,10,10));

         jp.add(new JLabel("Language"));
         jp.add(cbLanguage); 
         
         jp.add(new JLabel("Training Method"));
         jp.add(cbTraining);
         
         jp.add(new JLabel("Copy Training Set to Code"));
         jp.add(cbCopyTraining); 
      
    }


	@Override
	public void collectFields() throws ValidationException {
		switch( this.cbLanguage.getSelectedIndex() )
		{
			case 0:
				this.language = GenerateLanguage.Java;
				break;
			case 1:
				this.language = GenerateLanguage.CS;
				break;
			case 2:
				this.language = GenerateLanguage.VB;
				break;
		}
		
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @return the language
	 */
	public GenerateLanguage getLanguage() {
		return language;
	}
   
	
	
}
