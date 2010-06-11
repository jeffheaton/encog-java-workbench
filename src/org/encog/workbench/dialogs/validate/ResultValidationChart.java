/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.dialogs.validate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.NeuralOutputHolder;
import org.encog.workbench.tabs.EncogCommonTab;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ResultValidationChart extends EncogCommonTab {
	private static final long serialVersionUID = -2859655432840760344L;
	private JTabbedPane tabs = new JTabbedPane();
	private ArrayList<JFreeChart> charts = new ArrayList<JFreeChart>();
	private ArrayList<ChartPanel> chartPanels = new ArrayList<ChartPanel>();

	public ResultValidationChart() {
		super(null);
		setLayout(new BorderLayout());
		this.add(tabs,BorderLayout.CENTER);
	}

	public void setData(NeuralDataSet validationData, BasicNetwork network) {
		ArrayList<XYSeries> validation = new ArrayList<XYSeries>();
		ArrayList<XYSeries> computation = new ArrayList<XYSeries>();

		Vector<Vector<String>> tableData = new Vector<Vector<String>>();
		Vector<String> tableHeaders = null;

		int key = 0;
		Vector<String> tableDataRow;
		for (NeuralDataPair dataRow : validationData) {
			NeuralData input = dataRow.getInput();
			NeuralData validIdeal = dataRow.getIdeal();
			NeuralData computatedIdeal = getCalculatedResult(dataRow, network);
			int inputCount = input.size();
			int idealCount = validIdeal.size();

			tableDataRow = new Vector<String>();
			if (tableHeaders == null) {
				tableHeaders = new Vector<String>();
				for (int i = 0; i < inputCount; i++) {
					tableHeaders.add("Input " + i);
				}
				for (int i = 0; i < idealCount; i++) {
					tableHeaders.add("Ideal " + i);
					tableHeaders.add("Result " + i);
				}
			}

			for (int i = 0; i < inputCount; i++) {
				tableDataRow.add(new Double(input.getData(i)).toString());
			}

			for (int i = validation.size(); i < idealCount; i++) {
				validation.add(new XYSeries("Validation"));
				computation.add(new XYSeries("Computation"));
				createChart();
			}

			for (int i = 0; i < idealCount; i++) {
				double v = validIdeal.getData(i);
				double c = computatedIdeal.getData(i);

				validation.get(i).add(key, v);
				computation.get(i).add(key, c);

				tableDataRow.add(new Double(v).toString());
				tableDataRow.add(new Double(c).toString());
			}

			tableData.add(tableDataRow);

			key++;
		}

		drawGraphs(validation, computation);
		drawTable(tableData, tableHeaders);
	}

	private void drawGraphs(ArrayList<XYSeries> validation,
			ArrayList<XYSeries> computation) {
		// Add charts
		int size = validation.size();
		for (int i = 0; i < size; i++) {
			XYSeries vSeries = validation.get(i);
			XYSeries cSeries = computation.get(i);
			JFreeChart chart = charts.get(i);
			ChartPanel chartPanel = chartPanels.get(i);

			XYPlot plot = chart.getXYPlot();
			plot.setDataset(0, new XYSeriesCollection(vSeries));
			final XYItemRenderer renderer1 = new StandardXYItemRenderer();
			renderer1.setSeriesPaint(0, Color.blue);
			plot.setRenderer(0, renderer1);

			plot.setDataset(1, new XYSeriesCollection(cSeries));
			final XYItemRenderer renderer2 = new StandardXYItemRenderer();
			renderer2.setSeriesPaint(0, Color.red);
			plot.setRenderer(1, renderer2);

			ChartUtilities.applyCurrentTheme(chart);

			tabs.addTab("Ideal" + (i + 1), chartPanel);
		}
	}

	private void drawTable(Vector<Vector<String>> tableData,
			Vector<String> tableHeaders) {
		JTable table = new JTable(tableData, tableHeaders) {
			private static final long serialVersionUID = 8364655578079933961L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};

		tabs.addTab("Data", new JScrollPane(table));
	}

	private NeuralData getCalculatedResult(NeuralDataPair data,
			BasicNetwork network) {
		NeuralOutputHolder out = new NeuralOutputHolder();
		network.compute(data.getInput(), out);
		return out.getOutput();
	}

	/**
	 * Create the initial chart.
	 * 
	 * @return The chart.
	 */
	private void createChart() {
		JFreeChart chart = ChartFactory.createXYLineChart(null, "Result",
				"Increment", null, PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 360));
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);

		charts.add(chart);
		chartPanels.add(chartPanel);
	}
}
