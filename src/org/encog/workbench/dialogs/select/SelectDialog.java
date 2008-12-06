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
package org.encog.workbench.dialogs.select;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.util.StringConst;

/**
 * Common dialog box used to select from a choice list.
 * @author jheaton
 *
 */
public class SelectDialog extends EncogCommonDialog  {

	private final JList choices;
	private final List<SelectItem> choiceList;
	private SelectItem selected;

	public SelectDialog(final JFrame owner, final List<SelectItem> choiceList) {
		super(owner);
		final Container content = getContentPane();
		this.choiceList = choiceList;

		final String[] list = new String[choiceList.size()];
		for (int i = 0; i < choiceList.size(); i++) {
			list[i] = this.choiceList.get(i).getText();
		}

		this.choices = new JList(list);
		content.setLayout(new BorderLayout());
		content.add(this.choices, BorderLayout.CENTER);
		final JPanel panel = new JPanel();
		panel.add(createButton(StringConst.CREATE));
		panel.add(createButton(StringConst.CANCEL));
		content.add(panel, BorderLayout.SOUTH);
		setTitle("Create Object");
		this.choices.setSelectedIndex(0);
	}

	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equals(StringConst.CANCEL)) {
			dispose();
		} else if (event.getActionCommand().equals(StringConst.CREATE)) {
			this.selected = this.choiceList
					.get(this.choices.getSelectedIndex());
			dispose();
		}

	}

	public JButton createButton(final String name) {
		final JButton result = new JButton(name);
		result.addActionListener(this);
		return result;
	}

	/**
	 * @return the selected
	 */
	public SelectItem getSelected() {
		return this.selected;
	}

	@Override
	public void collectFields() throws ValidationException {
		this.selected = (SelectItem) this.choices.getSelectedValue();
		
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		
	}

}
