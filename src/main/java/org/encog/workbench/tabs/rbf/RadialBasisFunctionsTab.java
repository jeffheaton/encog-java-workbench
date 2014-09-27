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
package org.encog.workbench.tabs.rbf;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.encog.mathutil.rbf.GaussianFunction;
import org.encog.mathutil.rbf.InverseMultiquadricFunction;
import org.encog.mathutil.rbf.MexicanHatFunction;
import org.encog.mathutil.rbf.MultiquadricFunction;
import org.encog.mathutil.rbf.RadialBasisFunction;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.util.graph.EncogChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RadialBasisFunctionsTab extends EncogCommonTab implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4472644832610364833L;

	private JButton buttonClose;
	private RadialBasisFunction rbf;
	private JComboBox typeCombo;
	private ChartPanel chartPanel;
	
	public RadialBasisFunctionsTab() {
		super(null);
		
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		add(buttonPanel,BorderLayout.SOUTH);
		
		this.buttonClose = new JButton("Close");
		buttonPanel.add(this.buttonClose);
		this.buttonClose.addActionListener(this);
		
		//this.weightInfo = new WeightInfo(this);
		//this.add(this.weightInfo,BorderLayout.NORTH);
		
		//
		double[] center = { 0.0 };

		this.rbf = new GaussianFunction(1.0,center,1.0);
		
		XYDataset dataset = this.createDataset();
		JFreeChart chart = this.createChart(dataset);
		this.chartPanel = new EncogChartPanel(chart);
		this.add(chartPanel,BorderLayout.CENTER);
		
		this.typeCombo = new JComboBox();
		
		this.add(this.typeCombo,BorderLayout.NORTH);
		this.typeCombo.addItem(GaussianFunction.class.getSimpleName());
		this.typeCombo.addItem(MultiquadricFunction.class.getSimpleName());
		this.typeCombo.addItem(InverseMultiquadricFunction.class.getSimpleName());
		this.typeCombo.addItem(MexicanHatFunction.class.getSimpleName());
		
		this.typeCombo.addActionListener(this);
	}
	

    public JFreeChart createChart(XYDataset dataset) {
    	
        JFreeChart chart = ChartFactory.createXYLineChart(
            null,
            "input (x)",
            "output (y)",
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

        return chart;
    }
    
    public XYDataset createDataset() {
    	String title = rbf.getClass().getSimpleName();
        XYSeriesCollection dataset = new XYSeriesCollection();

        Function2D n1 = new RBFFunction2D(this.rbf);// //new NormalDistributionFunction2D(0.0, 1.0);
        XYSeries s1 = DatasetUtilities.sampleFunction2DToSeries(n1, -5.1, 5.1,
                121, title);
        dataset.addSeries(s1);

        return dataset;
    }

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.buttonClose ) {
			this.dispose();
		} else if( e.getSource()==this.typeCombo ) {
			int index = this.typeCombo.getSelectedIndex();

			double[] center = { 0.0 };			
						
			switch(index) {
				case 0:
					this.rbf = new GaussianFunction(1.0,center,1.0); 
					break;
				case 1:
					this.rbf = new MultiquadricFunction(1.0,center,1.0);
					break;
				case 2:
					this.rbf = new InverseMultiquadricFunction(1.0,center,1.0);
					break;
				case 3:
					this.rbf = new MexicanHatFunction(1.0,center,1.0);
					break;					
			}
			
			XYDataset dataset = this.createDataset();
			JFreeChart chart = this.createChart(dataset);
			this.chartPanel.setChart(chart);
		}
		
	}
	
	@Override
	public String getName() {
		return "RBF";
	}
	
}
