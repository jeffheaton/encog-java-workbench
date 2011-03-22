package org.encog.workbench.process;

import java.util.ArrayList;
import java.util.List;

import org.encog.mathutil.randomize.RangeRandomizer;
import org.encog.ml.MLMethod;
import org.encog.neural.art.ART1;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.networks.training.genetic.NeuralGeneticAlgorithm;
import org.encog.neural.networks.training.lma.LevenbergMarquardtTraining;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.manhattan.ManhattanPropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import org.encog.neural.networks.training.simple.TrainAdaline;
import org.encog.neural.som.SOM;
import org.encog.neural.som.training.basic.BasicTrainSOM;
import org.encog.neural.som.training.clustercopy.SOMClusterCopyTraining;
import org.encog.neural.thermal.HopfieldNetwork;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.dialogs.training.ChooseBasicNetworkTrainingMethod;
import org.encog.workbench.dialogs.training.TrainDialog;
import org.encog.workbench.dialogs.training.methods.InputADALINE;
import org.encog.workbench.dialogs.training.methods.InputAnneal;
import org.encog.workbench.dialogs.training.methods.InputBackpropagation;
import org.encog.workbench.dialogs.training.methods.InputGenetic;
import org.encog.workbench.dialogs.training.methods.InputLMA;
import org.encog.workbench.dialogs.training.methods.InputManhattan;
import org.encog.workbench.dialogs.training.methods.InputResilient;
import org.encog.workbench.dialogs.training.methods.InputSCG;
import org.encog.workbench.dialogs.training.methods.InputSOM;
import org.encog.workbench.tabs.training.BasicTrainingProgress;

public class TrainBasicNetwork {
	public static void performTrain(MLMethod mlMethod) {
				
		TrainDialog dialog = new TrainDialog(EncogWorkBench.getInstance()
				.getMainWindow());
		if (mlMethod != null)
			dialog.setMethod(mlMethod);

		if (dialog.process()) {
			MLMethod method = dialog.getNetwork();
			NeuralDataSet trainingData = dialog.getTrainingSet();

			if (method == null) {
				EncogWorkBench.displayError("Error",
						"Machine language method is required to train.");
				return;
			}
			
			if( method instanceof ART1 ) {
				EncogWorkBench.displayError("Error",
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
				for (NeuralDataPair pair : trainingData) {
					hp.addPattern(pair.getInput());
				}
				if (EncogWorkBench.askQuestion("Hopfield",
						"Training done, save?")) {
					EncogWorkBench.getInstance()
							.save((EncogPersistedObject) hp);
				}
			} else if (method instanceof SOM) {
				performSOM((SOM) method, trainingData);
			} else if (method instanceof BasicNetwork) {

				ChooseBasicNetworkTrainingMethod choose = new ChooseBasicNetworkTrainingMethod(
						EncogWorkBench.getInstance().getMainWindow(),
						(BasicNetwork) method);
				if (choose.process()) {

					switch (choose.getType()) {
					case SCG:
						performSCG((BasicNetwork) method, trainingData);
						break;
					case PropagationResilient:
						performRPROP((BasicNetwork) method, trainingData);
						break;
					case PropagationBack:
						performBPROP((BasicNetwork) method, trainingData);
						break;
					case PropagationManhattan:
						performManhattan((BasicNetwork) method, trainingData);
						break;
					case LevenbergMarquardt:
						performLMA((BasicNetwork) method, trainingData);
						break;
					case Genetic:
						performGenetic((BasicNetwork) method, trainingData);
						break;
					case Annealing:
						performAnnealing((BasicNetwork) method, trainingData);
						break;
					case ADALINE:
						performADALINE((BasicNetwork) method, trainingData);
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

	private static void performSOM(SOM som, NeuralDataSet trainingData) {

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
				BasicTrainSOM train = new BasicTrainSOM(som, somDialog
						.getLearningRate().getValue(), trainingData,
						somDialog.getNeighborhoodFunction());
				train.setForceWinner(somDialog.getForceWinner().getValue());
				startup(train, somDialog.getMaxError().getValue());
			}
		} else if (sel.getSelected() == selectSOMClusterCopy) {
			SOMClusterCopyTraining train = new SOMClusterCopyTraining(som,
					trainingData);
			train.iteration();
			if (EncogWorkBench.askQuestion("SOM", "Training done, save?")) {
				EncogWorkBench.getInstance().save((EncogPersistedObject) som);
			} else {
				EncogWorkBench.getInstance().revert((EncogPersistedObject) som);
			}
		}

	}

	private static void performADALINE(BasicNetwork method,
			NeuralDataSet trainingData) {
		InputADALINE dialog = new InputADALINE();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();

			Train train = new TrainAdaline((BasicNetwork) method, trainingData,
					learningRate);
			startup(train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private static void performBPROP(BasicNetwork method,
			NeuralDataSet trainingData) {
		InputBackpropagation dialog = new InputBackpropagation();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();
			double momentum = dialog.getMomentum().getValue();

			Train train = new Backpropagation((BasicNetwork) method,
					trainingData, learningRate, momentum);
			startup(train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private static void performAnnealing(BasicNetwork method,
			NeuralDataSet trainingData) {
		InputAnneal dialog = new InputAnneal();
		if (dialog.process()) {
			final double startTemp = dialog.getStartTemp().getValue();
			final double stopTemp = dialog.getStartTemp().getValue();
			final int cycles = dialog.getCycles().getValue();

			CalculateScore score = new TrainingSetScore(trainingData);
			final Train train = new NeuralSimulatedAnnealing(method, score,
					startTemp, stopTemp, cycles);
			startup(train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private static void performGenetic(BasicNetwork method,
			NeuralDataSet trainingData) {
		InputGenetic dialog = new InputGenetic();
		if (dialog.process()) {
			final int populationSize = dialog.getPopulationSize().getValue();
			final double mutationPercent = dialog.getMutationPercent()
					.getValue();
			final double percentToMate = dialog.getPercentToMate().getValue();

			CalculateScore score = new TrainingSetScore(trainingData);
			final Train train = new NeuralGeneticAlgorithm(method,
					new RangeRandomizer(-1, 1), score, populationSize,
					mutationPercent, percentToMate);
			startup(train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private static void performLMA(BasicNetwork method,
			NeuralDataSet trainingData) {
		InputLMA dialog = new InputLMA();

		if (dialog.process()) {
			boolean useBayse = dialog.getUseBayesian().getValue();
			LevenbergMarquardtTraining train = new LevenbergMarquardtTraining(
					(BasicNetwork) method, trainingData);
			train.setUseBayesianRegularization(useBayse);
			startup(train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private static void performManhattan(BasicNetwork method,
			NeuralDataSet trainingData) {
		InputManhattan dialog = new InputManhattan();
		if (dialog.process()) {
			double learningRate = dialog.getFixedDelta().getValue();

			Train train = new ManhattanPropagation((BasicNetwork) method,
					trainingData, learningRate);
			startup(train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private static void performRPROP(BasicNetwork method,
			NeuralDataSet trainingData) {
		InputResilient dialog = new InputResilient();
		if (dialog.process()) {
			final double initialUpdate = dialog.getInitialUpdate().getValue();
			final double maxStep = dialog.getMaxStep().getValue();
			Train train = new ResilientPropagation((BasicNetwork) method,
					trainingData, null, initialUpdate, maxStep);
			startup(train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private static void performSCG(BasicNetwork method,
			NeuralDataSet trainingData) {
		InputSCG dialog = new InputSCG();
		if (dialog.process()) {
			Train train = new ScaledConjugateGradient((BasicNetwork) method,
					trainingData);
			startup(train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private static void startup(Train train, double maxError) {
		BasicTrainingProgress tab = new BasicTrainingProgress(train,
				(EncogPersistedObject)train.getNetwork(), train.getTraining());
		tab.setMaxError(maxError);
		EncogWorkBench.getInstance().getMainWindow().openTab(tab, "Training");

	}

}
