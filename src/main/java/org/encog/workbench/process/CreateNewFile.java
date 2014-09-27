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
import java.io.IOException;
import java.util.Random;

import org.encog.ml.CalculateScore;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.ml.prg.EncogProgramContext;
import org.encog.ml.prg.VariableMapping;
import org.encog.ml.prg.extension.StandardExtensions;
import org.encog.ml.prg.generator.PrgGenerator;
import org.encog.ml.prg.generator.PrgGrowGenerator;
import org.encog.ml.prg.generator.RampedHalfAndHalf;
import org.encog.ml.prg.train.PrgPopulation;
import org.encog.ml.prg.train.ZeroEvalScoreFunction;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.createfile.CreateFileDialog;
import org.encog.workbench.dialogs.createfile.CreateFileType;
import org.encog.workbench.dialogs.createfile.CreatePopulationDialog;
import org.encog.workbench.dialogs.population.epl.CreateEPLPopulationDialog;
import org.encog.workbench.dialogs.population.neat.NewPopulationDialog;
import org.encog.workbench.dialogs.trainingdata.CreateEmptyTrainingDialog;
import org.encog.workbench.util.FileUtil;

public class CreateNewFile {
	public static void performCreateFile() throws IOException {
		CreateFileDialog dialog = new CreateFileDialog(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.setTheType(CreateFileType.MachineLearningMethod);

		if (dialog.process()) {
			String name = dialog.getFilename();

			if (name == null || name.length() == 0) {
				EncogWorkBench.displayError("Data Missing",
						"Must specify a filename.");
				return;
			}

			File basePath = EncogWorkBench.getInstance().getMainWindow()
					.getTree().getPath();

			if (dialog.getTheType() == CreateFileType.MachineLearningMethod) {

				name = FileUtil.forceExtension(new File(name).getName(), "eg");
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					CreateNeuralNetwork.process(path);
				}
			} else if (dialog.getTheType() == CreateFileType.TextFile) {

				name = FileUtil.forceExtension(new File(name).getName(), "txt");
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					FileUtil.writeFileAsString(path, "");
				}
			} else if (dialog.getTheType() == CreateFileType.CSVFile) {

				name = FileUtil.forceExtension(new File(name).getName(), "csv");
				File path = new File(basePath, name);
				if (FileUtil.checkOverWrite(path)) {
					FileUtil.writeFileAsString(path, "");
				}
			} else if (dialog.getTheType() == CreateFileType.TrainingFile) {
				name = FileUtil.forceExtension(new File(name).getName(), "egb");
				File path = new File(basePath, name);
				createNewEGB(path);
			} else if (dialog.getTheType() == CreateFileType.Population) {
				name = FileUtil.forceExtension(new File(name).getName(), "eg");
				File path = new File(basePath, name);
				createNewPopulation(path);
			} else if (dialog.getTheType() == CreateFileType.AnalystIndicator) {
				name = FileUtil.forceExtension(new File(name).getName(), "ega");
				File path = new File(basePath, name);
				createNewAnalystIndicator(path);
			}

			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
		}
	}

	private static void createNewAnalystIndicator(File path) {
		EncogAnalystWizard.createRealtimeEncogAnalyst(path);

	}

	private static void createNewEGB(File file) {
		CreateEmptyTrainingDialog dialog = new CreateEmptyTrainingDialog(
				EncogWorkBench.getInstance().getMainWindow());

		if (dialog.process()) {
			int elements = dialog.getElements().getValue();
			int input = dialog.getInput().getValue();
			int output = dialog.getIdeal().getValue();

			BufferedMLDataSet trainingData = new BufferedMLDataSet(file);

			MLDataPair pair = BasicMLDataPair.createPair(input, output);
			trainingData.beginLoad(input, output);
			for (int i = 0; i < elements; i++) {
				trainingData.add(pair);
			}
			trainingData.endLoad();
		}
	}

	private static void createPopulationNEAT(File path) {
		NewPopulationDialog dialog = new NewPopulationDialog();

		if (dialog.process()) {
			int populationSize = dialog.getPopulationSize().getValue();
			int inputCount = dialog.getInputNeurons().getValue();
			int outputCount = dialog.getOutputNeurons().getValue();
			int cycles = dialog.getActivationCycles().getValue();
			NEATPopulation pop = new NEATPopulation(inputCount, outputCount,
					populationSize);
			pop.setActivationCycles(cycles);
			pop.setNEATActivationFunction(dialog.getNeatActivationFunction());
			EncogWorkBench.getInstance().save(path, pop);
			EncogWorkBench.getInstance().refresh();
		}
	}

	public static void createPopulationEPL(File path, PrgPopulation pop) {
		CreateEPLPopulationDialog dialog = new CreateEPLPopulationDialog();

		if (pop != null) {
			dialog.getPopulationSize().setValue(pop.size());

			for (VariableMapping mapping : pop.getContext().getDefinedVariables()) {
				dialog.getInputVariables().getModel().addElement(mapping.getName());
			}
		} else {
			dialog.getInputVariables().getModel().addElement("x");
			dialog.getPopulationSize().setValue(1000);
		}

		if (dialog.process()) {
			int populationSize = dialog.getPopulationSize().getValue();
			int maxDepth = dialog.getMaxDepth().getValue();

			CalculateScore score;

			if (dialog.getTrainingSet() != null) {
				score = new TrainingSetScore(dialog.getTrainingSet());
			} else {
				score = new ZeroEvalScoreFunction();
			}

			EncogProgramContext context = new EncogProgramContext();

			for (int i = 0; i < dialog.getInputVariables().getModel().getSize(); i++) {
				String str = (String) dialog.getInputVariables().getModel()
						.get(i);
				context.defineVariable(str);
			}

			StandardExtensions.createNumericOperators(context);

			if (pop == null) {
				pop = new PrgPopulation(context, populationSize);
			}

			try {
				EncogWorkBench.getInstance().getMainWindow().beginWait();
				RampedHalfAndHalf generate = new RampedHalfAndHalf(pop.getContext(), 2, maxDepth);
				generate.setScore(score);
				generate.generate(new Random(), pop);
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
			}

			if (path != null) {
				EncogWorkBench.getInstance().save(path, pop);
				EncogWorkBench.getInstance().refresh();
			}
		}
	}

	private static void createNewPopulation(File path) {
		CreatePopulationDialog dialog = new CreatePopulationDialog();
		if (dialog.process()) {
			switch (dialog.getTheType()) {
			case NEAT:
				createPopulationNEAT(path);
				break;
			case EPL:
				createPopulationEPL(path, null);
				break;
			}
		}
	}
}
