package org.encog.workbench.dialogs.training.backpropagation;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JLabel;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressBackpropagation extends BasicTrainingProgress {
	
	private Train train;
	private BasicNetwork network;
	private NeuralDataSet trainingData;
	private double learningRate;
	private double momentum;
	private double maxError;
	private int iteration;
	private JLabel labelIteration;
	private JLabel labelCurrentError;
	
	public ProgressBackpropagation(
			Frame owner,
			BasicNetwork network,
			NeuralDataSet trainingData,
			double learningRate,
			double momentum,
			double maxError)
	{
		super(owner);
		this.network = network;
		this.trainingData = trainingData;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.maxError = maxError;
		
        this.panelBody.setLayout(new GridLayout(2,2,10,10));
        this.panelBody.add(new JLabel("Iteration #"));
        this.panelBody.add(this.labelIteration = new JLabel("1"));
        this.panelBody.add(new JLabel("Current Error"));
        this.panelBody.add(this.labelCurrentError = new JLabel("80%"));
        
	}

	@Override
	public void iteration() {
		this.iteration++;
		this.labelIteration.setText(""+this.iteration);
		this.labelCurrentError.setText(""+this.train.getError());
		train.iteration();
		
	}

	@Override
	public void shutdown() {		
	}

	@Override
	public void startup() {
		train = new Backpropagation(
				this.network, 
				this.trainingData,
				this.learningRate, 
				this.momentum);		
	}
	
}
