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

public class EditHopfieldLayer extends JDialog implements WindowListener,
		ActionListener {

	public enum Command {
		OK, CANCEL
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField ctrlName;
	private JTextField ctrlDescription;
	private JTextField ctrlNeuronCount;
	private JButton ctrlOK;
	private JButton ctrlCancel;
	private String resultName;
	private String resultDescription;
	private int resultNeuronCount;
	private Command command;

	public EditHopfieldLayer(final Frame owner) {
		super(owner, true);

		this.setLocation(200, 100);
		setTitle("Hopfield Layer");

		final Container content = getContentPane();
		content.setLayout(new GridLayout(4, 1, 10, 10));
		content.add(new JLabel("Name"));
		content.add(this.ctrlName = new JTextField());
		content.add(new JLabel("Description"));
		content.add(this.ctrlDescription = new JTextField());
		content.add(new JLabel("Neuron Count"));
		content.add(this.ctrlNeuronCount = new JTextField());
		content.add(this.ctrlOK = new JButton("OK"));
		content.add(this.ctrlCancel = new JButton("Cancel"));

		setModal(true);
		setResizable(false);

		pack();

		this.ctrlOK.addActionListener(this);
		this.ctrlCancel.addActionListener(this);
		addWindowListener(this);

	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.ctrlOK) {
			this.resultName = this.ctrlName.getText();
			this.resultDescription = this.ctrlDescription.getText();

			try {
				this.resultNeuronCount = Integer.parseInt(this.ctrlNeuronCount
						.getText());
			} catch (final NumberFormatException ex) {
				// just ignore it
			}

			this.command = Command.OK;
			dispose();
		} else if (e.getSource() == this.ctrlCancel) {
			this.command = Command.CANCEL;
			dispose();
		}

	}

	/**
	 * @return the command
	 */
	public Command getCommand() {
		return this.command;
	}

	/**
	 * @return the resultDescription
	 */
	public String getResultDescription() {
		return this.resultDescription;
	}

	/**
	 * @return the resultName
	 */
	public String getResultName() {
		return this.resultName;
	}

	/**
	 * @return the resultNeuronCount
	 */
	public int getResultNeuronCount() {
		return this.resultNeuronCount;
	}

	/**
	 * @param command
	 *            the command to set
	 */
	public void setCommand(final Command command) {
		this.command = command;
	}

	/**
	 * @param resultDescription
	 *            the resultDescription to set
	 */
	public void setResultDescription(final String resultDescription) {
		this.resultDescription = resultDescription;
	}

	/**
	 * @param resultName
	 *            the resultName to set
	 */
	public void setResultName(final String resultName) {
		this.resultName = resultName;
	}

	/**
	 * @param resultNeuronCount
	 *            the resultNeuronCount to set
	 */
	public void setResultNeuronCount(final int resultNeuronCount) {
		this.resultNeuronCount = resultNeuronCount;
	}

	public void windowActivated(final WindowEvent e) {
		this.ctrlName.setText(this.resultName);
		this.ctrlDescription.setText(this.resultDescription);
		this.ctrlNeuronCount.setText("" + this.resultNeuronCount);
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
