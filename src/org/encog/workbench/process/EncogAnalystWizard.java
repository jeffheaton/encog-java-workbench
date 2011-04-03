package org.encog.workbench.process;

import java.io.File;

import org.encog.EncogError;
import org.encog.app.analyst.AnalystFileFormat;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.wizard.analyst.AnalystWizardDialog;
import org.encog.workbench.util.FileUtil;

public class EncogAnalystWizard {
	public static void createEncogAnalyst(File csvFile) {
		
		if( !EncogWorkBench.getInstance().getMainWindow().getTabManager().queryViews(csvFile) )
			return;
		
		
		AnalystWizardDialog dialog = new AnalystWizardDialog(EncogWorkBench
				.getInstance().getMainWindow());
		
		if( csvFile!=null ) {
			dialog.getRawFile().setValue(csvFile.toString());
		}
		
		if (dialog.process()) {
			EncogAnalyst analyst = null;
			File projectFolder = EncogWorkBench.getInstance().getProjectDirectory();
			File egaFile = null;
			
			try {
				File sourceCSVFile = new File(dialog.getRawFile().getValue());
				File targetCSVFile = new File(projectFolder,
						sourceCSVFile.getName());

				if( !sourceCSVFile.toString().equals(targetCSVFile.toString())) {
					org.encog.util.file.FileUtil.copy(sourceCSVFile, targetCSVFile);
				}
				
				egaFile = new File(FileUtil.forceExtension(
						targetCSVFile.toString(), "ega"));
				
				if( !EncogWorkBench.getInstance().getMainWindow().getTabManager().queryViews(egaFile) )
					return;
				
				
				File egFile = new File(FileUtil.forceExtension(
						targetCSVFile.toString(), "eg"));

				analyst = new EncogAnalyst();
				AnalystWizard wizard = new AnalystWizard(analyst);
				boolean headers = dialog.getHeaders().getValue();
				AnalystFileFormat format = dialog.getFormat();

				wizard.setMethodType(dialog.getMethodType());
				wizard.setTargetField(dialog.getTargetField());
				wizard.setGoal(dialog.getGoal());
				wizard.setLagWindowSize(dialog.getLagCount().getValue());
				wizard.setLeadWindowSize(dialog.getLeadCount().getValue());
				wizard.setIncludeTargetField(dialog.getIncludeTarget().getValue());
				wizard.setEGName(egFile);
				wizard.setRange(dialog.getRange());
				wizard.setTaskNormalize(dialog.getNormalize().getValue());
				wizard.setTaskRandomize(dialog.getRandomize().getValue());
				wizard.setTaskSegregate(dialog.getSegregate().getValue());
				wizard.setTaskBalance(dialog.getBalance().getValue());

				wizard.wizard(targetCSVFile, headers, format);

				
				
			} catch (EncogError e) {
				EncogWorkBench.displayError("Error Generating Analyst Script", e);
			} finally {
				if( analyst!=null)
					analyst.save(egaFile);
				EncogWorkBench.getInstance().getMainWindow().getTree()
				.refresh();
			}
		}
	}
}
