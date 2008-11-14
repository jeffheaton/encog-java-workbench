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

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.Network;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;

public class EditEncogObjectProperties extends JDialog  implements WindowListener, ActionListener {
	
	private JTextField ctrlName;
	private JTextField ctrlDescription;
	private JButton ctrlOK;
	private JButton ctrlCancel;
	private EncogPersistedObject object;
	
	public EditEncogObjectProperties(Frame owner, EncogPersistedObject object) {
		super(owner, true);
		
		this.setLocation(200, 100);
		
		if( object instanceof Network )
		{
			setTitle("Network Properties");
		}
		else if (object instanceof NeuralDataSet )
		{
			setTitle("Neural Data Set Properties");
		}
		
		Container content = this.getContentPane();		
		content.setLayout(new GridLayout(3,1,10,10));
		content.add(new JLabel("Name"));
		content.add(this.ctrlName = new JTextField());
		content.add(new JLabel("Description"));
		content.add(this.ctrlDescription = new JTextField());
		content.add(ctrlOK = new JButton("OK"));
		content.add(ctrlCancel = new JButton("Cancel"));			
		
		this.setModal(true);
		this.setResizable(false);
		
		pack();
		
		this.ctrlOK.addActionListener(this);
		this.ctrlCancel.addActionListener(this);
		this.addWindowListener(this);
		
		this.object = object;
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()== this.ctrlOK )
		{
			if( EncogWorkBench.getInstance().getCurrentFile().find(ctrlName.getText().trim())!=null )
			{
				EncogWorkBench.displayError("Data Error", "That name is already in use, please choose another.");
				return;
			}
			if(ctrlName.getText().trim().length()<1 )
			{
				EncogWorkBench.displayError("Data Error", "You must provide a name.");
				return;
			}
			this.object.setName(ctrlName.getText());
			this.object.setDescription(ctrlDescription.getText());
			this.dispose();
		}
		else if( e.getSource()== this.ctrlCancel )
		{
			this.dispose();
		}
		
	}

	public void windowActivated(WindowEvent e) {
		this.ctrlName.setText(object.getName());
		this.ctrlDescription.setText(object.getDescription());		
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
	
	public void process()
	{
		this.setVisible(true);
	}
	
	
	
	
}
