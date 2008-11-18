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
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class ChartPane extends JPanel {
	
    public ChartPane() {
        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        this.setLayout(new BorderLayout());
        add(chartPanel,BorderLayout.CENTER);
    }

	private static JFreeChart createChart() {

		XYDataset dataset1 = createDataset("Current Error", 100.0, new Minute(), 200);

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				null, "Iteration", "Current Error",
				dataset1, true, true, false);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setOrientation(PlotOrientation.VERTICAL);

		plot.getRangeAxis().setFixedDimension(15.0);

		// AXIS 2
		NumberAxis axis2 = new NumberAxis("Error Improvement");
		axis2.setFixedDimension(10.0);
		axis2.setAutoRangeIncludesZero(false);
		axis2.setLabelPaint(Color.red);
		axis2.setTickLabelPaint(Color.red);
		plot.setRangeAxis(1, axis2);
		plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);

		XYDataset dataset2 = createDataset("Error Improvement", 1000.0, new Minute(),
				170);
		plot.setDataset(1, dataset2);
		plot.mapDatasetToRangeAxis(1, 1);
		XYItemRenderer renderer2 = new StandardXYItemRenderer();
		renderer2.setSeriesPaint(0, Color.red);
		plot.setRenderer(1, renderer2);


		ChartUtilities.applyCurrentTheme(chart);

		return chart;
	}

	private static XYDataset createDataset(String name, double base,
			RegularTimePeriod start, int count) {

		TimeSeries series = new TimeSeries(name, start.getClass());
		RegularTimePeriod period = start;
		double value = base;
		for (int i = 0; i < count; i++) {
			series.add(period, value);
			period = period.next();
			value = value * (1 + (Math.random() - 0.495) / 10.0);
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series);

		return dataset;

	}

}
