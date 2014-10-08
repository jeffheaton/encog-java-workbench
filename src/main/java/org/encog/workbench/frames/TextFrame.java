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
package org.encog.workbench.frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.workbench.util.EncogFonts;

public class TextFrame extends EncogCommonFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextArea text;
	private final JScrollPane scroll;

	public TextFrame(final String title, boolean readOnly, boolean wrap) {
		super();
		setTitle(title);
		this.setSize(640, 480);
		this.text = new JTextArea();
		this.text.setFont(EncogFonts.getInstance().getCodeFont());
		this.text.setEditable(!readOnly);
		if( wrap ) {
			this.text.setLineWrap(true);
			this.text.setWrapStyleWord(true);
		}
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
	
	public String getText()
	{
		return this.text.getText();
	}

	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
