/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
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

package org.encog.workbench.dialogs.select;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

/**
 * Common dialog box used to select from a choice list.  A collection
 * of SelectItems are provided that the user should select from.
 * @author jheaton
 *
 */
public class SelectDialog extends EncogCommonDialog  {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 217379094416842461L;
	
	/**
	 * The choice combo box that the user selects from.
	 */
	private final JList choices;
	
	/**
	 * The SelectItems that form the list.
	 */
	private final List<SelectItem> choiceList;
	
	/**
	 * The item that the user chose.
	 */
	private SelectItem selected;

	/**
	 * Construct the selection dialog box.
	 * @param owner The owner of the dialog box.
	 * @param choiceList The choices.
	 */
	public SelectDialog(final JFrame owner, final List<SelectItem> choiceList) {
		super(owner);
		final Container content = this.getBodyPanel();
		this.choiceList = choiceList;

		final String[] list = new String[choiceList.size()];
		for (int i = 0; i < choiceList.size(); i++) {
			list[i] = this.choiceList.get(i).getText();
		}

		this.choices = new JList(list);
		content.setLayout(new BorderLayout());
		content.add(this.choices, BorderLayout.CENTER);
		setTitle("Create Object");
		this.choices.setSelectedIndex(0);
		pack();
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
		int index = this.choices.getSelectedIndex();
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
		this.choices.setSelectedIndex(0);
		
	}

}
