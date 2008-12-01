package org.encog.workbench.dialogs.training.som;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JLabel;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.neural.networks.training.som.TrainSelfOrganizingMap;
import org.encog.neural.networks.training.som.TrainSelfOrganizingMap.LearningMethod;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;
import org.encog.workbench.util.TimeSpanFormatter;

public class ProgressSOM extends BasicTrainingProgress {
	
	private double learningRate;
	private LearningMethod method;
		
	public ProgressSOM(
			Frame owner,
			BasicNetwork network,
			NeuralDataSet trainingData,
			double learningRate,
			LearningMethod method,
			double maxError)
	{
		super(owner);
		setTitle("SOM Training");
		this.setNetwork( network );
		this.setTrainingData( trainingData );
		this.learningRate = learningRate;
		this.method = method;
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
		Train train = new TrainSelfOrganizingMap(
				this.getNetwork(), 
				this.getTrainingData(),
				this.method, 
				this.learningRate);	
		
		setTrain(train);
	}


}
