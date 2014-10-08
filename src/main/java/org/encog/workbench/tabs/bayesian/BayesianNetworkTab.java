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
package org.encog.workbench.tabs.bayesian;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.encog.Encog;
import org.encog.ml.MLInput;
import org.encog.ml.MLOutput;
import org.encog.ml.MLProperties;
import org.encog.ml.MLResettable;
import org.encog.ml.bayesian.BayesianChoice;
import org.encog.ml.bayesian.BayesianEvent;
import org.encog.ml.bayesian.BayesianNetwork;
import org.encog.ml.bayesian.bif.BIFUtil;
import org.encog.ml.bayesian.table.TableLine;
import org.encog.util.Format;
import org.encog.util.HTMLReport;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.createnetwork.CreateBayesian;
import org.encog.workbench.frames.MapDataFrame;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.process.TrainBasicNetwork;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.visualize.bayesian.BayesianStructureTab;

public class BayesianNetworkTab extends EncogCommonTab implements
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar toolbar;
	private JButton buttonRandomize;
	private JButton buttonDefineQuery;
	private JButton buttonQuery;
	private JButton buttonTrain;
	private JButton buttonRestructure;
	private JButton buttonProperties;
	private JButton buttonVisualize;
	private JButton buttonImportBIF;
	private JButton buttonExportBIF;
	private final JScrollPane scroll;
	private final JEditorPane editor;
	private BayesianNetwork method;

	public BayesianNetworkTab(final ProjectEGFile data) {
		super(data);

		this.method = (BayesianNetwork) data.getObject();
		setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.buttonRandomize = new JButton("Randomize/Reset"));
		this.toolbar.add(this.buttonDefineQuery = new JButton("Define Query"));
		this.toolbar.add(this.buttonQuery = new JButton("Query"));
		this.toolbar.add(this.buttonTrain = new JButton("Train"));
		this.toolbar.add(this.buttonRestructure = new JButton("Restructure"));
		this.toolbar.add(this.buttonImportBIF = new JButton("Import BIF"));
		this.toolbar.add(this.buttonExportBIF = new JButton("Export BIF"));
		this.toolbar.add(this.buttonProperties = new JButton("Properties"));
		this.toolbar.add(this.buttonVisualize = new JButton("Visualize"));

		this.buttonRandomize.addActionListener(this);
		this.buttonQuery.addActionListener(this);
		this.buttonDefineQuery.addActionListener(this);
		this.buttonTrain.addActionListener(this);
		this.buttonRestructure.addActionListener(this);
		this.buttonProperties.addActionListener(this);
		this.buttonVisualize.addActionListener(this);
		this.buttonExportBIF.addActionListener(this);
		this.buttonImportBIF.addActionListener(this);

		add(this.toolbar, BorderLayout.PAGE_START);

		this.editor = new JEditorPane("text/html", "");
		this.editor.setEditable(false);
		this.scroll = new JScrollPane(this.editor);
		add(this.scroll, BorderLayout.CENTER);
		produceReport();
	}

	public void actionPerformed(final ActionEvent action) {
		try {
			if (action.getSource() == this.buttonQuery) {
				performQuery();
			} else if (action.getSource() == this.buttonDefineQuery) {
				performDefineQuery();
			} else if (action.getSource() == this.buttonRandomize) {
				performRandomize();
			} else if (action.getSource() == this.buttonTrain) {
				performTrain();
			} else if (action.getSource() == this.buttonRestructure) {
				performRestructure();
			} else if (action.getSource() == this.buttonProperties) {
				performProperties();
			} else if (action.getSource() == this.buttonVisualize) {
				this.handleVisualize();
			} else if (action.getSource() == this.buttonExportBIF) {
				this.handleExportBIF();
			}  else if (action.getSource() == this.buttonImportBIF) {
				this.handleImportBIF();
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void handleExportBIF() {
		final JFileChooser fc = new JFileChooser();
		if (EncogWorkBench.getInstance().getProjectDirectory() != null)
			fc.setCurrentDirectory(EncogWorkBench.getInstance().getProjectDirectory());
		fc.addChoosableFileFilter(EncogDocumentFrame.XML_FILTER);
		final int result = fc.showSaveDialog(EncogWorkBench.getInstance()
				.getMainWindow());
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				EncogWorkBench.getInstance().getMainWindow().beginWait();
				String file = fc.getSelectedFile().getAbsolutePath();
				File targetFile = new File(file);
				BIFUtil.writeBIF(targetFile, this.method);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			} catch(Throwable t) {
				EncogWorkBench.displayError("Error", t);
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
			}
		}
	}
	
	private void handleImportBIF() {
		final JFileChooser fc = new JFileChooser();
		if (EncogWorkBench.getInstance().getProjectDirectory() != null)
			fc.setCurrentDirectory(EncogWorkBench.getInstance()
					.getProjectDirectory());
		fc.addChoosableFileFilter(EncogDocumentFrame.XML_FILTER);
		final int result = fc.showOpenDialog(EncogWorkBench.getInstance()
				.getMainWindow());
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				EncogWorkBench.getInstance().getMainWindow().beginWait();
				String file = fc.getSelectedFile().getAbsolutePath();
				File sourceFile = new File(file);
				this.method = BIFUtil.readBIF(sourceFile);
				((ProjectEGFile)this.getEncogObject()).setObject(this.method);
				this.produceReport();
				this.setDirty(true);
			} catch(Throwable t) {
				EncogWorkBench.displayError("Error", t);
			} finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
			}
		}
	}

	private void performTrain() {
		TrainBasicNetwork t = new TrainBasicNetwork(
				(ProjectEGFile) this.getEncogObject(), this);
		t.performTrain();
	}

	private void performRandomize() {
		if (EncogWorkBench.askQuestion("Are you sure?",
				"Clear probability tables and lose all training?")) {
			if (method instanceof MLResettable) {
				((MLResettable) method).reset();
			}
			
			produceReport();
			EncogWorkBench.getInstance().getMainWindow().redraw();
			this.setDirty(true);
		}

	}
	
	private void performDefineQuery() {
		try {
			String query = EncogWorkBench.displayInput("Enter query (i.e. P(+x,-y)?", this.method.getQuery().getProblem());
			if( query!=null) {
				//this.method.defineQuery(query);
				EncogWorkBench.displayMessage("Query", "Query successfully updated.");
				this.produceReport();
				this.setDirty(true);
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void performQuery() {
		boolean success = false;
		double p = 0;
		
		try {
			EncogWorkBench.getInstance().getMainWindow().beginWait();
			this.method.getQuery().execute();
			p = this.method.getQuery().getProbability();
			success = true;
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		} finally {
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
		
		EncogWorkBench.getInstance().outputLine(this.method.getQuery().getProblem() + " = " + Format.formatPercent(p));
		if( success ) {
			EncogWorkBench.displayMessage("Probability", Format.formatPercent(p));
		}
	}

	/**
	 * @return the data
	 */
	public BayesianNetwork getData() {
		return (BayesianNetwork) this.method;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void performProperties() {
		if (this.method instanceof MLProperties) {
			MapDataFrame frame = new MapDataFrame(
					((MLProperties) method).getProperties(), "Properties");
			frame.setVisible(true);
			setDirty(true);
		} else {
			EncogWorkBench
					.displayError("Error",
							"This Machine Learning Method type does not support properties.");
		}
	}

	public void handleVisualize() {
		BayesianStructureTab tab = new BayesianStructureTab(this.method);
		
		EncogWorkBench.getInstance().getMainWindow().getTabManager().openModalTab(tab, "Network Structure");
	}

	public void produceReport() {
		HTMLReport report = new HTMLReport();
		report.beginHTML();
		report.title("MLMethod");
		report.beginBody();
		report.h1(this.method.getClass().getSimpleName());

		report.beginTable();

		if (method instanceof MLInput) {
			MLInput reg = (MLInput) method;
			report.tablePair("Input Count",
					Format.formatInteger(reg.getInputCount()));
		}

		if (method instanceof MLOutput) {
			MLOutput reg = (MLOutput) method;
			report.tablePair("Output Count",
					Format.formatInteger(reg.getOutputCount()));
		}

		report.tablePair("Parameter Count",
				Format.formatInteger(this.method.calculateParameterCount()));
		report.tablePair("Expression", this.method.toString());
		
		String queryType = "";
		String queryStr = "";
		
		if( method.getQuery()!=null) {
			queryType = method.getQuery().getClass().getSimpleName();
			queryStr = method.getQuery().getProblem();
		}
		
		report.tablePair("Query Type", queryType);
		report.tablePair("Query", queryStr);

		report.endTable();

		report.h3("Events");
		report.beginTable();
		report.beginRow();
		report.header("Event");
		report.header("Choices");
		report.header("Probability");
		report.endRow();

		for (BayesianEvent event : this.method.getEvents()) {
			report.beginRow();
			report.cell(event.getLabel());
			StringBuilder l = new StringBuilder();
			boolean first = true;
			for (BayesianChoice str : event.getChoices()) {
				if (!first) {
					l.append(", ");
				}
				first = false;
				l.append(str);
			}
			report.cell(l.toString());
			report.cell(event.toString());
			report.endRow();
		}

		report.endTable();

		report.h3("Probability Tables");
		report.beginTable();

		for (BayesianEvent event : this.method.getEvents()) {
			report.beginRow();
			report.header(event.getLabel(), 2);
			report.endRow();
			for (TableLine line : event.getTable().getLines()) {
				if( line==null )
					continue;
				
				report.beginRow();
				StringBuilder str = new StringBuilder();
				str.append("P(");
				str.append(BayesianEvent.formatEventName(event, line.getResult()));

				if (event.getParents().size() > 0) {
					str.append("|");
				}

				int index = 0;
				boolean first = true;
				for (BayesianEvent parentEvent : event.getParents()) {
					if (!first) {
						str.append(",");
					}
					first = false;
					double arg = line.getArguments()[index];
					if (parentEvent.isBoolean()) {
						if (Math.abs(arg) < Encog.DEFAULT_DOUBLE_EQUAL) {
							str.append("-");
						} else {
							str.append("+");
						}
					}
					str.append(parentEvent.getLabel());
					if (!parentEvent.isBoolean()) {
						str.append("=");
						str.append(arg);
					}
				}
				str.append(")");
				report.cell(str.toString());
				report.cell(Format.formatPercent(line.getProbability()));
				report.endRow();
			}
		}

		report.endTable();

		report.endBody();
		report.endHTML();
		this.editor.setText(report.toString());
	}

	private void performRestructure() {
		String priorQuery = this.method.getQuery().getProblem();
		String priorContents = this.method.getContents();
		
		CreateBayesian d = new CreateBayesian(this.method);
		d.getQuery().setValue(priorQuery);
		d.getContents().setValue(priorContents);
		if( d.process() ) {
			String currentQuery = d.getQuery().getValue();
			String currentContents = d.getContents().getValue();
			
			boolean changed = false;
			if( !priorQuery.equals(currentQuery)) {
				//this.method.defineQuery(currentQuery);
				changed = true;
			}
			
			if( !priorContents.equals(currentContents)) {
				this.method.setContents(currentContents);
				changed = true;
			}
			
			if( changed ) {
				produceReport();
				EncogWorkBench.getInstance().getMainWindow().redraw();
				this.setDirty(true);
			}
		}
	}

	@Override
	public String getName() {
		return this.getEncogObject().getName();
	}

}
