/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
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

import java.util.ArrayList;
import java.util.List;

import org.encog.neural.networks.training.propagation.resilient.RPROPConst;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.training.DialogMaxError;

/**
 * A dialog box that inputs for the parameters to use with
 * the backpropagation training method.
 * @author jheaton
 *
 */
public class InputResilient extends DialogMaxError {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private final DoubleField maxStep;
	private final DoubleField initialUpdate;
	private final ComboBoxField rpropType;
	
	/**
	 * Construct the dialog box.
	 * @param owner
	 */
	public InputResilient() {
		super(true);
		
		List<String> list = new ArrayList<String>();
		list.add("RPROP+ (classic)");
		list.add("RPROP-");
		list.add("iRPROP+");
		list.add("iRPROP-");
		
		setTitle("Train Resilient Propagation");
		addProperty(this.rpropType = new ComboBoxField("rprop type", "RPROP Type",true,list));
		addProperty(this.maxStep = new DoubleField("max step","Max Step",true,0,-1));
		addProperty(this.initialUpdate = new DoubleField("initial update","Initial Update",true,0,-1));
		render();	
		this.maxStep.setValue(RPROPConst.DEFAULT_MAX_STEP);
		this.initialUpdate.setValue(RPROPConst.DEFAULT_INITIAL_UPDATE);
	}

	public DoubleField getMaxStep() {
		return maxStep;
	}

	public DoubleField getInitialUpdate() {
		return initialUpdate;
	}

	/**
	 * @return the rpropType
	 */
	public ComboBoxField getRpropType() {
		return rpropType;
	}

	

}
