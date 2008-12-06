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
package org.encog.workbench.dialogs.training;

import java.awt.Frame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public abstract class BasicTrainingInput extends NetworkAndTrainingDialog {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 6577905476465280583L;
	private JTextField txtmaximumError;
	private double maxError;

	public BasicTrainingInput(final Frame owner) {
		super(owner);

		final JPanel content = getBodyPanel();

		content.add(new JLabel("Maximum Error"));
		content.add(this.txtmaximumError = new JTextField());

		this.txtmaximumError.setText("0.01");
	}

	public void collectFields() throws ValidationException {
		super.collectFields();
		this.maxError = this.validateFieldNumeric("maximum error",
				this.txtmaximumError);
	}

	/**
	 * @return the maxError
	 */
	public double getMaxError() {
		return this.maxError;
	}

}
