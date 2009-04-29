package org.encog.workbench.dialogs.common;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
