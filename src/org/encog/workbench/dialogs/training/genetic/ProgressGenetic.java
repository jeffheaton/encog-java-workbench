/*
 * Encog(tm) Workbench v2.3
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.dialogs.training.genetic;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.genetic.NeuralGeneticAlgorithm;
import org.encog.util.randomize.RangeRandomizer;
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
		setTitle("Genetic Algorithm Training");
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
