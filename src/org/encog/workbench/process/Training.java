package org.encog.workbench.process;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.training.anneal.InputAnneal;
import org.encog.workbench.dialogs.training.anneal.ProgressAnneal;
import org.encog.workbench.dialogs.training.backpropagation.InputBackpropagation;
import org.encog.workbench.dialogs.training.backpropagation.ProgressBackpropagation;

public class Training {
	public static void performBackpropagation() {
		InputBackpropagation dialog = new InputBackpropagation(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			String nameNetwork = dialog.getNetwork();
			String nameTraining = dialog.getTrainingSet();

			BasicNetwork network = (BasicNetwork) EncogWorkBench.getInstance()
					.getCurrentFile().find(nameNetwork);
			NeuralDataSet training = (NeuralDataSet) EncogWorkBench
					.getInstance().getCurrentFile().find(nameTraining);

			ProgressBackpropagation train = new ProgressBackpropagation(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getLearningRate(), dialog.getMaxError(),
					dialog.getMomentum());

			train.setVisible(true);
		}
	}

	public static void performAnneal() {
		InputAnneal dialog = new InputAnneal(EncogWorkBench.getInstance()
				.getMainWindow());
		if (dialog.process()) {
			String nameNetwork = dialog.getNetwork();
			String nameTraining = dialog.getTrainingSet();

			BasicNetwork network = (BasicNetwork) EncogWorkBench.getInstance()
					.getCurrentFile().find(nameNetwork);
			NeuralDataSet training = (NeuralDataSet) EncogWorkBench
					.getInstance().getCurrentFile().find(nameTraining);

			ProgressAnneal train = new ProgressAnneal(EncogWorkBench
					.getInstance().getMainWindow(), network, training, dialog
					.getLearningRate(), dialog.getMaxError(), dialog
					.getMomentum());

			train.setVisible(true);
		}
	}
}
