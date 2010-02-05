/*
 * Encog(tm) Workbench v2.3
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
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
	
	public BuildingListField(String name, String label) {
		super(name, label, true);
		// TODO Auto-generated constructor stub
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
		this.getField().setSize(width,100);
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
