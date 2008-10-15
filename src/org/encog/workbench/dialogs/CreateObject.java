package org.encog.workbench.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import org.encog.workbench.util.StringConst;

public class CreateObject extends JDialog implements ActionListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2273478641755751005L;
	
	private JList choices;
	private final static String[] OBJECTS = {"Neural Network", "Training Data" };
	public enum CreateObjectResult { NEURAL_NETWORK, TRAINING_DATA };
	private CreateObjectResult selected;
	

	public CreateObject(JFrame owner)
	{
		super(owner,true);
		Container content = this.getContentPane();
		this.choices = new JList(OBJECTS);
		content.setLayout(new BorderLayout());
		content.add(this.choices,BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.add(createButton(StringConst.CREATE));
		panel.add(createButton(StringConst.CANCEL));
		content.add(panel,BorderLayout.SOUTH);
		this.setTitle("Create Object");
		this.choices.setSelectedIndex(0);
	}

	public JButton createButton(String name)
	{
		JButton result = new JButton(name);
		result.addActionListener(this);
		return result;
	}

	public void actionPerformed(ActionEvent event) {
		if( event.getActionCommand().equals(StringConst.CANCEL) )
			this.dispose();
		else if( event.getActionCommand().equals(StringConst.CREATE) )
		{
			switch( this.choices.getSelectedIndex() )
			{
				case 0:this.selected = CreateObjectResult.NEURAL_NETWORK;break;
				case 1:this.selected = CreateObjectResult.TRAINING_DATA;break;
			}
			this.dispose();
		}
		
	}

	/**
	 * @return the selected
	 */
	public CreateObjectResult getSelected() {
		return selected;
	}
	
	
}
