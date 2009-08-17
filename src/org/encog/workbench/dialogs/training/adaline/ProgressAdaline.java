package org.encog.workbench.dialogs.training.adaline;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.simple.TrainAdaline;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

/**
 * Dialog box to display the progress of adaline training.
 */
public class ProgressAdaline extends BasicTrainingProgress {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The learning rate.
	 */
	private final double learningRate;
	

	/**
	 * Construct the dialog box.
	 * @param owner The owner.
	 * @param network The network to train.
	 * @param trainingData The learning data to use for training.
	 * @param learningRate The learning rate.
	 * @param maxError The maximum error.
	 */
	public ProgressAdaline(final Frame owner,
			final BasicNetwork network, final NeuralDataSet trainingData,
			final double learningRate, final double maxError) {
		super(owner);
		setTitle("Backpropagation Training");
		setNetwork(network);
		setTrainingData(trainingData);
		this.learningRate = learningRate;
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
		final Train train = new TrainAdaline(getNetwork(),
				getTrainingData(), this.learningRate );

		setTrain(train);
	}

}
