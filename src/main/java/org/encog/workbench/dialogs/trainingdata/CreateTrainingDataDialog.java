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
package org.encog.workbench.dialogs.trainingdata;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.createfile.CreateFileType;
import org.encog.workbench.dialogs.createnetwork.NeuralNetworkType;

public class CreateTrainingDataDialog extends EncogCommonDialog implements
	ListSelectionListener {
		
	private JTextField objectNameField;
	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private TrainingDataType type;

	public CreateTrainingDataDialog(Frame owner) {
		super(owner);
		setTitle("Create Training Data");

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
		
		Component comp1 = new JLabel("File Name:  ");
		this.objectNameField = new JTextField(20); 
		
		gridBag.setConstraints(comp1, c);
		c.weightx = 1.0;
		gridBag.setConstraints(this.objectNameField, c);
		
		bottom.add(comp1);
		bottom.add(this.objectNameField);
		
		content.add(bottom,BorderLayout.SOUTH);
		
		this.model.addElement("Copy Training Set from File");
		this.model.addElement("Market Data Training Set");
		this.model.addElement("Random Training Set");		
		this.model.addElement("XOR Temporal Training Set");
		this.model.addElement("XOR Training Set");
		this.model.addElement("Iris Dataset");
		this.model.addElement("Sunspot Dataset");
		this.model.addElement("Digits");
		this.model.addElement("Simple Pattern (part 1)");
		this.model.addElement("Simple Pattern (part 2)");
		
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
			this.type = TrainingDataType.CopyCSV;
			break;
		case 1:
			this.type = TrainingDataType.MarketWindow;
			break;
		case 2:
			this.type = TrainingDataType.Random;
			break;
		case 3:
			this.type = TrainingDataType.XORTemp;
			break;
		case 4:
			this.type = TrainingDataType.XOR;
			break;		
		case 5:
			this.type = TrainingDataType.Iris;
			break;		
		case 6:
			this.type = TrainingDataType.Sunspots;
			break;			
		case 7:
			this.type = TrainingDataType.Digits;
			break;		
		case 8:
			this.type = TrainingDataType.Patterns1;
			break;		
		case 9:
			this.type = TrainingDataType.Patterns2;
			break;		
			
		}
	}

	@Override
	public void setFields() {
		switch (type) {
		case CopyCSV:
			this.list.setSelectedIndex(0);
			break;
		case MarketWindow:
			this.list.setSelectedIndex(1);
			break;
		case Random:
			this.list.setSelectedIndex(2);
			break;
		case XORTemp:
			this.list.setSelectedIndex(3);
			break;
		case XOR:
			this.list.setSelectedIndex(4);
			break;
		case Iris:
			this.list.setSelectedIndex(5);
			break;	
		case Sunspots:
			this.list.setSelectedIndex(6);
			break;
		case Digits:
			this.list.setSelectedIndex(7);
			break;
		case Patterns1:
			this.list.setSelectedIndex(8);
			break;
		case Patterns2:
			this.list.setSelectedIndex(9);
			break;
		}

	}

	public TrainingDataType getType() {
		return type;
	}

	public void setType(TrainingDataType type) {
		this.type = type;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text
					.setText("Copy training data from a CSV.");
			break;
								
		case 1:
			this.text
					.setText("Download market data from Yahoo Finance.  You need to enter a ticker symbol and date range.  You must also specify the size of the input window used to predict the output/prediction window.");
			break;

		case 2:
			this.text
					.setText("Create a training set of random numbers.  This is really only useful for some testing purposes.  ");
			break;

		case 3:
			this.text
					.setText("XOR temporal data.  Represent XOR as a sequence of numbers, 1 input 1 output.  Output is the next predicted input.");
			break;
			
		case 4:
			this.text
					.setText("Classic XOR operator as CSV data.");
			break;
			
		case 5:
			this.text
					.setText("The Iris dataset is a classic machine learning dataset.  It contains 4 characteristics about 3 different iris species.");
			break;

		case 6:
			this.text
					.setText("Download sunspot information from the Internet.");
			break;
		case 7:
			this.text
					.setText("The 10 arabic digits.  Width is 5, height is 7.");
			break;
			
		case 8:
			this.text
					.setText("A simple set of patterns.  Width is 5, height is 7.");
			break;

			
		case 9:
			this.text
					.setText("More simple patters, eimilar to part 1. Part 2 can be used to find the best match in part 1.  Width is 10, height is 10.");
			break;

			
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);

	}

	public String getFilenameName() {
		return this.objectNameField.getText();
	}
}
