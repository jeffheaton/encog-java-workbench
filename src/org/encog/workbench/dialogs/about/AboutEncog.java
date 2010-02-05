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

package org.encog.workbench.dialogs.about;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The about box for Encog Workbench.
 * @author jheaton
 */
public class AboutEncog extends JDialog implements ActionListener {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	private AboutEncogPanel aboutPanel;
	private JScrollPane scroll;

	/**
	 * Construct the dialog box.
	 */
	public AboutEncog() {
		this.setSize(500, 300);
		setTitle("About Encog Workbench");
		final Container content = getContentPane();
		content.setLayout(new BorderLayout());
		this.aboutPanel = new AboutEncogPanel();
		this.scroll = new JScrollPane(this.aboutPanel);
		content.add(this.scroll, BorderLayout.CENTER);
		final JPanel buttonPanel = new JPanel();
		final JButton ok = new JButton("OK");
		buttonPanel.add(ok);
		content.add(buttonPanel, BorderLayout.SOUTH);
		ok.addActionListener(this);

	}

	/**
	 * Handle the OK button.
	 */
	public void actionPerformed(final ActionEvent e) {
		dispose();
	}

	/**
	 * Display the dialog box.
	 */
	public void process() {
		setVisible(true);
	}
}
