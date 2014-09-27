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

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public class CreatePopulationDialog  extends EncogCommonDialog implements
ListSelectionListener {
	
	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private CreatePopulationType theType = CreatePopulationType.EPL;

	public CreatePopulationDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		setTitle("Create a Population");

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
		
		c.weightx = 1.0;
		
		content.add(bottom,BorderLayout.SOUTH);
		
		this.model.addElement("NEAT Population");
		this.model.addElement("EPL Population");
		
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
			this.theType = CreatePopulationType.NEAT;
			break;
		case 1:
			this.theType = CreatePopulationType.EPL;
			break;
		}
	}

	@Override
	public void setFields() {
		switch (theType) {
		case NEAT:
			this.list.setSelectedIndex(0);
			break;
		case EPL:
			this.list.setSelectedIndex(1);
			break;			
		}
	}

	public CreatePopulationType getTheType() {
		return theType;
	}

	public void setTheType(CreatePopulationType t) {
		this.theType = t;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text
					.setText("Create NeuroEvolution of Augmenting Topologies (NEAT) population.  This will create a population of genomes that can be used to create NEAT neural networks.  NEAT networks are trained using a genetic algorithm both to vary weights and structures.");
			break;
		case 1:
			this.text
					.setText("Create a population of Encog Programming Language (EPL) programs.  EPL can represent mathematical expressions or programs.  EPL can be trained to perform regression, classification and other tasks.");
			break;
			
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);
	}
}

