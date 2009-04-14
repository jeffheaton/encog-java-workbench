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
package org.encog.workbench.dialogs.training.manhattan;

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
public class InputManhattan extends BasicTrainingInput {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Text field for the learning rate.
	 */
	public JTextField txtFixedDelta;
	
	
	/**
	 * The learning rate.
	 */
	private double fixedDelta;

	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputManhattan(final Frame owner) {
		super(owner);
		setTitle("Train Manhattan Update Rule");

		this.setSize(300, 240);
		this.setLocation(200, 100);

		this.txtFixedDelta = new JTextField();

		final Container content = getBodyPanel();

		content.setLayout(new GridLayout(6, 1, 10, 10));

		content.add(new JLabel("Update Delta"));
		content.add(this.txtFixedDelta);

		this.txtFixedDelta.setText("0.000001");

	}

	/**
	 * Collect the data from all fields.
	 */
	@Override
	public void collectFields() throws ValidationException {
		super.collectFields();
		this.fixedDelta = this.validateFieldNumeric("fixed delta",
				this.txtFixedDelta);		
	}

	/**
	 * Not used.
	 */
	@Override
	public void setFields() {
	}

	public double getFixedDelta() {
		return fixedDelta;
	}
	
	

}
