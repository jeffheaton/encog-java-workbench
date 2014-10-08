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
package org.encog.workbench.process;

import java.io.File;
import java.util.List;

import org.encog.EncogError;
import org.encog.app.analyst.AnalystFileFormat;
import org.encog.app.analyst.AnalystGoal;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.missing.DiscardMissing;
import org.encog.app.analyst.missing.MeanAndModeMissing;
import org.encog.app.analyst.missing.NegateMissing;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.app.analyst.wizard.NormalizeRange;
import org.encog.app.analyst.wizard.PredictionType;
import org.encog.app.analyst.wizard.SourceElement;
import org.encog.app.analyst.wizard.WizardMethodType;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.wizard.analyst.AnalystWizardDialog;
import org.encog.workbench.dialogs.wizard.analyst.RealTimeAnalystWizardDialog;
import org.encog.workbench.dialogs.wizard.specific.BayesianWizardDialog;
import org.encog.workbench.util.FileUtil;

public class EncogAnalystWizard {
	public static void createEncogAnalyst(File csvFile) {
		boolean refresh = true;
		if (csvFile!=null && !EncogWorkBench.getInstance().getMainWindow().getTabManager()
				.queryViews(csvFile))
			return;

		AnalystWizardDialog dialog = new AnalystWizardDialog();

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
				wizard.setMaxError(dialog.getMaxError().getValue()/100.0);
				
				wizard.setCodeTargetLanguage(dialog.getGenerationTargetLanguage());
				wizard.setCodeEmbedData(dialog.getEmbedData().getValue());
				
				if( wizard.getMethodType()==WizardMethodType.NEAT ) {
					if(wizard.getRange()==NormalizeRange.NegOne2One ) {
						if( EncogWorkBench.askQuestion("Range", "NEAT uses a steepend sigmoid function which will not work with the range -1 to 1.\nShould this be switched to 0 to 1?")) {
							wizard.setRange(NormalizeRange.Zero2One);
						}
					}
				}
				
				if( !setSpecific(wizard) )
					return;

				wizard.wizard(targetCSVFile, headers, format);
				
				if (analyst != null) {
					analyst.save(egaFile);
					analyst = null;
				}
				
				EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
				refresh = false;
				EncogWorkBench.getInstance().getMainWindow().openFile(egaFile);

			} catch (EncogError e) {
				EncogWorkBench.displayError("Error Generating Analyst Script",
						e);
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
			}
		}
	}
	
	public static void createRealtimeEncogAnalyst(File egaFile) {

		File existingFile = null;
		
		if( egaFile.exists() ) {
			if( EncogWorkBench.askQuestion("Existing Project", "This file already exists.\nWould you like to recreate it from the same source data?")) {
				existingFile = egaFile;
			}
		}

		RealTimeAnalystWizardDialog dialog = new RealTimeAnalystWizardDialog(existingFile);
		dialog.getBaseName().setValue(egaFile.toString());

		if (dialog.process()) {
			EncogAnalyst analyst = null;

			File csvFile = new File(FileUtil.forceExtension(egaFile.toString(), "csv"));
			List<SourceElement> sourceData = dialog.getSourceData();
			
			int backwardWindow = dialog.getBackwardWindow().getValue();
			int forwardWindow = dialog.getForwardWindow().getValue();
			PredictionType prediction = dialog.getPrediction();
			String predictField = dialog.getPredictionField().getValue();
			
			try {
				File egFile = new File(FileUtil.forceExtension(
						csvFile.toString(), "eg"));

				analyst = new EncogAnalyst();
				AnalystWizard wizard = new AnalystWizard(analyst);
				wizard.setCodeTargetLanguage(dialog.getTargetLanguage());
				wizard.wizardRealTime(sourceData, 
						csvFile, 
						backwardWindow, 
						forwardWindow, 
						prediction,
						predictField);

			} catch (EncogError e) {
				EncogWorkBench.displayError("Error Generating Analyst Script",
						e);
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
				if (analyst != null) {
					analyst.save(egaFile);
					EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
					EncogWorkBench.getInstance().getMainWindow().openFile(egaFile);

				}
			}
		}
	}

	private static boolean setSpecific(AnalystWizard wizard) {
		if( wizard.getMethodType() == WizardMethodType.BayesianNetwork  ) {
			BayesianWizardDialog dialog = new BayesianWizardDialog();
			if( dialog.process() ) {
				wizard.setNaiveBayes(dialog.getNaiveBayesian().getValue());
				wizard.setEvidenceSegements(dialog.getEvidenceSegments().getValue());
				return true;
			} else {
				return false;
			}
		}
		
		return true;
		
	}
}
