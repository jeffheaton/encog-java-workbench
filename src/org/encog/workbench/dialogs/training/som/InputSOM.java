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
package org.encog.workbench.dialogs.training.som;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.neural.networks.training.som.TrainSelfOrganizingMap.LearningMethod;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class InputSOM extends BasicTrainingInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variables declaration
	private final JTextField txtlearningRate;
	private double learningRate;
	private final JComboBox cbMethod;

	private LearningMethod method;

	/** Creates new form UsersInput */
	public InputSOM(final Frame owner) {
		super(owner);
		setTitle("Train SOM Layers");

		this.setSize(300, 240);
		this.setLocation(200, 100);

		this.txtlearningRate = new JTextField();

		final Container content = getBodyPanel();
		content.setLayout(new GridLayout(6, 1, 10, 10));

		this.cbMethod = new JComboBox();
		final String[] languages = { "Additive", "Subtractive" };

		/**
		 * Subtractive learning.
		 */
		this.cbMethod.setModel(new DefaultComboBoxModel(languages));

		content.add(new JLabel("Learning Rate"));
		content.add(this.txtlearningRate);

		content.add(new JLabel("Method"));
		content.add(this.cbMethod);

		this.txtlearningRate.setText("0.7");

	}

	@Override
	public void collectFields() throws ValidationException {
		super.collectFields();
		this.learningRate = this.validateFieldNumeric("learning rate",
				this.txtlearningRate);
		if (this.cbMethod.getSelectedIndex() == 1) {
			this.method = LearningMethod.ADDITIVE;
		} else {
			this.method = LearningMethod.SUBTRACTIVE;
		}

	}

	/**
	 * @return the learningRate
	 */
	public double getLearningRate() {
		return this.learningRate;
	}

	/**
	 * @return the method
	 */
	public LearningMethod getMethod() {
		return this.method;
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub

	}

}
