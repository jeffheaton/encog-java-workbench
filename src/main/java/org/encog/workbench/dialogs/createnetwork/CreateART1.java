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
package org.encog.workbench.dialogs.createnetwork;

import java.awt.Frame;

import javax.swing.JTextField;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.PropertyType;

public class CreateART1 extends EncogPropertiesDialog {

	private IntegerField f1;
	private IntegerField f2;
	
	public CreateART1(Frame owner) {
		super(owner);
		setTitle("Create ART1 Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.f1 = new IntegerField("f1 neuron count","Layer F1 Neuron Count",true,1,100000));
		addProperty(this.f2 = new IntegerField("f2 neuron count","Layer F2 Neuron Count",true,1,100000));
		render();
	}

	public IntegerField getF1() {
		return f1;
	}

	public IntegerField getF2() {
		return f2;
	}


}
