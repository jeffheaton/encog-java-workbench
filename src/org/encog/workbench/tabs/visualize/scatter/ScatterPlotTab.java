package org.encog.workbench.tabs.visualize.scatter;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import org.encog.app.analyst.EncogAnalyst;
import org.encog.workbench.tabs.EncogCommonTab;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;

public class ScatterPlotTab extends EncogCommonTab {

	public ScatterPlotTab(EncogAnalyst analyst, String className, List<String> axisList) {
		super(null);
		this.add(createPanel());
	}
	
	private JPanel createPanel() {
		
		 XYDataset dataset = new ScatterXY();
	        JFreeChart chart = ChartFactory.createScatterPlot("Scatter Plot Demo 4",
	            "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);

	        XYPlot plot = (XYPlot) chart.getPlot();

	        //plot.setDomainTickBandPaint(new Color(200, 200, 100, 100));
	        plot.setRangeTickBandPaint(new Color(200, 200, 100, 100));
	        XYDotRenderer renderer = new XYDotRenderer();
	        renderer.setDotWidth(4);
	        renderer.setDotHeight(4);
	        plot.setRenderer(renderer);
	        plot.setDomainCrosshairVisible(true);
	        plot.setRangeCrosshairVisible(true);

	        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
	        domainAxis.setAutoRangeIncludesZero(false);
	        plot.getRangeAxis().setInverted(true);
	        return new ChartPanel(chart);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Scatter Plot";
	}

}
