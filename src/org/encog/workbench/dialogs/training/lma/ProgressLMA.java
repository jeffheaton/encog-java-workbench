package org.encog.workbench.dialogs.training.lma;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.lma.LevenbergMarquardtTraining;
import org.encog.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressLMA  extends BasicTrainingProgress {
	
	private boolean useBayesian;
	
	/**
	 * Construct the dialog box.
	 * @param owner The owner.
	 * @param network The network to train.
	 * @param initialUpdate The initial update.
	 * @param learningRate The max step value.
	 * @param maxError The maximum error.
	 */
	public ProgressLMA(final Frame owner,
			final BasicNetwork network, final NeuralDataSet trainingData,
			final double maxError, final boolean useBayesian) {
		super(owner);
		setTitle("Levenberg Marquardt Training");
		setNetwork(network);
		setTrainingData(trainingData);
		setMaxError(maxError);
		this.useBayesian = useBayesian;
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
		final LevenbergMarquardtTraining train = new LevenbergMarquardtTraining(getNetwork(),
				getTrainingData() );
		train.setUseBayesianRegularization(this.useBayesian);
		setTrain(train);
	}

}
