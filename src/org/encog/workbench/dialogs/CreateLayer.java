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

import org.encog.workbench.dialogs.CreateObject.CreateObjectResult;
import org.encog.workbench.util.StringConst;

public class CreateLayer  extends JDialog implements ActionListener {
	
	private JList choices;
	private final static String[] OBJECTS = {"Feedforward Layer", "Hopfield Layer", "Self Organizing Map Layer", "Simple Layer" };
	public enum CreateLayerResult { FEEDFORWARD, HOPFIELD, SOM, SIMPLE };
	private CreateLayerResult selected;

	public CreateLayer(JFrame owner)
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
				case 0:this.selected = CreateLayerResult.FEEDFORWARD;break;
				case 1:this.selected = CreateLayerResult.HOPFIELD;break;
				case 2:this.selected = CreateLayerResult.SOM;break;
				case 3:this.selected = CreateLayerResult.SIMPLE;break;
			}
			this.dispose();
		}
		
	}

	/**
	 * @return the selected
	 */
	public CreateLayerResult getSelected() {
		return selected;
	}
	

	
}
