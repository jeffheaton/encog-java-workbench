/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.training.som;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.training.BasicTrainingInput;

/**
 * Input data to train a SOM network.
 * @author jheaton
 *
 */
public class InputSOM extends BasicTrainingInput {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;


	private DoubleField learningRate;
	

	/**
	 * Construct the dialog box.
	 * @param owner The owner.
	 */
	public InputSOM(final Frame owner) {
		super(owner);
		setTitle("Train SOM Layers");
		addProperty(this.learningRate = new DoubleField("learning rate","Learning Rate",true,0,-1));
		render();
		this.learningRate.setValue(0.7);
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());

	}


	public DoubleField getLearningRate() {
		return learningRate;
	}
	
	


}
