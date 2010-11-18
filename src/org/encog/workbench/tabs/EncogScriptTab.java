/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.tabs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.neural.data.TextData;
import org.encog.persist.EncogPersistedObject;
import org.encog.script.EncogScript;
import org.encog.script.EncogScriptError;
import org.encog.script.EncogScriptRuntimeError;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
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
			try {
			EncogScript script = (EncogScript)getEncogObject();
			save();
			EncogWorkBench.getInstance().getExecute().execute(script);
			}
			catch(WorkBenchError ex)
			{
				EncogWorkBench.displayError("Error", ex.getMessage());
			}
			catch(Throwable t)
			{
				EncogWorkBench.displayError("Error",t);
			}
		}
		
	}

}
