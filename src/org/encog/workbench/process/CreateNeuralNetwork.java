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

package org.encog.workbench.process;

import org.encog.neural.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.ADALINEPattern;
import org.encog.neural.pattern.ART1Pattern;
import org.encog.neural.pattern.BAMPattern;
import org.encog.neural.pattern.BoltzmannPattern;
import org.encog.neural.pattern.CPNPattern;
import org.encog.neural.pattern.ElmanPattern;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.neural.pattern.HopfieldPattern;
import org.encog.neural.pattern.JordanPattern;
import org.encog.neural.pattern.RSOMPattern;
import org.encog.neural.pattern.RadialBasisPattern;
import org.encog.neural.pattern.SOMPattern;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.createnetwork.CreateADALINEDialog;
import org.encog.workbench.dialogs.createnetwork.CreateBAMDialog;
import org.encog.workbench.dialogs.createnetwork.CreateBlotzmannDialog;
import org.encog.workbench.dialogs.createnetwork.CreateCPNDialog;
import org.encog.workbench.dialogs.createnetwork.CreateElmanDialog;
import org.encog.workbench.dialogs.createnetwork.CreateFeedforward;
import org.encog.workbench.dialogs.createnetwork.CreateHopfieldDialog;
import org.encog.workbench.dialogs.createnetwork.CreateJordanDialog;
import org.encog.workbench.dialogs.createnetwork.CreateNeuralNetworkDialog;
import org.encog.workbench.dialogs.createnetwork.CreateRBFDialog;
import org.encog.workbench.dialogs.createnetwork.CreateRSOMDialog;
import org.encog.workbench.dialogs.createnetwork.CreateSOMDialog;
import org.encog.workbench.dialogs.createnetwork.NeuralNetworkType;

public class CreateNeuralNetwork {

	public static void process(String name) {
		BasicNetwork network = null;
		CreateNeuralNetworkDialog dialog = new CreateNeuralNetworkDialog(
				EncogWorkBench.getInstance().getMainWindow());
		dialog.setType(NeuralNetworkType.Empty);
		if (dialog.process()) {
			switch (dialog.getType()) {
			case Empty:
				network = createEmpty(name);
				break;
			case Feedforward:
				network = createFeedForward(name);
				break;
			case SOM:
				network = createSOM(name);
				break;
			case RSOM:
				network = createRSOM(name);
				break;
			case Hopfield:
				network = createHopfield(name);
				break;
			case Elman:
				network = createElman(name);
				break;
			case Jordan:
				network = createJordan(name);
				break;
			case RBF:
				network = createRBF(name);
				break;
			case BAM:
				network = createBAM(name);
				break;
			case CPN:
				network = createCPN(name);
				break;
			case Boltzmann:
				network = createBoltzmann(name);
				break;
			case ADALINE:
				network = createADALINE(name);
				break;
			case ART1:
				network = createART1(name);
				break;				
			default:
				network = createEmpty(name);
				break;
			}

			if (network != null) {
				EncogWorkBench.getInstance().getCurrentFile()
						.add(name, network);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	private static BasicNetwork createRSOM(String name) {
		CreateRSOMDialog dialog = new CreateRSOMDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			RSOMPattern rsom = new RSOMPattern();
			rsom.setInputNeurons(dialog.getInputCount().getValue());
			rsom.setOutputNeurons(dialog.getOutputCount().getValue());
			return rsom.generate();
		} else
			return null;
	}

	private static BasicNetwork createRBF(String name) {
		CreateRBFDialog dialog = new CreateRBFDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			RadialBasisPattern rbf = new RadialBasisPattern();
			rbf.setInputNeurons(dialog.getInputCount().getValue());
			rbf.addHiddenLayer(dialog.getHiddenCount().getValue());
			rbf.setOutputNeurons(dialog.getOutputCount().getValue());
			return rbf.generate();
		} else
			return null;

	}

	private static BasicNetwork createJordan(String name) {
		CreateJordanDialog dialog = new CreateJordanDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			JordanPattern jordan = new JordanPattern();
			jordan.setInputNeurons(dialog.getInputCount().getValue());
			jordan.addHiddenLayer(dialog.getHiddenCount().getValue());
			jordan.setOutputNeurons(dialog.getOutputCount().getValue());
			jordan.setActivationFunction(new ActivationTANH());
			return jordan.generate();
		} else
			return null;

	}

	private static BasicNetwork createElman(String name) {
		CreateElmanDialog dialog = new CreateElmanDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			ElmanPattern elman = new ElmanPattern();
			elman.setInputNeurons(dialog.getInputCount().getValue());
			elman.addHiddenLayer(dialog.getHiddenCount().getValue());
			elman.setOutputNeurons(dialog.getOutputCount().getValue());
			elman.setActivationFunction(new ActivationTANH());
			return elman.generate();
		} else
			return null;

	}

	private static BasicNetwork createHopfield(String name) {
		CreateHopfieldDialog dialog = new CreateHopfieldDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			HopfieldPattern hopfield = new HopfieldPattern();
			hopfield.setInputNeurons(dialog.getNeuronCount().getValue());
			return hopfield.generate();
		} else
			return null;
	}

	private static BasicNetwork createSOM(String name) {
		CreateSOMDialog dialog = new CreateSOMDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			SOMPattern som = new SOMPattern();
			som.setInputNeurons(dialog.getInputCount().getValue());
			som.setOutputNeurons(dialog.getOutputCount().getValue());
			return som.generate();
		} else
			return null;
	}

	private static BasicNetwork createFeedForward(String name) {
		CreateFeedforward dialog = new CreateFeedforward(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.setActivationFunction(new ActivationTANH());
		if (dialog.process()) {
			FeedForwardPattern feedforward = new FeedForwardPattern();
			feedforward.setActivationFunction(dialog.getActivationFunction());
			feedforward.setInputNeurons(dialog.getInputCount().getValue());
			for (int i = 0; i < dialog.getHidden().getModel().size(); i++) {
				String str = (String) dialog.getHidden().getModel()
						.getElementAt(i);
				int i1 = str.indexOf(':');
				int i2 = str.indexOf("neur");
				if (i1 != -1 && i2 != -1) {
					str = str.substring(i1 + 1, i2).trim();
					int neuronCount = Integer.parseInt(str);
					feedforward.addHiddenLayer(neuronCount);
				}
			}
			feedforward.setInputNeurons(dialog.getInputCount().getValue());
			feedforward.setOutputNeurons(dialog.getOutputCount().getValue());
			return feedforward.generate();
		}
		return null;

	}

	private static BasicNetwork createEmpty(String name) {
		BasicNetwork network = new BasicNetwork();
		network.setName(name);
		return network;
	}
	
	private static BasicNetwork createADALINE(String name) {
		CreateADALINEDialog dialog = new CreateADALINEDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			ADALINEPattern adaline = new ADALINEPattern();
			adaline.setInputNeurons(dialog.getNeuronCount().getValue());
			adaline.setOutputNeurons(dialog.getElementCount().getValue());
			return adaline.generate();
		} else
			return null;
	}
	
	private static BasicNetwork createBAM(String name) {
		CreateBAMDialog dialog = new CreateBAMDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			BAMPattern bam = new BAMPattern();
			bam.setInputNeurons(dialog.getLayerACount().getValue());
			bam.setOutputNeurons(dialog.getLayerBCount().getValue());
			return bam.generate();
		} else
			return null;
	}
	
	private static BasicNetwork createBoltzmann(String name) {
		CreateBlotzmannDialog dialog = new CreateBlotzmannDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			BoltzmannPattern boltz = new BoltzmannPattern();
			boltz.setInputNeurons(dialog.getNeuronCount().getValue());
			return boltz.generate();
		} else
			return null;
	}
	
	private static BasicNetwork createCPN(String name) {
		CreateCPNDialog dialog = new CreateCPNDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			CPNPattern cpn = new CPNPattern();
			cpn.setInputNeurons(dialog.getInputCount().getValue());
			cpn.setInstarCount(dialog.getInstarCount().getValue());
			cpn.setOutstarCount(dialog.getOutstarCount().getValue());
			return cpn.generate();
		} else
			return null;
	}
	
	private static BasicNetwork createART1(String name) {
		CreateHopfieldDialog dialog = new CreateHopfieldDialog(EncogWorkBench
				.getInstance().getMainWindow());
		if (dialog.process()) {
			ART1Pattern art1 = new ART1Pattern();
			art1.setInputNeurons(dialog.getNeuronCount().getValue());
			art1.setOutputNeurons(dialog.getNeuronCount().getValue());
			return art1.generate();
		} else
			return null;
	}
	
}
