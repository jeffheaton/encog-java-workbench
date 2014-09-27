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

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import org.encog.mathutil.randomize.NguyenWidrowRandomizer;
import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;
import org.encog.ml.bayesian.BayesianNetwork;
import org.encog.ml.bayesian.training.BayesianInit;
import org.encog.ml.bayesian.training.TrainBayesian;
import org.encog.ml.bayesian.training.estimator.BayesEstimator;
import org.encog.ml.bayesian.training.estimator.EstimatorNone;
import org.encog.ml.bayesian.training.estimator.SimpleEstimator;
import org.encog.ml.bayesian.training.search.SearchNone;
import org.encog.ml.bayesian.training.search.k2.BayesSearch;
import org.encog.ml.bayesian.training.search.k2.SearchK2;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.folded.FoldedDataSet;
import org.encog.ml.ea.score.adjust.ComplexityAdjustedScore;
import org.encog.ml.ea.train.basic.TrainEA;
import org.encog.ml.prg.opp.ConstMutation;
import org.encog.ml.prg.opp.SubtreeCrossover;
import org.encog.ml.prg.opp.SubtreeMutation;
import org.encog.ml.prg.species.PrgSpeciation;
import org.encog.ml.prg.train.PrgPopulation;
import org.encog.ml.prg.train.rewrite.RewriteAlgebraic;
import org.encog.ml.prg.train.rewrite.RewriteConstants;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.training.SVMSearchTrain;
import org.encog.ml.svm.training.SVMTrain;
import org.encog.ml.train.MLTrain;
import org.encog.neural.art.ART1;
import org.encog.neural.cpn.CPN;
import org.encog.neural.cpn.training.TrainInstar;
import org.encog.neural.cpn.training.TrainOutstar;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.NEATUtil;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.ContainsFlat;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.networks.training.cross.CrossValidationKFold;
import org.encog.neural.networks.training.lma.LevenbergMarquardtTraining;
import org.encog.neural.networks.training.nm.NelderMeadTraining;
import org.encog.neural.networks.training.pnn.TrainBasicPNN;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.manhattan.ManhattanPropagation;
import org.encog.neural.networks.training.propagation.quick.QuickPropagation;
import org.encog.neural.networks.training.propagation.resilient.RPROPType;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import org.encog.neural.networks.training.pso.NeuralPSO;
import org.encog.neural.networks.training.simple.TrainAdaline;
import org.encog.neural.pnn.BasicPNN;
import org.encog.neural.rbf.RBFNetwork;
import org.encog.neural.rbf.training.SVDTraining;
import org.encog.neural.som.SOM;
import org.encog.neural.som.training.basic.BasicTrainSOM;
import org.encog.neural.som.training.clustercopy.SOMClusterCopyTraining;
import org.encog.neural.thermal.HopfieldNetwork;
import org.encog.util.Format;
import org.encog.util.concurrency.EngineConcurrency;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.dialogs.population.epl.TrainEPLPopulationDialog;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.dialogs.training.ChooseBasicNetworkTrainingMethod;
import org.encog.workbench.dialogs.training.TrainDialog;
import org.encog.workbench.dialogs.training.methods.InputADALINE;
import org.encog.workbench.dialogs.training.methods.InputAnneal;
import org.encog.workbench.dialogs.training.methods.InputBackpropagation;
import org.encog.workbench.dialogs.training.methods.InputBayesian;
import org.encog.workbench.dialogs.training.methods.InputGenetic;
import org.encog.workbench.dialogs.training.methods.InputInstar;
import org.encog.workbench.dialogs.training.methods.InputLMA;
import org.encog.workbench.dialogs.training.methods.InputManhattan;
import org.encog.workbench.dialogs.training.methods.InputNEAT;
import org.encog.workbench.dialogs.training.methods.InputNelderMead;
import org.encog.workbench.dialogs.training.methods.InputOutstar;
import org.encog.workbench.dialogs.training.methods.InputPSO;
import org.encog.workbench.dialogs.training.methods.InputQPROP;
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
		final CrossValidationKFold trainFolded = new CrossValidationKFold(
				train, foldCount);
		return trainFolded;
	}

	public TrainBasicNetwork(ProjectEGFile mlMethod, EncogCommonTab parentTab) {
		this.mlMethod = mlMethod;
		this.parentTab = parentTab;
	}

	private void performNEATTrain(NEATPopulation population,
			MLDataSet trainingData, ProjectEGFile file, MLDataSet validationSet) {

		InputNEAT dialog = new InputNEAT();

		if (dialog.process()) {
			CalculateScore score = new TrainingSetScore(trainingData);

			int kFold = dialog.getKfold().getValue();

			if (kFold > 0) {
				trainingData = this.wrapTrainingData(trainingData);
			}

			MLTrain train = NEATUtil.constructNEATTrainer(population, score);

			if (kFold > 0) {
				train = this.wrapTrainer(trainingData, train, kFold);
			}

			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationSet);
		}
	}

	private void performMethodTrain(MLMethod method, MLDataSet trainingData,
			ProjectEGFile file, MLDataSet validationData) {

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
			for (MLDataPair pair : trainingData) {
				hp.addPattern(pair.getInput());
			}
			if (EncogWorkBench.askQuestion("Hopfield", "Training done, save?")) {
				file.save();
			}
		} else if (method instanceof SOM) {
			performSOM(file, trainingData, validationData);
		} else if (method instanceof SVM) {
			performSVM(file, trainingData, validationData);
		} else if (method instanceof CPN) {
			performCPN(file, trainingData, validationData);
		} else if (method instanceof BayesianNetwork) {
			performBayesian(file, trainingData, validationData);
		} else if (method instanceof BasicPNN) {
			performPNN(file, trainingData, validationData);
		} else if (method instanceof PrgPopulation) {
			performPrgPopulationTrain(file, trainingData, validationData);
		} else if (method instanceof BasicNetwork
				|| method instanceof RBFNetwork) {

			ChooseBasicNetworkTrainingMethod choose = new ChooseBasicNetworkTrainingMethod(
					EncogWorkBench.getInstance().getMainWindow(), method);
			if (choose.process()) {
				switch (choose.getTheType()) {
				case SCG:
					performSCG(file, trainingData, validationData);
					break;
				case PropagationResilient:
					performRPROP(file, trainingData, validationData);
					break;
				case PropagationBack:
					performBPROP(file, trainingData, validationData);
					break;
				case PropagationManhattan:
					performManhattan(file, trainingData, validationData);
					break;
				case LevenbergMarquardt:
					performLMA(file, trainingData, validationData);
					break;
				case Genetic:
					performGenetic(file, trainingData, validationData);
					break;
				case Annealing:
					performAnnealing(file, trainingData, validationData);
					break;
				case ADALINE:
					performADALINE(file, trainingData, validationData);
					break;
				case PropagationQuick:
					performQPROP(file, trainingData, validationData);
					break;
				case SVD:
					performSVD(file, trainingData);
					break;
				case PSO:
					performPSO(file, trainingData, validationData);
					break;
				case NelderMead:
					performNelderMead(file, trainingData, validationData);
					break;
				}
			}
		} else {
			EncogWorkBench.displayError("Unknown Method",
					"No training method is available for: "
							+ method.getClass().getName());
		}

	}

	public void performTrain() {

		TrainDialog dialog = new TrainDialog(true);
		if (mlMethod != null)
			dialog.setMethod(mlMethod);

		if (dialog.process()) {

			Object obj = dialog.getMethodOrPopulation();

			if (obj instanceof NEATPopulation) {
				NEATPopulation population = (NEATPopulation) obj;
				MLDataSet trainingData = dialog.getTrainingSet();
				ProjectEGFile file = (ProjectEGFile) dialog.getComboNetwork()
						.getSelectedValue();
				performNEATTrain(population, trainingData, file,
						dialog.getValidationSet());
			} else if (obj instanceof MLMethod) {
				MLMethod method = (MLMethod) obj;
				MLDataSet trainingData = dialog.getTrainingSet();
				ProjectEGFile file = (ProjectEGFile) dialog.getComboNetwork()
						.getSelectedValue();
				performMethodTrain(method, trainingData, file,
						dialog.getValidationSet());
			}

		}
	}

	private void performCPN(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
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
				TrainInstar train = new TrainInstar((CPN) file.getObject(),
						trainingData, learnRate, init);
				startup(file, train, dialog.getMaxError().getValue() / 100.0,
						validationData);
			}
		} else if (sel.getSelected() == selectOutstar) {
			InputOutstar dialog = new InputOutstar();

			if (dialog.process()) {
				double learnRate = dialog.getLearningRate().getValue();
				TrainOutstar train = new TrainOutstar((CPN) file.getObject(),
						trainingData, learnRate);
				startup(file, train, dialog.getMaxError().getValue() / 100.0,
						validationData);
			}
		}
	}

	private void performSOM(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {

		SelectItem selectBasicSOM;
		SelectItem selectSOMClusterCopy;

		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(selectBasicSOM = new SelectItem(
				"Basic SOM Neighborhood Training",
				"Train the nerual network using the classic neighborhood based SOM training."));
		list.add(selectSOMClusterCopy = new SelectItem(
				"SOM Cluster Copy Training",
				"Train the SOM using the cluser copy method.  This is a very limited type of training that copies the training set to the SOM.  Because of this copy, the training set must have the same (or fewer) count of elements as the SOM has output neurons.  Cluster copy training is typically used to provide the SOM with an initial set of weights to be used with more advanced training."));
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
				startup(file, train,
						somDialog.getMaxError().getValue() / 100.0,
						validationData);
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

	private void performADALINE(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputADALINE dialog = new InputADALINE();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();

			MLTrain train = new TrainAdaline((BasicNetwork) file.getObject(),
					trainingData, learningRate);
			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}

	}

	private void performBPROP(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputBackpropagation dialog = new InputBackpropagation();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();
			double momentum = dialog.getMomentum().getValue();
			int kFold = dialog.getKfold().getValue();

			if (kFold > 0) {
				trainingData = this.wrapTrainingData(trainingData);
			}

			MLTrain train = new Backpropagation(
					(BasicNetwork) file.getObject(), trainingData,
					learningRate, momentum);

			if (kFold > 0) {
				train = this.wrapTrainer(trainingData, train, kFold);
			}

			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}

	}

	private void performAnnealing(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
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
			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}

	}

	private void performGenetic(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputGenetic dialog = new InputGenetic();
		if (dialog.process()) {
			final int populationSize = dialog.getPopulationSize().getValue();
			final double mutationPercent = dialog.getMutationPercent()
					.getValue();
			final double percentToMate = dialog.getPercentToMate().getValue();

			CalculateScore score = new TrainingSetScore(trainingData);
			/*
			 * final MLMethodGeneticAlgorithm train = new
			 * MLMethodGeneticAlgorithm( (BasicNetwork) file.getObject(), new
			 * RangeRandomizer(-1, 1), score, populationSize, mutationPercent,
			 * percentToMate); train.setTraining(trainingData); startup(file,
			 * train, dialog.getMaxError().getValue() / 100.0, validationData);
			 */
		}

	}

	private void performPSO(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputPSO dialog = new InputPSO();
		if (dialog.process()) {
			final int particleCount = dialog.getParticleCount().getValue();
			final double c1 = dialog.getC1().getValue();
			final double c2 = dialog.getC2().getValue();
			final double particleInertia = dialog.getParticleInertia()
					.getValue();
			final double maxVelocity = dialog.getMaxVelocity().getValue();
			final double maxWeight = dialog.getMaxWeight().getValue();

			CalculateScore score = new TrainingSetScore(trainingData);
			final NeuralPSO train = new NeuralPSO(
					(BasicNetwork) file.getObject(),
					new NguyenWidrowRandomizer(), score, particleCount);
			train.setC1(c1);
			train.setC2(c2);
			train.setInertiaWeight(particleInertia);
			train.setMaxVelocity(maxVelocity);
			train.setMaxPosition(maxWeight);
			train.setTraining(trainingData);
			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}

	}

	private void performLMA(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputLMA dialog = new InputLMA();

		if (dialog.process()) {
			LevenbergMarquardtTraining train = new LevenbergMarquardtTraining(
					(BasicNetwork) file.getObject(), trainingData);
			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}
	}

	private void performManhattan(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputManhattan dialog = new InputManhattan();
		if (dialog.process()) {
			double learningRate = dialog.getFixedDelta().getValue();
			int kFold = dialog.getKfold().getValue();

			if (kFold > 0) {
				trainingData = this.wrapTrainingData(trainingData);
			}

			MLTrain train = new ManhattanPropagation(
					(BasicNetwork) file.getObject(), trainingData, learningRate);

			if (kFold > 0) {
				train = this.wrapTrainer(trainingData, train, kFold);
			}

			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}
	}

	private void performRPROP(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputResilient dialog = new InputResilient();
		if (dialog.process()) {
			final double initialUpdate = dialog.getInitialUpdate().getValue();
			final double maxStep = dialog.getMaxStep().getValue();

			int kFold = dialog.getKfold().getValue();

			if (kFold > 0) {
				trainingData = this.wrapTrainingData(trainingData);
			}

			MLTrain train = new ResilientPropagation(
					(ContainsFlat) file.getObject(), trainingData,
					initialUpdate, maxStep);

			switch (dialog.getRpropType().getSelectedIndex()) {
			case 0:
				((ResilientPropagation) train).setRPROPType(RPROPType.RPROPp);
				break;
			case 1:
				((ResilientPropagation) train).setRPROPType(RPROPType.RPROPm);
				break;
			case 2:
				((ResilientPropagation) train).setRPROPType(RPROPType.iRPROPp);
				break;
			case 3:
				((ResilientPropagation) train).setRPROPType(RPROPType.iRPROPm);
				break;
			}

			if (kFold > 0) {
				train = this.wrapTrainer(trainingData, train, kFold);
			}

			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}
	}

	private void performSCG(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputSCG dialog = new InputSCG();
		if (dialog.process()) {
			int kFold = dialog.getKfold().getValue();

			if (kFold > 0) {
				trainingData = this.wrapTrainingData(trainingData);
			}

			MLTrain train = new ScaledConjugateGradient(
					(BasicNetwork) file.getObject(), trainingData);

			if (kFold > 0) {
				train = this.wrapTrainer(trainingData, train, kFold);
			}

			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}
	}

	private void performSVM(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
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
			performSVMSimple(file, trainingData, validationData);
		} else if (sel.getSelected() == selectSearchSVM) {
			performSVMSearch(file, trainingData, validationData);
		}
	}

	private void performSVMSimple(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
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
			StringBuilder str = new StringBuilder();
			str.append("Training Error: ");
			str.append(Format.formatPercent(error));
			str.append("\n");

			if (validationData != null) {
				str.append("Validation Error: ");
				str.append(Format.formatPercent(method
						.calculateError(validationData)));
			}
			str.append("\nSave training?");

			if (EncogWorkBench.askQuestion("Training Done", str.toString())) {
				file.save();
			}
		}

	}

	private void performSVMSearch(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputSearchSVM dialog = new InputSearchSVM();
		SVM method = (SVM) file.getObject();

		dialog.getBeginningGamma().setValue(SVMSearchTrain.DEFAULT_GAMMA_BEGIN);
		dialog.getEndingGamma().setValue(SVMSearchTrain.DEFAULT_GAMMA_END);
		dialog.getStepGamma().setValue(SVMSearchTrain.DEFAULT_GAMMA_STEP);
		dialog.getBeginningC().setValue(SVMSearchTrain.DEFAULT_CONST_BEGIN);
		dialog.getEndingC().setValue(SVMSearchTrain.DEFAULT_CONST_END);
		dialog.getStepC().setValue(SVMSearchTrain.DEFAULT_CONST_STEP);

		if (dialog.process()) {
			double maxError = dialog.getMaxError().getValue() / 100.0;
			SVMSearchTrain train = new SVMSearchTrain(method, trainingData);
			train.setGammaBegin(dialog.getBeginningGamma().getValue());
			train.setGammaEnd(dialog.getEndingGamma().getValue());
			train.setGammaStep(dialog.getStepGamma().getValue());
			train.setConstBegin(dialog.getBeginningC().getValue());
			train.setConstEnd(dialog.getEndingC().getValue());
			train.setConstStep(dialog.getStepC().getValue());
			EngineConcurrency.getInstance().setThreadCount(
					dialog.getThreadCount().getValue());
			startup(file, train, maxError, validationData);
		}
	}

	private void performQPROP(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputQPROP dialog = new InputQPROP();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();

			int kFold = dialog.getKfold().getValue();

			if (kFold > 0) {
				trainingData = this.wrapTrainingData(trainingData);
			}

			MLTrain train = new QuickPropagation(
					(BasicNetwork) file.getObject(), trainingData, learningRate);

			if (kFold > 0) {
				train = this.wrapTrainer(trainingData, train, kFold);
			}

			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}

	}

	private void performSVD(ProjectEGFile file, MLDataSet trainingData) {

		if (!(file.getObject() instanceof RBFNetwork)) {
			throw new WorkBenchError("SVD training requires a RBF network.");
		}
		RBFNetwork network = (RBFNetwork) file.getObject();
		if (network.getOutputCount() != 1) {
			throw new WorkBenchError(
					"SVD training requires a single output neuron.");
		}
		SVDTraining train = new SVDTraining(network, trainingData);
		train.iteration();

		if (EncogWorkBench.askQuestion(
				"Finished Training",
				"SVD trained to an error of "
						+ Format.formatPercent(train.getError())
						+ "\nSave network?")) {
			file.save();
		}
	}

	private void performBayesian(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {

		InputBayesian d = new InputBayesian();

		if (d.process()) {
			BayesianInit theInit = null;
			BayesSearch theSearch = null;
			BayesEstimator theEstimator = null;

			if (((JComboBox) d.getInitOptions().getField()).getSelectedItem()
					.equals("Empty")) {
				theInit = BayesianInit.InitEmpty;
			} else if (((JComboBox) d.getInitOptions().getField())
					.getSelectedItem().equals("Naive Bayes")) {
				theInit = BayesianInit.InitNaiveBayes;
			} else if (((JComboBox) d.getInitOptions().getField())
					.getSelectedItem().equals("No Change")) {
				theInit = BayesianInit.InitNoChange;
			}

			if (((JComboBox) d.getSearchMethod().getField()).getSelectedItem()
					.equals("K2")) {
				theSearch = new SearchK2();
			} else if (((JComboBox) d.getSearchMethod().getField())
					.getSelectedItem().equals("None")) {
				theSearch = new SearchNone();
			}

			if (((JComboBox) d.getEstimateMethod().getField())
					.getSelectedItem().equals("Simple")) {
				theEstimator = new SimpleEstimator();
			} else if (((JComboBox) d.getEstimateMethod().getField())
					.getSelectedItem().equals("None")) {
				theEstimator = new EstimatorNone();
			}

			TrainBayesian train = new TrainBayesian(
					(BayesianNetwork) file.getObject(), trainingData, d
							.getMaxParents().getValue(), theInit, theSearch,
					theEstimator);

			startup(file, train, 0.0, validationData);
		}

	}

	private void performPNN(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {

		BasicPNN network = (BasicPNN) file.getObject();
		TrainBasicPNN train = new TrainBasicPNN(network, trainingData);
		train.iteration();
		EncogWorkBench.displayMessage("Training Complete", "Final error: "
				+ train.getError());

	}

	private void startup(ProjectEGFile file, MLTrain train, double maxError,
			MLDataSet validationData) {
		EncogWorkBench.getInstance().setupThreads(train);
		BasicTrainingProgress tab = new BasicTrainingProgress(train, file,
				train.getTraining(), validationData);
		if (this.parentTab != null) {
			tab.setParentTab(parentTab);
		}
		tab.setMaxError(maxError);
		EncogWorkBench.getInstance().getMainWindow().getTabManager()
				.openTab(tab);
	}

	private void performNelderMead(ProjectEGFile file, MLDataSet trainingData,
			MLDataSet validationData) {
		InputNelderMead dialog = new InputNelderMead();

		if (dialog.process()) {
			NelderMeadTraining train = new NelderMeadTraining(
					(BasicNetwork) file.getObject(), trainingData);
			startup(file, train, dialog.getMaxError().getValue() / 100.0,
					validationData);
		}
	}

	private void performPrgPopulationTrain(ProjectEGFile file,
			MLDataSet trainingData, MLDataSet validationData) {

		TrainEPLPopulationDialog dialog = new TrainEPLPopulationDialog();
		if (dialog.process()) {

			PrgPopulation pop = (PrgPopulation) file.getObject();
			TrainEA train = new TrainEA(pop, trainingData);

			if (dialog.getSimplify().getValue()) {
				train.getRules().addRewriteRule(new RewriteConstants());
				train.getRules().addRewriteRule(new RewriteAlgebraic());
			}

			train.addOperation(dialog.getCrossoverProbability().getValue(), new SubtreeCrossover());
			train.addOperation(dialog.getConstMutateProbability().getValue(), new ConstMutation(pop.getContext(),0.5,1.0));
			train.addOperation(dialog.getMutateProbability().getValue(), new SubtreeMutation(pop.getContext(), 4));
			train.setSpeciation(new PrgSpeciation());

			ComplexityAdjustedScore adj = new ComplexityAdjustedScore(dialog
					.getComplexityPenaltyThreshold().getValue(), dialog
					.getComplexityPentaltyFullThreshold().getValue(), dialog
					.getComplexityPenalty().getValue(), dialog
					.getComplexityFullPenalty().getValue());

			train.addScoreAdjuster(adj);

			startup(file, train, 0.0, validationData);
		}

	}

}
