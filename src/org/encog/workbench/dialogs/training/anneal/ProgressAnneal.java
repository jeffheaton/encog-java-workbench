/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.training.anneal;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

/**
 * Dialog box to display the progress as the simulated annealing
 * training occurs.
 */
public class ProgressAnneal extends BasicTrainingProgress {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The starting temperature. 
	 */
	private final double startTemp;
	
	/**
	 * The ending temperature. 
	 */
	private final double endTemp;
	
	/**
	 * The cycles.
	 */
	private final int cycles;

	/**
	 * Construct the dialog box.
	 * @param owner The owner of this dialog box.
	 * @param network The network for this dialog box.
	 * @param trainingData The training data to be used.
	 * @param maxError The max allowed error.
	 * @param startTemp The starting temperature.
	 * @param endTemp The ending temperature.
	 * @param cycles The cycles.
	 */
	public ProgressAnneal(final Frame owner, final BasicNetwork network,
			final NeuralDataSet trainingData, final double maxError,
			final double startTemp, final double endTemp, final int cycles) {
		super(owner);

		setNetwork(network);
		setTrainingData(trainingData);
		this.cycles = cycles;
		this.startTemp = startTemp;
		this.endTemp = endTemp;
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
		CalculateScore score = new TrainingSetScore(getTrainingData());
		final Train train = new NeuralSimulatedAnnealing(getNetwork(),
				score, this.startTemp, this.endTemp, this.cycles);

		setTrain(train);
	}

}
