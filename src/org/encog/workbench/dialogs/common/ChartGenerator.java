package org.encog.workbench.dialogs.common;

import org.encog.neural.activation.ActivationFunction;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

public interface ChartGenerator {
	public XYDataset createDataset();
	JFreeChart createChart(XYDataset dataset);
}
