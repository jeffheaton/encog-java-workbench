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

package org.encog.workbench.tabs.weights;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.structure.AnalyzeNetwork;
import org.encog.neural.networks.structure.NetworkCODEC;
import org.encog.persist.EncogPersistedObject;
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
	private HistogramDataset dataThresholds;
	
	public AnalyzeWeightsTab(EncogPersistedObject encogObject) {
		super(encogObject);
		
		this.network = (BasicNetwork)encogObject;
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
        ChartPanel thresholdChart = new ChartPanel(createChart(this.dataThresholds));
        
		
		this.tabs = new JTabbedPane();
		this.add(tabs,BorderLayout.CENTER);
		this.tabs.addTab("Weights & Thresholds", allChart);
		this.tabs.addTab("Thresholds", weightChart);
		this.tabs.addTab("Weights", thresholdChart);
		

		
	}
	
    private void createAllDataset() {
    	
    	// all values
        this.dataAll = new HistogramDataset();
        double[] values = this.analyze.getAllValues();
        this.dataAll.addSeries("Weights & Thresholds", values, 50);
        
        // weight values
        this.dataThresholds = new HistogramDataset();
        double[] values2 = this.analyze.getWeightValues();
        this.dataThresholds.addSeries("Weights", values2, 50);
        
        // threshold values
        this.dataWeights = new HistogramDataset();
        double[] values3 = this.analyze.getBiasValues();
        this.dataWeights.addSeries("Thresholds", values3, 50);
            
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
	
}
