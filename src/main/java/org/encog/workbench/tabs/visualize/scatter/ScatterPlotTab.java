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
package org.encog.workbench.tabs.visualize.scatter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.encog.app.analyst.EncogAnalyst;
import org.encog.workbench.tabs.EncogCommonTab;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;

public class ScatterPlotTab extends EncogCommonTab {

	private EncogAnalyst analyst;
	private ScatterFile file;
	private XYPlot samplePlot;
	public static Color COLORS[] = { Color.red, Color.green, Color.blue, Color.black, Color.cyan, Color.magenta, Color.orange, Color.pink, Color.white, Color.yellow, Color.lightGray, Color.darkGray };
	
	public ScatterPlotTab(EncogAnalyst analyst, String className, List<String> axisList) {
		super(null);
		this.analyst = analyst;
		this.file = new ScatterFile(this.analyst,className,axisList);
		
		if( axisList.size()<=2 ) {
			this.add(createPanel(0,1,true));
			return;
		} else {
			JPanel panel = new JPanel();
			int count = axisList.size();
			panel.setLayout(new GridLayout(count,count));
			
			for(int col=0;col<count;col++) {
				for(int row=0;row<count;row++) {
					if( col==row ) {
						panel.add(new ScatterLabelPane(axisList.get(row)));
					} else {
						panel.add(createPanel(row,col,false));			
					}											
				}				
			}
			
			this.setLayout(new BorderLayout());
			this.add(panel,BorderLayout.CENTER);
			LegendPanel legend = new LegendPanel(this.samplePlot);
			this.add(legend,BorderLayout.SOUTH);
		}
	}
	
	private JPanel createPanel(int xIndex, int yIndex, boolean legend) {
		
		 XYDataset dataset = new ScatterXY(file,xIndex,yIndex);
	        JFreeChart chart = ChartFactory.createScatterPlot(null,
	            null, null, dataset, PlotOrientation.VERTICAL, legend, true, false);

	        XYPlot plot = (XYPlot) chart.getPlot();

	        XYDotRenderer renderer = new XYDotRenderer();
	        renderer.setDotWidth(4);
	        renderer.setDotHeight(4);
	        
		if (this.file.isRegression()) {
			int per = 255 / 10;
			int r = 0;
			int b = 255;
			for(int i=0;i<file.getSeriesCount();i++) {
				renderer.setSeriesPaint(i, new Color(r,0,b));
				r+=per;
				b-=per;
			}
		} else {
			for (int i = 0; i < file.getSeriesCount(); i++) {
				renderer.setSeriesPaint(i, COLORS[i % COLORS.length]);
			}
		}
	        
	        plot.setRenderer(renderer);
	        plot.setDomainCrosshairVisible(true);
	        plot.setRangeCrosshairVisible(true);
	        

	        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
	        domainAxis.setAutoRangeIncludesZero(false);
	        plot.getRangeAxis().setInverted(false);
	        
	        ChartPanel result = new ChartPanel(chart); 	
	        result.setBorder(BorderFactory.createLineBorder(Color.black));
	        
	        // we need one to draw the legend off of
	        if( this.samplePlot==null)
	        	this.samplePlot = plot;
	        //chart.removeLegend();
	        return result;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Scatter Plot";
	}

}
