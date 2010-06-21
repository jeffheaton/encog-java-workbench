package org.encog.workbench.dialogs.training.svm;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.competitive.CompetitiveTraining;
import org.encog.neural.networks.training.competitive.neighborhood.NeighborhoodSingle;
import org.encog.neural.networks.training.svm.SVMTrain;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressSVM extends BasicTrainingProgress {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;

	private final double beginGamma;
	private final double endGamma;
	private final double stepGamma;
	private final double beginConst;
	private final double endConst;
	private final double stepConst;

	/**
	 * Construct the dialog box.
	 * 
	 * @param owner
	 *            The owner of this dialog box.
	 * @param network
	 *            The network to be trained.
	 * @param trainingData
	 *            The training data.
	 * @param learningRate
	 *            The learning rate.
	 * @param method
	 *            The training method.
	 * @param maxError
	 *            The max allowed error.
	 */
	public ProgressSVM(final Frame owner, final BasicNetwork network,
			final NeuralDataSet trainingData, double beginGamma,
			double endGamma, double stepGamma, double beginConst,
			double endConst, double stepConst, final double maxError) {
		super(owner);

		setNetwork(network);
		setTrainingData(trainingData);
		this.beginGamma = beginGamma;
		this.endGamma = endGamma;
		this.stepGamma = stepGamma;
		this.beginConst = beginConst;
		this.endConst = endConst;
		this.stepConst = stepConst;
		setMaxError(maxError);
	}

	/**
	 * Perform a training iteration.
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
		SVMTrain train = new SVMTrain(getNetwork(),
				getTrainingData());
		train.setConstBegin(this.beginConst);
		train.setConstEnd(this.endConst);
		train.setConstStep(this.stepConst);
		
		train.setGammaBegin(this.beginGamma);
		train.setGammaEnd(this.endGamma);
		train.setGammaStep(this.stepGamma);
		
		setTrain(train);
	}

}
