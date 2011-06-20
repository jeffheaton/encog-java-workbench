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
package org.encog.workbench.process;

import java.util.ArrayList;
import java.util.List;

import org.encog.mathutil.randomize.RangeRandomizer;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.folded.FoldedDataSet;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.training.SVMTrain;
import org.encog.ml.svm.training.search.SVMSearchJob;
import org.encog.ml.train.MLTrain;
import org.encog.neural.art.ART1;
import org.encog.neural.cpn.CPN;
import org.encog.neural.cpn.training.TrainInstar;
import org.encog.neural.cpn.training.TrainOutstar;
import org.encog.neural.flat.train.prop.RPROPType;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.ContainsFlat;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.networks.training.cross.CrossValidationKFold;
import org.encog.neural.networks.training.genetic.NeuralGeneticAlgorithm;
import org.encog.neural.networks.training.lma.LevenbergMarquardtTraining;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.manhattan.ManhattanPropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import org.encog.neural.networks.training.simple.TrainAdaline;
import org.encog.neural.rbf.RBFNetwork;
import org.encog.neural.som.SOM;
import org.encog.neural.som.training.basic.BasicTrainSOM;
import org.encog.neural.som.training.clustercopy.SOMClusterCopyTraining;
import org.encog.neural.thermal.HopfieldNetwork;
import org.encog.util.Format;
import org.encog.util.concurrency.EngineConcurrency;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.dialogs.training.ChooseBasicNetworkTrainingMethod;
import org.encog.workbench.dialogs.training.TrainDialog;
import org.encog.workbench.dialogs.training.methods.InputADALINE;
import org.encog.workbench.dialogs.training.methods.InputAnneal;
import org.encog.workbench.dialogs.training.methods.InputBackpropagation;
import org.encog.workbench.dialogs.training.methods.InputGenetic;
import org.encog.workbench.dialogs.training.methods.InputInstar;
import org.encog.workbench.dialogs.training.methods.InputLMA;
import org.encog.workbench.dialogs.training.methods.InputManhattan;
import org.encog.workbench.dialogs.training.methods.InputOutstar;
import org.encog.workbench.dialogs.training.methods.InputResilient;
import org.encog.workbench.dialogs.training.methods.InputSCG;
import org.encog.workbench.dialogs.training.methods.InputSOM;
import org.encog.workbench.dialogs.training.methods.InputSVM;
import org.encog.workbench.dialogs.training.methods.InputSearchSVM;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.training.BasicTrainingProgress;

public class TrainBasicNetwork {

	private ProjectEGFile mlMethod;
	private EncogCommonTab parentTab;
	
	private MLDataSet wrapTrainingData(MLDataSet trainingData) {
		final FoldedDataSet folded = new FoldedDataSet(trainingData);
		return folded;
	}
	
	private MLTrain wrapTrainer(MLDataSet folded, MLTrain train, int foldCount) { 
		final CrossValidationKFold trainFolded = new CrossValidationKFold(train,foldCount);
		return trainFolded;
	}

	public TrainBasicNetwork(ProjectEGFile mlMethod, EncogCommonTab parentTab) {
		this.mlMethod = mlMethod;
		this.parentTab = parentTab;
	}

	public void performTrain() {

		TrainDialog dialog = new TrainDialog(EncogWorkBench.getInstance()
				.getMainWindow());
		if (mlMethod != null)
			dialog.setMethod(mlMethod);

		if (dialog.process()) {
			MLMethod method = dialog.getNetwork();
			MLDataSet trainingData = dialog.getTrainingSet();

			if (method == null) {
				EncogWorkBench.displayError("Error",
						"Machine language method is required to train.");
				return;
			}

			if (method instanceof ART1) {
				EncogWorkBench
						.displayError("Error",
								"ART1 Networks are not trained, they learn as they are queried.");
				return;
			}

			if (trainingData == null) {
				EncogWorkBench.displayError("Error",
						"Training set is required to train.");
				return;
			}

			if (method instanceof HopfieldNetwork) {
				HopfieldNetwork hp = (HopfieldNetwork) method;
				ProjectEGFile file = (ProjectEGFile) dialog.getComboNetwork()
						.getSelectedValue();
				for (MLDataPair pair : trainingData) {
					hp.addPattern(pair.getInput());
				}
				if (EncogWorkBench.askQuestion("Hopfield",
						"Training done, save?")) {
					file.save();
				}
			} else if (method instanceof SOM) {
				ProjectEGFile file = (ProjectEGFile) dialog.getComboNetwork()
						.getSelectedValue();
				performSOM(file, trainingData);
			} else if (method instanceof SVM) {
				ProjectEGFile file = (ProjectEGFile) dialog.getComboNetwork()
						.getSelectedValue();
				performSVM(file, trainingData);
			} else if (method instanceof CPN) {
				ProjectEGFile file = (ProjectEGFile) dialog.getComboNetwork()
						.getSelectedValue();
				performCPN(file, trainingData);
			} else if (method instanceof BasicNetwork || method instanceof RBFNetwork ) {

				ChooseBasicNetworkTrainingMethod choose = new ChooseBasicNetworkTrainingMethod(
						EncogWorkBench.getInstance().getMainWindow(),method);
				if (choose.process()) {
					ProjectEGFile file = (ProjectEGFile) dialog
							.getComboNetwork().getSelectedValue();

					switch (choose.getType()) {
					case SCG:
						performSCG(file, trainingData);
						break;
					case PropagationResilient:
						performRPROP(file, trainingData);
						break;
					case PropagationBack:
						performBPROP(file, trainingData);
						break;
					case PropagationManhattan:
						performManhattan(file, trainingData);
						break;
					case LevenbergMarquardt:
						performLMA(file, trainingData);
						break;
					case Genetic:
						performGenetic(file, trainingData);
						break;
					case Annealing:
						performAnnealing(file, trainingData);
						break;
					case ADALINE:
						performADALINE(file, trainingData);
						break;
					}
				}
			} else {
				EncogWorkBench.displayError("Unknown Method",
						"No training method is available for: "
								+ method.getClass().getName());
			}
		}
	}

	private void performCPN(ProjectEGFile file, MLDataSet trainingData) {
		SelectItem selectInstar;
		SelectItem selectOutstar;

		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(selectInstar = new SelectItem(
				"Instar Training",
				"This training must be done first.  Train the competative clustering part of the network."));
		list.add(selectOutstar = new SelectItem("Outstar Training",
				"This training must be done second.  Train the regression part of the network."));
		SelectDialog sel = new SelectDialog(EncogWorkBench.getInstance()
				.getMainWindow(), list);
		sel.setVisible(true);

		if (sel.getSelected() == selectInstar) {
			InputInstar dialog = new InputInstar();

			if (dialog.process()) {
				double learnRate = dialog.getLearningRate().getValue();
				boolean init = dialog.getInitWeights().getValue();
				TrainInstar train = new TrainInstar((CPN)file.getObject(),trainingData,learnRate,init);
				startup(file,train, dialog.getMaxError().getValue()/100.0);
			}
		} else if (sel.getSelected() == selectOutstar) {
			InputOutstar dialog = new InputOutstar();

			if (dialog.process()) {
				double learnRate = dialog.getLearningRate().getValue();
				TrainOutstar train = new TrainOutstar((CPN)file.getObject(),trainingData,learnRate);
				startup(file,train, dialog.getMaxError().getValue()/100.0);
			}
		}
	}

	private void performSOM(ProjectEGFile file, MLDataSet trainingData) {

		SelectItem selectBasicSOM;
		SelectItem selectSOMClusterCopy;

		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(selectBasicSOM = new SelectItem(
				"Basic SOM Neighborhood Training",
				"Train the nerual network using the classic neighborhood based SOM training."));
		list.add(selectSOMClusterCopy = new SelectItem(
				"SOM Cluster Copy Training",
				"Train the SOM using the cluser copy method."));
		SelectDialog sel = new SelectDialog(EncogWorkBench.getInstance()
				.getMainWindow(), list);
		sel.setVisible(true);

		if (sel.getSelected() == selectBasicSOM) {
			InputSOM somDialog = new InputSOM();

			if (somDialog.process()) {
				BasicTrainSOM train = new BasicTrainSOM((SOM) file.getObject(),
						somDialog.getLearningRate().getValue(), trainingData,
						somDialog.getNeighborhoodFunction());
				train.setForceWinner(somDialog.getForceWinner().getValue());
				startup(file, train, somDialog.getMaxError().getValue() / 100.0);
			}
		} else if (sel.getSelected() == selectSOMClusterCopy) {
			SOMClusterCopyTraining train = new SOMClusterCopyTraining(
					(SOM) file.getObject(), trainingData);
			train.iteration();
			if (EncogWorkBench.askQuestion("SOM", "Training done, save?")) {
				file.save();
			} else {
				file.revert();
			}
		}

	}

	private void performADALINE(ProjectEGFile file, MLDataSet trainingData) {
		InputADALINE dialog = new InputADALINE();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();

			MLTrain train = new TrainAdaline((BasicNetwork) file.getObject(),
					trainingData, learningRate);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private void performBPROP(ProjectEGFile file, MLDataSet trainingData) {
		InputBackpropagation dialog = new InputBackpropagation();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();
			double momentum = dialog.getMomentum().getValue();
			int kFold = dialog.getKfold().getValue();
			
			if( kFold>0 ) {
				trainingData = this.wrapTrainingData(trainingData);
			}

			MLTrain train = new Backpropagation((BasicNetwork) file.getObject(),
					trainingData, learningRate, momentum);
			
			if( kFold>0 ) {
				train = this.wrapTrainer(trainingData,train,kFold);
			}
			
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private void performAnnealing(ProjectEGFile file, MLDataSet trainingData) {
		InputAnneal dialog = new InputAnneal();
		if (dialog.process()) {
			final double startTemp = dialog.getStartTemp().getValue();
			final double stopTemp = dialog.getStartTemp().getValue();
			final int cycles = dialog.getCycles().getValue();

			CalculateScore score = new TrainingSetScore(trainingData);
			final NeuralSimulatedAnnealing train = new NeuralSimulatedAnnealing(
					(BasicNetwork) file.getObject(), score, startTemp,
					stopTemp, cycles);
			train.setTraining(trainingData);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private void performGenetic(ProjectEGFile file, MLDataSet trainingData) {
		InputGenetic dialog = new InputGenetic();
		if (dialog.process()) {
			final int populationSize = dialog.getPopulationSize().getValue();
			final double mutationPercent = dialog.getMutationPercent()
					.getValue();
			final double percentToMate = dialog.getPercentToMate().getValue();

			CalculateScore score = new TrainingSetScore(trainingData);
			final NeuralGeneticAlgorithm train = new NeuralGeneticAlgorithm(
					(BasicNetwork) file.getObject(),
					new RangeRandomizer(-1, 1), score, populationSize,
					mutationPercent, percentToMate);
			train.setTraining(trainingData);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private void performLMA(ProjectEGFile file, MLDataSet trainingData) {
		InputLMA dialog = new InputLMA();

		if (dialog.process()) {
			LevenbergMarquardtTraining train = new LevenbergMarquardtTraining(
					(BasicNetwork) file.getObject(), trainingData);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private void performManhattan(ProjectEGFile file, MLDataSet trainingData) {
		InputManhattan dialog = new InputManhattan();
		if (dialog.process()) {
			double learningRate = dialog.getFixedDelta().getValue();
			int kFold = dialog.getKfold().getValue();

			if( kFold>0 ) {
				trainingData = this.wrapTrainingData(trainingData);
			}
			
			MLTrain train = new ManhattanPropagation(
					(BasicNetwork) file.getObject(), trainingData, learningRate);
			
			if( kFold>0 ) {
				train = this.wrapTrainer(trainingData,train,kFold);
			}			
			
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private void performRPROP(ProjectEGFile file, MLDataSet trainingData) {
		InputResilient dialog = new InputResilient();
		if (dialog.process()) {
			final double initialUpdate = dialog.getInitialUpdate().getValue();
			final double maxStep = dialog.getMaxStep().getValue();
						
			int kFold = dialog.getKfold().getValue();
			
			if( kFold>0 ) {
				trainingData = this.wrapTrainingData(trainingData);
			}
			
			MLTrain train = new ResilientPropagation(
					(ContainsFlat) file.getObject(), trainingData,
					initialUpdate, maxStep);
			
			switch( dialog.getRpropType().getSelectedIndex() )
			{
				case 0:
					((ResilientPropagation)train).setRPROPType(RPROPType.RPROPp);
					break;
				case 1:
					((ResilientPropagation)train).setRPROPType(RPROPType.RPROPm);
					break;
				case 2:
					((ResilientPropagation)train).setRPROPType(RPROPType.iRPROPp);
					break;
				case 3:
					((ResilientPropagation)train).setRPROPType(RPROPType.iRPROPm);
					break;
			}
			
			if( kFold>0 ) {
				train = this.wrapTrainer(trainingData,train,kFold);
			}
			
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private void performSCG(ProjectEGFile file, MLDataSet trainingData) {
		InputSCG dialog = new InputSCG();
		if (dialog.process()) {
			int kFold = dialog.getKfold().getValue();
			
			if( kFold>0 ) {
				trainingData = this.wrapTrainingData(trainingData);
			}
			
			MLTrain train = new ScaledConjugateGradient(
					(BasicNetwork) file.getObject(), trainingData);
			
			if( kFold>0 ) {
				train = this.wrapTrainer(trainingData,train,kFold);
			}
			
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private void performSVM(ProjectEGFile file, MLDataSet trainingData) {
		SelectItem selectBasicSVM;
		SelectItem selectSearchSVM;

		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(selectBasicSVM = new SelectItem(
				"Basic SVM Training",
				"Train the SVM using a fixed gamma and constant.  Very fast training, but will not result in the lowest possable error for your SVM."));
		list.add(selectSearchSVM = new SelectItem(
				"Search SVM Training",
				"Works similar to SimpleSVM training, but tries many different gamma and constant values."));
		SelectDialog sel = new SelectDialog(EncogWorkBench.getInstance()
				.getMainWindow(), list);
		sel.setVisible(true);

		if (sel.getSelected() == selectBasicSVM) {
			performSVMSimple(file, trainingData);
		} else if (sel.getSelected() == selectSearchSVM) {
			performSVMSearch(file, trainingData);
		}
	}

	private void performSVMSimple(ProjectEGFile file, MLDataSet trainingData) {
		InputSVM dialog = new InputSVM((SVM) file.getObject());

		if (dialog.process()) {
			double c = dialog.getC().getValue();
			double g = dialog.getGamma().getValue();
			SVM method = (SVM) file.getObject();
			SVMTrain train = new SVMTrain((SVM) method, trainingData);
			train.setC(c);
			train.setGamma(g);
			train.iteration();
			double error = method.calculateError(trainingData);
			if (EncogWorkBench.askQuestion("Training Done",
					"Error: " + Format.formatPercent(error)
							+ "\nSave training?")) {
				file.save();
			}
		}

	}

	private void performSVMSearch(ProjectEGFile file, MLDataSet trainingData) {
		InputSearchSVM dialog = new InputSearchSVM();
		SVM method = (SVM) file.getObject();

		dialog.getBeginningGamma().setValue(SVMTrain.DEFAULT_GAMMA_BEGIN);
		dialog.getEndingGamma().setValue(SVMTrain.DEFAULT_GAMMA_END);
		dialog.getStepGamma().setValue(SVMTrain.DEFAULT_GAMMA_STEP);
		dialog.getBeginningC().setValue(SVMTrain.DEFAULT_CONST_BEGIN);
		dialog.getEndingC().setValue(SVMTrain.DEFAULT_CONST_END);
		dialog.getStepC().setValue(SVMTrain.DEFAULT_CONST_STEP);

		if (dialog.process()) {
			double maxError = dialog.getMaxError().getValue() / 100.0;
			SVMSearchJob train = new SVMSearchJob(method, trainingData, null);
			train.setGammaBegin(dialog.getBeginningGamma().getValue());
			train.setGammaEnd(dialog.getEndingGamma().getValue());
			train.setGammaStep(dialog.getStepGamma().getValue());
			train.setConstBegin(dialog.getBeginningC().getValue());
			train.setConstEnd(dialog.getEndingC().getValue());
			train.setConstStep(dialog.getStepC().getValue());
			EngineConcurrency.getInstance().setThreadCount(dialog.getThreadCount().getValue());
			startup(file, train, maxError);
		}
	}

	private void startup(ProjectEGFile file, MLTrain train, double maxError) {
		BasicTrainingProgress tab = new BasicTrainingProgress(train, file,
				train.getTraining());
		if (this.parentTab != null) {
			tab.setParentTab(tab);
		}
		tab.setMaxError(maxError);
		EncogWorkBench.getInstance().getMainWindow().getTabManager().openTab(tab);
	}
}
