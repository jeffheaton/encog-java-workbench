/*
 * Encog(tm) Workbench v2.4
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

package org.encog.workbench.dialogs;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.encog.workbench.EncogWorkBench;

public class CreateDataSet extends JDialog implements WindowListener,
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField ctrlName;
	private JTextField ctrlDescription;
	private JTextField ctrlInput;
	private JTextField ctrlIdeal;
	private JButton ctrlOK;
	private JButton ctrlCancel;
	private String name;
	private String description;
	private int inputSize;
	private int idealSize;
	private boolean cancel;

	public CreateDataSet(final Frame owner) {
		super(owner, true);

		this.setLocation(200, 100);

		final Container content = getContentPane();
		content.setLayout(new GridLayout(5, 1, 10, 10));
		content.add(new JLabel("Name"));
		content.add(this.ctrlName = new JTextField());
		content.add(new JLabel("Description"));
		content.add(this.ctrlDescription = new JTextField());

		content.add(new JLabel("Input Set Size"));
		content.add(this.ctrlInput = new JTextField());

		content.add(new JLabel("Ideal Set Size"));
		content.add(this.ctrlIdeal = new JTextField());

		content.add(this.ctrlOK = new JButton("OK"));
		content.add(this.ctrlCancel = new JButton("Cancel"));

		setModal(true);
		setResizable(false);

		pack();

		this.ctrlOK.addActionListener(this);
		this.ctrlCancel.addActionListener(this);
		addWindowListener(this);
		setTitle("Import Training Set");

	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.ctrlOK) {
			try {
				this.cancel = false;
				this.name = this.ctrlName.getText();
				this.description = this.ctrlDescription.getText();
				this.inputSize = Integer.parseInt(this.ctrlInput.getText());
				this.idealSize = Integer.parseInt(this.ctrlIdeal.getText());

				if (this.name.length() < 1) {
					EncogWorkBench.displayError("Data Error",
							"Name field is requied.");
					return;
				}
				if (this.inputSize < 1) {
					EncogWorkBench.displayError("Data Error",
							"Input size must be at least 1.");
					return;
				}
				if (EncogWorkBench.getInstance().getCurrentFile().find(
						this.ctrlName.getText()) != null) {
					EncogWorkBench
							.displayError("Data Error",
									"That name is already in use, please choose another.");
					return;
				}
				dispose();
			} catch (final NumberFormatException e2) {
				EncogWorkBench
						.displayError(
								"Data Error",
								"Must enter valid numbers for input and ideal.\nEnter zero for ideal if unsupervised training.");
			}
		} else if (e.getSource() == this.ctrlCancel) {
			this.cancel = true;
			dispose();
		}

	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the idealSize
	 */
	public int getIdealSize() {
		return this.idealSize;
	}

	/**
	 * @return the inputSize
	 */
	public int getInputSize() {
		return this.inputSize;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	public boolean process() {
		setVisible(true);
		return !this.cancel;
	}

	public void windowActivated(final WindowEvent e) {
	}

	public void windowClosed(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(final WindowEvent e) {

	}
}
