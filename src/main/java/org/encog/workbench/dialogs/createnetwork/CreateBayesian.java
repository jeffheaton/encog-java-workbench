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

import org.encog.ml.bayesian.BayesianNetwork;
import org.encog.ml.bayesian.parse.ParseProbability;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TextAreaField;
import org.encog.workbench.dialogs.common.TextField;

public class CreateBayesian extends EncogPropertiesDialog {

	private BayesianNetwork network;
	private TextAreaField contents;
	private TextField query;
	
	public CreateBayesian(BayesianNetwork theNetwork) {
		super(EncogWorkBench.getInstance().getMainWindow());
		this.network = theNetwork;
		setTitle("Create Bayesian Network");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.query = new TextField("query","Query",false));
		addProperty(this.contents = new TextAreaField("Bayesian Contents", "contents", true));
		render();
	}
	
	@Override
	public boolean passesValidation() {
		try {
				BayesianNetwork temp = new BayesianNetwork();
				temp.setContents(this.contents.getValue());
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error Parsing Contents", t);
			return false;
		}
		return true;
	}

	public TextAreaField getContents() {
		return contents;
	}

	public TextField getQuery() {
		return query;
	}


	
	

	

}
