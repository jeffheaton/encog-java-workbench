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
package org.encog.workbench.dialogs.training.genetic;

import java.awt.Frame;

import org.encog.mathutil.randomize.RangeRandomizer;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.genetic.NeuralGeneticAlgorithm;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

/**
 * Display a dialog box to show the progress of the genetic training.
 * @author jheaton
 */
public class ProgressGenetic extends BasicTrainingProgress {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The population size.
	 */
	private final int populationSize;
	
	/**
	 * The mutation percent.
	 */
	private final double mutationPercent;
	
	/**
	 * The percent to mate.
	 */
	private final double percentToMate;

	/**
	 * Construct the dialog box.
	 * @param owner The owner of this dialog box.
	 * @param network The network to train.
	 * @param trainingData The training data to use.
	 * @param maxError The maximum error.
	 * @param populationSize The population size.
	 * @param mutationPercent The mutation percent.
	 * @param percentToMate The percent to mate.
	 */
	public ProgressGenetic(final Frame owner, final BasicNetwork network,
			final NeuralDataSet trainingData, final double maxError,
			final int populationSize, final double mutationPercent,
			final double percentToMate) {
		super(owner);

		setNetwork(network);
		setTrainingData(trainingData);
		this.populationSize = populationSize;
		this.mutationPercent = mutationPercent;
		this.percentToMate = percentToMate;
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
		final Train train = new NeuralGeneticAlgorithm(getNetwork(),
				new RangeRandomizer(-1,1), score, this.populationSize,
				this.mutationPercent, this.percentToMate);

		setTrain(train);
	}

}
