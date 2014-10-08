/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.validate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.ml.MLClassification;
import org.encog.ml.MLMethod;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.workbench.WorkBenchError;
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
		this.add(tabs, BorderLayout.CENTER);

	}

	public void setData(MLDataSet validationData, MLMethod method) {
		ArrayList<XYSeries> validation = new ArrayList<XYSeries>();
		ArrayList<XYSeries> computation = new ArrayList<XYSeries>();

		Vector<Vector<String>> tableData = new Vector<Vector<String>>();
		Vector<String> tableHeaders = null;

		int key = 0;
		Vector<String> tableDataRow;
		for (MLDataPair dataRow : validationData) {
			MLData input = dataRow.getInput();
			MLData validIdeal = dataRow.getIdeal();
			MLData computatedIdeal = getCalculatedResult(dataRow, method);
			int inputCount = input.size();
			int idealCount = validIdeal == null ? 0 : validIdeal.size();

			tableDataRow = new Vector<String>();
			if (tableHeaders == null) {
				tableHeaders = new Vector<String>();
				for (int i = 0; i < inputCount; i++) {
					tableHeaders.add("Input " + i);
				}
				for (int i = 0; i < computatedIdeal.size(); i++) {
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

			for (int i = 0; i < computatedIdeal.size(); i++) {
				double c = computatedIdeal.getData(i);
								
				if (idealCount > 0) {
					double v = validIdeal.getData(i);
					validation.get(i).add(key, v);
					tableDataRow.add(new Double(v).toString());
					computation.get(i).add(key, c);
				} else {
					tableDataRow.add("N/A");
				}
				
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
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabs.addTab("Data", new JScrollPane(table));
	}

	private MLData getCalculatedResult(MLDataPair data, MLMethod method) {

		MLData out;

		if (method instanceof MLRegression) {
			out = ((MLRegression) method).compute(data.getInput());
		} else if (method instanceof MLClassification) {
			out = new BasicMLData(1);
			out.setData(0,
					((MLClassification) method).classify(data.getInput()));

		} else {
			throw new WorkBenchError("Unsupported Machine Learning Method:"
					+ method.getClass().getSimpleName());
		}

		return out;
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

	@Override
	public String getName() {
		return "Validation";
	}
}
