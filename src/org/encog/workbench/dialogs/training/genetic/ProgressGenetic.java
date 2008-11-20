package org.encog.workbench.dialogs.training.genetic;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.networks.training.genetic.NeuralGeneticAlgorithm;
import org.encog.neural.networks.training.genetic.TrainingSetNeuralGeneticAlgorithm;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressGenetic extends BasicTrainingProgress {
	
	private int populationSize;
	private double mutationPercent;
	private double percentToMate;
		
	public ProgressGenetic(
			Frame owner,
			BasicNetwork network,
			NeuralDataSet trainingData,
			double maxError,
			int populationSize,
			double mutationPercent,
			double percentToMate)
	{
		super(owner);
		setTitle("Genetic Algorithm Training");
		this.setNetwork( network );
		this.setTrainingData( trainingData );
		this.populationSize = populationSize;
		this.mutationPercent = mutationPercent;
		this.percentToMate = percentToMate;
		this.setMaxError( maxError);
        
	}

	@Override
	public void iteration() {

		this.getTrain().iteration();

	}

	@Override
	public void shutdown() {		
	}

	@Override
	public void startup() {
		Train train = new TrainingSetNeuralGeneticAlgorithm(
				this.getNetwork(), 
				true,
				this.getTrainingData(),
				this.populationSize, 
				this.mutationPercent,
				this.percentToMate);	
		
		setTrain(train);
	}


}
