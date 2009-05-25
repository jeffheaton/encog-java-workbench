/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
		setTitle("SOM Training");
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
				0.7,
				getTrainingData(),
				new NeighborhoodSingle());

		setTrain(train);
	}

}
