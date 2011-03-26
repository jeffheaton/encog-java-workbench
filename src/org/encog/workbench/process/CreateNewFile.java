package org.encog.workbench.process;

import java.io.File;
import java.io.IOException;

import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.neural.data.buffer.BufferedNeuralDataSet;
import org.encog.neural.neat.NEATPopulation;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.createfile.CreateFileDialog;
import org.encog.workbench.dialogs.createfile.CreateFileType;
import org.encog.workbench.dialogs.population.NewPopulationDialog;
import org.encog.workbench.dialogs.trainingdata.CreateEmptyTrainingDialog;
import org.encog.workbench.util.FileUtil;

public class CreateNewFile {
	public static void performCreateFile() throws IOException {
		CreateFileDialog dialog = new CreateFileDialog(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.setType(CreateFileType.MachineLearningMethod);
		
		
		if (dialog.process()) {
			String name = dialog.getFilename();
			
			if( name==null || name.length()==0 ) {
				EncogWorkBench.displayError("Data Missing", "Must specify a filename.");
				return;
			}
			
			File basePath = EncogWorkBench.getInstance().getMainWindow()
			.getTree().getPath();
			
			if (dialog.getType() == CreateFileType.MachineLearningMethod) {
				
				name = FileUtil.forceExtension(new File(name).getName(), "eg");
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					CreateNeuralNetwork.process(new File(name));
				}
			} else if (dialog.getType() == CreateFileType.TextFile) {
				
				name = FileUtil.forceExtension(new File(name).getName(), "txt");
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					FileUtil.writeFileAsString(path, "");					
				}
			} else if (dialog.getType() == CreateFileType.CSVFile) {

				name = FileUtil.forceExtension(new File(name).getName(), "csv");
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					FileUtil.writeFileAsString(path, "");
				}
			} else if (dialog.getType() == CreateFileType.TrainingFile) {
				name = FileUtil.forceExtension(new File(name).getName(), "egb");
				File path = new File(basePath, name);
				createNewEGB(path);
			} else if( dialog.getType() == CreateFileType.NEAT ) {
				name = FileUtil.forceExtension(new File(name).getName(), "eg");
				File path = new File(basePath, name);
				createNewPopulation(path);
			}
			
			EncogWorkBench.getInstance().getMainWindow().getTree()
			.refresh();
		}
	}
	
	private static void createNewEGB(File file)
	{
		CreateEmptyTrainingDialog dialog = new CreateEmptyTrainingDialog(
				EncogWorkBench.getInstance().getMainWindow());

		if (dialog.process()) {
			int elements = dialog.getElements().getValue();
			int input = dialog.getInput().getValue();
			int output = dialog.getIdeal().getValue();

			BufferedNeuralDataSet trainingData = new BufferedNeuralDataSet(file);

			NeuralDataPair pair = BasicNeuralDataPair.createPair(input,
					output);
			trainingData.beginLoad(input, output);
			for (int i = 0; i < elements; i++) {
				trainingData.add(pair);
			}
			trainingData.endLoad();
		}
	}
	
	private static void createNewPopulation(File path) {
		NewPopulationDialog dialog = new NewPopulationDialog();

		if (dialog.process()) {
			int populationSize = dialog.getPopulationSize().getValue();
			int inputCount = dialog.getInputNeurons().getValue();
			int outputCount = dialog.getOutputNeurons().getValue();
			NEATPopulation pop = new NEATPopulation(inputCount,outputCount,populationSize);
			pop.setNeatActivationFunction(dialog.getNeatActivationFunction());
			pop.setOutputActivationFunction(dialog.getOutputActivationFunction());
			EncogWorkBench.getInstance().save(path,pop);
			EncogWorkBench.getInstance().refresh();
		}
	}
}
