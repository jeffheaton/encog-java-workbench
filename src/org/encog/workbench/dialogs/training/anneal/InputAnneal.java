/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.dialogs.training.anneal;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

/**
 * Dialog box to input data for simulated annealing training.
 */
public class InputAnneal extends BasicTrainingInput {

	/**
	 * The serial id for this class.
	 */
	private static final long serialVersionUID = 1L;
	
	private DoubleField startTemp;
	private DoubleField endTemp;
	private IntegerField cycles;
	
	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public InputAnneal(final Frame owner) {
		super(owner);
		setTitle("Train Simulated Annealing");

		addProperty(this.startTemp = new DoubleField("starting temperature","Starting Temperature",true,-1,-1));
		addProperty(this.endTemp = new DoubleField("ending temperature","Ending Temperature",true,-1,-1));
		addProperty(this.cycles = new IntegerField("cycles","Ending Temperature",true,0,-1));
		render();
		
		this.startTemp.setValue(1);
		this.endTemp.setValue(20);
		this.cycles.setValue(10);
	}

	public DoubleField getStartTemp() {
		return startTemp;
	}

	public DoubleField getEndTemp() {
		return endTemp;
	}

	public IntegerField getCycles() {
		return cycles;
	}

	

}
