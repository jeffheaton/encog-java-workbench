/*
 * Encog(tm) Workbench v3.0
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2011 Heaton Research, Inc.
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.mathutil.randomize.ConsistentRandomizer;
import org.encog.mathutil.randomize.ConstRandomizer;
import org.encog.mathutil.randomize.Distort;
import org.encog.mathutil.randomize.FanInRandomizer;
import org.encog.mathutil.randomize.GaussianRandomizer;
import org.encog.mathutil.randomize.NguyenWidrowRandomizer;
import org.encog.mathutil.randomize.Randomizer;
import org.encog.mathutil.randomize.RangeRandomizer;
import org.encog.mathutil.rbf.RadialBasisFunction;
import org.encog.ml.MLClassification;
import org.encog.ml.MLContext;
import org.encog.ml.MLEncodable;
import org.encog.ml.MLInput;
import org.encog.ml.MLMethod;
import org.encog.ml.MLOutput;
import org.encog.ml.MLProperties;
import org.encog.ml.MLRegression;
import org.encog.ml.MLResettable;
import org.encog.ml.bayesian.BayesianNetwork;
import org.encog.neural.cpn.CPN;
import org.encog.neural.flat.FlatNetwork;
import org.encog.neural.neat.NEATNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.neural.pattern.HopfieldPattern;
import org.encog.neural.prune.PruneSelective;
import org.encog.neural.rbf.RBFNetwork;
import org.encog.neural.thermal.HopfieldNetwork;
import org.encog.neural.thermal.ThermalNetwork;
import org.encog.util.Format;
import org.encog.util.HTMLReport;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.dialogs.RandomizeNetworkDialog;
import org.encog.workbench.dialogs.createnetwork.CreateFeedforward;
import org.encog.workbench.dialogs.createnetwork.CreateHopfieldDialog;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.frames.MapDataFrame;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.process.TrainBasicNetwork;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.query.general.ClassificationQueryTab;
import org.encog.workbench.tabs.query.general.RegressionQueryTab;
import org.encog.workbench.tabs.query.ocr.OCRQueryTab;
import org.encog.workbench.tabs.query.thermal.QueryThermalTab;
import org.encog.workbench.tabs.visualize.ThermalGrid.ThermalGridTab;
import org.encog.workbench.tabs.visualize.structure.StructureTab;
import org.encog.workbench.tabs.visualize.weights.AnalyzeWeightsTab;

public class BayesianNetworkTab extends EncogCommonTab implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar toolbar;
	private JButton buttonRandomize;
	private JButton buttonQuery;
	private JButton buttonTrain;
	private JButton buttonRestructure;
	private JButton buttonProperties;
	private JButton buttonVisualize;
	private final JScrollPane scroll;
	private final JEditorPane editor;
	private BayesianNetwork method;

	public BayesianNetworkTab(final ProjectEGFile data) {
		super(data);

		this.method = (BayesianNetwork)data.getObject();
		setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.buttonRandomize = new JButton("Randomize/Reset"));
		this.toolbar.add(this.buttonQuery = new JButton("Query"));
		this.toolbar.add(this.buttonTrain = new JButton("Train"));
		this.toolbar.add(this.buttonRestructure = new JButton("Restructure"));
		this.toolbar.add(this.buttonProperties = new JButton("Properties"));
		this.toolbar.add(this.buttonVisualize = new JButton("Visualize"));

		this.buttonRandomize.addActionListener(this);
		this.buttonQuery.addActionListener(this);
		this.buttonTrain.addActionListener(this);
		this.buttonRestructure.addActionListener(this);
		this.buttonProperties.addActionListener(this);
		this.buttonVisualize.addActionListener(this);

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
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void performTrain() {
		TrainBasicNetwork t = new TrainBasicNetwork((ProjectEGFile)this.getEncogObject(),this);
		t.performTrain();
	}


	private void performRandomize() {



	}

	

	private void performQuery() {
		try {
			
				SelectItem selectClassification = null;
				SelectItem selectRegression = null;
				SelectItem selectOCR;
				
				List<SelectItem> list = new ArrayList<SelectItem>();
				if( this.method instanceof MLClassification ) {
				list.add(selectClassification = new SelectItem("Query Classification",
						"Machine Learning output is a class."));
				}
				if( this.method instanceof MLRegression ) {
				list.add(selectRegression = new SelectItem("Query Regression",
						"Machine Learning output is a number(s)."));
				}
				list.add(selectOCR = new SelectItem("Query OCR",
					"Query using drawn chars.  Supports regression or classification."));
				SelectDialog sel = new SelectDialog(EncogWorkBench.getInstance()
						.getMainWindow(), list);
				sel.setVisible(true);
				
				if( sel.getSelected()==selectClassification ) {
					ClassificationQueryTab tab = new ClassificationQueryTab(
							((ProjectEGFile) this.getEncogObject()));
					EncogWorkBench.getInstance().getMainWindow().getTabManager()
							.openModalTab(tab, "Query Classification");					
				} else if( sel.getSelected()==selectRegression ) {
					RegressionQueryTab tab = new RegressionQueryTab(
							((ProjectEGFile) this.getEncogObject()));
					EncogWorkBench.getInstance().getMainWindow().getTabManager()
							.openModalTab(tab, "Query Regression");					
				}  else if( sel.getSelected()==selectOCR ) {
					OCRQueryTab tab = new OCRQueryTab(
							((ProjectEGFile) this.getEncogObject()));
					EncogWorkBench.getInstance().getMainWindow().getTabManager()
							.openModalTab(tab, "Query OCR");					
				}
			
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
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
		if ( this.method instanceof MLProperties) {
			MapDataFrame frame = new MapDataFrame(
					((MLProperties)method).getProperties(),
					"Properties");
			frame.setVisible(true);
			setDirty(true);
		} else {
			EncogWorkBench
					.displayError("Error",
							"This Machine Learning Method type does not support properties.");
		}
	}

	public void handleVisualize() {
		SelectItem selectWeights;
		SelectItem selectStructure;
		SelectItem selectThermal;
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(selectWeights = new SelectItem("Weights Histogram",
				"A histogram of the weights."));
		list.add(selectStructure = new SelectItem("Network Structure",
				"The structure of the neural network."));
		list.add(selectThermal = new SelectItem("Thermal Matrix",
				"Shows the matrix of a Hopfield or Boltzmann Machine."));
		SelectDialog sel = new SelectDialog(EncogWorkBench.getInstance()
				.getMainWindow(), list);
		sel.setVisible(true);

		if (sel.getSelected() == selectWeights) {
			analyzeWeights();
		} else if (sel.getSelected() == selectStructure) {
			analyzeStructure();
		} else if (sel.getSelected() == selectThermal) {
			analyzeThermal();
		}

	}

	private void analyzeThermal() {
		ThermalGridTab tab = new ThermalGridTab((ProjectEGFile) this.getEncogObject());
		EncogWorkBench.getInstance().getMainWindow().getTabManager()
				.openModalTab(tab, "Thermal Grid");
	}

	private void analyzeStructure() {

		if (method instanceof MLMethod) {
			StructureTab tab = new StructureTab(
					((MLMethod)this.method));
			EncogWorkBench.getInstance().getMainWindow().getTabManager()
					.openModalTab(tab, "Network Structure");
		} else {
			throw new WorkBenchError("No analysis available for: "
					+ this.method.getClass().getSimpleName());
		}

	}

	public void analyzeWeights() {
		AnalyzeWeightsTab tab = new AnalyzeWeightsTab((ProjectEGFile)this.getEncogObject());
		EncogWorkBench.getInstance().getMainWindow().getTabManager()
				.openModalTab(tab, "Analyze Weights");
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

		if (method instanceof MLEncodable) {
			MLEncodable encode = (MLEncodable)method;
			report.tablePair("Encoded Length",
					Format.formatInteger(encode.encodedArrayLength()));
		}

		report.tablePair("Resettable",
				(method instanceof MLResettable) ? "true" : "false");
		
		report.tablePair("Context",
				(method instanceof MLContext) ? "true" : "false");
		
				
		report.endTable();
		
		

		report.endBody();
		report.endHTML();
		this.editor.setText(report.toString());
	}


	private void performRestructure() {

	}

	@Override
	public String getName() {
		return this.getEncogObject().getName();
	}

}
