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

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Simple pane that holds the chart of training.  This uses JFreeChart
 * @author jheaton
 *
 */
public class ChartPane extends JPanel {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Series 1 holds the current error.
	 */
	XYSeries series1;
	
	/**
	 * Series 2 holds the error improvement.
	 */
	XYSeries series2;
	
	/**
	 * The data set for the current error.
	 */
	XYSeriesCollection dataset1;
	
	/**
	 * The 
	 */
	XYSeriesCollection dataset2;
	
	/**
	 * The chart.
	 */
	JFreeChart chart;
	
	/**
	 * The panel that the chart is drawn upon.
	 */
	ChartPanel chartPanel;
	
	/**
	 * How many data points have been displayed.
	 */
	int count;

	/**
	 * Construct the pane.
	 */
	public ChartPane() {
		this.series1 = new XYSeries("Current Error");
		this.dataset1 = new XYSeriesCollection();
		this.dataset1.addSeries(this.series1);
		this.series1.setMaximumItemCount(50);

		this.series2 = new XYSeries("Error Improvement");
		this.dataset2 = new XYSeriesCollection();
		this.dataset2.addSeries(this.series2);
		this.series2.setMaximumItemCount(50);

		// addData(1,1,0.01);

		final JFreeChart chart = createChart();
		this.chartPanel = new ChartPanel(chart);
		this.chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
		this.chartPanel.setDomainZoomable(true);
		this.chartPanel.setRangeZoomable(true);
		setLayout(new BorderLayout());
		add(this.chartPanel, BorderLayout.CENTER);

	}

	/**
	 * Add a data point to the chart.
	 * @param iteration Which iteration is this.
	 * @param error What is the error at this point.
	 * @param improvement What is the error improvement from the last
	 * iteration.
	 */
	public void addData(final int iteration, final double error,
			final double improvement) {
		if (iteration > 10) {
			this.series1.add(iteration, error * 100.0);
			this.series2.add(iteration, improvement * 100.0);
		}

	}

	/**
	 * Create the initial chart.
	 * @return The chart.
	 */
	private JFreeChart createChart() {

		this.chart = ChartFactory.createXYLineChart(null, "Iteration",
				"Current Error", this.dataset1, PlotOrientation.VERTICAL, true,
				true, false);

		final XYPlot plot = (XYPlot) this.chart.getPlot();
		plot.setOrientation(PlotOrientation.VERTICAL);

		plot.getRangeAxis().setFixedDimension(15.0);

		// AXIS 2
		final NumberAxis axis2 = new NumberAxis("Error Improvement");
		axis2.setFixedDimension(10.0);
		axis2.setAutoRangeIncludesZero(false);
		axis2.setLabelPaint(Color.red);
		axis2.setTickLabelPaint(Color.red);
		plot.setRangeAxis(1, axis2);
		plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);

		plot.setDataset(1, this.dataset2);
		plot.mapDatasetToRangeAxis(1, 1);
		final XYItemRenderer renderer2 = new StandardXYItemRenderer();
		renderer2.setSeriesPaint(0, Color.red);
		plot.setRenderer(1, renderer2);

		ChartUtilities.applyCurrentTheme(this.chart);

		return this.chart;
	}

}
