package org.encog.workbench.dialogs.training.neat;

import java.awt.Frame;

import org.encog.neural.activation.ActivationStep;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.neat.NEATTraining;
import org.encog.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import org.encog.solve.genetic.population.Population;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressNEAT extends BasicTrainingProgress {

	private Population population;
	
	
	/**
	 * Construct the dialog box.
	 * @param owner The owner.
	 * @param network The network to train.
	 * @param initialUpdate The initial update.
	 * @param learningRate The max step value.
	 * @param maxError The maximum error.
	 */
	public ProgressNEAT(
			final Frame owner,
			final NeuralDataSet trainingData,
			final Population population,
			final BasicNetwork model,
			final double maxError) {
		super(owner);
		setTitle("NeuroEvolution of Augmenting Topologies (NEAT)");
		setNetwork(model);
		setTrainingData(trainingData);
		setMaxError(maxError);
		this.population = population;
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
		
		CalculateScore score = new TrainingSetScore(getTrainingData());
		// train the neural network
		ActivationStep step = new ActivationStep();
		step.setCenter(0.5);
		
		final NEATTraining train = new NEATTraining(
				score, this.getNetwork(),this.population);
		
		train.setOutputActivationFunction(step);

		setTrain(train);
	}

}
