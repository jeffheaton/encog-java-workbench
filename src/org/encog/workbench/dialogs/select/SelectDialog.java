package org.encog.workbench.dialogs.select;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import org.encog.workbench.dialogs.CreateObject.CreateObjectResult;
import org.encog.workbench.util.StringConst;

public class SelectDialog  extends JDialog implements ActionListener {
	
	private JList choices;
	private List<SelectItem> choiceList;
	private SelectItem selected;

	public SelectDialog(JFrame owner, List<SelectItem> choiceList)
	{
		super(owner,true);
		Container content = this.getContentPane();
		this.choiceList = choiceList;
		
		String[] list = new String[choiceList.size()];
		for(int i=0;i<choiceList.size();i++)
		{
			list[i] = this.choiceList.get(i).getText();
		}
		
		
		this.choices = new JList(list);
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
			this.selected = this.choiceList.get(this.choices.getSelectedIndex());
			this.dispose();
		}
		
	}

	/**
	 * @return the selected
	 */
	public SelectItem getSelected() {
		return selected;
	}
	

	
}
