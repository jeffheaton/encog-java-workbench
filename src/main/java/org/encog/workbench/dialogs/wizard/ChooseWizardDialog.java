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
package org.encog.workbench.dialogs.wizard;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.encog.ml.MLMethod;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public class ChooseWizardDialog extends EncogCommonDialog implements
		ListSelectionListener {

	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private WizardType type;

	public ChooseWizardDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		setTitle("Choose a Wizard");

		this.setSize(500, 250);
		this.setLocation(50, 100);
		final Container content = getBodyPanel();
		content.setLayout(new GridLayout(1, 2));

		content.add(this.scroll1);
		content.add(this.scroll2);

		this.model.addElement("Analyst Wizard");
		this.model.addElement("Realtime Analyst Wizard");

		this.list.addListSelectionListener(this);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.text.setEditable(false);
		scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.type = WizardType.AnalystWizard;
	}

	@Override
	public void collectFields() throws ValidationException {
		switch (list.getSelectedIndex()) {
		case 0:
			this.type = WizardType.AnalystWizard;
			break;
		case 1:
			this.type = WizardType.RealTimeAnalystWizard;
			break;
		}
	}

	@Override
	public void setFields() {
		switch (type) {
		case AnalystWizard:
			this.list.setSelectedIndex(0);
			break;
		case RealTimeAnalystWizard:
			this.list.setSelectedIndex(1);
			break;
		}
	}

	public WizardType getTheType() {
		return type;
	}

	public void setType(WizardType type) {
		this.type = type;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text.setText("Encog Analyst Wizard allows you to create a Encog Analyst project from a CSV file.  Data can be automatically normalized and split into training sets.");
			break;
		case 1:
			this.text.setText("Real-Time Encog Analyst Wizard is used to create an Encog Analyst project for dealing with real-time collected data.  This is typically used with Encog cloud connections to Ninjatrader and others.");
			break;
		}
		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);
	}
}
