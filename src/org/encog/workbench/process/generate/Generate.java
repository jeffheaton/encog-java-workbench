package org.encog.workbench.process.generate;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;



public interface Generate {
	
	public enum GenerateLanguage
	{
		Java,
		CS,
		VB
	}
	
	public enum TrainingMethod
	{
		Backpropagation,
		Genetic,
		Anneal,
		TrainSOM,
		TrainHopfield, 
		NoTraining
	}
	
	public String generate(
			BasicNetwork network,
			NeuralDataSet training,
			boolean copy,
			TrainingMethod trainMethod);
}
