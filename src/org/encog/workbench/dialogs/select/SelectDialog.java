
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

import org.encog.workbench.util.StringConst;

/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
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
	
	public SelectItem process()
	{
		this.selected = null;
		pack();
		setVisible(true);
		return this.getSelected();
	}
	

	
}
