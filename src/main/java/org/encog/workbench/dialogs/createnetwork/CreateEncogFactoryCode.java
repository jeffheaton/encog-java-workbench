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

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TextField;

public class CreateEncogFactoryCode extends EncogPropertiesDialog {

	private TextField typeCode;
	private TextField architectureCode;
	
	public CreateEncogFactoryCode() {
		super(EncogWorkBench.getInstance().getMainWindow());
		setTitle("Create from Factory Code");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.typeCode = new TextField("type","Type Code",true));
		addProperty(this.architectureCode = new TextField("architecture","Architecture Code",true));
		render();
	}

	public TextField getTypeCode() {
		return typeCode;
	}

	public TextField getArchitectureCode() {
		return architectureCode;
	}

	
}
