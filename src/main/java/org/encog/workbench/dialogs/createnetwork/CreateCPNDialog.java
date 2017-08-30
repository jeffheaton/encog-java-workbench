/*
 * Encog(tm) Java Workbench v3.4
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-examples
 *
 * Copyright 2008-2017 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class CreateCPNDialog extends EncogPropertiesDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1916684369370397010L;
	
	private IntegerField inputCount;
	private IntegerField instarCount;
	private IntegerField outstarCount;
	
	public CreateCPNDialog(Frame owner) {
		super(owner);
		setTitle("Create CPN Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.inputCount = new IntegerField("input neurons","Input Neuron Count",true,1,100000));
		addProperty(this.instarCount = new IntegerField("instar neurons","Instar Neuron Count",true,1,100000));
		addProperty(this.outstarCount = new IntegerField("outstar neurons","Outstar Neuron Count",true,1,100000));
		render();
	}

	public IntegerField getInputCount() {
		return inputCount;
	}

	public IntegerField getInstarCount() {
		return instarCount;
	}

	public IntegerField getOutstarCount() {
		return outstarCount;
	}
	
	



}
