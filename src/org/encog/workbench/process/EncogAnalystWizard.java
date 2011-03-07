package org.encog.workbench.process;

import java.io.File;

import org.encog.EncogError;
import org.encog.app.analyst.AnalystFileFormat;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.util.csv.CSVFormat;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.wizard.analyst.AnalystWizardDialog;
import org.encog.workbench.util.FileUtil;

public class EncogAnalystWizard {
	public static void createEncogAnalyst(File projectFile) {
		AnalystWizardDialog dialog = new AnalystWizardDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			EncogAnalyst analyst = null;
			File projectFolder = projectFile.getParentFile();
			File egaFile = new File(FileUtil.forceExtension(
					projectFile.toString(), "ega"));

			try {
				File sourceCSVFile = new File(dialog.getRawFile().getValue());
				File targetCSVFile = new File(projectFolder,
						sourceCSVFile.getName());

				org.encog.util.file.FileUtil.copy(sourceCSVFile, targetCSVFile);

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
