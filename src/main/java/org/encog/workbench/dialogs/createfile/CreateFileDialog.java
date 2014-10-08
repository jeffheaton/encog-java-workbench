/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.createfile;

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

public class CreateFileDialog  extends EncogCommonDialog implements
ListSelectionListener {
	
	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private CreateFileType theType;
	private JTextField objectNameField;
	private String filename;

	public CreateFileDialog(Frame owner) {
		super(owner);
		setTitle("Create a File");

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
		
		this.model.addElement("Text File (*.txt)");
		this.model.addElement("Machine Learning Method (*.eg)");
		this.model.addElement("CSV File (*.csv)");
		this.model.addElement("Training File (*.egb)");
		this.model.addElement("Population (*.eg)");
		this.model.addElement("Encog Analyst Financial Indicator (*.ega)");
		
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
			this.theType = CreateFileType.TextFile;
			break;
		case 1:
			this.theType = CreateFileType.MachineLearningMethod;
			break;
		case 2:
			this.theType = CreateFileType.CSVFile;
			break;
		case 3:
			this.theType = CreateFileType.TrainingFile;
			break;
		case 4:
			this.theType = CreateFileType.Population;
			break;
		case 5:
			this.theType = CreateFileType.AnalystIndicator;
			break;
		}
		
		this.filename = this.objectNameField.getText();
	}

	@Override
	public void setFields() {
		switch (theType) {
		case TextFile:
			this.list.setSelectedIndex(0);
			break;
		case MachineLearningMethod:
			this.list.setSelectedIndex(1);
			break;			
		case CSVFile:
			this.list.setSelectedIndex(2);
			break;
		case TrainingFile:
			this.list.setSelectedIndex(3);
			break;
		case Population:
			this.list.setSelectedIndex(4);
			break;
		case AnalystIndicator:
			this.list.setSelectedIndex(5);
			break;
		}
	}

	public CreateFileType getTheType() {
		return theType;
	}

	public void setTheType(CreateFileType t) {
		this.theType = t;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text
					.setText("A regular text file.  Text files usually end in .txt, and hold text data.");
			break;
		case 1:
			this.text
					.setText("A machine learning method learns to recognize patterns in your data.  Machine learning methods include neural networks, support vector machines, and others.");
			break;
		case 2:
			this.text
					.setText("CSV files hold tables of data.  Encog uses CSV files for many different purposes.");
			break;
		case 3:
			this.text
					.setText("A binary training file.  Encog training files can be either supervised or unsupervised.  They are stored in a cross-platform binary format and can be easily converted to/from CSV files.");
			break;
		case 4:
			this.text.setText("Create a population for an Evolutionary Algorithm (EA).");
			break;
		case 5:
			this.text
					.setText("Create an Encog Analyst file to generate a Ninjatrader or MetaTrader indicator.  Note: To create a regular Encog Analst script, right-click a .CSV file and choose the Analyst Wizard.");
			break;

			
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);
	}

	public String getFilename() {
		return this.filename;
	}
	
	
	
	
}

