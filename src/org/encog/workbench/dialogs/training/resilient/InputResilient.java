/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.dialogs.training.resilient;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.engine.network.train.prop.RPROPConst;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

/**
 * A dialog box that inputs for the parameters to use with
 * the backpropagation training method.
 * @author jheaton
 *
 */
public class InputResilient extends BasicTrainingInput {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private final DoubleField maxStep;
	private final DoubleField initialUpdate;
	
	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputResilient(final Frame owner) {
		super(owner);
		setTitle("Train Resilient Propagation");
		addProperty(this.maxStep = new DoubleField("max step","Max Step",true,0,-1));
		addProperty(this.initialUpdate = new DoubleField("initial update","Initial Update",true,0,-1));
		render();	
		this.maxStep.setValue(RPROPConst.DEFAULT_MAX_STEP);
		this.initialUpdate.setValue(RPROPConst.DEFAULT_INITIAL_UPDATE);
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
	}

	public DoubleField getMaxStep() {
		return maxStep;
	}

	public DoubleField getInitialUpdate() {
		return initialUpdate;
	}
	
	
	
	

}
