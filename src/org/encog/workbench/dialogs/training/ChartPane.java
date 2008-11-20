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
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartPane extends JPanel {
	
	XYSeries series1;
	XYSeries series2;
	XYSeriesCollection dataset1;
	XYSeriesCollection dataset2;
	JFreeChart chart;
	ChartPanel chartPanel;
	int count;
	
    public ChartPane() {
		this.series1 = new XYSeries("Current Error");
		this.dataset1 = new XYSeriesCollection();
		this.dataset1.addSeries(series1);
		this.series1.setMaximumItemCount(50);
		
		this.series2 = new XYSeries("Error Improvement");
		this.dataset2 = new XYSeriesCollection();
		this.dataset2.addSeries(series2);
		this.series2.setMaximumItemCount(50);
		
		//addData(1,1,0.01);

    	
        JFreeChart chart = createChart();
        this.chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        this.setLayout(new BorderLayout());
        add(chartPanel,BorderLayout.CENTER);
        

    }

	private JFreeChart createChart() {


		/*JFreeChart chart = ChartFactory.createTimeSeriesChart(
				null, "Iteration", "Current Error",
				dataset1, true, true, false);*/
		
		chart = ChartFactory.createXYLineChart(
	            null,
	            "Iteration",
	            "Current Error",
	            this.dataset1,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );

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

		plot.setDataset(1, this.dataset2);
		plot.mapDatasetToRangeAxis(1, 1);
		XYItemRenderer renderer2 = new StandardXYItemRenderer();
		renderer2.setSeriesPaint(0, Color.red);
		plot.setRenderer(1, renderer2);


		ChartUtilities.applyCurrentTheme(chart);

		return chart;
	}
	
	public void addData(int iteration,double error,double improvement)
	{
		if( iteration>10 )
		{
			series1.add(iteration,error*100.0);
			series2.add(iteration,improvement*100.0);
		}
		
	}

	

}
