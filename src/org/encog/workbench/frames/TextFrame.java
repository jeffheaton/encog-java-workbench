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
package org.encog.workbench.frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.workbench.frames.manager.EncogCommonFrame;

public class TextFrame extends EncogCommonFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextArea text;
	private final JScrollPane scroll;

	public TextFrame(final String title, boolean readOnly) {
		setTitle(title);
		this.setSize(640, 480);
		this.text = new JTextArea();
		this.text.setFont(new Font("monospaced", 0, 12));
		this.text.setEditable(!readOnly);
		this.scroll = new JScrollPane(this.text);
		getContentPane().add(this.scroll);
	}

	public void actionPerformed(final ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void setText(final String t) {
		this.text.setText(t);
	}

	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
