/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.dialogs.select;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

/**
 * Common dialog box used to select from a choice list.  A collection
 * of SelectItems are provided that the user should select from.
 * @author jheaton
 *
 */
public class SelectDialog extends EncogCommonDialog implements ListSelectionListener {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 217379094416842461L;
	
	
	/**
	 * The item that the user chose.
	 */
	private SelectItem selected;
	
	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private List<SelectItem> choiceList;


	/**
	 * Construct the selection dialog box.
	 * @param owner The owner of the dialog box.
	 * @param choiceList The choices.
	 */
	public SelectDialog(final JFrame owner, final List<SelectItem> choiceList) {
		super(owner);
		final Container content = this.getBodyPanel();
		
		
		this.choiceList = choiceList;
		this.setSize(500, 250);
		this.setLocation(50, 100);
		setTitle("Select");
		content.setLayout(new GridLayout(1, 2));

		content.add(this.scroll1);
		content.add(this.scroll2);
		
		for(SelectItem item : this.choiceList)
		{
			this.model.addElement(item.getText());
		}

		this.list.addListSelectionListener(this);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.text.setEditable(false);
		scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}


	/**
	 * @return the selected
	 */
	public SelectItem getSelected() {
		return this.selected;
	}

	/**
	 * Collect the data when the user clicks OK.
	 */
	@Override
	public void collectFields() throws ValidationException {
		int index = this.list.getSelectedIndex();
		if( index>=0 )
			this.selected = this.choiceList.get(index);
		else
			this.selected = null;
		
	}

	/**
	 * Set the initial state of the fields.  Simply select the first
	 * item in the choice list.
	 */
	@Override
	public void setFields() {
		this.list.setSelectedIndex(0);
		
	}
	
	public void valueChanged(ListSelectionEvent e) {
		int index = list.getSelectedIndex();
		
		if( index!=-1 )
		{
			String desc = this.choiceList.get(index).getDescription();
			this.text.setText(desc);
			this.text.setSelectionStart(0);
			this.text.setSelectionEnd(0);
		}


	}


}
