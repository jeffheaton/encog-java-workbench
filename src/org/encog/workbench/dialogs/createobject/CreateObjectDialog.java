/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.dialogs.createobject;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.process.validate.ResourceNameValidate;

public class CreateObjectDialog  extends EncogCommonDialog implements
ListSelectionListener {
	
	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private ObjectType type;
	private JTextField objectNameField;
	private String resourceName;

	public CreateObjectDialog(Frame owner) {
		super(owner);
		setTitle("Create Object");

		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		
		this.setSize(500, 250);
		this.setLocation(50, 100);
		final Container content = getBodyPanel();
		content.setLayout(new BorderLayout());
		top.setLayout(new GridLayout(1, 2));
		top.add(this.scroll1);
		top.add(this.scroll2);

		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		
		content.add(top,BorderLayout.CENTER);
		bottom.setLayout(gridBag);
		
		Component comp1 = new JLabel("Object Name:  ");
		this.objectNameField = new JTextField(20); 
		
		gridBag.setConstraints(comp1, c);
		c.weightx = 1.0;
		gridBag.setConstraints(this.objectNameField, c);
		
		bottom.add(comp1);
		bottom.add(this.objectNameField);
		
		content.add(bottom,BorderLayout.SOUTH);
		
		//this.model.addElement("Data Normalization");
		this.model.addElement("Encog Script");
		this.model.addElement("Neural Network");
		this.model.addElement("NEAT Population");
		this.model.addElement("Property Data");
		this.model.addElement("Text");
		this.model.addElement("Training Data");		
		
		this.list.addListSelectionListener(this);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.text.setEditable(false);
		scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5882600361686632769L;
	

	@Override
	public void collectFields() throws ValidationException {
		switch (list.getSelectedIndex()) {
		case 0:
			this.type = ObjectType.EncogScript;
			break;
		case 1:
			this.type = ObjectType.NeuralNetwork;
			break;
		case 2:
			this.type = ObjectType.NEATPopulation;
			break;		
		case 3:
			this.type = ObjectType.PropertyData;
			break;
		case 4:
			this.type = ObjectType.Text;
			break;
		case 5:
			this.type = ObjectType.TrainingData;
			break;	
		}
		
		this.resourceName = this.objectNameField.getText();
		
		String error = ResourceNameValidate.validateResourceName(resourceName);
		if( error!=null ) {
			throw new ValidationException(error);
		}
	}

	@Override
	public void setFields() {
		switch (type) {
		/*case DataNormalization:
			this.list.setSelectedIndex(0);
			break;*/
		case EncogScript:
			this.list.setSelectedIndex(0);
			break;
		case NeuralNetwork:
			this.list.setSelectedIndex(1);
			break;
		case NEATPopulation:
			this.list.setSelectedIndex(2);
			break;
		case PropertyData:
			this.list.setSelectedIndex(3);
			break;
		case Text:
			this.list.setSelectedIndex(4);
			break;
		case TrainingData:
			this.list.setSelectedIndex(5);
			break;
			
		}

	}

	public ObjectType getType() {
		return type;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		/*case 0:
			this.text
					.setText("Data for neural networks is rarely used in raw form.  This data must be normalized to be in specific ranges, generally between 0 and 1, or -1 and +1.");
			break;	*/	
		case 0:
			this.text
					.setText("A script is a series of commands to perform operations on Encog objects.");
			break;
		case 1:
			this.text
					.setText("A neural network is a construct that can recognize patterns.  Encog supports many different neural network architectures.  After selecting to create a neural network you will be allowed to choose the type of neural network to create.");
			break;

		case 2:
			this.text
					.setText("Create NeuroEvolution of Augmenting Topologies (NEAT) population.  This will create a population of genomes that can be used to create NEAT neural networks.  NEAT networks are trained using a genetic algorithm both to vary weights and structures.");
			break;
								
		case 3:
			this.text
					.setText("Property data is a collection of name-value pairs.  Property data is not directly used by any part of Encog, however, third party applications often use property data to store configuration information with an Encog EG file.");
			break;

		case 4:
			this.text
					.setText("Text object can be used to store freeform text.  Though not directly used by Encog, they can be used to store configuration or \"readme\" information in an Encog EG file.");
			break;

		case 5:
			this.text
					.setText("Training data is used to train and evaluate neural networks.  There are a number of ways that training data can be generated.");
			break;
			
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);
	}

	public String getResourceName() {
		return resourceName;
	}
	
	
	
	
}

