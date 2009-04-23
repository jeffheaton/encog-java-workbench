package org.encog.workbench.dialogs.activation;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
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
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class EquationPanel extends JPanel {
    /**
     * Creates a dataset with sample values from the normal distribution
     * function.
     *
     * @return A dataset.
     */
    public static XYDataset createDataset() {
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

    /**
     * Creates a line chart using the data from the supplied dataset.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    public static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Normal Distribution Demo 2",
            "X",
            "Y",
            dataset,
            PlotOrientation.VERTICAL,
            true,
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

        // it would be cool if the following annotations had some kind of
        // auto-layout facility...but they don't!
        XYPointerAnnotation a1 = new XYPointerAnnotation(
                "\u03BC = -2.0, \u03C3\u00B2 = 0.5", -2.0, 0.564,
                5 * Math.PI / 4);
        a1.setLabelOffset(4.0);
        a1.setTextAnchor(TextAnchor.BOTTOM_RIGHT);
        a1.setBackgroundPaint(Color.yellow);
       // a1.setOutlineVisible(true);
        plot.addAnnotation(a1);

        XYPointerAnnotation a2 = new XYPointerAnnotation(
                "\u03BC = 0.0, \u03C3\u00B2 = 0.2", 0.225, 0.80, 0.0);
        a2.setLabelOffset(4.0);
        a2.setTextAnchor(TextAnchor.CENTER_LEFT);
        a2.setBackgroundPaint(new Color(0, 0, 255, 63));
       // a2.setOutlineVisible(true);

        plot.addAnnotation(a2);

        XYPointerAnnotation a3 = new XYPointerAnnotation(
                "\u03BC = 0.0, \u03C3\u00B2 = 1.0", 0.75, 0.3, 7 * Math.PI / 4);
        a3.setLabelOffset(4.0);
        a3.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
        a3.setBackgroundPaint(new Color(255, 0, 0, 63));
       // a3.setOutlineVisible(true);
        plot.addAnnotation(a3);

        XYPointerAnnotation a4 = new XYPointerAnnotation(
                "\u03BC = 0.0, \u03C3\u00B2 = 5.0", 3.0, 0.075,
                3 * Math.PI / 2);
        a4.setLabelOffset(4.0);
        a4.setTextAnchor(TextAnchor.BOTTOM_CENTER);
        a4.setBackgroundPaint(new Color(0, 255, 0, 63));
       // a4.setOutlineVisible(true);
        plot.addAnnotation(a4);

        return chart;
    }
}
