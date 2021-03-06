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
package org.encog.workbench.dialogs.wizard.analyst;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TextField;

public class IndicatorSourceDialog extends EncogPropertiesDialog {

	private final TextField sourceName;
	private final TextField source;
	
	public IndicatorSourceDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		setTitle("Define Data Source");
		setSize(400,200);
		addProperty(this.sourceName = new TextField("name","Simple Field Name",true));
		addProperty(this.source = new TextField("source","NinjaTrader/MT Source",true));
		render();
	}

	public TextField getSourceName() {
		return this.sourceName;
	}

	public TextField getSource() {
		return this.source;
	}

	
}
