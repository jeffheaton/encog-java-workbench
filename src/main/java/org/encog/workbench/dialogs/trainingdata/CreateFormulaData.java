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
import org.encog.workbench.dialogs.common.TextField;


public class CreateFormulaData extends EncogPropertiesDialog {
	
	private final TextField formula;
	private final DoubleField xBegin;
	private final DoubleField xEnd;
	private final IntegerField elements;
	
	public CreateFormulaData(Frame owner) {
		super(owner);
		setTitle("Formula Training Data");
		setSize(400,200);
		addProperty(this.formula = new TextField("formula","Formula",true));
		addProperty(this.xBegin = new DoubleField("x-begin","X-Begin",true, 0, -1));
		addProperty(this.xEnd = new DoubleField("x-end","X-End",true, 0, -1));
		addProperty(this.elements = new IntegerField("elements","Training Set Elements",true, 0, 1000000));
		render();
		
		this.formula.setValue("(10*x)+3");
		this.xBegin.setValue(-5);
		this.xEnd.setValue(5);
		this.elements.setValue(100);
	}

	public IntegerField getElements() {
		return elements;
	}

	/**
	 * @return the formula
	 */
	public TextField getFormula() {
		return formula;
	}

	public DoubleField getxBegin() {
		return xBegin;
	}

	public DoubleField getxEnd() {
		return xEnd;
	}

	
}
