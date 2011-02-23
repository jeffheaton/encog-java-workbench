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
import org.encog.ml.genetic.population.BasicPopulation;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.PropertyData;
import org.encog.neural.data.TextData;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogMemoryCollection;
import org.encog.script.EncogScript;
import org.encog.util.csv.CSVFormat;
import org.encog.util.file.Directory;
import org.encog.util.simple.EncogUtility;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.config.EncogWorkBenchConfig;
import org.encog.workbench.dialogs.BenchmarkDialog;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.dialogs.EvaluateDialog;
import org.encog.workbench.dialogs.PopulationDialog;
import org.encog.workbench.dialogs.config.EncogConfigDialog;
import org.encog.workbench.dialogs.createfile.CreateFileDialog;
import org.encog.workbench.dialogs.createfile.CreateFileType;
import org.encog.workbench.dialogs.createobject.CreateObjectDialog;
import org.encog.workbench.dialogs.createobject.ObjectType;
import org.encog.workbench.dialogs.newdoc.CreateNewDocument;
import org.encog.workbench.dialogs.training.TrainDialog;
import org.encog.workbench.dialogs.trainingdata.CreateTrainingDataDialog;
import org.encog.workbench.dialogs.trainingdata.TrainingDataType;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectEGItem;
import org.encog.workbench.frames.document.tree.ProjectTraining;
import org.encog.workbench.process.CreateNeuralNetwork;
import org.encog.workbench.process.CreateTrainingData;
import org.encog.workbench.process.validate.ResourceNameValidate;
import org.encog.workbench.tabs.BrowserFrame;
import org.encog.workbench.tabs.rbf.RadialBasisFunctionsTab;
import org.encog.workbench.tabs.training.BasicTrainingProgress;
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
			
			if( EncogWorkBench.askQuestion("Wizard", "Would you like to use a wizard to generate files\nnecessary to work with a CSV data file?") )
			{
				
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
				EncogWorkBench.getInstance().getMainWindow().getTree()
						.refresh(path);
			}
		} catch (final Throwable e) {
			EncogWorkBench.displayError("Can't Change Directory", e);
			e.printStackTrace();
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
	}

	public void performNetworkQuery(DirectoryEntry item) {

		/*		if (owner.getTabManager()
						.checkBeforeOpen(item, NetworkQueryFrame.class)) {
					BasicNetwork net = (BasicNetwork) EncogWorkBench.getInstance()
							.getCurrentFile().find(item);
					final NetworkQueryFrame frame = new NetworkQueryFrame(net);
					frame.setVisible(true);
				}
		*/
	}

	public void performObjectsCreate() throws IOException {

		try {
			if( EncogWorkBench.getInstance().getMainWindow().getTree().listEGFiles().length <1 )
			{
				EncogWorkBench.displayError("Can't Create an Object","There are no EG files in the current directory tree.");
				return;
			}
						
			CreateObjectDialog dialog = new CreateObjectDialog(EncogWorkBench
					.getInstance().getMainWindow());

			dialog.setType(ObjectType.NeuralNetwork);

			if (!dialog.process())
				return;

			String name = dialog.getResourceName();
			String filename = dialog.getFilename();
			
			ProjectEGFile pef = (ProjectEGFile)EncogWorkBench.getInstance().getMainWindow().getTree().findTreeFile(filename);
			EncogMemoryCollection encog = pef.getCollection();
			
			switch (dialog.getType()) {
			case EncogScript:
				final EncogScript script = new EncogScript();
				script.setDescription("An Encog script");
				script.setSource("console.println(\'Hello World\')\n");
				encog.add(name, script);
				encog.save(pef.getFile().toString());
				pef.generateChildrenList();
				EncogWorkBench.getInstance().getMainWindow().redraw();
				break;
			case NeuralNetwork:
				CreateNeuralNetwork.process(name,pef);
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
			//EncogWorkBench.getInstance().getCurrentFile().add(
			//		generateNextID("population-"), pop);
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

	public void performObjectsProperties(DirectoryEntry selected) {

		final EditEncogObjectProperties dialog = new EditEncogObjectProperties(
				owner);
		dialog.getNameField().setValue(selected.getName());
		dialog.getDescription().setValue(selected.getDescription());
		if (dialog.process()) {

			String error = ResourceNameValidate.validateResourceName(dialog
					.getNameField().getValue());

			if (error != null) {
				EncogWorkBench.displayError("Data Error", error);
				return;
			}

			/* (EncogWorkBench.getInstance().getCurrentFile().updateProperties(
					selected.getName(), dialog.getNameField().getValue(),
					dialog.getDescription().getValue());*/
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}

	public void performEvaluate() {
		try {
			EvaluateDialog dialog = new EvaluateDialog(EncogWorkBench
					.getInstance().getMainWindow());
			if (dialog.process()) {
				//BasicNetwork network = dialog.getNetwork();
				//NeuralDataSet training = dialog.getTrainingSet();

				//double error = network.calculateError(training);
				//EncogWorkBench.displayMessage("Error For this Network", ""
				//		+ Format.formatPercent(error));
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

	public void performCreateTrainingData(String name) throws IOException {
		CreateTrainingDataDialog dialog = new CreateTrainingDataDialog(
				EncogWorkBench.getInstance().getMainWindow());

		dialog.setType(TrainingDataType.Empty);

		if (dialog.process()) {
			performLinkTraining(dialog.getType(), name);
		}
	}

	public void performLinkTraining(TrainingDataType type, String name)
			throws IOException {
		switch (type) {
		case Empty:
			CreateTrainingData.linkEmpty(name);
			break;
		case ImportCSV:
			CreateTrainingData.linkCSV(name);
			break;
		case MarketWindow:
			CreateTrainingData.linkMarketWindow(name);
			break;
		case PredictWindow:
			CreateTrainingData.linkPredictWindow(name);
			break;
		case Random:
			CreateTrainingData.linkRandom(name);
			break;
		case XORTemp:
			CreateTrainingData.linkXORTemp(name);
			break;
		case XOR:
			CreateTrainingData.linkXOR(name);
			break;
		}

	}

	public void performQuit() {

		System.exit(0);

	}

	public void performFileNewFile() throws IOException {
		CreateFileDialog dialog = new CreateFileDialog(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.setType(CreateFileType.EGFile);
		if (dialog.process()) {
			if (dialog.getType() == CreateFileType.EGFile) {
				String name = dialog.getFilename();
				name = FileUtil.forceExtension(new File(name).getName(), "eg");
				String basePath = EncogWorkBench.getInstance().getMainWindow()
						.getTree().getPath();
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					EncogMemoryCollection encog = new EncogMemoryCollection();
					encog.save(path.toString());
					EncogWorkBench.getInstance().getMainWindow().getTree()
							.refresh();
				}
			} else if (dialog.getType() == CreateFileType.TextFile) {
				String name = dialog.getFilename();
				name = FileUtil.forceExtension(new File(name).getName(), "txt");
				String basePath = EncogWorkBench.getInstance().getMainWindow()
						.getTree().getPath();
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					FileUtil.writeFileAsString(path, "");
					EncogWorkBench.getInstance().getMainWindow().getTree()
							.refresh();
				}
			}
		}

	}

	public void performObjectsDelete(Object selected) {
		if( selected instanceof ProjectEGItem ) {
			ProjectEGItem item = (ProjectEGItem)selected;
			item.getCollection().delete(item.getObj().getName());
			item.getCollection().save(item.getEncogFile().getFile().toString());
			item.getEncogFile().generateChildrenList();
			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
		}
		
	}

	public void performTrain() {
		TrainDialog dialog = new TrainDialog(EncogWorkBench.getInstance().getMainWindow());
		
		if( dialog.process() ) {
			ProjectEGItem methodItem = dialog.getNetwork();
			ProjectTraining trainingItem = dialog.getTrainingSet();
			
			BasicNetwork method = (BasicNetwork)methodItem.getObj();
			String ext = FileUtil.getFileExt(trainingItem.getFile());
			
			NeuralDataSet trainingData;
			
			if( ext.equalsIgnoreCase("csv") )
			{
				CSVFormat format;
				
				if( dialog.getUseDecimalComma().getValue() )
					format = CSVFormat.DECIMAL_COMMA;
				else
					format = CSVFormat.ENGLISH;
				
				boolean headers = dialog.getHeaders().getValue();
				
				trainingData = EncogUtility.loadCSV2Memory(
						trainingItem.getFile().toString(), 
						method.getInputCount(), 
						method.getOutputCount(), 
						headers, 
						format);
			}
			else if( ext.equalsIgnoreCase("egb") )
			{
				trainingData = EncogUtility.loadEGB2Memory(trainingItem.getFile().toString());
			}
			else
			{
				EncogWorkBench.displayError("Error", "Uknown file extension: " + ext);
				return;
			}
				
			Train train = new ResilientPropagation(method,trainingData);
			
			BasicTrainingProgress tab = new BasicTrainingProgress(train,methodItem,trainingItem);
			tab.setMaxError(dialog.getMaxError().getValue()/100.0);
			EncogWorkBench.getInstance().getMainWindow().openTab(tab);
		}
	}
}
