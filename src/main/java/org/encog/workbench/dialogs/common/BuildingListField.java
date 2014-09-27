/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import org.encog.workbench.EncogWorkBench;

public class BuildingListField extends PropertiesField implements ActionListener {

	private JList list;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnEdit; 
	private DefaultListModel model;
	private int height;
	
	public BuildingListField(String name, String label, int height) {
		super(name, label, true);
		this.height = height;
	}
	
	public BuildingListField(String name, String label) {
		this(name, label, 100);
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		// build the label
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		this.setField(new JPanel());
		this.getField().setLocation(x,y);
		this.getField().setSize(width,this.height);
		panel.add(createLabel());
		panel.add(this.getField());
		
		// build the panel
		this.model = new DefaultListModel();
		this.getField().setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.getField().setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(this.list = new JList(this.model));
		this.getField().add(scroll,BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		this.getField().add(buttons,BorderLayout.SOUTH);
		buttons.setLayout(new GridLayout(1,4));
		buttons.add(this.btnAdd = new JButton("Add"));
		buttons.add(this.btnDelete = new JButton("Remove"));
		buttons.add(this.btnEdit = new JButton("Edit"));
		this.btnAdd.addActionListener(this);
		this.btnEdit.addActionListener(this);
		this.btnDelete.addActionListener(this);
		y+=100;
		return y;
	}

	public void actionPerformed(ActionEvent e) {
		if( this.btnAdd == e.getSource())
		{
			((BuildingListListener)this.getOwner()).add(this, this.list.getSelectedIndex());
		}
		else if( this.btnDelete == e.getSource())
		{
			((BuildingListListener)this.getOwner()).del(this, this.list.getSelectedIndex());
		}
		else if( this.btnEdit == e.getSource())
		{
			((BuildingListListener)this.getOwner()).edit(this, this.list.getSelectedIndex());
		}
	}

	public DefaultListModel getModel() {
		return model;
	}
	
	

}
