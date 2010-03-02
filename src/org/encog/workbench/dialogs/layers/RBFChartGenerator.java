/*
 * Encog(tm) Workbench v2.4
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

package org.encog.workbench.dialogs.layers;

import java.awt.BasicStroke;
import java.awt.Color;

import org.encog.mathutil.Convert;
import org.encog.mathutil.rbf.GaussianFunction;
import org.encog.mathutil.rbf.RadialBasisFunction;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.workbench.dialogs.common.ChartGenerator;
import org.encog.workbench.dialogs.common.TableFieldModel;
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

	private EditRadialLayer owner;
	private XYSeriesCollection dataset;
	
	public RBFChartGenerator(EditRadialLayer owner)
	{
		this.owner = owner;
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
	        
	        TableFieldModel mode = this.owner.getRadial().getModel();
	        
	        for(int i=0;i<mode.getRowCount();i++)
	        {
	        	r.setDrawSeriesLineAsPath(true);
		        r.setSeriesStroke(i, new BasicStroke(1.5f));
	        }
	        
	        
	        


	        return chart;
	}

	public XYDataset createDataset() {
		
		if( this.dataset==null )
        this.dataset = new XYSeriesCollection();
        
        dataset.removeAllSeries();

        TableFieldModel mode = this.owner.getRadial().getModel();
        
        for(int i=0;i<mode.getRowCount();i++)
        {
        	double center = Convert.string2double((String)mode.getValueAt(i, 1));
        	double peak = Convert.string2double((String)mode.getValueAt(i, 2));
        	double width = Convert.string2double((String)mode.getValueAt(i, 3));
        	
        	RadialBasisFunction rbf = new GaussianFunction(center,peak,width);
        	
        	Function2D n1 = new RadialBasisFunction2D(rbf);
            XYSeries s1 = DatasetUtilities.sampleFunction2DToSeries(n1, -5.1, 5.1,
                    121, "N1");
            dataset.addSeries(s1);
        }
        
        


        return dataset;
	}

}
