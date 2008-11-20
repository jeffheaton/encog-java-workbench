package org.encog.workbench.dialogs.training.anneal;

import java.awt.Frame;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressAnneal extends BasicTrainingProgress {
	
	private double startTemp;
	private double endTemp;
	private int cycles;
		
	public ProgressAnneal(
			Frame owner,
			BasicNetwork network,
			NeuralDataSet trainingData,
			double maxError,
			double startTemp,
			double endTemp,
			int cycles)
	{
		super(owner);
		setTitle("Simulated Annealing Training");
		this.setNetwork( network );
		this.setTrainingData( trainingData );
		this.cycles = cycles;
		this.startTemp = startTemp;
		this.endTemp = endTemp;
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
		Train train = new NeuralSimulatedAnnealing(
				this.getNetwork(), 
				this.getTrainingData(),
				this.startTemp, 
				this.endTemp,this.cycles);	
		
		setTrain(train);
	}


}
