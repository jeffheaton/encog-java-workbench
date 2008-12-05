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
package org.encog.workbench.dialogs.common;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.encog.workbench.EncogWorkBench;

abstract public class EncogCommonDialog extends JDialog implements
		ActionListener {

	private JButton ctrlOK;
	private JButton ctrlCancel;
	private final JPanel bodyPanel;
	private final JPanel buttonPanel;
	private boolean shouldProcess;

	public EncogCommonDialog(final Frame owner) {
		super(owner, true);

		final Container content = getContentPane();
		content.setLayout(new BorderLayout());

		this.bodyPanel = new JPanel();
		this.buttonPanel = new JPanel();

		this.buttonPanel.add(this.ctrlOK = new JButton("OK"));
		this.buttonPanel.add(this.ctrlCancel = new JButton("Cancel"));

		this.ctrlCancel.addActionListener(this);
		this.ctrlOK.addActionListener(this);

		content.add(this.bodyPanel, BorderLayout.CENTER);
		content.add(this.buttonPanel, BorderLayout.SOUTH);
	}

	public void actionPerformed(final ActionEvent e) {

		if (e.getSource() == this.ctrlOK) {
			if (collect()) {
				dispose();
				this.shouldProcess = true;
			}
		} else if (e.getSource() == this.ctrlCancel) {
			dispose();
			this.shouldProcess = false;
		}
	}

	public boolean collect() {
		try {
			collectFields();
			return true;
		} catch (final ValidationException e) {
			EncogWorkBench.displayError("Validation Error", e.getMessage());
			return false;
		}
	}

	abstract public void collectFields() throws ValidationException;

	/**
	 * @return the bodyPanel
	 */
	public JPanel getBodyPanel() {
		return this.bodyPanel;
	}

	/**
	 * @return the buttonPanel
	 */
	public JPanel getButtonPanel() {
		return this.buttonPanel;
	}

	public boolean process() {
		setVisible(true);
		return this.shouldProcess;
	}

	abstract public void setFields();

	public double validateFieldNumeric(final String name, final JTextField field)
			throws ValidationException {
		try {
			final double d = Double.parseDouble(field.getText());
			return d;
		} catch (final NumberFormatException e) {
			throw new ValidationException("Must enter a valid number for: "
					+ name);
		}
	}

	public double validateFieldNumeric(final String name,
			final JTextField field, final double low, final double high)
			throws ValidationException {
		final double d = validateFieldNumeric(name, field);
		if (d < low) {
			throw new ValidationException("Must enter a value above " + low
					+ " for: " + name);
		}
		if (d > high) {
			throw new ValidationException("Must enter a value below " + low
					+ " for: " + name);
		}

		return d;
	}

	public String validateFieldString(final String name, final JComboBox field,
			final boolean required) throws ValidationException {
		String result = (String) field.getSelectedItem();
		if (result == null && required) {
			throw new ValidationException("The " + name + " field is required.");
		}

		if (result != null) {
			result = result.trim();
		}

		return result;
	}

	public String validateFieldString(final String name,
			final JTextField field, final boolean required)
			throws ValidationException {
		final String result = field.getText().trim();
		if (result.length() < 1 && required) {
			throw new ValidationException("The " + name + " field is required.");
		}
		return result;
	}

}
