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
package org.encog.workbench.dialogs.select;

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
