/*
 * Encog(tm) Workbench v3.0
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2011 Heaton Research, Inc.
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
package org.encog.workbench.process;

import java.io.File;

import org.encog.EncogError;
import org.encog.app.analyst.AnalystFileFormat;
import org.encog.app.analyst.AnalystGoal;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.missing.DiscardMissing;
import org.encog.app.analyst.missing.MeanAndModeMissing;
import org.encog.app.analyst.missing.NegateMissing;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.app.analyst.wizard.WizardMethodType;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.wizard.analyst.AnalystWizardDialog;
import org.encog.workbench.util.FileUtil;

public class EncogAnalystWizard {
	public static void createEncogAnalyst(File csvFile) {

		if (!EncogWorkBench.getInstance().getMainWindow().getTabManager()
				.queryViews(csvFile))
			return;

		AnalystWizardDialog dialog = new AnalystWizardDialog(EncogWorkBench
				.getInstance().getMainWindow());

		if (csvFile != null) {
			dialog.getRawFile().setValue(csvFile.toString());
		}

		if (dialog.process()) {
			EncogAnalyst analyst = null;
			File projectFolder = EncogWorkBench.getInstance()
					.getProjectDirectory();
			File egaFile = null;
			
			if( dialog.getMethodType()==WizardMethodType.SOM && dialog.getGoal()==AnalystGoal.Regression ) {
				EncogWorkBench.displayError("Error", "Can't use a SOM with regression.");
				return;
			}

			try {
				EncogWorkBench.getInstance().getMainWindow().beginWait();
				File sourceCSVFile = new File(dialog.getRawFile().getValue());
				File targetCSVFile = new File(projectFolder,
						sourceCSVFile.getName());

				if (!sourceCSVFile.toString().equals(targetCSVFile.toString())) {
					org.encog.util.file.FileUtil.copy(sourceCSVFile,
							targetCSVFile);
				}

				egaFile = new File(FileUtil.forceExtension(
						targetCSVFile.toString(), "ega"));

				if (!EncogWorkBench.getInstance().getMainWindow()
						.getTabManager().queryViews(egaFile))
					return;

				File egFile = new File(FileUtil.forceExtension(
						targetCSVFile.toString(), "eg"));

				analyst = new EncogAnalyst();
				AnalystWizard wizard = new AnalystWizard(analyst);
				boolean headers = dialog.getHeaders().getValue();
				AnalystFileFormat format = dialog.getFormat();

				wizard.setMethodType(dialog.getMethodType());
				wizard.setTargetField(dialog.getTargetField());
				
				String m = (String)dialog.getMissing().getSelectedValue(); 
				if( m.equals("DiscardMissing") ) {
					wizard.setMissing(new DiscardMissing());	
				} else if( m.equals("MeanAndModeMissing") ) {
					wizard.setMissing(new MeanAndModeMissing());	
				} else if( m.equals("NegateMissing") ) {
					wizard.setMissing(new NegateMissing());	
				} else {
					wizard.setMissing(new DiscardMissing());
				}
				
				wizard.setGoal(dialog.getGoal());
				wizard.setLagWindowSize(dialog.getLagCount().getValue());
				wizard.setLeadWindowSize(dialog.getLeadCount().getValue());
				wizard.setIncludeTargetField(dialog.getIncludeTarget()
						.getValue());
				wizard.setRange(dialog.getRange());
				wizard.setTaskNormalize(dialog.getNormalize().getValue());
				wizard.setTaskRandomize(dialog.getRandomize().getValue());
				wizard.setTaskSegregate(dialog.getSegregate().getValue());
				wizard.setTaskBalance(dialog.getBalance().getValue());
				wizard.setTaskCluster(dialog.getCluster().getValue());

				wizard.wizard(targetCSVFile, headers, format);

			} catch (EncogError e) {
				EncogWorkBench.displayError("Error Generating Analyst Script",
						e);
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
				if (analyst != null)
					analyst.save(egaFile);
				EncogWorkBench.getInstance().getMainWindow().getTree()
						.refresh();
			}
		}
	}
}
