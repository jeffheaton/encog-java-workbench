package org.encog.workbench.process;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.HopfieldPattern;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.createnetwork.CreateNeuralNetworkDialog;
import org.encog.workbench.dialogs.createnetwork.NeuralNetworkType;

public class CreateNeuralNetwork {
	
	public static void process(String name)
	{
		BasicNetwork network = null;
		CreateNeuralNetworkDialog dialog = new CreateNeuralNetworkDialog(EncogWorkBench.getInstance().getMainWindow());
		dialog.setType(NeuralNetworkType.Empty);
		if( dialog.process() )
		{
			switch(dialog.getType())
			{
				case Empty:
					network = createEmpty(name);
					break;
				case Feedforward:
					network = createFeedForward(name);
					break;
				case SOM:
					network = createSOM(name);
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
				default:
					network = createEmpty(name);
					break;
			}
			
			EncogWorkBench.getInstance().getCurrentFile().add(name,network);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}

	private static BasicNetwork createRBF(String name) {
		return null;
		
	}

	private static BasicNetwork createJordan(String name) {
		return null;
		
	}

	private static BasicNetwork createElman(String name) {
		return null;
		
	}

	private static BasicNetwork createHopfield(String name) {
		HopfieldPattern hopfield = new HopfieldPattern();
		hopfield.setInputNeurons(4);
		return hopfield.generate();
		
	}

	private static BasicNetwork createSOM(String name) {
		return null;
	}

	private static BasicNetwork createFeedForward(String name) {
		return null;
		
	}

	private static BasicNetwork createEmpty(String name) {
		BasicNetwork network = new BasicNetwork();
		network.setName(name);
		return network;
	}
}
