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
package org.encog.workbench.dialogs.common;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

public class ChartField extends PropertiesField implements ActionListener {

	private ChartGenerator generator;
	private int height;
	private JButton refreshButton;
	private ChartListener listener;
	private JPanel chartHolder;
	
	public ChartField(String name, ChartGenerator generator, int height) {
		super(name, "", true);
		this.generator = generator;
		this.height = height;
	}

	public int createField(JPanel panel, int x, int y,int width)
	{
		this.chartHolder = new JPanel();
		
		XYDataset dataset = generator.createDataset();
		JFreeChart chart = generator.createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartHolder.setLayout(new BorderLayout());
		chartHolder.add(chartPanel,BorderLayout.CENTER);
		chartHolder.add(this.refreshButton = new JButton("Refresh Chart"),BorderLayout.SOUTH);
		
		this.refreshButton.addActionListener(this);
		this.setField( chartHolder );
		
		this.getField().setLocation(5, y);
		this.getField().setSize(this.getOwner().getWidth()-5, height);

		JLabel label = createLabel();
		label.setVisible(false);
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.getField());
		
		return y+this.getField().getHeight();
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}
	
	

	public ChartListener getListener() {
		return listener;
	}

	public void setListener(ChartListener listener) {
		this.listener = listener;
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.refreshButton )
		{
			if( listener!=null )
			{
				listener.refresh(this);
			}
		}
		
	}

	public void refresh() {
		generator.createDataset();
		chartHolder.repaint();
	}
	
	

}
