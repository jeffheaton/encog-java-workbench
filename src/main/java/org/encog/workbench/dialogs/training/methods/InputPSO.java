/*
 * Encog(tm) Workbanch v3.1 - Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.dialogs.training.methods;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.training.DialogMaxError;

/**
 * Dialog box to input the parameters for genetic training.
 */
public class InputPSO extends DialogMaxError {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;

	private final IntegerField particleCount;
	private final DoubleField c1;
	private final DoubleField c2;
	

	/**
	 * Construct the dialog.
	 * @param owner The owner.
	 */
	public InputPSO() {
		super(false);
		setTitle("Particle Swarm Optimization Training");
		
		addProperty(this.particleCount = new IntegerField("particle count","Particle Count",true,5,100));
		addProperty(this.c1 = new DoubleField("cognitive lr","Cognitive Learning Rate",true,0,1000));
		addProperty(this.c2 = new DoubleField("social lr","Social Learning Rate",true,0,1000));
		render();
		this.particleCount.setValue(30);
		this.c1.setValue(2.0);
		this.c2.setValue(2.0);
	}


	/**
	 * @return the particleCount
	 */
	public IntegerField getParticleCount() {
		return particleCount;
	}


	/**
	 * @return the c1
	 */
	public DoubleField getC1() {
		return c1;
	}


	/**
	 * @return the c2
	 */
	public DoubleField getC2() {
		return c2;
	}


	
}
