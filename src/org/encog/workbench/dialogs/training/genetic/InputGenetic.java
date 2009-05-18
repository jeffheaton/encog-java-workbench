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

import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

/**
 * Dialog box to input the parameters for genetic training.
 */
public class InputGenetic extends BasicTrainingInput {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;

	private final IntegerField populationSize;
	private final DoubleField mutationPercent;
	private final DoubleField percentToMate;
	

	/**
	 * Construct the dialog.
	 * @param owner The owner.
	 */
	public InputGenetic(final Frame owner) {
		super(owner);
		setTitle("Train Simulated Annealing");
		
		addProperty(this.populationSize = new IntegerField("population size","Learning Rate",true,1,-1));
		addProperty(this.mutationPercent = new DoubleField("mutation percent","Mutation Percent",true,0,1));
		addProperty(this.percentToMate = new DoubleField("percent to mate","Percent to Mate",true,0,1));
		render();
		
		this.populationSize.setValue(5000);
		this.mutationPercent.setValue(0.1);
		this.percentToMate.setValue(0.25);
	}


	public IntegerField getPopulationSize() {
		return populationSize;
	}


	public DoubleField getMutationPercent() {
		return mutationPercent;
	}


	public DoubleField getPercentToMate() {
		return percentToMate;
	}
	
	


}
