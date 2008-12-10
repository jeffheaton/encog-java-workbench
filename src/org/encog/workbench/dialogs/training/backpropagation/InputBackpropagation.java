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
package org.encog.workbench.dialogs.training.backpropagation;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

/**
 * A dialog box that inputs for the parameters to use with
 * the backpropagation training method.
 * @author jheaton
 *
 */
public class InputBackpropagation extends BasicTrainingInput {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Text field for the learning rate.
	 */
	public JTextField txtlearningRate;
	
	/**
	 * Text field for the momentum.
	 */
	public JTextField txtmomentum;
	
	/**
	 * The learning rate.
	 */
	private double learningRate;

	/**
	 * The momentum.
	 */
	private double momentum;

	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputBackpropagation(final Frame owner) {
		super(owner);
		setTitle("Train Backpropagation");

		this.setSize(300, 240);
		this.setLocation(200, 100);

		this.txtmomentum = new JTextField();
		this.txtlearningRate = new JTextField();

		final Container content = getBodyPanel();

		content.setLayout(new GridLayout(6, 1, 10, 10));

		content.add(new JLabel("Momentum"));
		content.add(this.txtmomentum);

		content.add(new JLabel("Learning Rate"));
		content.add(this.txtlearningRate);

		this.txtlearningRate.setText("0.7");
		this.txtmomentum.setText("0.7");

	}

	/**
	 * Collect the data from all fields.
	 */
	@Override
	public void collectFields() throws ValidationException {
		super.collectFields();
		this.learningRate = this.validateFieldNumeric("learning rate",
				this.txtlearningRate);
		this.momentum = this.validateFieldNumeric("momentum", this.txtmomentum);
	}

	/**
	 * @return the learningRate
	 */
	public double getLearningRate() {
		return this.learningRate;
	}

	/**
	 * @return the momentum
	 */
	public double getMomentum() {
		return this.momentum;
	}

	/**
	 * Not used.
	 */
	@Override
	public void setFields() {
	}

}
