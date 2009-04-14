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
package org.encog.workbench.process;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.hopfield.TrainHopfield;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.training.anneal.InputAnneal;
import org.encog.workbench.dialogs.training.anneal.ProgressAnneal;
import org.encog.workbench.dialogs.training.backpropagation.InputBackpropagation;
import org.encog.workbench.dialogs.training.backpropagation.ProgressBackpropagation;
import org.encog.workbench.dialogs.training.genetic.InputGenetic;
import org.encog.workbench.dialogs.training.genetic.ProgressGenetic;
import org.encog.workbench.dialogs.training.hopfield.InputHopfield;
import org.encog.workbench.dialogs.training.manhattan.InputManhattan;
import org.encog.workbench.dialogs.training.manhattan.ProgressManhattan;
import org.encog.workbench.dialogs.training.resilient.InputResilient;
import org.encog.workbench.dialogs.training.resilient.ProgressResilient;
import org.encog.workbench.dialogs.training.som.InputSOM;
import org.encog.workbench.dialogs.training.som.ProgressSOM;
import org.encog.workbench.process.validate.ValidateTraining;

public class Training {
	public static void performAnneal() {
		final InputAnneal dialog = new InputAnneal(EncogWorkBench.getInstance()
				.getMainWindow());
		if (dialog.process()) {
			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateFeedForward()) {
				return;
			}

			final ProgressAnneal train = new ProgressAnneal(EncogWorkBench
					.getInstance().getMainWindow(), network, training, dialog
					.getMaxError(), dialog.getStartTemp(), dialog.getEndTemp(),
					dialog.getCycles());

			train.setVisible(true);
		}
	}

	public static void performBackpropagation() {
		final InputBackpropagation dialog = new InputBackpropagation(
				EncogWorkBench.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateFeedForward()) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressBackpropagation train = new ProgressBackpropagation(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getLearningRate(), dialog.getMomentum(),
					dialog.getMaxError());

			train.setVisible(true);
		}
	}

	public static void performGenetic() {
		final InputGenetic dialog = new InputGenetic(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateFeedForward()) {
				return;
			}

			final ProgressGenetic train = new ProgressGenetic(EncogWorkBench
					.getInstance().getMainWindow(), network, training, dialog
					.getMaxError(), dialog.getPopulationSize(), dialog
					.getMutationPercent(), dialog.getPercentToMate());

			train.setVisible(true);
		}
	}

	public static void performHopfield() {
		final InputHopfield dialog = new InputHopfield(EncogWorkBench
				.getInstance().getMainWindow());

		if (dialog.process()) {
			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateHopfield()) {
				return;
			}

			final TrainHopfield train = new TrainHopfield(training, network);
			train.iteration();
			EncogWorkBench
					.displayMessage("Train Hopfield", "Training Complete");
		}

	}

	public static void performSOM() {
		final InputSOM dialog = new InputSOM(EncogWorkBench.getInstance()
				.getMainWindow());

		if (dialog.process()) {
			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateSOM()) {
				return;
			}

			final ProgressSOM train = new ProgressSOM(EncogWorkBench
					.getInstance().getMainWindow(), network, training, dialog
					.getMaxError(),  dialog
					.getLearningRate());

			train.setVisible(true);
		}

	}

	public static void performResilient() {
		final InputResilient dialog = new InputResilient(
				EncogWorkBench.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateFeedForward()) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressResilient train = new ProgressResilient(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getInitialUpdate(), dialog.getMaxStep(),
					dialog.getMaxError());

			train.setVisible(true);
		}
		
	}

	public static void performManhattan() {
		final InputManhattan dialog = new InputManhattan(
				EncogWorkBench.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateFeedForward()) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressManhattan train = new ProgressManhattan(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getFixedDelta(), dialog.getMaxError());

			train.setVisible(true);
		}
		
	}
}
