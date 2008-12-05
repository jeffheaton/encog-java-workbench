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
package org.encog.workbench.dialogs;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.process.generate.Generate.GenerateLanguage;
import org.encog.workbench.process.generate.Generate.TrainingMethod;

public class GenerateCode extends NetworkAndTrainingDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JComboBox cbLanguage;
	private final JComboBox cbTraining;

	private final JComboBox cbCopyTraining;
	private GenerateLanguage language;
	private TrainingMethod trainingMethod;

	private boolean copyTraining;

	/** Creates new form UsersInput */
	public GenerateCode(final Frame owner) {
		super(owner);
		findData();
		setTitle("Generate Code");
		this.setSize(300, 240);
		this.setLocation(200, 100);

		this.cbCopyTraining = new JComboBox();
		this.cbLanguage = new JComboBox();
		this.cbTraining = new JComboBox();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		final String[] languages = { "Java", "C#", "VB.Net" };
		this.cbLanguage.setModel(new DefaultComboBoxModel(languages));
		final String[] training = { "Backpropagation", "Genetic Algorithm",
				"Simulated Annealing", "Hopfield", "Self Organizing",
				"Do Not Train/Already Trained" };
		this.cbTraining.setModel(new DefaultComboBoxModel(training));
		final String[] save = { "Yes", "No" };
		this.cbCopyTraining.setModel(new DefaultComboBoxModel(save));

		final JPanel jp = getBodyPanel();
		jp.setLayout(new GridLayout(6, 1, 10, 10));

		jp.add(new JLabel("Language"));
		jp.add(this.cbLanguage);

		jp.add(new JLabel("Training Method"));
		jp.add(this.cbTraining);

		jp.add(new JLabel("Copy Training Set to Code"));
		jp.add(this.cbCopyTraining);

	}

	@Override
	public void collectFields() throws ValidationException {
		super.collectFields();
		switch (this.cbLanguage.getSelectedIndex()) {
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

		switch (this.cbTraining.getSelectedIndex()) {
		case 0:
			this.trainingMethod = TrainingMethod.Backpropagation;
			break;
		case 1:
			this.trainingMethod = TrainingMethod.Genetic;
			break;
		case 2:
			this.trainingMethod = TrainingMethod.Anneal;
			break;
		case 3:
			this.trainingMethod = TrainingMethod.TrainHopfield;
			break;
		case 4:
			this.trainingMethod = TrainingMethod.TrainSOM;
			break;
		case 5:
			this.trainingMethod = TrainingMethod.NoTraining;
			break;
		}

		if (this.cbCopyTraining.getSelectedIndex() == 0) {
			this.copyTraining = true;
		} else {
			this.copyTraining = false;
		}

	}

	/**
	 * @return the language
	 */
	public GenerateLanguage getLanguage() {
		return this.language;
	}

	/**
	 * @return the trainingMethod
	 */
	public TrainingMethod getTrainingMethod() {
		return this.trainingMethod;
	}

	/**
	 * @return the copyTraining
	 */
	public boolean isCopyTraining() {
		return this.copyTraining;
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub

	}

}
