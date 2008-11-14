package org.encog.workbench.dialogs;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.workbench.EncogWorkBench;

public class CreateDataSet extends JDialog  implements WindowListener, ActionListener {
	
	private JTextField ctrlName;
	private JTextField ctrlDescription;
	private JTextField ctrlInput;
	private JTextField ctrlIdeal;
	private JButton ctrlOK;
	private JButton ctrlCancel;
	private String name;
	private String description;
	private int inputSize;
	private int idealSize;
	private boolean cancel;
	
	public CreateDataSet(Frame owner) {
		super(owner, true);
		
		this.setLocation(200, 100);
	
		
		Container content = this.getContentPane();		
		content.setLayout(new GridLayout(5,1,10,10));
		content.add(new JLabel("Name"));
		content.add(this.ctrlName = new JTextField());
		content.add(new JLabel("Description"));
		content.add(this.ctrlDescription = new JTextField());
		
		content.add(new JLabel("Input Set Size"));
		content.add(this.ctrlInput = new JTextField());
		
		content.add(new JLabel("Ideal Set Size"));
		content.add(this.ctrlIdeal = new JTextField());
		
		
		content.add(ctrlOK = new JButton("OK"));
		content.add(ctrlCancel = new JButton("Cancel"));			
		
		this.setModal(true);
		this.setResizable(false);
		
		pack();
		
		this.ctrlOK.addActionListener(this);
		this.ctrlCancel.addActionListener(this);
		this.addWindowListener(this);
		this.setTitle("Import Training Set");
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()== this.ctrlOK )
		{
			try
			{
				this.cancel = false;
				this.name = ctrlName.getText();
				this.description = ctrlDescription.getText();
				this.inputSize = Integer.parseInt(this.ctrlInput.getText());
				this.idealSize = Integer.parseInt(this.ctrlIdeal.getText());
								
				if( this.name.length()<1  )
				{
					EncogWorkBench.displayError("Data Error", "Name field is requied.");
					return;
				}
				if( this.inputSize<1  )
				{
					EncogWorkBench.displayError("Data Error", "Input size must be at least 1.");
					return;
				}
				if( EncogWorkBench.getInstance().getCurrentFile().find(ctrlName.getText())!=null )
				{
					EncogWorkBench.displayError("Data Error", "That name is already in use, please choose another.");
					return;
				}
				this.dispose();
			}
			catch(NumberFormatException e2)
			{
				EncogWorkBench.displayError("Data Error", "Must enter valid numbers for input and ideal.\nEnter zero for ideal if unsupervised training.");
			}
		}
		else if( e.getSource()== this.ctrlCancel )
		{
			this.cancel = true;
			this.dispose();
		}
		
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {

		
	}
	
	public boolean process()
	{
		this.setVisible(true);
		return !this.cancel;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the inputSize
	 */
	public int getInputSize() {
		return inputSize;
	}

	/**
	 * @return the idealSize
	 */
	public int getIdealSize() {
		return idealSize;
	}	
}
