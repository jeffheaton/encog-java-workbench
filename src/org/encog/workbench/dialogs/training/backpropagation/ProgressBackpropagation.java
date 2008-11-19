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
	private NumberFormat nfShort = NumberFormat.getInstance();
	private int performanceCount;
	private Date performanceLast;
	private int performanceLastIteration;
	
	public ProgressBackpropagation(
			Frame owner,
			BasicNetwork network,
			NeuralDataSet trainingData,
			double learningRate,
			double momentum,
			double maxError)
	{
		super(owner);
		setTitle("Backpropagation Training");
		this.network = network;
		this.trainingData = trainingData;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.maxError = maxError;
		this.headFont = new Font("Sans",Font.BOLD,12);
		this.bodyFont = new Font("Sans",0,12);
		nfShort.setMaximumFractionDigits(2);
        
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
			this.chartPanel.addData(this.iteration, train.getError(), this.errorImprovement);
		}
		
		Date now = new Date();
		if( (now.getTime()-this.performanceLast.getTime())>60000 )
		{
			this.performanceLast = now;
			this.performanceCount = this.iteration - this.performanceLastIteration;
			this.performanceLastIteration = this.iteration;
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
		this.performanceLast = this.started;
		this.performanceCount = -1;
		this.performanceLastIteration = 0;
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
		g.drawString("Elapsed Time:", 400, y);
		
		y = fm.getHeight();
		g.setFont(this.bodyFont);
		String str = nf.format(iteration);
		if( this.performanceCount==-1 )
			str+="  (calculating performance)";
		else
		{
			double d = this.performanceCount/60.0;
			str+="  ("+nfShort.format(d)+"/sec)";
		}
		g.drawString(str, 150, y);
		y+=fm.getHeight();
		g.drawString(""+(100.0*this.currentError)+"%", 150, y);
		y+=fm.getHeight();
		g.drawString(""+(100.0*this.errorImprovement)+"%", 150, y);
		y = fm.getHeight();
		
		long seconds = 0;
		if( this.started!=null )
		{
			Date now = new Date();
			seconds = (now.getTime() - this.started.getTime())/1000;			
		}
		g.drawString(formatTime(seconds), 500, y);
		
	}
	
	public static final int SECONDS_IN_MINUTE = 60;
	public static final int SECONDS_IN_HOUR = (SECONDS_IN_MINUTE*60);
	public static final int SECONDS_IN_DAY = (SECONDS_IN_HOUR*24);
	
	String formatTime(long seconds)
	{
		StringBuilder result = new StringBuilder();
		long days = seconds/SECONDS_IN_DAY;
		seconds = seconds - (days*SECONDS_IN_DAY);
		long hours = seconds/SECONDS_IN_HOUR;
		seconds = seconds - (hours*SECONDS_IN_HOUR);
		long minutes = seconds/SECONDS_IN_MINUTE;
		seconds = seconds - (minutes*SECONDS_IN_MINUTE);
		
		if( days>0 )
		{
			result.append(days);
			result.append(" days, ");
		}
		
		result.append(formatDigit(hours));
		result.append(':');
		result.append(formatDigit(minutes));
		result.append(':');
		result.append(formatDigit(seconds));
			
		return result.toString();
	}
	
	String formatDigit(long d)
	{
		StringBuilder result = new StringBuilder();
		if( d<10 )
		{
			result.append('0');
		}
		result.append(d);
		return result.toString();
	}
}
