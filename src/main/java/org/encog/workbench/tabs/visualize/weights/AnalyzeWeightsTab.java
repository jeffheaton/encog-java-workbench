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
package org.encog.workbench.tabs.visualize.weights;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.structure.AnalyzeNetwork;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.tabs.EncogCommonTab;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

public class AnalyzeWeightsTab extends EncogCommonTab implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4472644832610364833L;

	private JButton buttonClose;
	private JTabbedPane tabs;
	private WeightInfo weightInfo;
	private AnalyzeNetwork analyze;
	private BasicNetwork network;
	private HistogramDataset dataAll;
	private HistogramDataset dataWeights;
	private HistogramDataset dataBiases;
	
	public AnalyzeWeightsTab(ProjectEGFile encogObject) {
		super(encogObject);
		
		if( !(encogObject.getObject() instanceof BasicNetwork) ) {
			throw new WorkBenchError("Can't visualize weights of " + encogObject.getObject().getClass().getSimpleName());
		}
		
		this.network = (BasicNetwork)encogObject.getObject();
		this.analyze = new AnalyzeNetwork(this.network);
		
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		add(buttonPanel,BorderLayout.SOUTH);
		
		this.buttonClose = new JButton("Close");
		buttonPanel.add(this.buttonClose);
		this.buttonClose.addActionListener(this);
		
		this.weightInfo = new WeightInfo(this);
		this.add(this.weightInfo,BorderLayout.NORTH);
		
		//
		createAllDataset();
        ChartPanel allChart = new ChartPanel(createChart(this.dataAll));
        ChartPanel weightChart = new ChartPanel(createChart(this.dataWeights));
        ChartPanel biasesChart = new ChartPanel(createChart(this.dataBiases));
        
		
		this.tabs = new JTabbedPane();
		this.add(tabs,BorderLayout.CENTER);
		this.tabs.addTab("Weights & Biases", allChart);
		this.tabs.addTab("Weights", weightChart);
		this.tabs.addTab("Biases", biasesChart);
		

		
	}
	
    private void createAllDataset() {
    	
    	// all values
        this.dataAll = new HistogramDataset();
        double[] values = this.analyze.getAllValues();
        this.dataAll.addSeries("Weights & Biases", values, 50);
        
        // weight values
        this.dataBiases = new HistogramDataset();
        double[] values2 = this.analyze.getWeightValues();
        this.dataBiases.addSeries("Biases", values2, 50);
        
        // threshold values
        this.dataWeights = new HistogramDataset();
        double[] values3 = this.analyze.getBiasValues();
        this.dataWeights.addSeries("Weights", values3, 50);
            
    }
    
 
    /**
     * Creates a chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(IntervalXYDataset dataset) {
        JFreeChart chart = ChartFactory.createHistogram(
            null, 
            null, 
            null, 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            false, 
            false
        );
        chart.getXYPlot().setForegroundAlpha(0.75f);
        return chart;
    }
	
	public AnalyzeNetwork getAnalyze()
	{
		return this.analyze;
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.buttonClose ) {
			this.dispose();
		}
		
	}
	
	@Override
	public String getName() {
		return "Visualize :" + this.getEncogObject().getName();
	}
	
}
