/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.tabs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.neural.data.PropertyData;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.models.PropertyDataModel;
import org.encog.workbench.EncogWorkBench;

public class PropertyDataTab extends EncogCommonTab implements ActionListener {

	private JPanel panelButtons;
	private JScrollPane scroll;
	private JTable table;
	private JButton btnAdd;
	private PropertyDataModel model;
	private JButton btnDel;
	
	public PropertyDataTab(JTabbedPane owner, PropertyData data)
	{
		super(data);
		
		setLayout(new BorderLayout());
		this.panelButtons = new JPanel();
		this.panelButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(this.panelButtons,BorderLayout.NORTH);
		this.model = new PropertyDataModel(data);
		this.table = new JTable(this.model);
		this.scroll = new JScrollPane(this.table);
		add(this.scroll,BorderLayout.CENTER);
		this.btnAdd = new JButton("Add Row");
		this.btnDel = new JButton("Delete Row");
		this.panelButtons.add(this.btnAdd);
		this.btnAdd.addActionListener(this);
		this.panelButtons.add(this.btnDel);
		this.btnDel.addActionListener(this);
		
		
	}
	
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean validate(String str)
	{
		// check for invalid name
		for(int i=0;i<str.length();i++)
		{
			char ch=str.charAt(i);
			if( !Character.isLetterOrDigit(ch))
			{
				EncogWorkBench.displayError("Error", "Name contains an invalid character.");
				return false;
			}
		}
		
		// check for dup
		if( ((PropertyData)this.getEncogObject()).isDefined(str))
		{
			EncogWorkBench.displayError("Error", "Name already defined.");
			return false;
		}
		
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.btnAdd)
		{
			String input = EncogWorkBench.displayInput("Enter the property to add? (no spaces)");
			if(input!=null)
			{
				if( validate(input))
					this.model.addProperty(input);
			}
		}
		else if( e.getSource()==this.btnDel)
		{
			int row = this.table.getSelectedRow();
			
			if( row == -1 )
			{
				EncogWorkBench.displayError("Error", "Please select the row that you wish to delete.");
				return;
			}
			
			if( EncogWorkBench.askQuestion("Are you sure", "Are you sure you want to delete this property row?"))
			{
				this.model.deleteRow(row);
			}
		}
		
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
