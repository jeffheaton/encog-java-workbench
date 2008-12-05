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
package org.encog.workbench.dialogs.training.genetic;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

public class InputGenetic extends BasicTrainingInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variables declaration
	private final JTextField txtPopulationSize;
	private final JTextField txtMutationPercent;
	private final JTextField txtPercentToMate;
	private int populationSize;
	private double mutationPercent;

	private double percentToMate;

	/** Creates new form UsersInput */
	public InputGenetic(final Frame owner) {
		super(owner);
		setTitle("Train Simulated Annealing");

		this.setSize(300, 240);
		this.setLocation(200, 100);

		this.txtPopulationSize = new JTextField();
		this.txtMutationPercent = new JTextField();
		this.txtPercentToMate = new JTextField();

		final Container content = getBodyPanel();

		content.setLayout(new GridLayout(6, 1, 10, 10));

		content.add(new JLabel("Population Size"));
		content.add(this.txtPopulationSize);

		content.add(new JLabel("Mutation Percent"));
		content.add(this.txtMutationPercent);

		content.add(new JLabel("Percent to Mate"));
		content.add(this.txtPercentToMate);

		this.txtPopulationSize.setText("5000");
		this.txtMutationPercent.setText("0.1");
		this.txtPercentToMate.setText("0.25");

	}

	@Override
	public void collectFields() throws ValidationException {
		super.collectFields();
		this.populationSize = (int) this.validateFieldNumeric(
				"population size", this.txtPopulationSize);
		this.percentToMate = this.validateFieldNumeric("percent to mate",
				this.txtPercentToMate);
		this.mutationPercent = this.validateFieldNumeric("mutation percent",
				this.txtMutationPercent);
	}

	public double getMutationPercent() {
		return this.mutationPercent;
	}

	public double getPercentToMate() {
		return this.percentToMate;
	}

	public int getPopulationSize() {
		return this.populationSize;
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub

	}
}
