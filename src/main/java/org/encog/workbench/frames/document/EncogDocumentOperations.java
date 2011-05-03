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
package org.encog.workbench.frames.document;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import org.encog.mathutil.error.ErrorCalculation;
import org.encog.mathutil.error.ErrorCalculationMode;
import org.encog.ml.MLError;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataSet;
import org.encog.util.Format;
import org.encog.util.file.Directory;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.config.EncogWorkBenchConfig;
import org.encog.workbench.dialogs.BenchmarkDialog;
import org.encog.workbench.dialogs.EvaluateDialog;
import org.encog.workbench.dialogs.config.EncogConfigDialog;
import org.encog.workbench.dialogs.newdoc.CreateNewDocument;
import org.encog.workbench.dialogs.trainingdata.CreateTrainingDataDialog;
import org.encog.workbench.dialogs.trainingdata.TrainingDataType;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.frames.document.tree.ProjectItem;
import org.encog.workbench.process.CreateTrainingData;
import org.encog.workbench.tabs.BrowserFrame;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.files.text.BasicTextTab;
import org.encog.workbench.tabs.rbf.RadialBasisFunctionsTab;
import org.encog.workbench.util.FileUtil;

public class EncogDocumentOperations {

	private EncogDocumentFrame owner;

	public EncogDocumentOperations(EncogDocumentFrame owner) {
		this.owner = owner;
	}

	public void performEditCopy() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.copy();
		}

	}

	public void performEditCut() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.cut();
		}
	}

	public void performEditPaste() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.paste();
		}

	}

	public void performFileNewProject() {

		CreateNewDocument dialog = new CreateNewDocument(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.getParentDirectory().setValue(
				EncogWorkBench.getInstance().getEncogFolders().toString());
		dialog.getProjectFilename().setValue("MyEncogProject");

		if (dialog.process()) {
			File parent = new File(dialog.getParentDirectory().getValue());
			File project = new File(parent, dialog.getProjectFilename()
					.getValue());
			Directory.deleteDirectory(project); // the user was warned!
			project.mkdir();

			EncogWorkBench.getInstance().getMainWindow().getTree()
					.refresh(project);

		}
	}

	public void performFileChooseDirectory() {
		try {
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setCurrentDirectory(EncogWorkBench.getInstance()
					.getEncogFolders());
			final int result = fc.showOpenDialog(owner);
			if (result == JFileChooser.APPROVE_OPTION) {
				File path = fc.getSelectedFile().getAbsoluteFile();
				EncogWorkBench.getInstance().getMainWindow()
						.changeDirectory(path);
			}
		} catch (final Throwable e) {
			EncogWorkBench.displayError("Can't Change Directory", e);
			e.printStackTrace();
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
	}

	public void performBrowse() {
		BrowserFrame browse = new BrowserFrame();
		this.owner.getTabManager().openTab(browse);
	}

	public void performRBF() {
		RadialBasisFunctionsTab rbf = new RadialBasisFunctionsTab();
		this.owner.getTabManager().openTab(rbf);
	}

	public void performHelpAbout() {
		EncogWorkBench.getInstance().getMainWindow().displayAboutTab();
	}

	public void performEditConfig() {

		EncogConfigDialog dialog = new EncogConfigDialog(EncogWorkBench
				.getInstance().getMainWindow());

		EncogWorkBenchConfig config = EncogWorkBench.getInstance().getConfig();

		dialog.getDefaultError().setValue(config.getDefaultError());
		dialog.getThreadCount().setValue(config.getThreadCount());
		dialog.getUseOpenCL().setValue(config.isUseOpenCL());
		switch (config.getErrorCalculation()) {
		case RMS:
			((JComboBox) dialog.getErrorCalculation().getField())
					.setSelectedIndex(0);
			break;
		case MSE:
			((JComboBox) dialog.getErrorCalculation().getField())
					.setSelectedIndex(1);
			break;
		case ARCTAN:
			((JComboBox) dialog.getErrorCalculation().getField())
					.setSelectedIndex(2);
			break;
		}

		if (dialog.process()) {
			config.setDefaultError(dialog.getDefaultError().getValue());
			config.setThreadCount(dialog.getThreadCount().getValue());
			config.setUseOpenCL(dialog.getUseOpenCL().getValue());
			switch (((JComboBox) dialog.getErrorCalculation().getField())
					.getSelectedIndex()) {
			case 0:
				config.setErrorCalculation(ErrorCalculationMode.RMS);
				break;
			case 1:
				config.setErrorCalculation(ErrorCalculationMode.MSE);
				break;
			case 2:
				config.setErrorCalculation(ErrorCalculationMode.ARCTAN);
				break;
			}
			EncogWorkBench.getInstance().getConfig().saveConfig();

			ErrorCalculation.setMode(EncogWorkBench.getInstance().getConfig()
					.getErrorCalculation());

			/*if (config.isUseOpenCL() && Encog.getInstance().getCL() == null) {
				EncogWorkBench.initCL();
				if (Encog.getInstance().getCL() != null) {
					EncogWorkBench
							.displayMessage("OpenCL",
									"Success, your graphics card(s) are now ready to help train neural networks.");
				}
			} else if (!EncogWorkBench.getInstance().getConfig().isUseOpenCL()
					&& Encog.getInstance().getCL() != null) {
				EncogWorkBench
						.displayMessage(
								"OpenCL",
								"Encog Workbench will stop using your GPU the next time\nthe workbench is restarted.");
			}*/
		}
	}

	public void performEvaluate() {
		try {
			EvaluateDialog dialog = new EvaluateDialog(EncogWorkBench
					.getInstance().getMainWindow());
			if (dialog.process()) {
				MLMethod method = dialog.getNetwork();
				MLDataSet training = dialog.getTrainingSet();

				double error = 0;

				if (method instanceof MLError) {
					error = ((MLError) method).calculateError(training);
					EncogWorkBench.displayMessage("Error For this Network", ""
							+ Format.formatPercent(error));

				} else {
					EncogWorkBench.displayError("Error",
							"The Machine Learning method "
									+ method.getClass().getSimpleName()
									+ " does not support error calculation.");
				}
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error Evaluating Network", t);
		}

	}

	public void performBenchmark() {
		if (EncogWorkBench
				.askQuestion(
						"Benchmark",
						"Would you like to benchmark Encog on this machine?\nThis process will take several minutes to complete.")) {
			BenchmarkDialog dialog = new BenchmarkDialog();
			dialog.setVisible(true);
		}

	}

	public void performCreateTrainingData() throws IOException {
		CreateTrainingDataDialog dialog = new CreateTrainingDataDialog(
				EncogWorkBench.getInstance().getMainWindow());

		dialog.setType(TrainingDataType.CopyCSV);

		if (dialog.process()) {
			String name = dialog.getFilenameName();

			if (name.trim().length() == 0) {
				EncogWorkBench
						.displayError("Error", "Must specify a filename.");
				return;
			}

			name = FileUtil.forceExtension(name, "csv");
			File targetFile = new File(EncogWorkBench.getInstance()
					.getProjectDirectory(), name);

			if (!EncogWorkBench.getInstance().getMainWindow().getTabManager()
					.queryViews(targetFile)) {
				return;
			}

			switch (dialog.getType()) {
			case CopyCSV:
				CreateTrainingData.copyCSV(name);
				break;
			case MarketWindow:
				CreateTrainingData.downloadMarketData(name);
				break;
			case Random:
				CreateTrainingData.generateRandom(name);
				break;
			case XORTemp:
				CreateTrainingData.generateXORTemp(name);
				break;
			case XOR:
				CreateTrainingData.copyXOR(name);
				break;
			case Iris:
				CreateTrainingData.copyIris(name);
				break;
			case Sunspots:
				CreateTrainingData.downloadSunspots(name);
				break;
			case Digits:
				CreateTrainingData.copyDigits(name);
				break;
			case Patterns1:
				CreateTrainingData.copyPatterns1(name);
				break;
			case Patterns2:
				CreateTrainingData.copyPatterns2(name);
				break;
			case Download:
				CreateTrainingData.downloadURL(name);
				break;
			}
			EncogWorkBench.getInstance().refresh();
		}
	}

	public void performQuit() {
		EncogWorkBench.getInstance().getMainWindow().getTabManager().closeAll();
		System.exit(0);
	}

	public void performFileProperties(ProjectFile selected) {
		String name = selected.getFile().getName();
		String newName = EncogWorkBench
				.displayInput("What would you like to rename the file \""
						+ name + "\" to?");
		if (newName != null) {
			File oldFile = selected.getFile();
			File dir = oldFile.getParentFile();
			File newFile = new File(dir, newName);
			oldFile.renameTo(newFile);
			EncogWorkBench.getInstance().refresh();
		}

	}

	public void performSave() {
		EncogCommonTab tab = this.owner.getTabManager()
				.getCurrentTab();
		if (tab != null) {
			tab.save();
		}

	}

	public void performDelete() {

		boolean first = true;
		List<ProjectItem> list = this.owner.getTree().getSelectedValue();

		for (ProjectItem selected : list) {
			if (first
					&& !EncogWorkBench.askQuestion("Warning",
							"Are you sure you want to delete these file(s)?")) {
				return;
			}
			first = false;
			if (selected instanceof ProjectFile) {
				File f = ((ProjectFile) selected).getFile();
				if (!f.delete()) {
					if (FileUtil.getFileExt(f).equalsIgnoreCase("egb")) {
						EncogWorkBench
								.displayError(
										"Can't Delete:\n" + f.toString(),
										f.toString()
												+ "\nUnfortunatly, due to a limitation in Java, EGB files cannot be deleted once opened.\nRestart the workbench, and you will be able to delete this file.");
					} else {
						EncogWorkBench.displayError("Can't Delete",
								f.toString());
					}

				} else {
					EncogWorkBench.getInstance().getMainWindow()
							.getTabManager().closeAll(f);
				}
			}
			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
		}
	}

	public void performEditFind() {
		EncogCommonTab tab = EncogWorkBench.getInstance().getMainWindow().getTabManager().getCurrentTab();

		if( tab instanceof BasicTextTab ) {
			((BasicTextTab)tab).find();
		}
		
	}
}
