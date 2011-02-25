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
package org.encog.workbench.tabs.analyst;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.app.analyst.AnalystError;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.util.file.FileUtil;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.tabs.files.BasicFileTab;
import org.encog.workbench.util.EncogFonts;

public class EncogAnalystTab extends BasicFileTab implements ActionListener {

	private final JTextArea text;
	private final JScrollPane scroll;
	private final JButton buttonExecute;
	private final JButton buttonAnalyzeData;
	private final EncogAnalyst analyst;
	private final JComboBox tasks;
	private final TasksModel model;
	
	public EncogAnalystTab(File file) {
		super(file);
		this.analyst = new EncogAnalyst();
		this.analyst.load(file);
		this.text = new JTextArea();
		this.text.setFont(EncogFonts.getInstance().getCodeFont());
		this.text.setEditable(true);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.text);
		add(this.scroll, BorderLayout.CENTER);
		loadFromFile();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.buttonExecute = new JButton("Execute");
		
		this.buttonAnalyzeData = new JButton("Analyze Ranges");
		this.buttonAnalyzeData.addActionListener(this);
		
		this.buttonExecute.addActionListener(this);
		this.tasks = new JComboBox(model = new TasksModel(this.analyst));		
		
		buttonPanel.add(tasks);
		buttonPanel.add(this.buttonExecute);
		buttonPanel.add(this.buttonAnalyzeData);
		
		add(buttonPanel,BorderLayout.NORTH);
		
		compile();	
	}
	
	public void compile() {
		this.model.refresh();
		
		// set the selected item
		if( this.analyst.getScript().getTask(EncogAnalyst.TASK_FULL)!=null) {
			this.tasks.setSelectedItem(EncogAnalyst.TASK_FULL);
		} else {
			if( this.model.getSize()>0 )
				this.tasks.setSelectedIndex(0);
		}
	}
	
	private void loadFromFile()
	{
		try {
			this.text.setText(org.encog.util.file.FileUtil.readFileAsString(file));
		} catch (IOException e) {
			throw new WorkBenchError(e);
		}
	}
	
	private void saveToFile() {
		try {
			FileUtil.writeFileAsString(this.getFile(), this.text.getText());
		} catch (IOException e) {
			throw new WorkBenchError(e);
		}
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
		this.saveToFile();
	}


	public boolean isTextSelected() {
		return this.text.getSelectionEnd()>this.text.getSelectionStart();
	}



	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.buttonExecute) {
			try {
				EncogWorkBench.getInstance().clearOutput();
				String name = (String)this.tasks.getSelectedItem();
				analyst.executeTask(name);
			} catch(AnalystError ex) {
				EncogWorkBench.getInstance().outputLine("***Encog Analyst Error");
				EncogWorkBench.getInstance().outputLine(ex.getMessage());
			} catch(Throwable t) {
				EncogWorkBench.displayError("Error During Analyst Execution", t);
			}
		} if(e.getSource()==this.buttonAnalyzeData) {
			saveToFile();
			loadFromFile();
			AnalystWizard wizard = new AnalystWizard(this.analyst);
			wizard.reanalyze();
			analyst.save(this.getFile());
			loadFromFile();
		}
		
	}

}
