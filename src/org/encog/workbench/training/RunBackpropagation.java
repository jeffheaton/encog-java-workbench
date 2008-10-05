package org.encog.workbench.training;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;

public class RunBackpropagation extends RunTraining {

	private double momentum;
	private double learningRate;
	private double maximumError;
	private NeuralDataSet trainingData;
	private BasicNetwork neuralNetwork;
	
	@Override
	public void begin() {
		
		// these are the parameters that define how this training
		// is to take place.  It is at this point that you should
		// pop open a modal dialog box and prompt for these values
		// for now I will just hard code them. 
		this.momentum = 0.07;
		this.learningRate = 0.07;
		this.maximumError = 0.01;
		String trainingDataName = "data-1"; // for this one just do a combo box with every training set in the current file
		String neuralNetworkName = "network-1";// also a combo box of every neural network
		
		// now load the training set
		EncogPersistedCollection currentFile = EncogWorkBench.getInstance().getCurrentFile();
		this.trainingData = (NeuralDataSet)currentFile.find(trainingDataName);
		this.neuralNetwork = (BasicNetwork)currentFile.find(neuralNetworkName);
		
		// we have the data, now train.  I am doing this in the console
		// create a dialog box that displays the results.  Display the current, the epoc(iteration)
		// error.  This is where we will graph the decay of the error.
		// the following loop will need to be run from a thread, and
		// then update the status dialog box and graph as needed.  Allow the user to cancel
		
		// train the neural network
		final Train train = new Backpropagation(
				this.neuralNetwork, 
				this.trainingData,
				this.learningRate, 
				this.momentum);

		int epoch = 1;

		do {
			train.iteration();
			System.out
					.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
		} while ( train.getError() > this.maximumError);

		
		
		
	}

}
