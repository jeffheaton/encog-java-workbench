package org.encog.workbench.dialogs.training.backpropagation;

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
import org.encog.workbench.dialogs.training.BasicTrainingProgress;

public class ProgressBackpropagation extends BasicTrainingProgress {
	
	private Train train;
	private BasicNetwork network;
	private NeuralDataSet trainingData;
	private double learningRate;
	private double momentum;
	private double maxError;
	private int iteration;
	private Font headFont;
	private Font bodyFont;
	private double currentError;
	private double lastError;
	private double errorImprovement;
	private Date started;
	private long lastUpdate;
	private NumberFormat nf = NumberFormat.getInstance();
	
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
		this.headFont = new Font("Sans",Font.BOLD,12);
		this.bodyFont = new Font("Sans",0,12);
        
	}

	@Override
	public void iteration() {
		this.iteration++;
		this.lastError = train.getError();
		train.iteration();
		this.currentError = train.getError();
		this.errorImprovement = (this.lastError-this.currentError)/this.lastError;
		if( (System.currentTimeMillis()-this.lastUpdate)>1000 )
		{
			this.statusPanel.repaint();
			this.lastUpdate = System.currentTimeMillis();
		}
		
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
		this.started = new Date();
	}

	@Override
	public void paintStatus(Graphics g) {
		g.setColor(Color.white);
		int width = this.getWidth();
		int height = this.getHeight();
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.setFont(this.headFont);
		FontMetrics fm = g.getFontMetrics();
		int y = fm.getHeight();
		g.drawString("Iteration:", 10, y);
		y+=fm.getHeight();
		g.drawString("Current Error:", 10, y);
		y+=fm.getHeight();
		g.drawString("Error Improvement:", 10, y);
		
		y = fm.getHeight();
		g.setFont(this.bodyFont);
		g.drawString(nf.format(iteration), 150, y);
		y+=fm.getHeight();
		g.drawString(""+(100.0*this.currentError)+"%", 150, y);
		y+=fm.getHeight();
		g.drawString(""+(100.0*this.errorImprovement)+"%", 150, y);
	}
	
}
