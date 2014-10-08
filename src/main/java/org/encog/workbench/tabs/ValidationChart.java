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
package org.encog.workbench.tabs;

import java.awt.Frame;

import org.encog.ml.MLContext;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.validate.InputValidationChart;
import org.encog.workbench.dialogs.validate.ResultValidationChart;

public class ValidationChart {
	private MLMethod method;
	private MLDataSet training;

	public void perform(Frame owner) {
		try {
		final InputValidationChart dialog = new InputValidationChart();

		if (dialog.process()) {
			method = dialog.getNetwork();
			training = dialog.getTrainingSet();

			if( method instanceof MLContext )
				((MLContext)method).clearContext();
			
			ResultValidationChart chart = new ResultValidationChart();
			chart.setData(training, method);
			EncogWorkBench.getInstance().getMainWindow().getTabManager().openModalTab(chart, "Validation");
		}
		}
		catch(Throwable t)
		{
			EncogWorkBench.displayError("An Error Occured", t);
		}
	}
}
