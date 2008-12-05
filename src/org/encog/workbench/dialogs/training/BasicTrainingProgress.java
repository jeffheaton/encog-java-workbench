/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
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

public abstract class BasicTrainingProgress extends JDialog implements
		Runnable, ActionListener {

	private final JButton buttonStart;
	private final JButton buttonStop;
	private final JButton buttonClose;
	private final JPanel panelBody;
	private final JPanel panelButtons;
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
	private final NumberFormat nf = NumberFormat.getInstance();
	private final NumberFormat nfShort = NumberFormat.getInstance();
	private int performanceCount;
	private Date performanceLast;
	private int performanceLastIteration;

	private boolean shouldExit;

	public BasicTrainingProgress(final Frame owner) {
		super(owner);
		this.setSize(640, 400);
		final Container content = getContentPane();
		this.buttonStart = new JButton("Start");
		this.buttonStop = new JButton("Stop");
		this.buttonClose = new JButton("Close");

		this.buttonStart.addActionListener(this);
		this.buttonStop.addActionListener(this);
		this.buttonClose.addActionListener(this);

		content.setLayout(new BorderLayout());
		this.panelBody = new JPanel();
		this.panelButtons = new JPanel();
		this.panelButtons.add(this.buttonStart);
		this.panelButtons.add(this.buttonStop);
		this.panelButtons.add(this.buttonClose);
		content.add(this.panelBody, BorderLayout.CENTER);
		content.add(this.panelButtons, BorderLayout.SOUTH);
		this.panelBody.setLayout(new BorderLayout());
		this.panelBody.add(this.statusPanel = new TrainingStatusPanel(this),
				BorderLayout.NORTH);
		this.panelBody.add(this.chartPanel = new ChartPane(),
				BorderLayout.SOUTH);
		this.buttonStop.setEnabled(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.shouldExit = false;

	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.buttonClose) {
			if (this.thread == null) {
				dispose();
			} else {
				this.shouldExit = true;
				this.cancel = true;
			}
		} else if (e.getSource() == this.buttonStart) {
			performStart();
		} else if (e.getSource() == this.buttonStop) {
			performStop();
		}
	}

	/**
	 * @return the network
	 */
	public BasicNetwork getNetwork() {
		return this.network;
	}

	/**
	 * @return the train
	 */
	public Train getTrain() {
		return this.train;
	}

	/**
	 * @return the trainingData
	 */
	public NeuralDataSet getTrainingData() {
		return this.trainingData;
	}

	public abstract void iteration();

	public void paintStatus(final Graphics g) {
		g.setColor(Color.white);
		final int width = getWidth();
		final int height = getHeight();
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.setFont(this.headFont);
		final FontMetrics fm = g.getFontMetrics();
		int y = fm.getHeight();
		g.drawString("Iteration:", 10, y);
		y += fm.getHeight();
		g.drawString("Current Error:", 10, y);
		y += fm.getHeight();
		g.drawString("Error Improvement:", 10, y);

		y = fm.getHeight();
		g.drawString("Elapsed Time:", 400, y);

		y = fm.getHeight();
		g.setFont(this.bodyFont);
		String str = this.nf.format(this.iteration);
		if (this.performanceCount == -1) {
			str += "  (calculating performance)";
		} else {
			final double d = this.performanceCount / 60.0;
			str += "  (" + this.nfShort.format(d) + "/sec)";
		}
		g.drawString(str, 150, y);
		y += fm.getHeight();
		g.drawString("" + 100.0 * this.currentError + "%", 150, y);
		y += fm.getHeight();
		g.drawString("" + 100.0 * this.errorImprovement + "%", 150, y);
		y = fm.getHeight();

		long seconds = 0;
		if (this.started != null) {
			final Date now = new Date();
			seconds = (now.getTime() - this.started.getTime()) / 1000;
		}
		g.drawString(TimeSpanFormatter.formatTime(seconds), 500, y);

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

	private void performStop() {
		this.cancel = true;
	}

	public void redraw() {
		this.statusPanel.repaint();
		this.lastUpdate = System.currentTimeMillis();
		this.chartPanel.addData(this.iteration, this.train.getError(),
				this.errorImprovement);
	}

	public void run() {
		startup();
		while (!this.cancel) {
			this.iteration++;
			this.lastError = this.train.getError();

			iteration();

			this.currentError = this.train.getError();

			if (this.currentError < this.maxError) {
				this.cancel = true;
			}

			this.errorImprovement = (this.lastError - this.currentError)
					/ this.lastError;
			if (System.currentTimeMillis() - this.lastUpdate > 1000
					|| this.cancel) {
				redraw();
			}

			final Date now = new Date();
			if (now.getTime() - this.performanceLast.getTime() > 60000) {
				this.performanceLast = now;
				this.performanceCount = this.iteration
						- this.performanceLastIteration;
				this.performanceLastIteration = this.iteration;
			}
		}
		shutdown();
		stopped();

		if (this.shouldExit) {
			dispose();
		}
	}

	/**
	 * @param maxError
	 *            the maxError to set
	 */
	public void setMaxError(final double maxError) {
		this.maxError = maxError;
	}

	/**
	 * @param network
	 *            the network to set
	 */
	public void setNetwork(final BasicNetwork network) {
		this.network = network;
	}

	/**
	 * @param train
	 *            the train to set
	 */
	public void setTrain(final Train train) {
		this.train = train;
	}

	/**
	 * @param trainingData
	 *            the trainingData to set
	 */
	public void setTrainingData(final NeuralDataSet trainingData) {
		this.trainingData = trainingData;
	}

	public abstract void shutdown();

	public abstract void startup();

	private void stopped() {
		this.thread = null;
		this.buttonStart.setEnabled(true);
		this.buttonStop.setEnabled(false);
		this.cancel = true;
	}

}
