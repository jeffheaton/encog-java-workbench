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
package org.encog.workbench.dialogs.trainingdata;

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

		content.add(top, BorderLayout.CENTER);
		bottom.setLayout(gridBag);

		Component comp1 = new JLabel("File Name:  ");
		this.objectNameField = new JTextField(20);

		gridBag.setConstraints(comp1, c);
		c.weightx = 1.0;
		gridBag.setConstraints(this.objectNameField, c);

		bottom.add(comp1);
		bottom.add(this.objectNameField);

		content.add(bottom, BorderLayout.SOUTH);

		this.model.addElement("Copy Training Set from File");
		this.model.addElement("Digits");
		this.model.addElement("Download from URL");
		this.model.addElement("Fahlman Encoder");
		this.model.addElement("Formula");
		this.model.addElement("Iris Dataset");
		this.model.addElement("Linear");
		this.model.addElement("Market Data Training Set");
		this.model.addElement("Random Training Set");
		this.model.addElement("Simple Pattern (part 1)");
		this.model.addElement("Simple Pattern (part 2)");
		this.model.addElement("Sine Wave");
		this.model.addElement("Sunspot Dataset");
		this.model.addElement("XOR Temporal Training Set");
		this.model.addElement("XOR Training Set");

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
			this.type = TrainingDataType.Digits;
			break;
		case 2:
			this.type = TrainingDataType.Download;
			break;
		case 3:
			this.type = TrainingDataType.Encoder;
			break;
		case 4:
			this.type = TrainingDataType.Formula;
			break;
		case 5:
			this.type = TrainingDataType.Iris;
			break;
		case 6:
			this.type = TrainingDataType.Linear;
			break;
		case 7:
			this.type = TrainingDataType.MarketWindow;
			break;
		case 8:
			this.type = TrainingDataType.Random;
			break;
		case 9:
			this.type = TrainingDataType.Patterns1;
			break;
		case 10:
			this.type = TrainingDataType.Patterns2;
			break;
		case 11:
			this.type = TrainingDataType.SineWave;
			break;
		case 12:
			this.type = TrainingDataType.Sunspots;
			break;
		case 13:
			this.type = TrainingDataType.XORTemp;
			break;
		case 14:
			this.type = TrainingDataType.XOR;
			break;

		}
	}

	@Override
	public void setFields() {
		switch (this.type) {
		case CopyCSV:
			this.list.setSelectedIndex(0);
			break;
		case Digits:
			this.list.setSelectedIndex(1);
			break;
		case Download:
			this.list.setSelectedIndex(2);
			break;
		case Encoder:
			this.list.setSelectedIndex(3);
			break;
		case Formula:
			this.list.setSelectedIndex(4);
			break;
		case Iris:
			this.list.setSelectedIndex(5);
			break;
		case Linear:
			this.list.setSelectedIndex(6);
			break;
		case MarketWindow:
			this.list.setSelectedIndex(7);
			break;
		case Random:
			this.list.setSelectedIndex(8);
			break;
		case Patterns1:
			this.list.setSelectedIndex(9);
			break;
		case Patterns2:
			this.list.setSelectedIndex(10);
			break;
		case SineWave:
			this.list.setSelectedIndex(11);
			break;
		case Sunspots:
			this.list.setSelectedIndex(12);
			break;
		case XORTemp:
			this.list.setSelectedIndex(13);
			break;
		case XOR:
			this.list.setSelectedIndex(14);
			break;
		}

	}

	public TrainingDataType getTheType() {
		return type;
	}

	public void setTheType(TrainingDataType type) {
		this.type = type;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text.setText("Copy training data from a CSV.");
			break;
		case 1:
			this.text
					.setText("The 10 arabic digits.  Width is 5, height is 7.");
			break;
		case 2:
			this.text
					.setText("Enter a URL and the contents will be downloaded to your project.");
			break;
		case 3:
			this.text
					.setText("A very simple data set that has the same number of inputs as ideals. Usually a smaller number of hidden neurons is placed between the input and output layers of a neural network trained with this data. The neural network must learn to encode the input to the smaller hidden layer.");
			break;
		case 4:
			this.text.setText("Generates data from a single-variable formula.");
			break;
		case 5:
			this.text
					.setText("The Iris dataset is a classic machine learning dataset.  It contains 4 characteristics about 3 different iris species.");
			break;
		case 6:
			this.text
					.setText("Generate linear data in slope-intercept (y=mx+b) form.");
			break;
		case 7:
			this.text
					.setText("Download market data from Yahoo Finance.  You need to enter a ticker symbol and date range.  You must also specify the size of the input window used to predict the output/prediction window.");
			break;
		case 8:
			this.text
					.setText("Create a training set of random numbers.  This is really only useful for some testing purposes.  ");
			break;
		case 9:
			this.text
					.setText("A simple set of patterns.  Width is 5, height is 7.");
			break;
		case 10:
			this.text
					.setText("More simple patters, eimilar to part 1. Part 2 can be used to find the best match in part 1.  Width is 10, height is 10.");
			break;
		case 11:
			this.text.setText("Generate one or more cycles of the sine wave.");
			break;
		case 12:
			this.text
					.setText("Download sunspot information from the Internet.");
			break;
		case 13:
			this.text
					.setText("XOR temporal data.  Represent XOR as a sequence of numbers, 1 input 1 output.  Output is the next predicted input.");
			break;

		case 14:
			this.text.setText("Classic XOR operator as CSV data.");
			break;
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);

	}

	public String getFilenameName() {
		return this.objectNameField.getText();
	}
}
