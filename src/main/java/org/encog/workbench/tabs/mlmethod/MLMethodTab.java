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
package org.encog.workbench.tabs.mlmethod;

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
import org.encog.ml.MLFactory;
import org.encog.ml.MLInput;
import org.encog.ml.MLMethod;
import org.encog.ml.MLOutput;
import org.encog.ml.MLProperties;
import org.encog.ml.MLRegression;
import org.encog.ml.MLResettable;
import org.encog.neural.cpn.CPN;
import org.encog.neural.flat.FlatNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.ContainsFlat;
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
import org.encog.workbench.dialogs.training.NetworkDialog;
import org.encog.workbench.frames.MapDataFrame;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.process.TrainBasicNetwork;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.query.general.ClassificationQueryTab;
import org.encog.workbench.tabs.query.general.RegressionQueryTab;
import org.encog.workbench.tabs.query.ocr.OCRQueryTab;
import org.encog.workbench.tabs.query.thermal.QueryThermalTab;
import org.encog.workbench.tabs.visualize.ThermalGrid.ThermalGridTab;
import org.encog.workbench.tabs.visualize.compare.NetCompareTab;
import org.encog.workbench.tabs.visualize.structure.StructureTab;
import org.encog.workbench.tabs.visualize.weights.AnalyzeWeightsTab;

public class MLMethodTab extends EncogCommonTab implements ActionListener {

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
	private JButton buttonWeights;
	private final JScrollPane scroll;
	private final JEditorPane editor;
	private MLMethod method;

	public MLMethodTab(final ProjectEGFile data) {
		super(data);

		this.method = (MLMethod)data.getObject();
		setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.buttonRandomize = new JButton("Randomize/Reset"));
		this.toolbar.add(this.buttonQuery = new JButton("Query"));
		this.toolbar.add(this.buttonTrain = new JButton("Train"));
		this.toolbar.add(this.buttonRestructure = new JButton("Restructure"));
		if( this.method instanceof BasicNetwork ) {
			this.toolbar.add(this.buttonWeights = new JButton("Weights"));
			this.buttonWeights.addActionListener(this);
		}
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
			} else if (action.getSource() == this.buttonWeights) {
				this.performWeights();
			}			
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void performTrain() {
		TrainBasicNetwork t = new TrainBasicNetwork((ProjectEGFile)this.getEncogObject(),this);
		t.performTrain();
	}

	private void randomizeBasicNetwork() {
		RandomizeNetworkDialog dialog = new RandomizeNetworkDialog(
				EncogWorkBench.getInstance().getMainWindow());

		dialog.getHigh().setValue(1);
		dialog.getConstHigh().setValue(1);
		dialog.getLow().setValue(-1);
		dialog.getConstLow().setValue(-1);
		dialog.getSeedValue().setValue(1000);
		dialog.getConstantValue().setValue(0);
		dialog.getPerturbPercent().setValue(0.01);

		if (dialog.process()) {
			switch (dialog.getCurrentTab()) {
			case 0:
				optionRandomize(dialog);
				break;

			case 1:
				optionPerturb(dialog);
				break;
			case 2:
				optionGaussian(dialog);
				break;
			case 3:
				optionConsistent(dialog);
				break;

			case 4:
				optionConstant(dialog);
				break;
			}
		}

	}

	private void performRandomize() {

		if (EncogWorkBench.askQuestion("Are you sure?",
				"Randomize/reset network weights and lose all training?")) {
			if (this.method instanceof BasicNetwork) {
				randomizeBasicNetwork();
			} else if (method instanceof MLResettable) {
				((MLResettable) method).reset();
			}
		}

	}

	private void optionConstant(RandomizeNetworkDialog dialog) {
		double value = dialog.getConstantValue().getValue();
		ConstRandomizer r = new ConstRandomizer(value);
		r.randomize((BasicNetwork)this.method);
		setDirty(true);
	}

	private void optionConsistent(RandomizeNetworkDialog dialog) {
		int seed = dialog.getSeedValue().getValue();
		double min = dialog.getConstLow().getValue();
		double max = dialog.getConstHigh().getValue();
		ConsistentRandomizer c = new ConsistentRandomizer(min, max, seed);
		c.randomize(this.method);
		setDirty(true);
	}

	private void optionPerturb(RandomizeNetworkDialog dialog) {
		double percent = dialog.getPerturbPercent().getValue();

		Distort distort = new Distort(percent);
		distort.randomize((BasicNetwork) this.method);
		setDirty(true);
	}

	private void optionGaussian(RandomizeNetworkDialog dialog) {
		double mean = dialog.getMean().getValue();
		double dev = dialog.getDeviation().getValue();

		GaussianRandomizer g = new GaussianRandomizer(mean, dev);
		g.randomize((BasicNetwork) this.method);
		setDirty(true);
	}

	private void optionRandomize(RandomizeNetworkDialog dialog) {
		Randomizer r = null;

		switch (dialog.getTheType().getSelectedIndex()) {
		case 0: // Random
			r = new RangeRandomizer(dialog.getLow().getValue(), dialog
					.getHigh().getValue());
			break;
		case 1: // Nguyen-Widrow
			r = new NguyenWidrowRandomizer();
			break;
		case 2: // Fan in
			r = new FanInRandomizer(dialog.getLow().getValue(), dialog
					.getHigh().getValue(), false);
			break;
		}

		if (r != null) {
			r.randomize((BasicNetwork) this.method);
			setDirty(true);
		}
	}

	private void performQuery() {
		try {
			if (this.method instanceof ThermalNetwork) {
				QueryThermalTab tab = new QueryThermalTab((ProjectEGFile)this.getEncogObject());
				EncogWorkBench.getInstance().getMainWindow().getTabManager()
						.openModalTab(tab, "Thermal Query");			
			}
			// only supports regression
			else {
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
					RegressionQueryTab tab = new RegressionQueryTab((MLRegression)
							((ProjectEGFile) this.getEncogObject()).getObject());
					EncogWorkBench.getInstance().getMainWindow().getTabManager()
							.openModalTab(tab, "Query Regression");					
				}  else if( sel.getSelected()==selectOCR ) {
					OCRQueryTab tab = new OCRQueryTab(
							((ProjectEGFile) this.getEncogObject()));
					EncogWorkBench.getInstance().getMainWindow().getTabManager()
							.openModalTab(tab, "Query OCR");					
				}
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	/**
	 * @return the data
	 */
	public BasicNetwork getData() {
		return (BasicNetwork) this.method;
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
		SelectItem selectCompare;
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(selectWeights = new SelectItem("Weights Histogram",
				"A histogram of the weights."));
		list.add(selectStructure = new SelectItem("Network Structure",
				"The structure of the neural network."));
		list.add(selectThermal = new SelectItem("Thermal Matrix",
				"Shows the matrix of a Hopfield or Boltzmann Machine."));
		list.add(selectCompare = new SelectItem("Compare Network",
				"Compare this neural network to another neural network with the same weight count."));
		SelectDialog sel = new SelectDialog(EncogWorkBench.getInstance()
				.getMainWindow(), list);
		sel.setVisible(true);

		if (sel.getSelected() == selectWeights) {
			analyzeWeights();
		} else if (sel.getSelected() == selectStructure) {
			analyzeStructure();
		} else if (sel.getSelected() == selectThermal) {
			analyzeThermal();
		}  else if (sel.getSelected() == selectCompare) {
			compareNetworks();
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
		
		if( method instanceof CPN ) {
			CPN cpn = (CPN)method;
			report.tablePair("Instar Count", Format.formatInteger(cpn.getInstarCount()));
			report.tablePair("Outstar Count", Format.formatInteger(cpn.getOutstarCount()));			
		}
				
		report.endTable();
		
		
		if( method instanceof MLFactory ) {
			MLFactory factory = ((MLFactory)method);
			
			String factoryType = factory.getFactoryType();
			String factoryArchitecture = factory.getFactoryArchitecture();
			
			if( factoryType!=null ) {			
				report.h3("Encog Factory Code");
				report.beginTable();
	
				report.tablePair("Type", factoryType);
				report.tablePair("Architecture", factoryArchitecture);
				report.endTable();
			}
		}

		
		
		if (this.method instanceof RBFNetwork) {
			RBFNetwork rbfNetwork = (RBFNetwork)this.method;
			
			report.h3("RBF Centers");
			report.beginTable();
			report.beginRow();
			report.header("RBF");
			report.header("Peak");
			report.header("Width");
			for(int i=1;i<=rbfNetwork.getInputCount();i++) {
				report.header("Center " + i);
			}
			report.endRow();
			
			
			for( RadialBasisFunction rbf : rbfNetwork.getRBF() ) {
				report.beginRow();
				report.cell(rbf.getClass().getSimpleName());
				report.cell(Format.formatDouble(rbf.getPeak(), 5));
				report.cell(Format.formatDouble(rbf.getWidth(), 5));
				for(int i=0;i<rbfNetwork.getInputCount();i++) {
					report.cell(Format.formatDouble(rbf.getCenter(i), 5));
				}
				report.endRow();
			}
		}

		if (this.method instanceof BasicNetwork) {
			report.h3("Layers");
			report.beginTable();
			report.beginRow();
			report.header("Layer #");
			report.header("Total Count");
			report.header("Neuron Count");
			report.header("Activation Function");
			report.header("Bias");
			report.header("Context Target Size");
			report.header("Context Target Offset");
			report.header("Context Count");
			report.endRow();

			BasicNetwork network = (BasicNetwork) method;
			FlatNetwork flat = network.getStructure().getFlat();
			int layerCount = network.getLayerCount();

			for (int l = 0; l < layerCount; l++) {
				report.beginRow();
				StringBuilder str = new StringBuilder();
				str.append(Format.formatInteger(l + 1));
				if (l == 0) {
					str.append(" (Output)");
				} else if (l == network.getLayerCount() - 1) {
					str.append(" (Input)");
				}
				report.cell(str.toString());
				report.cell(Format.formatInteger(flat.getLayerCounts()[l]));
				report.cell(Format.formatInteger(flat.getLayerFeedCounts()[l]));
				report.cell(flat.getActivationFunctions()[l].getClass()
						.getSimpleName());
				report.cell(Format.formatDouble(flat.getBiasActivation()[l], 4));
				report.cell(Format.formatInteger(flat.getContextTargetSize()[l]));
				report.cell(Format.formatInteger(flat.getContextTargetOffset()[l]));
				report.cell(Format.formatInteger(flat.getLayerContextCount()[l]));
				report.endRow();
			}
			report.endTable();
		}
		


		report.endBody();
		report.endHTML();
		this.editor.setText(report.toString());
	}

	private void restructureHopfield() {
		HopfieldNetwork hopfield = (HopfieldNetwork) method;
		CreateHopfieldDialog dialog = new CreateHopfieldDialog(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.getNeuronCount().setValue(hopfield.getNeuronCount());

		if (dialog.process()
				&& (hopfield.getNeuronCount() != dialog.getNeuronCount()
						.getValue())) {
			HopfieldPattern pattern = new HopfieldPattern();
			pattern.setInputNeurons(dialog.getNeuronCount().getValue());
			setDirty(true);
			produceReport();
		}
	}

	private void restructureFeedforward() {
		CreateFeedforward dialog = new CreateFeedforward(EncogWorkBench
				.getInstance().getMainWindow());
		BasicNetwork network = (BasicNetwork)method;

		int hiddenLayerCount = network.getLayerCount() - 2;
		ActivationFunction oldActivationOutput = network.getActivation(network.getLayerCount() - 1);
		ActivationFunction oldActivationHidden;
		
		if( hiddenLayerCount>0 ) {
			oldActivationHidden = network.getActivation(1);
		} else {
			oldActivationHidden = new ActivationTANH();
		}
		
		dialog.setActivationFunctionOutput(oldActivationOutput);
		dialog.setActivationFunctionHidden(oldActivationHidden);
		
		dialog.getInputCount().setValue(network.getInputCount());
		dialog.getOutputCount().setValue(network.getOutputCount());
		
		for (int i = 0; i < hiddenLayerCount; i++) {
			int num = network.getLayerNeuronCount(i + 1);
			String str = "Hidden Layer " + (i + 1) + ": " + num + " neurons";
			dialog.getHidden().getModel().addElement(str);
		}
		

		if (dialog.process()) {
			// decide if entire network is to be recreated
			if ((dialog.getActivationFunctionHidden() != oldActivationHidden)
					|| (dialog.getActivationFunctionOutput() != oldActivationOutput)
					|| dialog.getHidden().getModel().size() != (network
							.getLayerCount() - 2)) {
				FeedForwardPattern feedforward = new FeedForwardPattern();
				feedforward.setActivationFunction(dialog.getActivationFunctionHidden());
				feedforward.setActivationOutput(dialog.getActivationFunctionOutput());
				feedforward.setInputNeurons(dialog.getInputCount().getValue());
				for (int i = 0; i < dialog.getHidden().getModel().size(); i++) {
					String str = (String) dialog.getHidden().getModel()
							.getElementAt(i);
					int i1 = str.indexOf(':');
					int i2 = str.indexOf("neur");
					if (i1 != -1 && i2 != -1) {
						str = str.substring(i1 + 1, i2).trim();
						int neuronCount = Integer.parseInt(str);
						feedforward.addHiddenLayer(neuronCount);
					}
				}
				feedforward.setInputNeurons(dialog.getInputCount().getValue());
				feedforward.setOutputNeurons(dialog.getOutputCount().getValue());
				this.method = (BasicNetwork) feedforward.generate();
				((ProjectEGFile)getEncogObject()).setObject(this.method);
				produceReport();
			} else {
				// try to prune it
				PruneSelective prune = new PruneSelective(network);
				int newInputCount = dialog.getInputCount().getValue();
				int newOutputCount = dialog.getOutputCount().getValue();

				// did input neurons change?
				if (newInputCount != network.getInputCount()) {
					prune.changeNeuronCount(0, newInputCount);
				}

				// did output neurons change?
				if (newOutputCount != network.getOutputCount()) {
					prune.changeNeuronCount(0, newOutputCount);
				}

				// did the hidden layers change?
				for (int i = 0; i < network.getLayerCount() - 2; i++) {
					int newHiddenCount = 1;
					String str = (String) dialog.getHidden().getModel()
							.getElementAt(i);
					int i1 = str.indexOf(':');
					int i2 = str.indexOf("neur");
					if (i1 != -1 && i2 != -1) {
						str = str.substring(i1 + 1, i2).trim();
						newHiddenCount = Integer.parseInt(str);
					}

					// did this hidden layer change?
					if (network.getLayerNeuronCount(i) != newHiddenCount) {
						prune.changeNeuronCount(i + 1, newHiddenCount);
					}
				}
			}
			setDirty(true);
			produceReport();
		}
	}

	private void performRestructure() {
		if (method instanceof HopfieldNetwork) {
			restructureHopfield();
		} else if (method instanceof BasicNetwork) {
			restructureFeedforward();
		} else {
			EncogWorkBench.displayError("Error",
					"This Machine Learning Method cannot be restructured.");
		}
	}
	
	public void compareNetworks() {
		NetworkDialog dialog = new NetworkDialog(false);
		if( dialog.process()) {
			NetCompareTab tab = new NetCompareTab(this.method,(MLMethod)dialog.getMethodOrPopulation());
			EncogWorkBench.getInstance().getMainWindow().getTabManager()
			.openModalTab(tab, "Compare");
		}
		
	}

	@Override
	public String getName() {
		return this.getEncogObject().getName();
	}
	
	public void performWeights() {
		WeightsTab tab = new WeightsTab(this,(BasicNetwork)this.method);
		EncogWorkBench.getInstance().getMainWindow().getTabManager().openTab(tab);
	}

}
