/*
 * Encog(tm) Workbench v3.2
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
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

import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.ml.ea.score.CalculateGenomeScore;
import org.encog.ml.ea.score.GeneticScoreAdapter;
import org.encog.ml.prg.EncogProgramContext;
import org.encog.ml.prg.extension.StandardExtensions;
import org.encog.ml.prg.train.PrgPopulation;
import org.encog.ml.prg.train.rewrite.RewriteConstants;
import org.encog.ml.prg.train.rewrite.algebraic.RewriteAlgebraic;
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
			
			if( name==null || name.length()==0 ) {
				EncogWorkBench.displayError("Data Missing", "Must specify a filename.");
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
			} else if( dialog.getTheType() == CreateFileType.Population ) {
				name = FileUtil.forceExtension(new File(name).getName(), "eg");
				File path = new File(basePath, name);
				createNewPopulation(path);
			} else if( dialog.getTheType() == CreateFileType.AnalystIndicator ) {
				name = FileUtil.forceExtension(new File(name).getName(), "ega");
				File path = new File(basePath, name);
				createNewAnalystIndicator(path);
			}
			
			EncogWorkBench.getInstance().getMainWindow().getTree()
			.refresh();
		}
	}
	
	private static void createNewAnalystIndicator(File path) {
		EncogAnalystWizard.createRealtimeEncogAnalyst(path);
		
	}

	private static void createNewEGB(File file)
	{
		CreateEmptyTrainingDialog dialog = new CreateEmptyTrainingDialog(
				EncogWorkBench.getInstance().getMainWindow());

		if (dialog.process()) {
			int elements = dialog.getElements().getValue();
			int input = dialog.getInput().getValue();
			int output = dialog.getIdeal().getValue();

			BufferedMLDataSet trainingData = new BufferedMLDataSet(file);

			MLDataPair pair = BasicMLDataPair.createPair(input,
					output);
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
			NEATPopulation pop = new NEATPopulation(inputCount,outputCount,populationSize);
			pop.setActivationCycles(cycles);
			pop.setNeatActivationFunction(dialog.getNeatActivationFunction());
			EncogWorkBench.getInstance().save(path,pop);
			EncogWorkBench.getInstance().refresh();
		}
	}
	
	public static void resetEPLPopulation(PrgPopulation pop, int sz, CalculateGenomeScore score) {
		pop.addRewriteRule(new RewriteConstants());
		pop.addRewriteRule(new RewriteAlgebraic());
		Random random = new Random();
		pop.getContext().getParams().setPopulationSize(sz);
		pop.getGenomeFactory().factorRandomPopulation(random,
				pop, score, 5);
	}
	
	private static void createPopulationEPL(File path) {
		CreateEPLPopulationDialog dialog = new CreateEPLPopulationDialog();
		if( dialog.process() ) {
			int populationSize = dialog.getPopulationSize().getValue();
			int inputCount = dialog.getInputSize().getValue();
			
			CalculateGenomeScore score = null;
			
			if( dialog.getTrainingSet()!=null) {
				score = new GeneticScoreAdapter(new TrainingSetScore(dialog.getTrainingSet()));
			}
			
			EncogProgramContext context = new EncogProgramContext();
			context.defineVariable("x");

			StandardExtensions.createNumericOperators(context.getFunctions());

			PrgPopulation pop = new PrgPopulation(context);
			resetEPLPopulation(pop,populationSize,score);

			EncogWorkBench.getInstance().save(path,pop);
			EncogWorkBench.getInstance().refresh();
		}
	}
	
	private static void createNewPopulation(File path) {
		CreatePopulationDialog dialog = new CreatePopulationDialog();
		if(dialog.process()) 
		{
			switch(dialog.getTheType()) {
			case NEAT:createPopulationNEAT(path);break;
			case EPL:createPopulationEPL(path);break;
			}
		}
	}
}
