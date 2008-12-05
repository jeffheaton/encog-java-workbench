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
package org.encog.workbench.process.validate;

import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.HopfieldLayer;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.workbench.EncogWorkBench;

public class ValidateTraining {

	BasicNetwork network;
	BasicNeuralDataSet training;

	public ValidateTraining(final BasicNetwork network,
			final BasicNeuralDataSet training) {
		this.network = network;
		this.training = training;
	}

	@SuppressWarnings("unchecked")
	public boolean validateContainsLayer(final Class layerType) {
		for (final Layer layer : this.network.getLayers()) {
			if (layer.getClass().getName().equals(layerType.getName())) {
				return true;
			}
		}

		EncogWorkBench.displayError("Training Error",
				"This sort of training requires that at least one layer be of type:\n"
						+ layerType.getSimpleName());
		return false;
	}

	public boolean validateFeedForward() {
		if (!validateIsSupervised()) {
			return false;
		}

		if (!validateContainsLayer(FeedforwardLayer.class)) {
			return false;
		}

		if (!validateInputSize()) {
			return false;
		}

		if (!validateOutputSize()) {
			return false;
		}

		return true;
	}

	public boolean validateHopfield() {
		if (!validateIsUnsupervised()) {
			return false;
		}

		if (!validateContainsLayer(HopfieldLayer.class)) {
			return false;
		}

		if (!validateIsSupervised()) {
			return false;
		}

		if (!validateInputSize()) {
			return false;
		}

		return true;
	}

	public boolean validateInputSize() {
		final int inputNeurons = this.network.getInputLayer().getNeuronCount();
		final int trainingInputs = this.training.getInputSize();

		if (inputNeurons != trainingInputs) {

			EncogWorkBench.displayError("Training Error",
					"Training input size must match the number of input neurons.\n Input neurons:"
							+ inputNeurons + "\nTraining Input Size: "
							+ trainingInputs);
			return false;
		}
		return true;
	}

	public boolean validateIsSupervised() {
		if (!this.training.isSupervised()) {
			EncogWorkBench
					.displayError(
							"Training Error",
							"This sort of training requires a suprvised training set,\n which means that it must have ideal data provided.");
			return false;
		}

		return true;
	}

	public boolean validateIsUnsupervised() {
		if (!this.training.isSupervised()) {
			EncogWorkBench
					.displayError(
							"Training Error",
							"This sort of training requires an unsuprvised training set,\n which means that it must not have ideal data provided.");
			return false;
		}

		return true;
	}

	public boolean validateOutputSize() {
		final int outputNeurons = this.network.getOutputLayer()
				.getNeuronCount();
		final int trainingOutputs = this.training.getIdealSize();

		if (outputNeurons != trainingOutputs) {

			EncogWorkBench
					.displayError(
							"Training Error",
							"Training ideal size must match the number of output neurons.\n Output neurons:"
									+ outputNeurons
									+ "\nTraining Ideal Size: "
									+ trainingOutputs);
			return false;
		}
		return true;
	}

	public boolean validateSOM() {

		if (!validateIsSupervised()) {
			return false;
		}

		if (!validateContainsLayer(SOMLayer.class)) {
			return false;
		}

		if (!validateInputSize()) {
			return false;
		}

		return true;
	}
}
