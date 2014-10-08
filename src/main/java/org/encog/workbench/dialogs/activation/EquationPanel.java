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
package org.encog.workbench.dialogs.activation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationSigmoid;
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

public class EquationPanel extends JPanel {
	
	private ChartPanel panel;
	
	public EquationPanel()
	{
		this.setLayout(new BorderLayout());
		setupEquation(new ActivationSigmoid(),true);
	}
	
	public void setupEquation(ActivationFunction activation, boolean normal)
	{
		JFreeChart chart = createChart(createDataset(activation, normal),activation,normal);
		
		if( this.panel==null)
		{
			this.panel = new EncogChartPanel(chart);
        	panel.setMouseWheelEnabled(true);
        	this.add(panel,BorderLayout.CENTER);
		}
		else
		{
			this.panel.setChart(chart);
		}
        
	}
	
    /**
     * Creates a dataset with sample values from the normal distribution
     * function.
     *
     * @return A dataset.
     */
    public static XYDataset createDataset(ActivationFunction activation, boolean normal) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        if( normal )
        {
        Function2D n1 = new ActivationFunction2D(activation);// //new NormalDistributionFunction2D(0.0, 1.0);
        XYSeries s1 = DatasetUtilities.sampleFunction2DToSeries(n1, -5.1, 5.1,
                121, "Activation Function");
        dataset.addSeries(s1);
        }
        else
        {
        	if( activation.hasDerivative())
        	{
        Function2D n2 = new DerivativeFunction2D(activation);
        XYSeries s2 = DatasetUtilities.sampleFunction2DToSeries(n2, -5.1, 5.1,
                121, "Derivative Function");
        dataset.addSeries(s2);
        	}
        }

        return dataset;
    }

    /**
     * Creates a line chart using the data from the supplied dataset.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    public static JFreeChart createChart(XYDataset dataset, ActivationFunction activation, boolean normal) {
    	
    	String title;
    	
    	if( normal )
    	{
    		title = activation.getClass().getSimpleName();
    	}
    	else
    	{
    		if( activation.hasDerivative() )
    		{
    			title = "Derv of " + activation.getClass().getSimpleName();
    		}
    		else
    		{
    			title = "NO Derv of " + activation.getClass().getSimpleName();
    		}
    	}
    	
    	
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "input (x)",
            "output (y)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        XYPlot plot = (XYPlot) chart.getPlot();
        
        if(normal)
        {
        
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
    }
        

        return chart;
    }
}
