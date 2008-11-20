package org.encog.workbench.dialogs.training;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.workbench.util.TimeSpanFormatter;

public abstract class BasicTrainingProgress extends JDialog implements Runnable,ActionListener {
	
	private JButton buttonStart;
	private JButton buttonStop;
	private JButton buttonClose;
	private JPanel panelBody;
	private JPanel panelButtons;
	private Thread thread;
	private boolean cancel;
	protected TrainingStatusPanel statusPanel;
	protected ChartPane chartPanel;
	
	private Train train;
	private BasicNetwork network;
	private NeuralDataSet trainingData;
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
	
	private boolean shouldExit;
	
	public BasicTrainingProgress(Frame owner)
	{
		super(owner);
		this.setSize(640,400);
		Container content = this.getContentPane();
		this.buttonStart = new JButton("Start");
		this.buttonStop = new JButton("Stop");
		this.buttonClose = new JButton("Close");
		
		this.buttonStart.addActionListener(this);
		this.buttonStop.addActionListener(this);
		this.buttonClose.addActionListener(this);
		
		content.setLayout(new BorderLayout());
		this.panelBody = new JPanel();
		this.panelButtons = new JPanel();
		panelButtons.add(this.buttonStart);
		panelButtons.add(this.buttonStop);
		panelButtons.add(this.buttonClose);
		content.add(this.panelBody,BorderLayout.CENTER);
		content.add(this.panelButtons,BorderLayout.SOUTH);
		this.panelBody.setLayout(new BorderLayout());
		this.panelBody.add(this.statusPanel = new TrainingStatusPanel(this),BorderLayout.NORTH);
		this.panelBody.add(this.chartPanel = new ChartPane(),BorderLayout.SOUTH);
		this.buttonStop.setEnabled(false);	
		setDefaultCloseOperation(
				WindowConstants.DO_NOTHING_ON_CLOSE); 
		shouldExit = false;
		
	}

	public void run() {
		startup();
		while(!cancel)
		{
			this.iteration++;
			this.lastError = train.getError();
			
			iteration();
			
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
		shutdown();
		stopped();
		
		if( shouldExit )
			dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.buttonClose )
		{
			if( thread==null )
				dispose();
			else
			{
				this.shouldExit = true;
				this.cancel = true;				
			}
		}
		else if(e.getSource()==this.buttonStart)
			performStart();
		else if(e.getSource()==this.buttonStop)
			performStop();		
	}

	private void performStop() {
		this.cancel = true;
	}

	private void performStart() {
		
		this.started = new Date();
		this.performanceLast = this.started;
		this.performanceCount = -1;
		this.performanceLastIteration = 0;

		
		this.buttonStart.setEnabled(false);
		this.buttonStop.setEnabled(true);
		this.cancel = false;
		this.thread = new Thread(this);
		this.thread.start();
	}	
	
	private void stopped()
	{
		this.thread = null;
		this.buttonStart.setEnabled(true);
		this.buttonStop.setEnabled(false);		
		this.cancel = true;
	}
	

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
		g.drawString(TimeSpanFormatter.formatTime(seconds), 500, y);
		
	}

	
	
	public abstract void startup();
	public abstract void shutdown();
	public abstract void iteration();
	

	/**
	 * @return the train
	 */
	public Train getTrain() {
		return train;
	}

	/**
	 * @param train the train to set
	 */
	public void setTrain(Train train) {
		this.train = train;
	}

	/**
	 * @return the network
	 */
	public BasicNetwork getNetwork() {
		return network;
	}

	/**
	 * @param network the network to set
	 */
	public void setNetwork(BasicNetwork network) {
		this.network = network;
	}

	/**
	 * @return the trainingData
	 */
	public NeuralDataSet getTrainingData() {
		return trainingData;
	}

	/**
	 * @param trainingData the trainingData to set
	 */
	public void setTrainingData(NeuralDataSet trainingData) {
		this.trainingData = trainingData;
	}


	/**
	 * @param maxError the maxError to set
	 */
	public void setMaxError(double maxError) {
		this.maxError = maxError;
	}
	

	
	
}
