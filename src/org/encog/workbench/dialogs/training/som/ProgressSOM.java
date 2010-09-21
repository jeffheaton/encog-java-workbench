/*
 * Encog(tm) Workbench v2.5
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
package org.encog.workbench.dialogs.training.som;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.competitive.CompetitiveTraining;
import org.encog.neural.networks.training.competitive.neighborhood.NeighborhoodSingle;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

/**
 * Display progress as a SOM is trained.
 * @author jheaton
 */
public class ProgressSOM extends BasicTrainingProgress {

	/**
	 * The serial id. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The learning rate.
	 */
	private final double learningRate;

	/**
	 * Construct the dialog box.
	 * @param owner The owner of this dialog box.
	 * @param network The network to be trained.
	 * @param trainingData The training data.
	 * @param learningRate The learning rate.
	 * @param method The training method.
	 * @param maxError The max allowed error.
	 */
	public ProgressSOM(final Frame owner, final BasicNetwork network,
			final NeuralDataSet trainingData, final double learningRate,
			final double maxError) {
		super(owner);
		
		setNetwork(network);
		setTrainingData(trainingData);
		this.learningRate = learningRate;
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
		CompetitiveTraining train = new CompetitiveTraining(
				getNetwork(),
				this.learningRate,
				getTrainingData(),
				new NeighborhoodSingle());

		setTrain(train);
	}

}
