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
package org.encog.workbench.dialogs.trainingdata;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;


public class CreateSineTrainingDialog extends EncogPropertiesDialog {
	
	private final IntegerField elements;
	private final DoubleField cycles;
	
	public CreateSineTrainingDialog(Frame owner) {
		super(owner);
		setTitle("Sin Wave Training Data");
		setSize(400,200);
		addProperty(this.elements = new IntegerField("elements","Training Set Elements",true, 0, 1000000));
		addProperty(this.cycles = new DoubleField("low","Complete Cycles",true, 0, 100));
		render();
		
		this.elements.setValue(100);
		this.cycles.setValue(1.0);
	}

	public IntegerField getElements() {
		return elements;
	}

	public DoubleField getCycles() {
		return cycles;
	}

	

	
	
	

}
