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
package org.encog.workbench.dialogs.training.hopfield;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

/**
 * Input the training parameters for Hopfield training.
 */
public class InputHopfield extends BasicTrainingInput {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form UsersInput */
	public InputHopfield(final Frame owner) {
		super(owner);
		setTitle("Train Hopfield Layers");

		this.setSize(300, 240);
		this.setLocation(200, 100);

		final Container content = getBodyPanel();

		content.setLayout(new GridLayout(6, 1, 10, 10));

	}

	/**
	 * Collect the data.
	 */
	@Override
	public void collectFields() throws ValidationException {
		super.collectFields();
	}

	/**
	 * Not used.
	 */
	@Override
	public void setFields() {
	}
}
