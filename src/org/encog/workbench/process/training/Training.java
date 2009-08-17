/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
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

package org.encog.workbench.process.training;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.logic.SOMLogic;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.training.ChooseTrainingMethodDialog;
import org.encog.workbench.dialogs.training.adaline.InputAdaline;
import org.encog.workbench.dialogs.training.adaline.ProgressAdaline;
import org.encog.workbench.dialogs.training.anneal.InputAnneal;
import org.encog.workbench.dialogs.training.anneal.ProgressAnneal;
import org.encog.workbench.dialogs.training.backpropagation.InputBackpropagation;
import org.encog.workbench.dialogs.training.backpropagation.ProgressBackpropagation;
import org.encog.workbench.dialogs.training.genetic.InputGenetic;
import org.encog.workbench.dialogs.training.genetic.ProgressGenetic;
import org.encog.workbench.dialogs.training.hopfield.InputHopfield;
import org.encog.workbench.dialogs.training.instar.InputInstar;
import org.encog.workbench.dialogs.training.instar.ProgressInstar;
import org.encog.workbench.dialogs.training.manhattan.InputManhattan;
import org.encog.workbench.dialogs.training.manhattan.ProgressManhattan;
import org.encog.workbench.dialogs.training.outstar.InputOutstar;
import org.encog.workbench.dialogs.training.outstar.ProgressOutstar;
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

			if (!validate.validateIsSupervised() ) {
				return;
			}		
			
			if( !validate.validateFeedforwardOrSRN() ) {
				return;
			}

			final ProgressAnneal train = new ProgressAnneal(EncogWorkBench
					.getInstance().getMainWindow(), network, training, dialog
					.getMaxError().getValue(),
					dialog.getStartTemp().getValue(), dialog.getEndTemp()
							.getValue(), dialog.getCycles().getValue());

			train.setVisible(true);
		}
	}

	public static void performBackpropagation() {
		final InputBackpropagation dialog = new InputBackpropagation(
				EncogWorkBench.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateIsSupervised()) {
				return;
			}
			
			if( !validate.validateFeedforwardOrSRN() ) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressBackpropagation train = new ProgressBackpropagation(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getLearningRate().getValue(), dialog
							.getMomentum().getValue(), dialog.getMaxError()
							.getValue());

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

			if (!validate.validateIsSupervised()) {
				return;
			}
			
			if( !validate.validateFeedforwardOrSRN() ) {
				return;
			}

			final ProgressGenetic train = new ProgressGenetic(EncogWorkBench
					.getInstance().getMainWindow(), network, training, dialog
					.getMaxError().getValue(), dialog.getPopulationSize()
					.getValue(), dialog.getMutationPercent().getValue(), dialog
					.getPercentToMate().getValue());

			train.setVisible(true);
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

			if (!validate.validateIsUnsupervised()) {
				return;
			}
			
			if( !validate.validateLogicType(SOMLogic.class)) {
				return;
			}

			final ProgressSOM train = new ProgressSOM(EncogWorkBench
					.getInstance().getMainWindow(), network, training, dialog
					.getMaxError().getValue(), dialog.getLearningRate()
					.getValue());

			train.setVisible(true);
		}

	}

	public static void performResilient() {
		final InputResilient dialog = new InputResilient(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateIsSupervised()) {
				return;
			}
			
			if( !validate.validateFeedforwardOrSRN() ) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressResilient train = new ProgressResilient(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getInitialUpdate().getValue(), dialog
							.getMaxStep().getValue(), dialog.getMaxError()
							.getValue());

			train.setVisible(true);
		}

	}

	public static void performManhattan() {
		final InputManhattan dialog = new InputManhattan(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateIsSupervised()) {
				return;
			}
			
			if( !validate.validateFeedforwardOrSRN() ) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressManhattan train = new ProgressManhattan(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getFixedDelta().getValue(), dialog
							.getMaxError().getValue());

			train.setVisible(true);
		}

	}
	
	public void performADALINE()
	{
		final InputAdaline dialog = new InputAdaline(
				EncogWorkBench.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateIsSupervised()) {
				return;
			}
			
			if( !validate.validateFeedforwardOrSRN() ) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressAdaline train = new ProgressAdaline(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getLearningRate().getValue(), dialog.getMaxError()
							.getValue());

			train.setVisible(true);
		}
	}
	
	public void performInstar()
	{
		final InputInstar dialog = new InputInstar(
				EncogWorkBench.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());
			
			if( !validate.validateFeedforwardOrSRN() ) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressInstar train = new ProgressInstar(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getLearningRate().getValue(), dialog.getMaxError()
							.getValue());

			train.setVisible(true);
		}
	}
	
	public void performOutstar()
	{
		final InputOutstar dialog = new InputOutstar(
				EncogWorkBench.getInstance().getMainWindow());
		if (dialog.process()) {
			final ValidateTraining validate = new ValidateTraining(dialog
					.getNetwork(), (BasicNeuralDataSet) dialog.getTrainingSet());

			if (!validate.validateIsSupervised() ) {
				return;
			}
			
			if( !validate.validateFeedforwardOrSRN() ) {
				return;
			}

			final BasicNetwork network = dialog.getNetwork();
			final NeuralDataSet training = dialog.getTrainingSet();

			final ProgressOutstar train = new ProgressOutstar(
					EncogWorkBench.getInstance().getMainWindow(), network,
					training, dialog.getLearningRate().getValue(), dialog.getMaxError()
							.getValue());

			train.setVisible(true);
		}
	}

	public void perform(Frame owner, BasicNetwork network) {
		ChooseTrainingMethodDialog dialog = new ChooseTrainingMethodDialog(
				owner, network);
		
		try
		{
			if (dialog.process()) {
				switch (dialog.getType()) {
				case PropagationResilient:
					performResilient();
					break;
				case PropagationBack:
					performBackpropagation();
					break;
				case PropagationManhattan:
					performManhattan();
					break;
				case Genetic:
					performGenetic();
					break;
				case Annealing:
					performAnneal();
					break;
				case SOM:
					performSOM();
					break;
				case ADALINE:
					performADALINE();
					break;
					
				}
			}
		}
		catch(Throwable t)
		{
			EncogWorkBench.displayError("Error While Training", t);
		}
	}
}
