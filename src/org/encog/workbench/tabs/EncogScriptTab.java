/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.tabs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.neural.data.TextData;
import org.encog.persist.EncogPersistedObject;
import org.encog.script.EncogScript;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.EncogFonts;
import org.encog.workbench.util.WorkbenchConsoleInputOutput;

public class EncogScriptTab extends EncogCommonTab implements ActionListener {

	private final JTextArea text;
	private final JScrollPane scroll;
	private final JButton buttonExecute;
	
	public EncogScriptTab(EncogPersistedObject encogObject) {
		super(encogObject);
		
		this.text = new JTextArea();
		this.text.setFont(EncogFonts.getInstance().getCodeFont());
		this.text.setEditable(true);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.text);
		add(this.scroll, BorderLayout.CENTER);
		this.text.setText(((EncogScript)getEncogObject()).getSource());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.buttonExecute = new JButton("Execute");
		buttonPanel.add(this.buttonExecute);
		this.buttonExecute.addActionListener(this);
		add(buttonPanel,BorderLayout.NORTH);
		
	}
	


	public void setText(final String t) {
		this.text.setText(t);
	}
	
	public String getText()
	{
		return this.text.getText();
	}
	
	public boolean close()
	{
		save();
		return true;
	}

	public void save()
	{
		
		((EncogScript)getEncogObject()).setSource(this.getText());
	}


	public boolean isTextSelected() {
		return this.text.getSelectionEnd()>this.text.getSelectionStart();
	}



	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.buttonExecute) {
			try
			{
			}
			catch(Throwable t)
			{
				EncogWorkBench.displayError("Error",t);
			}
		}
		
	}

}
