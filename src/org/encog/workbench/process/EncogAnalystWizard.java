package org.encog.workbench.process;

import java.io.File;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.wizard.analyst.AnalystWizardDialog;

public class EncogAnalystWizard {
	public static void createEncogAnalyst(File projectFile)
	{
		AnalystWizardDialog dialog = new AnalystWizardDialog(EncogWorkBench.getInstance().getMainWindow());
		if( dialog.process()) {
			System.out.println(projectFile);
		}
	}
}
