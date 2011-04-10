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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.encog.app.analyst.AnalystError;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.tabs.files.text.BasicTextTab;
import org.encog.workbench.tabs.visualize.datareport.DataReportTab;

public class EncogAnalystTab extends BasicTextTab implements ActionListener {

	private final JButton buttonExecute;
	private final JButton buttonAnalyzeData;
	private final JButton buttonVisualize;
	private final EncogAnalyst analyst;
	private final JComboBox tasks;
	private final TasksModel model;

	public EncogAnalystTab(ProjectFile file) {
		super(file);
		this.analyst = new EncogAnalyst();

		loadFromFile();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.buttonExecute = new JButton("Execute");

		this.buttonAnalyzeData = new JButton("Analyze Ranges");
		this.buttonAnalyzeData.addActionListener(this);
		this.buttonVisualize = new JButton("Visualize");
		this.buttonVisualize.addActionListener(this);

		this.buttonExecute.addActionListener(this);
		this.tasks = new JComboBox(model = new TasksModel(this.analyst));

		buttonPanel.add(tasks);
		buttonPanel.add(this.buttonExecute);
		buttonPanel.add(this.buttonAnalyzeData);
		buttonPanel.add(this.buttonVisualize);

		add(buttonPanel, BorderLayout.NORTH);

		compile();
	}

	public boolean compile() {
		try {
			int selected = this.tasks.getSelectedIndex();
			byte[] b = this.getText().getBytes();
			ByteArrayInputStream ms = new ByteArrayInputStream(b);
			this.analyst.getScript().setBasePath(
					EncogWorkBench.getInstance().getProjectDirectory()
							.toString());
			this.analyst.load(ms);
			ms.close();

			this.model.refresh();

			// set the selected item
			if (this.analyst.getScript().getTask(EncogAnalyst.TASK_FULL) != null) {
				this.tasks.setSelectedItem(EncogAnalyst.TASK_FULL);
			} else {
				if (this.model.getSize() > 0)
					this.tasks.setSelectedIndex(0);
			}

			if (selected != -1)
				this.tasks.setSelectedIndex(selected);
			return true;
		} catch (AnalystError ex) {
			EncogWorkBench.getInstance().clearOutput();
			EncogWorkBench.getInstance().outputLine("**Compile Error");
			EncogWorkBench.getInstance().outputLine(ex.getMessage());
			EncogWorkBench.displayError("Error",
					"This Analyst Script has compile errors.");
			return false;
		} catch (IOException ex) {
			throw new WorkBenchError(ex);
		}
	}

	private void loadFromFile() {
		try {
			this.setText(org.encog.util.file.FileUtil.readFileAsString(this
					.getEncogObject().getFile()));
		} catch (IOException e) {
			throw new WorkBenchError(e);
		}
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == this.buttonExecute) {
				execute();
			} else if (e.getSource() == this.buttonAnalyzeData) {
				analyzeData();
			} else if (e.getSource() == this.buttonVisualize) {
				visualizeData();
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void visualizeData() {
		if (compile()) {
			try {
				EncogWorkBench.getInstance().getMainWindow().beginWait();
				DataReportTab tab = new DataReportTab(this.analyst);
				EncogWorkBench.getInstance().getMainWindow().getTabManager()
						.openTab(tab);
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
			}
		}
	}

	private boolean forceSave() {
		if (isDirty()) {
			if (EncogWorkBench
					.askQuestion(
							"Save",
							"To perform this operation, you must save your script.\nWould you like to save this script?")) {
				this.save();
				return true;
			}
			return false;
		}
		return true;
	}

	private void execute() {
		if (forceSave()) {
			if (this.tasks.getSelectedIndex() == -1) {
				EncogWorkBench.displayError("Error", "No tasks to execute.");
				return;
			}

			if (compile()) {
				EncogWorkBench.getInstance().clearOutput();
				String name = (String) this.tasks.getSelectedItem();
				AnalystProgressTab tab = new AnalystProgressTab(this.analyst,
						name);
				EncogWorkBench.getInstance().getMainWindow().getTabManager()
						.openModalTab(tab, "Analyst");
			}
		}
	}

	private void analyzeData() {
		try {
			if (!compile())
				return;

			EncogWorkBench.getInstance().getMainWindow().beginWait();
			AnalystWizard wizard = new AnalystWizard(this.analyst);
			wizard.reanalyze();

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			analyst.save(os);
			os.close();

			this.setText(os.toString());
		} catch (IOException ex) {
			throw new WorkBenchError(ex);
		} finally {
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}

	}

}
