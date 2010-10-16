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
package org.encog.workbench.dialogs.training.backpropagation;

import java.awt.Frame;

import org.encog.engine.network.train.prop.OpenCLTrainingProfile;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.util.simple.EncogUtility;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

/**
 * Dialog box to display the progress of backpropagation training.
 */
public class ProgressBackpropagation extends BasicTrainingProgress {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The learning rate.
	 */
	private final double learningRate;
	
	/**
	 * The momentum.
	 */
	private final double momentum;

	/**
	 * Construct the dialog box.
	 * @param owner The owner.
	 * @param network The network to train.
	 * @param trainingData The learning data to use for training.
	 * @param learningRate The learning rate.
	 * @param momentum The momentum.
	 * @param maxError The maximum error.
	 */
	public ProgressBackpropagation(final Frame owner,
			final BasicNetwork network, final NeuralDataSet trainingData,
			final double learningRate, final double momentum,
			final double maxError) {
		super(owner);

		setNetwork(network);
		setTrainingData(trainingData);
		this.learningRate = learningRate;
		this.momentum = momentum;
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
		
		OpenCLTrainingProfile profile = null;
		if( this.getDevice()!=null )
			profile = new OpenCLTrainingProfile(this.getDevice());
		
		final Backpropagation train = new Backpropagation(getNetwork(),
				getTrainingData(), profile,this.learningRate, this.momentum);
		train.setNumThreads(EncogWorkBench.getInstance().getConfig().getThreadCount());

		setTrain(train);
	}

}
