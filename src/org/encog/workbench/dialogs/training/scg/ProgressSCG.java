package org.encog.workbench.dialogs.training.scg;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressSCG extends BasicTrainingProgress {

	/**
	 * Construct the dialog box.
	 * @param owner The owner.
	 * @param network The network to train.
	 * @param initialUpdate The initial update.
	 * @param learningRate The max step value.
	 * @param maxError The maximum error.
	 */
	public ProgressSCG(final Frame owner,
			final BasicNetwork network, final NeuralDataSet trainingData,
			final double maxError) {
		super(owner);
		setTitle("Train Levenberg-Marquardt(SCG)");
		setNetwork(network);
		setTrainingData(trainingData);
		setMaxError(maxError);

	}

	/**
	 * Perform one training iteration.
	 */
	@Override
	public void iteration() {

		getTrain().iteration();

	}

	/**
	 * Not used.
	 */
	@Override
	public void shutdown() {
	}

	/**
	 * Construct the training objects.
	 */
	@Override
	public void startup() {
		final Train train = new ScaledConjugateGradient(getNetwork(),
				getTrainingData() );

		setTrain(train);
	}

	
}
