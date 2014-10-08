/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.tabs.incremental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.encog.StatusReportable;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.neural.prune.PruneIncremental;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.Format;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.util.EncogFonts;

public class IncrementalPruneTab extends EncogCommonTab implements
		ActionListener, Runnable, StatusReportable {

	/**
	 * The start button.
	 */
	private final JButton buttonStart;

	/**
	 * The stop button.
	 */
	private final JButton buttonStop;

	/**
	 * The close button.
	 */
	private final JButton buttonClose;

	/**
	 * The body of the dialog box is stored in this panel.
	 */
	private final JPanel panelBody;

	/**
	 * The buttons are hold in this panel.
	 */
	private final JPanel panelButtons;

	/**
	 * The background thread that processes training.
	 */
	private Thread thread;

	/**
	 * Has training been canceled.
	 */
	private boolean cancel;

	/**
	 * When was training started.
	 */
	private Date started;

	private String status;

	private JPanel statusPanel;
	private JPanel chartPanel;

	private int total;
	private int current;
	private double low;
	private double high;
	private final File path;

	/**
	 * The font to use for headings.
	 */
	private Font headFont;

	/**
	 * The font for body text.
	 */
	private Font bodyFont;

	/**
	 * Should the dialog box exit? Are we waiting for training to shut down
	 * first.
	 */
	private boolean shouldExit;

	private PruneIncremental prune;

	private int iterations;
	private int weightTries;
	private MLDataSet training;
	private FeedForwardPattern pattern;
	private int windowSize;

	public IncrementalPruneTab(int iterations, int weightTries, int windowSize, MLDataSet training,
			FeedForwardPattern pattern, File path) {
		super(null);

		this.weightTries = weightTries;
		this.iterations = iterations;
		this.training = training;
		this.pattern = pattern;
		this.windowSize = windowSize;
		
		this.prune = new PruneIncremental(this.training, this.pattern,
				this.iterations, this.weightTries , this.windowSize, this);
		this.prune.init();

		this.buttonStart = new JButton("Start");
		this.buttonStop = new JButton("Stop");
		this.buttonClose = new JButton("Close");

		this.buttonStart.addActionListener(this);
		this.buttonStop.addActionListener(this);
		this.buttonClose.addActionListener(this);
		
		this.path = path;

		setLayout(new BorderLayout());
		this.panelBody = new JPanel();
		this.panelButtons = new JPanel();
		this.panelButtons.add(this.buttonStart);
		this.panelButtons.add(this.buttonStop);
		this.panelButtons.add(this.buttonClose);
		add(this.panelBody, BorderLayout.CENTER);
		add(this.panelButtons, BorderLayout.SOUTH);
		this.panelBody.setLayout(new BorderLayout());
		this.panelBody.add(this.statusPanel = new IncrementalPruneStatusPanel(
				this), BorderLayout.NORTH);
		this.panelBody.add(this.chartPanel = new IncrementalPruneChart(this),
				BorderLayout.CENTER);
		this.buttonStop.setEnabled(false);

		this.shouldExit = false;
		this.bodyFont = EncogFonts.getInstance().getBodyFont();
		this.headFont = EncogFonts.getInstance().getHeadFont();
		this.status = "Ready to Start";
		
	}

	/**
	 * Track button presses.
	 * 
	 * @param e
	 *            Event info.
	 */
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.buttonClose) {
			dispose();
		} else if (e.getSource() == this.buttonStart) {
			performStart();
		} else if (e.getSource() == this.buttonStop) {
			performStop();
		}
	}

	/**
	 * Start the training.
	 */
	private void performStart() {
		this.started = new Date();

		this.buttonStart.setEnabled(false);
		this.buttonStop.setEnabled(true);
		this.cancel = false;
		this.status = "Started";
		repaint();
		this.thread = new Thread(this);
		this.thread.start();
	}

	/**
	 * Request that the training stop.
	 */
	private void performStop() {
		this.buttonStop.setEnabled(false);
		this.status = "Canceled";
		this.cancel = true;
		this.repaint();
		this.prune.stop();
	}

	public void run() {

		try {
		this.prune.process();

		this.buttonStart.setEnabled(false);
		this.buttonStop.setEnabled(false);
		this.thread = null;

		if (this.shouldExit) {
			dispose();
		}
		}
		catch(Throwable t)
		{
			EncogWorkBench.displayError("Error", t);
		}
	}

	public void paintStatus(Graphics g) {
		g.setColor(Color.white);
		final int width = getWidth();
		final int height = getHeight();
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.setFont(this.headFont);
		final FontMetrics fm = g.getFontMetrics();
		int y = fm.getHeight();
		g.drawString("Progress:", 10, y);
		y += fm.getHeight();
		g.drawString("Percent Complete:", 10, y);
		y += fm.getHeight();
		g.drawString("Status:", 10, y);
		
		
		y = fm.getHeight();
		g.drawString("High Error:", 250, y);
		y += fm.getHeight();
		g.drawString("Low Error:", 250, y);
		
		y = fm.getHeight();
		g.drawString("Iterations to Try:", 450, y);
		y += fm.getHeight();
		g.drawString("Weights to Try:", 450, y);
				
		g.setFont(this.bodyFont);

		StringBuilder progress = new StringBuilder();
		if (this.total > 0) {
			progress.append(Format.formatInteger(this.current));
			progress.append(" of ");
			progress.append(Format.formatInteger(this.total));
		}

		double percent = 0;

		if (this.total > 0)
			percent = (double) this.current / (double) this.total;

		y = fm.getHeight();
		g.drawString(progress.toString(), 150, y);
		y += fm.getHeight();
		g.drawString(Format.formatPercent(percent), 150, y);
		y += fm.getHeight();
		g.drawString(this.status, 150, y);
		
		
		y = fm.getHeight();
		g.drawString(Format.formatPercent(this.high), 350, y);
		y += fm.getHeight();
		g.drawString(Format.formatPercent(this.low), 350, y);

		y = fm.getHeight();
		g.drawString(Format.formatInteger(this.iterations), 550, y);
		y += fm.getHeight();
		g.drawString(Format.formatInteger(this.weightTries), 550, y);


	}

	public void paintChart(Graphics g, int width, int height) {
		g.setColor(Color.black);
		this.high = this.prune.getHigh();
		this.low = this.prune.getLow();

		
		if( this.prune.getHidden1Size()==0 && 
			this.prune.getHidden2Size()==0 )
		{
			
			g.drawString("Chart not supported for more than 2 layers.", 0, 20);
		}
		else if( this.prune.getHidden1Size()>0  )
		{
			int blockWidth = (this.prune.getHidden2Size()>0) ? (width-32)/this.prune.getHidden2Size() : (width-32);
			int blockHeight = (height-32)/this.prune.getHidden1Size();

			g.setFont(this.headFont);

			g.drawString("H1", 10, height/2);			
			g.drawString(""+this.prune.getHidden().get(0).getMin(), 10, 42);
			
			if( this.prune.getHidden().size()>1 )
			{
				g.drawString("H2", width/2, 15);
				g.drawString(""+this.prune.getHidden().get(1).getMin(), 32, 15);
			}
			
			int xLimit = Math.max(this.prune.getHidden2Size(),1); 

			for(int y=0;y<this.prune.getHidden1Size();y++)
			{
				for(int x=0;x<xLimit;x++)
				{
					int xLoc = x*blockWidth;
					int yLoc = y*blockHeight;
					
					double error = this.prune.getResults()[y][x];
					
					if( error>0.00001 )
					{
						high = Math.max(high,error);
						low = Math.min(low,error);						
						double range = high - low;
						double p = (error-low)/range;
						int c = (int)(p*255.0);						
						g.setColor(new Color(c,c,c));
						g.fillRect(32+xLoc, 32+yLoc, blockWidth, blockHeight);
					}
					else
					{
						g.setColor(Color.black);
						g.drawRect(32+xLoc, 32+yLoc, blockWidth, blockHeight);
					}
				}
			}
		}
	}

	public void report(int total, int current, String message) {
		this.total = total;
		this.current = current;
		this.status = message;
		repaint();
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public MLDataSet getTraining() {
		return training;
	}

	public void setTraining(MLDataSet training) {
		this.training = training;
	}

	public FeedForwardPattern getPattern() {
		return pattern;
	}

	public void setPattern(FeedForwardPattern pattern) {
		this.pattern = pattern;
	}

	public boolean close() {
		if (this.thread == null) {
			performClose();
			return true;
		} else {
			this.shouldExit = true;
			this.cancel = true;
			return false;
		}
	}

	public void performClose() {
		if (this.prune != null) {
			BasicNetwork network = this.prune.getBestNetwork();

			if (network != null) {
				if (EncogWorkBench.askQuestion("Network",
						"Do you wish to save this network?")) {
					if (network != null) {
						 
						EncogDirectoryPersistence.saveObject(this.path,network);					
						EncogWorkBench.getInstance().refresh();
					}
				}
			}
		}
	}

	public void addHiddenRange(int low, int high) {
		this.prune.addHiddenLayer(low, high);
		this.prune.init();
		this.repaint();
	}

	public void reportPhase(int arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Prune Progress";
	}

}
