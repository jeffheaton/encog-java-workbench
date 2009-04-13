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

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.Network;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;

public class EditEncogObjectProperties extends JDialog implements
		WindowListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField ctrlName;
	private JTextField ctrlDescription;
	private JButton ctrlOK;
	private JButton ctrlCancel;
	private final DirectoryEntry entry;

	public EditEncogObjectProperties(final Frame owner,
			final DirectoryEntry entry) {
		super(owner, true);
		
		

		this.setLocation(200, 100);

		if ( EncogPersistedCollection.TYPE_BASIC_NET.equals(entry.getType()) ) {
			setTitle("Network Properties");
		} else if ( EncogPersistedCollection.TYPE_TRAINING.equals(entry.getType()) ) {
			setTitle("Neural Data Set Properties");
		}
		else
			setTitle("Edit: " + entry.getType());

		final Container content = getContentPane();
		content.setLayout(new GridLayout(3, 1, 10, 10));
		content.add(new JLabel("Name"));
		content.add(this.ctrlName = new JTextField());
		content.add(new JLabel("Description"));
		content.add(this.ctrlDescription = new JTextField());
		content.add(this.ctrlOK = new JButton("OK"));
		content.add(this.ctrlCancel = new JButton("Cancel"));

		setModal(true);
		setResizable(false);

		pack();

		this.ctrlOK.addActionListener(this);
		this.ctrlCancel.addActionListener(this);
		addWindowListener(this);

		this.entry = entry;

	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.ctrlOK) {
			
			String name = this.ctrlName.getText().trim();
			String desc = this.ctrlDescription.getText().trim();
			
			if( !this.entry.getName().equals(name))
			{
			
			if (EncogWorkBench.getInstance().getCurrentFile().find(name) != null) {
				EncogWorkBench.displayError("Data Error",
						"That name is already in use, please choose another.");
				return;
			}
			}
			if (this.ctrlName.getText().trim().length() < 1) {
				EncogWorkBench.displayError("Data Error",
						"You must provide a name.");
				return;
			}
			EncogWorkBench.getInstance().getCurrentFile().updateProperties(this.entry.getName(),name,desc);
			//this.object.setName(this.ctrlName.getText());
			//this.object.setDescription(this.ctrlDescription.getText());
			dispose();
		} else if (e.getSource() == this.ctrlCancel) {
			dispose();
		}

	}

	public void process() {
		setVisible(true);
	}

	public void windowActivated(final WindowEvent e) {
		this.ctrlName.setText(this.entry.getName());
		this.ctrlDescription.setText(this.entry.getDescription());
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
