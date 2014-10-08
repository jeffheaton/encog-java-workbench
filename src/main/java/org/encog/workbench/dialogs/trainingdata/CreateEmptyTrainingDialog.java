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

import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;


public class CreateEmptyTrainingDialog extends EncogPropertiesDialog {
	
	private IntegerField elements;
	private IntegerField input;
	private IntegerField ideal;
	
	public CreateEmptyTrainingDialog(Frame owner) {
		super(owner);
		setTitle("Empty Training Data");
		setSize(400,200);
		addProperty(this.elements = new IntegerField("elements","Training Set Elements",true, 0, 10000));
		addProperty(this.input = new IntegerField("input","Input Field Count",true, 1, 1000));
		addProperty(this.ideal = new IntegerField("ideal","Output Field Count",true, 0, 1000));
		render();
	}

	public IntegerField getElements() {
		return elements;
	}

	public IntegerField getInput() {
		return input;
	}

	public IntegerField getIdeal() {
		return ideal;
	}

}
