/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.tabs.analyst;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.encog.app.analyst.AnalystError;
import org.encog.app.analyst.AnalystListener;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.engine.util.Format;
import org.encog.engine.util.Stopwatch;
import org.encog.ml.MLTrain;
import org.encog.neural.networks.training.Train;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.util.EncogFonts;


public class AnalystProgressTab extends EncogCommonTab implements
		Runnable, ActionListener, AnalystListener {

	/**
	 * The start button.
	 */
	private final JButton buttonStart;

	/**
	 * The stop button.
	 */
	private final JButton buttonStopAll;
	
	/**
	 * Stop the current command.
	 */
	private final JButton buttonStopCurrent;

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

	private boolean cancelCommand;
	private boolean cancelAll;


	/**
	 * The font to use for headings.
	 */
	private Font headFont;

	/**
	 * The font for body text.
	 */
	private Font bodyFont;

	private String status;
	
	private EncogAnalyst analyst;
	private String targetTask = "";
	private String currentCommandName = "";
	private String commandStatus = "";
	private String trainingError = "";
	private String trainingIterations = "";
	private int currentCommandNumber;
	private int totalCommands;
	private boolean shouldExit;
	private long lastUpdate;
	private Stopwatch totalTime = new Stopwatch();
	private Stopwatch commandTime = new Stopwatch();
	private MLTrain train;
	
	/**
	 * Construct the dialog box.
	 * 
	 * @param owner
	 *            The owner of the dialog box.
	 */
	public AnalystProgressTab(EncogAnalyst analyst, String targetTask) {
		super(null);
		
		this.status = "Waiting to start.";
		this.analyst = analyst;
		this.targetTask = targetTask;
		
		this.buttonStart = new JButton("Start");
		this.buttonStopAll = new JButton("Stop All Commands");
		this.buttonStopCurrent = new JButton("Stop Current Command");
		this.buttonClose = new JButton("Close");

		this.buttonStart.addActionListener(this);
		this.buttonStopAll.addActionListener(this);
		this.buttonClose.addActionListener(this);
		this.buttonStopCurrent.addActionListener(this);

		setLayout(new BorderLayout());
		this.panelBody = new AnalystStatusPanel(this);
		this.panelButtons = new JPanel();
		this.panelButtons.add(this.buttonStart);
		this.panelButtons.add(this.buttonStopAll);
		this.panelButtons.add(this.buttonStopCurrent);
		this.panelButtons.add(this.buttonClose);		
		
		add(this.panelBody, BorderLayout.CENTER);
		add(this.panelButtons, BorderLayout.SOUTH);

		this.buttonStopAll.setEnabled(false);
		this.buttonStopCurrent.setEnabled(false);

		this.bodyFont = EncogFonts.getInstance().getBodyFont();
		this.headFont = EncogFonts.getInstance().getHeadFont();
	}

	private void performClose() {
		
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
		} else if (e.getSource() == this.buttonStopAll) {
			performStopAll();
		} else if (e.getSource() == this.buttonStopCurrent) {
			performStopCurrent();
		}
	}
	
	public boolean close()
	{
		if (this.thread == null) {
			performClose();
			return true;
		} else {
			this.shouldExit = true;
			this.cancelAll = true;
			return false;
		}		
	}



	public void paintStatus(final Graphics g) {
		g.setColor(Color.white);
		final int width = getWidth();
		final int height = getHeight();
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.setFont(this.headFont);
		final FontMetrics fm = g.getFontMetrics();
		int y = fm.getHeight();
		g.drawString("Overall Status:", 10, y);
		y += fm.getHeight();
		g.drawString("Running Task:", 10, y);
		y += fm.getHeight();
		g.drawString("Current Command Name:", 10, y);
		y += fm.getHeight();
		g.drawString("Current Command Number:", 10, y);
		y += fm.getHeight();
		g.drawString("Current Command Status:", 10, y);
		

		y = fm.getHeight();
		g.drawString("Elapsed Time:", 350, y);
		y += fm.getHeight();
		g.drawString("Command Elapsed Time:", 350, y);
		y += fm.getHeight();
		g.drawString("Training Iterations:", 350, y);
		y += fm.getHeight();
		g.drawString("Training Error:", 350, y);
		y += fm.getHeight();
		g.drawString("Training Type:", 350, y);


		y = fm.getHeight();
		g.setFont(this.bodyFont);

		g.drawString(this.status, 175, y);
		y += fm.getHeight();
		g.drawString(this.targetTask, 175, y);
		y += fm.getHeight();
		g.drawString(this.currentCommandName, 175, y);
		y += fm.getHeight();
		g.drawString(this.currentCommandNumber + " / " + this.totalCommands, 175, y);
		y += fm.getHeight();
		g.drawString(this.commandStatus, 175, y);
		y += fm.getHeight();
		
		String time1 = Format.formatTimeSpan((int)(this.totalTime.getElapsedMilliseconds()/1000));
		String time2 = Format.formatTimeSpan((int)(this.commandTime.getElapsedMilliseconds()/1000));

		y = fm.getHeight();
		g.setFont(this.bodyFont);

		g.drawString(time1, 500, y);
		y += fm.getHeight();
		g.drawString(time2, 500, y);
		y += fm.getHeight();
		g.drawString(this.trainingIterations, 500, y);
		y += fm.getHeight();
		g.drawString(this.trainingError, 500, y);
		y += fm.getHeight();
		if( train!=null ) {
			g.drawString(train.getClass().getSimpleName(), 500, y);
		}


	}

	/**
	 * Start the training.
	 */
	private void performStart() {

		this.buttonStart.setEnabled(false);
		this.buttonStopAll.setEnabled(true);
		this.buttonStopCurrent.setEnabled(true);
		this.cancelAll = false;
		this.cancelCommand = false;
		this.status = "Started";
		this.thread = new Thread(this);
		this.thread.start();
	}

	/**
	 * Request that the training stop.
	 */
	private void performStopAll() {
		this.status = "Canceled";
		this.cancelCommand = true;
		this.cancelAll = true;
		this.analyst.stopCurrentTask();
	}
	
	private void performStopCurrent() {
		this.cancelCommand = true;
		this.analyst.stopCurrentTask();
	}


	

	/**
	 * Process the background thread. Cycle through training iterations. If the
	 * cancel flag is set, then exit.
	 */
	public void run() {

		try {
			this.status = "Running...";
			this.totalTime.reset();
			this.commandTime.reset();
			this.totalTime.start();
			update();
			analyst.addAnalystListener(this);
			analyst.executeTask(this.targetTask);		
			analyst.removeAnalystListener(this);
			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
			
		} catch (AnalystError ex) {
			EncogWorkBench.getInstance().outputLine("***Encog Analyst Error");
			EncogWorkBench.getInstance().outputLine(ex.getMessage());
			this.status = "Error encountered.";
			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
			shutdown();
			stopped();
			dispose();
		} finally {
			shutdown();
			stopped();
			
			this.status = "Done.";
			update(true);
			EncogWorkBench.getInstance().refresh();

			if (this.shouldExit) {
				dispose();
			}
		}
	}


	/**
	 * Implemented by subclasses to perform any shutdown after training.
	 */
	public void shutdown()
	{
		
	}

	/**
	 * Implemented by subclasses to perform any activity before training.
	 */
	public void startup()
	{
		
	}

	/**
	 * Called when training has stopped.
	 */
	private void stopped() {
		this.thread = null;
		this.buttonStart.setEnabled(true);
		this.buttonStopAll.setEnabled(false);
		this.buttonStopCurrent.setEnabled(false);
		this.cancelAll = true;
	}

	public void requestShutdown() {
		this.cancelAll = true;
	}

	public boolean shouldShutDown() {
		return this.cancelAll;
	}

	public void reportCommandBegin(int total, int current, String name) {
		this.cancelCommand = false;
		this.currentCommandName=name;
		this.totalCommands=total;
		this.currentCommandNumber=current;
		this.commandTime.reset();
		this.commandTime.start();
		String str = "Beginning Command #" + current + "/" + total + ": " + name;
		this.commandStatus = "Running";
		EncogWorkBench.getInstance().outputLine(str);
		update();
	}

	public void reportCommandEnd(boolean canceled) {
		this.commandTime.stop();
		String str;
		
		if( canceled )
			str = "Canceled";
		else
			str = "Done with";
		
		str += " Command #" + this.currentCommandNumber + ": " + this.currentCommandName + ", elapsed time: " + Format.formatTimeSpan((int)(this.commandTime.getElapsedMilliseconds()/1000));
		EncogWorkBench.getInstance().outputLine(str);
		update(true);
	}

	public void reportTrainingBegin() {
		update();
	}

	public void reportTrainingEnd() {
		this.commandStatus = "Training Done";
		this.train = null;
		update(true);
	}

	public void reportTraining(MLTrain train) {
		this.train = train;
		this.commandStatus = "Training";
		this.trainingIterations = Format.formatInteger(train.getIteration());
		this.trainingError = Format.formatPercent(train.getError());
		update();
	}

	public void report(int total, int current, String message) {
		if (total == 0) {
			this.commandStatus = Format.formatInteger(current) + " : " + message;
		} else {
			this.commandStatus = Format.formatInteger(current) + "/" + Format.formatInteger(total) + " : " + message;
		}
		update();		
	}
	
	public void update()
	{
		update(false);
	}
	
	public void update(boolean force)
	{
		long now = System.currentTimeMillis();
		if( (now-this.lastUpdate)>1000 || force ) {
			this.lastUpdate = now;
			repaint();
		}
	}

	public void requestCancelCommand() {
		this.cancelCommand = true;
	}

	public boolean shouldStopCommand() {
		return this.cancelCommand;
	}

	@Override
	public String getName() {
		return "Analyst Progress";
	}

}
