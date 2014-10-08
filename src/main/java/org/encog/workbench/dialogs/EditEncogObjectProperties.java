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
package org.encog.workbench.dialogs;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TextField;

public class EditEncogObjectProperties extends EncogPropertiesDialog {

	private final TextField name;
	private final TextField description;

	public EditEncogObjectProperties(final Frame owner) {
		super(owner);

		addProperty(this.name = new TextField("name","Name",true));
		addProperty(this.description = new TextField("description","Description",false));
		
		setTitle("Encog Object Properties");
		setModal(true);
		setResizable(false);
		this.setSize(640,100);

		render();

	}

	public TextField getNameField() {
		return name;
	}

	public TextField getDescription() {
		return description;
	}
	
	

}
