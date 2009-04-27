package org.encog.workbench.dialogs.layers;

import java.awt.BasicStroke;
import java.awt.Color;

import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.workbench.dialogs.common.ChartGenerator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;

public class RBFChartGenerator implements ChartGenerator {

	private RadialBasisFunctionLayer layer;
	
	public RBFChartGenerator(RadialBasisFunctionLayer layer)
	{
		this.layer = layer;
	}
	
	public JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart(
	            null,
	            "X",
	            "Y",
	            dataset,
	            PlotOrientation.VERTICAL,
	            false,
	            true,
	            false
	        );
	        XYPlot plot = (XYPlot) chart.getPlot();
	        plot.setDomainZeroBaselineVisible(true);
	        plot.setRangeZeroBaselineVisible(true);
	        plot.setDomainPannable(true);
	        plot.setRangePannable(true);
	        ValueAxis xAxis = plot.getDomainAxis();
	        xAxis.setLowerMargin(0.0);
	        xAxis.setUpperMargin(0.0);
	        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
	        r.setDrawSeriesLineAsPath(true);
	        r.setSeriesStroke(0, new BasicStroke(1.5f));
	        r.setSeriesStroke(1, new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
	                BasicStroke.JOIN_ROUND, 1.0f, new float[] { 6.0f, 4.0f },
	                0.0f));
	        r.setSeriesStroke(2, new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
	                BasicStroke.JOIN_ROUND, 1.0f, new float[] { 6.0f, 4.0f, 3.0f,
	                3.0f }, 0.0f));
	        r.setSeriesStroke(3, new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
	                BasicStroke.JOIN_ROUND, 1.0f, new float[] { 4.0f, 4.0f },
	                0.0f));

	        return chart;
	}

	public XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        Function2D n1 = new NormalDistributionFunction2D(0.0, 1.0);
        XYSeries s1 = DatasetUtilities.sampleFunction2DToSeries(n1, -5.1, 5.1,
                121, "N1");
        dataset.addSeries(s1);

        Function2D n2 = new NormalDistributionFunction2D(0.0, Math.sqrt(0.2));
        XYSeries s2 = DatasetUtilities.sampleFunction2DToSeries(n2, -5.1, 5.1,
                121, "N2");
        dataset.addSeries(s2);

        Function2D n3 = new NormalDistributionFunction2D(0.0, Math.sqrt(5.0));
        XYSeries s3 = DatasetUtilities.sampleFunction2DToSeries(n3, -5.1, 5.1,
                121, "N3");
        dataset.addSeries(s3);

        Function2D n4 = new NormalDistributionFunction2D(-2.0, Math.sqrt(0.5));
        XYSeries s4 = DatasetUtilities.sampleFunction2DToSeries(n4, -5.1, 5.1,
                121, "N4");
        dataset.addSeries(s4);
        return dataset;
	}

}
