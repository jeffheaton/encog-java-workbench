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
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.training.BasicTrainingProgress;

public class TrainBasicNetwork {
	
	private ProjectEGFile mlMethod; 
	private EncogCommonTab parentTab;
	
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

				}
			} else if (method instanceof SOM) {
				ProjectEGFile file = (ProjectEGFile)dialog.getComboNetwork().getSelectedValue();
				performSOM(file, trainingData);
			} else if (method instanceof BasicNetwork) {

				ChooseBasicNetworkTrainingMethod choose = new ChooseBasicNetworkTrainingMethod(
						EncogWorkBench.getInstance().getMainWindow(),
						(BasicNetwork) method);
				if (choose.process()) {
					ProjectEGFile file = (ProjectEGFile)dialog.getComboNetwork().getSelectedValue();
					
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

	private void performSOM(ProjectEGFile file,
			NeuralDataSet trainingData) {

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
				BasicTrainSOM train = new BasicTrainSOM((SOM)file.getObject(), somDialog
						.getLearningRate().getValue(), trainingData,
						somDialog.getNeighborhoodFunction());
				train.setForceWinner(somDialog.getForceWinner().getValue());
				startup(file,train, somDialog.getMaxError().getValue());
			}
		} else if (sel.getSelected() == selectSOMClusterCopy) {
			SOMClusterCopyTraining train = new SOMClusterCopyTraining((SOM)file.getObject(),
					trainingData);
			train.iteration();
			if (EncogWorkBench.askQuestion("SOM", "Training done, save?")) {
				//EncogWorkBench.getInstance().save((EncogPersistedObject) som);
			} else {
				//EncogWorkBench.getInstance().revert((EncogPersistedObject) som);
			}
		}

	}

	private void performADALINE(ProjectEGFile file,
			NeuralDataSet trainingData) {
		InputADALINE dialog = new InputADALINE();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();

			Train train = new TrainAdaline((BasicNetwork) file.getObject(), trainingData,
					learningRate);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private void performBPROP(ProjectEGFile file,
			NeuralDataSet trainingData) {
		InputBackpropagation dialog = new InputBackpropagation();
		if (dialog.process()) {
			double learningRate = dialog.getLearningRate().getValue();
			double momentum = dialog.getMomentum().getValue();

			Train train = new Backpropagation((BasicNetwork) file.getObject(),
					trainingData, learningRate, momentum);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private void performAnnealing(ProjectEGFile file,
			NeuralDataSet trainingData) {
		InputAnneal dialog = new InputAnneal();
		if (dialog.process()) {
			final double startTemp = dialog.getStartTemp().getValue();
			final double stopTemp = dialog.getStartTemp().getValue();
			final int cycles = dialog.getCycles().getValue();

			CalculateScore score = new TrainingSetScore(trainingData);
			final Train train = new NeuralSimulatedAnnealing((BasicNetwork)file.getObject(), score,
					startTemp, stopTemp, cycles);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private void performGenetic(ProjectEGFile file,
			NeuralDataSet trainingData) {
		InputGenetic dialog = new InputGenetic();
		if (dialog.process()) {
			final int populationSize = dialog.getPopulationSize().getValue();
			final double mutationPercent = dialog.getMutationPercent()
					.getValue();
			final double percentToMate = dialog.getPercentToMate().getValue();

			CalculateScore score = new TrainingSetScore(trainingData);
			final Train train = new NeuralGeneticAlgorithm((BasicNetwork)file.getObject(),
					new RangeRandomizer(-1, 1), score, populationSize,
					mutationPercent, percentToMate);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}

	}

	private void performLMA(ProjectEGFile file,
			NeuralDataSet trainingData) {
		InputLMA dialog = new InputLMA();

		if (dialog.process()) {
			boolean useBayse = dialog.getUseBayesian().getValue();
			LevenbergMarquardtTraining train = new LevenbergMarquardtTraining(
					(BasicNetwork) file.getObject(), trainingData);
			train.setUseBayesianRegularization(useBayse);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private void performManhattan(ProjectEGFile file,
			NeuralDataSet trainingData) {
		InputManhattan dialog = new InputManhattan();
		if (dialog.process()) {
			double learningRate = dialog.getFixedDelta().getValue();

			Train train = new ManhattanPropagation((BasicNetwork) file.getObject(),
					trainingData, learningRate);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private void performRPROP(ProjectEGFile file,
			NeuralDataSet trainingData) {
		InputResilient dialog = new InputResilient();
		if (dialog.process()) {
			final double initialUpdate = dialog.getInitialUpdate().getValue();
			final double maxStep = dialog.getMaxStep().getValue();
			Train train = new ResilientPropagation((BasicNetwork) file.getObject(),
					trainingData, null, initialUpdate, maxStep);
			startup(file, train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private void performSCG(ProjectEGFile file,
			NeuralDataSet trainingData) {
		InputSCG dialog = new InputSCG();
		if (dialog.process()) {
			Train train = new ScaledConjugateGradient((BasicNetwork) file.getObject(),
					trainingData);
			startup(file ,train, dialog.getMaxError().getValue() / 100.0);
		}
	}

	private void startup(ProjectEGFile file, Train train, double maxError) {
		BasicTrainingProgress tab = new BasicTrainingProgress(train,
				file, train.getTraining());
		if( this.parentTab!=null ) {
			tab.setParentTab(tab);
		}
		tab.setMaxError(maxError);
		EncogWorkBench.getInstance().getMainWindow().openTab(tab);

	}

}
