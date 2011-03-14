/*
 * Encog(tm) Workbench v2.5
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

import org.encog.engine.network.activation.ActivationTANH;
import org.encog.engine.network.flat.FlatNetwork;
import org.encog.engine.util.Format;
import org.encog.mathutil.randomize.ConsistentRandomizer;
import org.encog.mathutil.randomize.ConstRandomizer;
import org.encog.mathutil.randomize.Distort;
import org.encog.mathutil.randomize.FanInRandomizer;
import org.encog.mathutil.randomize.GaussianRandomizer;
import org.encog.mathutil.randomize.NguyenWidrowRandomizer;
import org.encog.mathutil.randomize.Randomizer;
import org.encog.mathutil.randomize.RangeRandomizer;
import org.encog.ml.MLEncodable;
import org.encog.ml.MLMethod;
import org.encog.ml.MLProperties;
import org.encog.ml.MLRegression;
import org.encog.ml.MLResettable;
import org.encog.neural.neat.NEATNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.neural.pattern.HopfieldPattern;
import org.encog.neural.thermal.HopfieldNetwork;
import org.encog.persist.EncogCollection;
import org.encog.persist.EncogMemoryCollection;
import org.encog.persist.EncogPersistedObject;
import org.encog.util.HTMLReport;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.dialogs.RandomizeNetworkDialog;
import org.encog.workbench.dialogs.createnetwork.CreateFeedforward;
import org.encog.workbench.dialogs.createnetwork.CreateHopfieldDialog;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.frames.MapDataFrame;
import org.encog.workbench.frames.query.NetworkQueryFrame;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.mlmethod.structure.StructureTab;
import org.encog.workbench.tabs.visualize.neat.NEATTab;
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
	private final JScrollPane scroll;
	private final JEditorPane editor;

	public MLMethodTab(final MLMethod data) {
		super((EncogPersistedObject) data);

		setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.buttonRandomize = new JButton("Randomize"));
		this.toolbar.add(this.buttonQuery = new JButton("Query"));
		this.toolbar.add(this.buttonTrain = new JButton("Train"));
		this.toolbar.add(this.buttonRestructure = new JButton("Restructure"));
		this.toolbar.add(this.buttonProperties = new JButton(
				"Properties"));
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
	}

	private void performTrain() {
		EncogWorkBench.getInstance().getMainWindow().getOperations().performTrain();
	}

	private void performRandomize() {

		if (EncogWorkBench.askQuestion("Are you sure?",
				"Randomize network weights and lose all training?")) {

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

	}

	private void optionConstant(RandomizeNetworkDialog dialog) {
		double value = dialog.getConstantValue().getValue();
		ConstRandomizer r = new ConstRandomizer(value);
		r.randomize((BasicNetwork) getEncogObject());
	}

	private void optionConsistent(RandomizeNetworkDialog dialog) {
		int seed = dialog.getSeedValue().getValue();
		double min = dialog.getConstLow().getValue();
		double max = dialog.getConstHigh().getValue();
		ConsistentRandomizer c = new ConsistentRandomizer(min, max, seed);
		c.randomize((BasicNetwork) getEncogObject());
	}

	private void optionPerturb(RandomizeNetworkDialog dialog) {
		double percent = dialog.getPerturbPercent().getValue();

		Distort distort = new Distort(percent);
		distort.randomize((BasicNetwork) getEncogObject());
	}

	private void optionGaussian(RandomizeNetworkDialog dialog) {
		double mean = dialog.getMean().getValue();
		double dev = dialog.getDeviation().getValue();

		GaussianRandomizer g = new GaussianRandomizer(mean, dev);
		g.randomize((BasicNetwork) getEncogObject());
	}

	private void optionRandomize(RandomizeNetworkDialog dialog) {
		Randomizer r = null;

		switch (dialog.getType().getSelectedIndex()) {
		case 0: // Random
			r = new RangeRandomizer(dialog.getLow().getValue(), dialog
					.getHigh().getValue());
			break;
		case 1: // Nguyen-Widrow
			r = new NguyenWidrowRandomizer(dialog.getLow().getValue(), dialog
					.getHigh().getValue());
			break;
		case 2: // Fan in
			r = new FanInRandomizer(dialog.getLow().getValue(), dialog
					.getHigh().getValue(), false);
			break;
		}

		if (r != null)
			r.randomize((BasicNetwork) this.getEncogObject());
	}

	private void performQuery() {
		try {
			NetworkQueryFrame query = new NetworkQueryFrame(
					((MLRegression) this.getEncogObject()));
			query.setVisible(true);

		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t, ((MLMethod) this
					.getEncogObject()), null);
		}

	}

	/**
	 * @return the data
	 */
	public BasicNetwork getData() {
		return (BasicNetwork) getEncogObject();
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean close() {
		if( EncogWorkBench.askQuestion("Save", "Would you like to save your changes?")) {
			EncogWorkBench.getInstance().save(this.getEncogObject());
			EncogWorkBench.getInstance().refresh();
		} else {
			EncogWorkBench.getInstance().revert(this.getEncogObject());
		}
		return true;
	}

	public void performProperties() {
		if (this.getEncogObject() instanceof MLProperties) {
			MapDataFrame frame = new MapDataFrame(((MLProperties) this
					.getEncogObject()).getProperties(), "Properties");
			frame.setVisible(true);
		} else {
			EncogWorkBench.displayError("Error", "This Machine Learning Method type does not support properties.");
		}
	}

	public void handleVisualize() {
		SelectItem selectWeights;
		SelectItem selectStructure;
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(selectWeights = new SelectItem("Weights Histogram",
				"A histogram of the weights."));
		list.add(selectStructure = new SelectItem("Network Structure",
				"The structure of the neural network."));
		SelectDialog sel = new SelectDialog(EncogWorkBench.getInstance()
				.getMainWindow(), list);
		sel.setVisible(true);

		if (sel.getSelected() == selectWeights) {
			analyzeWeights();
		} else if (sel.getSelected() == selectStructure) {
			analyzeStructure();
		}

	}

	private void analyzeStructure() {

		EncogPersistedObject obj = this.getEncogObject();

		if (obj instanceof BasicNetwork) {
			StructureTab tab = new StructureTab(((BasicNetwork) this
					.getEncogObject()));
			EncogWorkBench.getInstance().getMainWindow().openModalTab(tab,
					"Network Structure");
		} else if (obj instanceof NEATNetwork) {
			NEATTab tab = new NEATTab((NEATNetwork) this.getEncogObject());
			EncogWorkBench.getInstance().getMainWindow().openModalTab(tab,
					"NEAT Structure");
		} else {
			throw new WorkBenchError("No analysis available for: "
					+ obj.getClass().getSimpleName());
		}

	}

	public void analyzeWeights() {
		AnalyzeWeightsTab tab = new AnalyzeWeightsTab(this.getEncogObject());
		EncogWorkBench.getInstance().getMainWindow().openModalTab(tab,
				"Analyze Weights");
	}

	public void produceReport() {
		HTMLReport report = new HTMLReport();
		report.beginHTML();
		report.title("MLMethod");
		report.beginBody();
		report.h1(this.getEncogObject().getClass().getSimpleName());

		report.beginTable();

		if (this.getEncogObject() instanceof MLRegression) {
			MLRegression reg = (MLRegression) this.getEncogObject();
			report.tablePair("Input Count", Format.formatInteger(reg
					.getInputCount()));
			report.tablePair("Output Count", Format.formatInteger(reg
					.getOutputCount()));
		}

		if (this.getEncogObject() instanceof MLEncodable) {
			MLEncodable encode = (MLEncodable) this.getEncogObject();
			report.tablePair("Encoded Length", Format.formatInteger(encode
					.encodedArrayLength()));
		}

		report.tablePair("Resettable",
				(getEncogObject() instanceof MLResettable) ? "true" : "false");
		report.endTable();

		if (this.getEncogObject() instanceof BasicNetwork) {
			report.h3("Layers");
			report.beginTable();
			report.beginRow();
			report.header("Layer #");
			report.header("Total Count");
			report.header("Neuron Count");
			report.header("Activation Function");
			report.header("Bias");
			report.header("Context Size");
			report.header("Context Offset");
			report.endRow();

			BasicNetwork network = (BasicNetwork) getEncogObject();
			FlatNetwork flat = network.getStructure().getFlat();

			for (int l = 0; l < network.getLayerCount(); l++) {
				report.beginRow();
				report.cell(Format.formatInteger(l + 1));
				report.cell(Format.formatInteger(flat.getLayerCounts()[l]));
				report.cell(Format.formatInteger(flat.getLayerFeedCounts()[l]));
				report.cell(flat.getActivationFunctions()[l].getClass()
						.getSimpleName());
				report
						.cell(Format.formatDouble(flat.getBiasActivation()[l],
								4));
				report.cell(Format
						.formatInteger(flat.getContextTargetSize()[l]));
				report.cell(Format
						.formatInteger(flat.getContextTargetOffset()[l]));
				report.endRow();
			}
			report.endTable();
		}

		report.endBody();
		report.endHTML();
		this.editor.setText(report.toString());
	}
	
	private void restructureHopfield(EncogCollection collection, String name, MLMethod method) {
		HopfieldNetwork hopfield = (HopfieldNetwork)method;
		CreateHopfieldDialog dialog = new CreateHopfieldDialog(EncogWorkBench
				.getInstance().getMainWindow());
		dialog.getNeuronCount().setValue(hopfield.getNeuronCount());
		
		if (dialog.process() && (hopfield.getNeuronCount()!=dialog.getNeuronCount().getValue())) {
			HopfieldPattern pattern = new HopfieldPattern();
			pattern.setInputNeurons(dialog.getNeuronCount().getValue());
			this.setEncogObject( hopfield = (HopfieldNetwork)pattern.generate() );
			collection.add(name, hopfield);
			produceReport();
		}
	}
	
	private void restructureFeedforward(EncogCollection collection, String name, MLMethod method) {
		CreateFeedforward dialog = new CreateFeedforward(EncogWorkBench
				.getInstance().getMainWindow());
		BasicNetwork network = (BasicNetwork)this.getEncogObject();
		dialog.setActivationFunctionHidden(new ActivationTANH());
		dialog.getInputCount().setValue(network.getInputCount());
		dialog.getOutputCount().setValue(network.getOutputCount());
		int hiddenLayerCount = network.getLayerCount()-2;
		
		for(int i=0;i<hiddenLayerCount;i++) {
			int num = network.getLayerNeuronCount(i+1);
			String str = "Hidden Layer " + (i+1) + ": " + num + " neurons";
			dialog.getHidden().getModel().addElement(str);
		}
		
		if (dialog.process()) {
			FeedForwardPattern feedforward = new FeedForwardPattern();
			feedforward.setActivationFunction(dialog.getActivationFunctionHidden());
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
		}
	}
	
	private void performRestructure() {
		MLMethod method = (MLMethod)this.getEncogObject();
		EncogCollection collection = this.getEncogObject().getCollection();
		String name = this.getEncogObject().getName();
		
		if( method instanceof HopfieldNetwork ) {			
			restructureHopfield(collection,name,method);			
		} else if( method instanceof BasicNetwork ) {
			restructureFeedforward(collection,name,method);
		} else {
			EncogWorkBench.displayError("Error", "This Machine Learning Method cannot be restructured.");
		}
	}

}
