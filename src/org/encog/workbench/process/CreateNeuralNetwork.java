package org.encog.workbench.process;

import org.encog.neural.networks.BasicNetwork;
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
			
			}
			EncogWorkBench.getInstance().getCurrentFile().add(name,network);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}
}
