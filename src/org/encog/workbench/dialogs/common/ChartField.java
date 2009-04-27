package org.encog.workbench.dialogs.common;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

public class ChartField extends PropertiesField {

	private ChartGenerator generator;
	private int height;
	
	public ChartField(String name, ChartGenerator generator, int height) {
		super(name, "", true);
		this.generator = generator;
		this.height = height;
	}

	public int createField(JPanel panel, int x, int y,int width)
	{
		XYDataset dataset = generator.createDataset();
		JFreeChart chart = generator.createChart(dataset);
		this.setField( new ChartPanel(chart) );
		
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
	
	

}
