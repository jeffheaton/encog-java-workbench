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
package org.encog.workbench.dialogs.training;

import java.awt.Frame;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

/**
 * Basic dialog box that displays two combo boxes used to select
 * the training set and network to be used.  Subclasses can
 * add additional fields.  This class is based on the Encog
 * common dialog box.
 * @author jheaton
 */
public class ProbenDialog extends EncogPropertiesDialog {

	private final DoubleField maxError;

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public ProbenDialog() {
		
		super(EncogWorkBench.getInstance().getMainWindow());
		setSize(400,400);
		setLocation(200,200);
		
		addProperty(this.maxError = new DoubleField("max error",
				"Maximum Error Percent(0-100)", true, 0, 100));

	}
	
	
	public void render() {
		super.render();
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());

	}

	public DoubleField getMaxError() {
		return maxError;
	}

	

}
