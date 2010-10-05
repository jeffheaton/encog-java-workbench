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
package org.encog.workbench.dialogs.training.resilient;

import java.awt.Frame;

import org.encog.engine.network.train.prop.RPROPConst;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
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
		super(owner,true);
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
