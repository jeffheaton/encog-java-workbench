package org.encog.workbench.process;

import java.io.File;

import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.util.csv.CSVFormat;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.wizard.analyst.AnalystWizardDialog;
import org.encog.workbench.util.FileUtil;

public class EncogAnalystWizard {
	public static void createEncogAnalyst(File projectFile)
	{
		AnalystWizardDialog dialog = new AnalystWizardDialog(EncogWorkBench.getInstance().getMainWindow());
		if( dialog.process()) {
			System.out.println(projectFile);
			File projectFolder = projectFile.getParentFile();
			File egaFile = new File(FileUtil.forceExtension(projectFile.toString(), "ega"));
			File sourceCSVFile = new File(dialog.getRawFile().getValue());
			File targetCSVFile = new File(projectFolder,sourceCSVFile.getName());
			
			org.encog.util.file.FileUtil.copy(sourceCSVFile,targetCSVFile);
			
			EncogAnalyst analyst = new EncogAnalyst();
			AnalystWizard wizard = new AnalystWizard(analyst);
			boolean headers = dialog.getHeaders().getValue();
			CSVFormat format = CSVFormat.DECIMAL_POINT;
			
			if(dialog.getDecimalComma().getValue())
				format = CSVFormat.DECIMAL_COMMA;
			
			wizard.setMethodType(dialog.getMethodType());
			
			wizard.wizard(targetCSVFile, headers, format);
			
			analyst.save(egaFile);
			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
		}
	}
}
