package org.encog.workbench.training;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;

public class RunBackpropagation extends RunTraining {

	private NeuralDataSet trainingData;
	private BasicNetwork neuralNetwork;
	
	@Override
	public void begin(TrainingInput ti) {
		
		EncogPersistedCollection currentFile = EncogWorkBench.getInstance().getCurrentFile();
		this.trainingData = (NeuralDataSet)currentFile.find(ti.gettrainingDataName());
		this.neuralNetwork = (BasicNetwork)currentFile.find(ti.getneuralNetworkName());
		
		// we have the data, now train.  I am doing this in the console
		// create a dialog box that displays the results.  Display the current, the epoc(iteration)
		// error.  This is where we will graph the decay of the error.
		// the following loop will need to be run from a thread, and
		// then update the status dialog box and graph as needed.  Allow the user to cancel
		
		// train the neural network
		final Train train = new Backpropagation(
				this.neuralNetwork, 
				this.trainingData,
				ti.getlearningRate(), 
				ti.getmomentum());

		int epoch = 1;

		do {
			train.iteration();
			System.out
					.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
		} while ( train.getError() > ti.getmaximumError());

		
		
		
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}

}
