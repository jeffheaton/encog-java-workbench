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
package org.encog.workbench.frames.document;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.Encog;
import org.encog.util.logging.EncogLogging;
import org.encog.workbench.WorkbenchLogging;
import org.encog.workbench.util.EncogFonts;

public class EncogOutputPanel extends JPanel implements ActionListener {
	
	public static final String[] LEVELS = {"OFF","CRITICAL","ERROR","INFO","DEBUG"};
	
	private final JTextArea text;
	private final JScrollPane scroll;
	private final JButton buttonClear;
	private final JComboBox comboLogLevel;
	
	public EncogOutputPanel() {
		this.text = new JTextArea();
		this.text.setFont(EncogFonts.getInstance().getCodeFont());
		this.text.setEditable(true);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.text);
		add(this.scroll, BorderLayout.CENTER);
		this.text.setEditable(false);		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		add(buttonPanel,BorderLayout.NORTH);
		
		this.comboLogLevel = new JComboBox(LEVELS);
		buttonPanel.add(new JLabel("Log Level:"));
		buttonPanel.add(this.comboLogLevel);
		buttonPanel.add(buttonClear = new JButton("Clear"));
		
		this.buttonClear.addActionListener(this);
		this.comboLogLevel.addActionListener(this);
	}
	
	public void output(String output)
	{
		this.text.append(output);
		
		int l = this.text.getText().length();		
		this.text.setSelectionStart(l);
		this.text.setSelectionEnd(l);
	}
	
	public void outputLine(String output)
	{
		output(output+"\n");
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.buttonClear ) {
			this.text.setText("");
		}
		else if( e.getSource()==this.comboLogLevel ) {
			String level = (String)this.comboLogLevel.getSelectedItem();
			outputLine("Logging level set to: " + level);
			
			int l = EncogLogging.LEVEL_DISABLE;
			
			if( level.equals("OFF") )
			{
				l = EncogLogging.LEVEL_DISABLE;
			}
			else if( level.equals("CRITICAL") )
			{
				l = EncogLogging.LEVEL_CRITICAL;
			}
			else if( level.equals("ERROR") )
			{
				l = EncogLogging.LEVEL_ERROR;
			}
			else if( level.equals("INFO") )
			{
				l = EncogLogging.LEVEL_INFO;	
			}
			else if( level.equals("DEBUG") )
			{
				l = EncogLogging.LEVEL_DEBUG;	
			}
			
			((WorkbenchLogging)Encog.getInstance().getLoggingPlugin()).setLogLevel(l);			
		} 
	}
	
	

	public JComboBox getComboLogLevel() {
		return comboLogLevel;
	}

	public void clear() {
		this.text.setText("");
	}
}
