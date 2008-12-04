package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.models.NetworkQueryModel;

public class NetworkQueryFrame extends EncogCommonFrame {

	private JTable inputTable;
	private JTable outputTable;
	private int inputCount;
	private int outputCount;
	private JButton calculateButton;
	
	public NetworkQueryFrame(BasicNetwork data)  {
		this.setEncogObject(data);
		addWindowListener(this);
	}

	public void windowOpened(WindowEvent e) {
		//
		this.inputCount = getData().getInputLayer().getNeuronCount();
		this.outputCount = getData().getOutputLayer().getNeuronCount();
		
		// create the graphic objects
		this.setTitle("Query Network: " + this.getData().getName());
		this.setSize(640,480);
		Container contents = this.getContentPane();
		contents.setLayout(new BorderLayout());
		JPanel body = new JPanel();
		body.setLayout(new GridLayout(1,2,10,10));
		contents.add(body,BorderLayout.CENTER);
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		body.add(left);
		body.add(right);
		left.setLayout(new BorderLayout());
		right.setLayout(new BorderLayout());
		left.add(new JLabel("Input"),BorderLayout.NORTH);
		right.add(new JLabel("Output"),BorderLayout.NORTH);
		left.add(this.inputTable = new JTable(new NetworkQueryModel(this.inputCount,2)),BorderLayout.CENTER);
		right.add(this.outputTable = new JTable(new NetworkQueryModel(this.outputCount,2)),BorderLayout.CENTER);
		contents.add(this.calculateButton = new JButton("Calculate"),BorderLayout.SOUTH);
		this.outputTable.setEnabled(false);
		
		for(int i=1;i<=this.inputCount;i++)
		{
			this.inputTable.setValueAt("Input " + i + ":", i-1, 0);
			this.inputTable.setValueAt("0.0", i-1, 1);
		}
		
		for(int i=1;i<=this.outputCount;i++)
		{
			this.outputTable.setValueAt("Output " + i + ":", i-1, 0);
			this.outputTable.setValueAt("0.0", i-1, 1);
		}
		
		this.calculateButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.calculateButton )
		{
			BasicNeuralData input = new BasicNeuralData(this.inputCount);
			for(int i=0;i<this.inputCount;i++)
			{
				double value = 0;
				String str = (String)this.inputTable.getValueAt(i, 1);
				try
				{
					value = Double.parseDouble(str);
				}
				catch(NumberFormatException e2)
				{
					EncogWorkBench.displayError("Data Error", "Please enter a valid input number.");
				}
				input.setData(i,value);
			}
			
			NeuralData output = this.getData().compute(input);
			
			for(int i=0;i<this.outputCount;i++)
			{
				this.outputTable.setValueAt(output.getData(i), i, 1);
			}
		}
		
		
		
		
		
	}
	
	public BasicNetwork getData()
	{
		return (BasicNetwork)this.getEncogObject();
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
