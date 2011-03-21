/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import org.encog.Encog;
import org.encog.EncogError;
import org.encog.engine.util.ErrorCalculation;
import org.encog.engine.util.ErrorCalculationMode;
import org.encog.engine.util.Format;
import org.encog.ml.MLError;
import org.encog.ml.MLMethod;
import org.encog.ml.genetic.population.BasicPopulation;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.PropertyData;
import org.encog.neural.data.TextData;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.neural.som.SOM;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogMemoryCollection;
import org.encog.util.file.Directory;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.config.EncogWorkBenchConfig;
import org.encog.workbench.dialogs.BenchmarkDialog;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.dialogs.EvaluateDialog;
import org.encog.workbench.dialogs.PopulationDialog;
import org.encog.workbench.dialogs.config.EncogConfigDialog;
import org.encog.workbench.dialogs.createobject.CreateObjectDialog;
import org.encog.workbench.dialogs.createobject.ObjectType;
import org.encog.workbench.dialogs.newdoc.CreateNewDocument;
import org.encog.workbench.dialogs.trainingdata.CreateTrainingDataDialog;
import org.encog.workbench.dialogs.trainingdata.TrainingDataType;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectEGItem;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.process.CreateNeuralNetwork;
import org.encog.workbench.process.CreateTrainingData;
import org.encog.workbench.process.EncogAnalystWizard;
import org.encog.workbench.process.validate.ResourceNameValidate;
import org.encog.workbench.tabs.BrowserFrame;
import org.encog.workbench.tabs.rbf.RadialBasisFunctionsTab;
import org.encog.workbench.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncogDocumentOperations {

	private EncogDocumentFrame owner;
	private int browser = 0;

	@SuppressWarnings("unused")
	final private Logger logger = LoggerFactory.getLogger(this.getClass());

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
			File projectFile = new File(project, dialog.getProjectFilename()
					.getValue() + ".eg");
			EncogMemoryCollection temp = new EncogMemoryCollection();
			temp.save(projectFile.toString());
			EncogWorkBench.getInstance().getMainWindow().getTree()
					.refresh(project.toString());

			if (EncogWorkBench
					.askQuestion(
							"Wizard",
							"Would you like to use a wizard to generate files\nnecessary to work with a CSV data file?")) {
				EncogAnalystWizard.createEncogAnalyst(null);
			}
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
				String path = fc.getSelectedFile().getAbsolutePath();
				EncogWorkBench.getInstance().getMainWindow()
						.changeDirectory(path);
			}
		} catch (final Throwable e) {
			EncogWorkBench.displayError("Can't Change Directory", e);
			e.printStackTrace();
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
	}

	public void performNetworkQuery(DirectoryEntry item) {

		/*
		 * if (owner.getTabManager() .checkBeforeOpen(item,
		 * NetworkQueryFrame.class)) { BasicNetwork net = (BasicNetwork)
		 * EncogWorkBench.getInstance() .getCurrentFile().find(item); final
		 * NetworkQueryFrame frame = new NetworkQueryFrame(net);
		 * frame.setVisible(true); }
		 */
	}

	public void performObjectsCreate() throws IOException {

		try {
			if (EncogWorkBench.getInstance().getMainWindow().getTree()
					.listEGFiles().length < 1) {
				EncogWorkBench.displayError("Can't Create an Object",
						"There are no EG files in the current directory tree.");
				return;
			}

			CreateObjectDialog dialog = new CreateObjectDialog(EncogWorkBench
					.getInstance().getMainWindow());

			dialog.setType(ObjectType.NeuralNetwork);

			if (!dialog.process())
				return;

			String name = dialog.getResourceName();
			String filename = dialog.getFilename();

			ProjectEGFile pef = (ProjectEGFile) EncogWorkBench.getInstance()
					.getMainWindow().getTree().findTreeFile(filename);
			EncogMemoryCollection encog = pef.getCollection();

			switch (dialog.getType()) {
			case NeuralNetwork:
				CreateNeuralNetwork.process(name, pef);
				break;
			case NEATPopulation:
				performCreatePopulation();
				break;
			case PropertyData:
				final PropertyData prop = new PropertyData();
				prop.setDescription("Some property data");
				encog.add(name, prop);
				encog.save(pef.getFile().toString());
				pef.generateChildrenList();
				EncogWorkBench.getInstance().getMainWindow().redraw();
				break;
			case Text:
				final TextData text = new TextData();
				text.setDescription("A text file");
				text.setText("Insert text here.");
				encog.add(name, text);
				encog.save(pef.getFile().toString());
				pef.generateChildrenList();
				EncogWorkBench.getInstance().getMainWindow().redraw();
				break;
			}

		} catch (EncogError t) {
			EncogWorkBench.displayError("Error creating object", t);
			logger.error("Error creating object", t);
		}
	}

	private void performCreatePopulation() {
		PopulationDialog dialog = new PopulationDialog(owner);

		if (dialog.process()) {
			int populationSize = dialog.getPopulationSize().getValue();
			BasicPopulation pop = new BasicPopulation(populationSize);

			for (int i = 0; i < populationSize; i++) {
				pop.add(new NEATGenome(null, pop.assignGenomeID(), dialog
						.getInputNeurons().getValue(), dialog
						.getOutputNeurons().getValue()));
			}

			pop.setDescription("Population");
			// EncogWorkBench.getInstance().getCurrentFile().add(
			// generateNextID("population-"), pop);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}

	}

	public void performBrowse() {
		BrowserFrame browse = new BrowserFrame();
		this.owner.openTab(browse, "Browser" + (browser++));
	}

	public void performRBF() {
		RadialBasisFunctionsTab rbf = new RadialBasisFunctionsTab();
		this.owner.openTab(rbf, "RBF");
	}

	public void performHelpAbout() {
		EncogWorkBench.getInstance().getMainWindow().displayAboutTab();
	}

	public void performEditConfig() {
		// ObjectEditorFrame config = new
		// ObjectEditorFrame(EncogWorkBench.getInstance().getConfig());
		// config.setVisible(true);

		EncogConfigDialog dialog = new EncogConfigDialog(EncogWorkBench
				.getInstance().getMainWindow());

		EncogWorkBenchConfig config = EncogWorkBench.getInstance().getConfig();

		dialog.getUserID().setValue(config.getEncogCloudUserID());
		dialog.getPassword().setValue(config.getEncogCloudPassword());
		dialog.getDefaultError().setValue(config.getDefaultError());
		dialog.getNetwork().setValue(config.getEncogCloudNetwork());
		dialog.getAutoConnect().setValue(config.isAutoConnect());
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
			config.setEncogCloudUserID(dialog.getUserID().getValue());
			config.setEncogCloudPassword(dialog.getPassword().getValue());
			config.setDefaultError(dialog.getDefaultError().getValue());
			config.setAutoConnect(dialog.getAutoConnect().getValue());
			config.setEncogCloudNetwork(dialog.getNetwork().getValue());
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
			EncogWorkBench.saveConfig();

			ErrorCalculation.setMode(EncogWorkBench.getInstance().getConfig()
					.getErrorCalculation());

			if (config.isUseOpenCL() && Encog.getInstance().getCL() == null) {
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
			}

		}
	}

	public void performObjectsProperties(ProjectEGItem selected) {

		final EditEncogObjectProperties dialog = new EditEncogObjectProperties(
				owner);
		dialog.getNameField().setValue(selected.getObj().getName());
		dialog.getDescription().setValue(selected.getObj().getDescription());
		if (dialog.process()) {

			String error = ResourceNameValidate.validateResourceName(dialog
					.getNameField().getValue());

			if (error != null) {
				EncogWorkBench.displayError("Data Error", error);
				return;
			}

			selected.getCollection().updateProperties(
					selected.getObj().getName(),
					dialog.getNameField().getValue(),
					dialog.getDescription().getValue());
			selected.getCollection().save();
			EncogWorkBench.getInstance().refresh();
		}
	}

	public void performEvaluate() {
		try {
			EvaluateDialog dialog = new EvaluateDialog(EncogWorkBench
					.getInstance().getMainWindow());
			if (dialog.process()) {
				MLMethod method = dialog.getNetwork();
				NeuralDataSet training = dialog.getTrainingSet();

				double error = 0;

				if (method instanceof MLError) {
					error = ((MLError) method).calculateError(training);
					EncogWorkBench.displayMessage("Error For this Network", ""
							+ Format.formatPercent(error));

				} else if (method instanceof SOM) {
					EncogWorkBench
							.displayError(
									"Error",
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
			}
			EncogWorkBench.getInstance().refresh();
		}
	}

	public void performQuit() {

		System.exit(0);

	}

	public void performObjectsDelete(Object selected) {
		if (selected instanceof ProjectEGItem) {
			ProjectEGItem item = (ProjectEGItem) selected;
			item.getCollection().delete(item.getObj().getName());
			item.getCollection().save(item.getEncogFile().getFile().toString());
			item.getEncogFile().generateChildrenList();
			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
		}

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
}
