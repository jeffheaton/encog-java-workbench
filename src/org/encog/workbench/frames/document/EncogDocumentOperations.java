/*
 * Encog(tm) Workbench v2.4
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

package org.encog.workbench.frames.document;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.encog.EncogError;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.PropertyData;
import org.encog.neural.data.TextData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.training.neat.NEATGenome;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.solve.genetic.population.BasicPopulation;
import org.encog.solve.genetic.population.Population;
import org.encog.util.file.Directory;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.BenchmarkDialog;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.dialogs.EvaluateDialog;
import org.encog.workbench.dialogs.PopulationDialog;
import org.encog.workbench.dialogs.about.AboutEncog;
import org.encog.workbench.dialogs.config.EncogConfigDialog;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.dialogs.trainingdata.CreateTrainingDataDialog;
import org.encog.workbench.dialogs.trainingdata.TrainingDataType;
import org.encog.workbench.editor.ObjectEditorFrame;
import org.encog.workbench.frames.BrowserFrame;
import org.encog.workbench.frames.PropertyDataFrame;
import org.encog.workbench.frames.TextEditorFrame;
import org.encog.workbench.frames.TrainingDataFrame;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.frames.network.NetworkFrame;
import org.encog.workbench.frames.population.PopulationFrame;
import org.encog.workbench.frames.query.NetworkQueryFrame;
import org.encog.workbench.process.CreateNeuralNetwork;
import org.encog.workbench.process.CreateTrainingData;
import org.encog.workbench.process.ImportExport;
import org.encog.workbench.process.cloud.CloudProcess;
import org.encog.workbench.process.generate.CodeGeneration;
import org.encog.workbench.util.ExtensionFilter;
import org.encog.workbench.util.NeuralConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncogDocumentOperations {

	private EncogDocumentFrame owner;
		
	@SuppressWarnings("unused")
	final private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public EncogDocumentOperations(EncogDocumentFrame owner) {
		this.owner = owner;
	}
	
	public void openItem(final Object item) {
		
		DirectoryEntry entry = (DirectoryEntry)item;
		
		if (entry.getType().equals(EncogPersistedCollection.TYPE_TRAINING)) {
			
			if (owner.getSubwindows().checkBeforeOpen(entry, TrainingDataFrame.class)) {
				BasicNeuralDataSet set = (BasicNeuralDataSet) EncogWorkBench.getInstance().getCurrentFile().find(entry);
				final TrainingDataFrame frame = new TrainingDataFrame(set);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		} else if (entry.getType().equals(EncogPersistedCollection.TYPE_BASIC_NET) ) {

			final DirectoryEntry net = (DirectoryEntry) item;
			if (owner.getSubwindows().checkBeforeOpen(net, TrainingDataFrame.class)) {
				BasicNetwork net2 = (BasicNetwork)EncogWorkBench.getInstance().getCurrentFile().find(net);
				final NetworkFrame frame = new NetworkFrame(net2);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		} else if (entry.getType().equals(EncogPersistedCollection.TYPE_TEXT)) {
			DirectoryEntry text = (DirectoryEntry)item;
			if (owner.getSubwindows().checkBeforeOpen(text, TextData.class)) {
				TextData text2 = (TextData)EncogWorkBench.getInstance().getCurrentFile().find(text);
				final TextEditorFrame frame = new TextEditorFrame(text2);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		} else if (entry.getType().equals(EncogPersistedCollection.TYPE_PROPERTY)) {
			DirectoryEntry prop = (DirectoryEntry)item;
			if (owner.getSubwindows().checkBeforeOpen(prop, PropertyData.class)) {
				PropertyData prop2 = (PropertyData)EncogWorkBench.getInstance().getCurrentFile().find(prop);
				final PropertyDataFrame frame = new PropertyDataFrame(prop2);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		} else if (entry.getType().equals(EncogPersistedCollection.TYPE_POPULATION)) {
			DirectoryEntry prop = (DirectoryEntry)item;
			if (owner.getSubwindows().checkBeforeOpen(prop, BasicPopulation.class)) {
				BasicPopulation pop2 = (BasicPopulation)EncogWorkBench.getInstance().getCurrentFile().find(prop);
				final PopulationFrame frame = new PopulationFrame(pop2);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		} 
		else
		{
			EncogWorkBench.displayError("Error", "Unknown object type.\nDo not know how to open.");
		}
	}

	public void performEditCopy() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.copy();
		}

	}

	public void performEditCut() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.cut();
		}
	}

	public void performEditPaste() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.paste();
		}

	}

	public void performExport(final Object obj) {
		ImportExport.performExport(obj);
	}

	public void performFileClose() {
		if (!checkSave()) {
			return;
		}
		EncogWorkBench.getInstance().close();
	}

	public void performFileOpen() {
		try {
			if (!checkSave()) {
				return;
			}

			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			final int result = fc.showOpenDialog(owner);
			if (result == JFileChooser.APPROVE_OPTION) {
				EncogWorkBench.load(fc.getSelectedFile().getAbsolutePath());
			}
		} catch (final Throwable e) {
			EncogWorkBench.displayError("Can't Open File", e);
		}
	}

	public void performFileSave() {
		try {
			if (EncogWorkBench.getInstance().getCurrentFileName() == null) {
				performFileSaveAs();
			} else {
				EncogWorkBench.save();
			}
		} catch (final Throwable e) {
			EncogWorkBench.displayError("Can't Save File", e);
		}
	}

	public void performFileSaveAs() {
		try {
			final JFileChooser fc = new JFileChooser();
			fc.setFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			final int result = fc.showSaveDialog(owner);

			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				// no extension
				if (ExtensionFilter.getExtension(file) == null) {
					file = new File(file.getPath() + ".eg");
				}
				// wrong extension
				else if (ExtensionFilter.getExtension(file).compareTo("eg") != 0) {

					if (JOptionPane
							.showConfirmDialog(
									owner,
									"Encog files are usually stored with the .eg extension. \nDo you wish to save with the name you specified?",
									"Warning", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
				}

				// file doesn't exist yet
				if (file.exists()) {
					final int response = JOptionPane.showConfirmDialog(null,
							"Overwrite existing file?", "Confirm Overwrite",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}

				EncogWorkBench.save(file.getAbsolutePath());
			}
		} catch (final Throwable e) {
			EncogWorkBench.displayError("Can't Save File", e);
		}

	}

	public void performGenerateCode() {
		final CodeGeneration code = new CodeGeneration();
		code.processCodeGeneration();

	}

	public void performImport(final Object obj) {
		ImportExport.performImport(obj);
	}

	public void performNetworkQuery() {
		final DirectoryEntry item = (DirectoryEntry) owner.getContents().getSelectedValue();

		if (owner.getSubwindows().checkBeforeOpen(item, NetworkQueryFrame.class)) {
			BasicNetwork net = (BasicNetwork)EncogWorkBench.getInstance().getCurrentFile().find(item);
			final NetworkQueryFrame frame = new NetworkQueryFrame(net);
			frame.setVisible(true);
			owner.getSubwindows().add(frame);
		}

	}
	
	public static String generateNextID(String base)
	{
		int index = 1;
		String result = "";
		
		do {
			result = base + index;
			index++;
		} while( EncogWorkBench.getInstance().getCurrentFile().exists(result) );
		
		
		return result;
	}
	
	public void performObjectsCreate() {

		try
		{
		SelectItem itemTraining, itemNetwork, itemOptions, itemText, itemPopulation;
		final List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(itemPopulation = new SelectItem("NEAT Population"));
		list.add(itemNetwork = new SelectItem("Neural Network"));
		list.add(itemOptions = new SelectItem("Property Data"));
		list.add(itemText = new SelectItem("Text"));
		list.add(itemTraining = new SelectItem("Training Data"));
				
		final SelectDialog dialog = new SelectDialog(owner, list);
		if(! dialog.process() )
			return;
		
		final SelectItem result = dialog.getSelected();

		if (result == itemNetwork) {
			CreateNeuralNetwork.process( generateNextID("network-") );
		} else if (result == itemTraining) {
			performCreateTrainingData();
		}  else if(result == itemText)
		{
			final TextData text = new TextData();
			text.setDescription("A text file");
			text.setText("Insert text here.");
			EncogWorkBench.getInstance().getCurrentFile().add(generateNextID("text-") ,text);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if( result == itemOptions )
		{
			final PropertyData prop = new PropertyData();
			prop.setDescription("Some property data");
			EncogWorkBench.getInstance().getCurrentFile().add(generateNextID("properties-") ,prop);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if( result==itemPopulation) {
			performCreatePopulation();
		}
		}
		catch(EncogError t)
		{
			EncogWorkBench.displayError("Error creating object", t);
			logger.error("Error creating object",t);
		}
	}
	private void performCreatePopulation() {
		PopulationDialog dialog = new PopulationDialog(owner);
		
		if( dialog.process() )
		{
			int populationSize = dialog.getPopulationSize().getValue();
			BasicPopulation pop = new BasicPopulation(populationSize);
			
			for (int i = 0; i < populationSize; i++) {
				pop.add(
						new NEATGenome(null, pop.assignGenomeID(),
								dialog.getInputNeurons().getValue(), 
								dialog.getOutputNeurons().getValue()));
			}

			
			pop.setDescription("Population");
			EncogWorkBench.getInstance().getCurrentFile().add(generateNextID("population-"),pop);
		}
		
	}

	public void performObjectsDelete() {
		final Object object = owner.getContents().getSelectedValue();
		if (object != null) {
			if (owner.getSubwindows().find((DirectoryEntry) object) != null) {
				EncogWorkBench.displayError("Can't Delete Object",
						"This object can not be deleted while it is open.");
				return;
			}

			if (JOptionPane.showConfirmDialog(owner,
					"Are you sure you want to delete this object?", "Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				EncogWorkBench.getInstance().getCurrentFile().delete((DirectoryEntry)object);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	
	public void performBrowse() {
		BrowserFrame browse = new BrowserFrame();
		browse.setVisible(true);
	}

	public void performHelpAbout() {
		AboutEncog dialog = new AboutEncog();
		dialog.process();		
	}
	
	boolean checkSave() {
		final String currentFileName = EncogWorkBench.getInstance()
				.getCurrentFileName();

		if (currentFileName != null
				|| EncogWorkBench.getInstance().getCurrentFile().getDirectory()
						.size() > 0) {
			final String f = currentFileName != null ? currentFileName
					: "Untitled";
			final int response = JOptionPane.showConfirmDialog(null, "Save "
					+ f + ", first?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				performFileSave();
				return true;
			} else if (response == JOptionPane.NO_OPTION) {
				return true;
			} else {
				return false;
			}

		}

		return true;
	}

	public void performEditConfig() {
		//ObjectEditorFrame config = new ObjectEditorFrame(EncogWorkBench.getInstance().getConfig());
		//config.setVisible(true);
		EncogConfigDialog dialog = new EncogConfigDialog(EncogWorkBench.getInstance().getMainWindow());
		if( dialog.process() )
		{
			
		}
	}

	public void performObjectsProperties() {
		final DirectoryEntry selected = (DirectoryEntry)owner.getContents().getSelectedValue();
		final EditEncogObjectProperties dialog = new EditEncogObjectProperties
		(owner, selected);
		dialog.process();		
	}

	public void performFileRevert() {
		if( EncogWorkBench.askQuestion("Revert", "Would you like to revert to the last time you saved?") )
		{
			EncogWorkBench.load(EncogWorkBench.getInstance().getCurrentFileName());
			EncogWorkBench.getInstance().getCurrentFile().buildDirectory();
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
		
	}

	public void performEvaluate() {
		try
		{
		EvaluateDialog dialog = new EvaluateDialog(EncogWorkBench.getInstance().getMainWindow());
		if( dialog.process() )
		{
			BasicNetwork network = dialog.getNetwork();
			NeuralDataSet training = dialog.getTrainingSet();
			
			
			double error = network.calculateError(training);
			EncogWorkBench.displayMessage("Error For this Network", ""+error);
		}
		}
		catch(Throwable t)
		{
			EncogWorkBench.displayError("Error Evaluating Network", t);
		}
		
	}

	public void performBenchmark() {
		if( EncogWorkBench.askQuestion("Benchmark", "Would you like to benchmark Encog on this machine?\nThis process will take several minutes to complete.") ) {
			BenchmarkDialog dialog = new BenchmarkDialog();
			dialog.setVisible(true);
		}
		
	}
	
	public void performCreateTrainingData()
	{
		CreateTrainingDataDialog dialog = new CreateTrainingDataDialog(
				EncogWorkBench.getInstance().getMainWindow());
		
		dialog.setType(TrainingDataType.Empty);
		
		if( dialog.process() )
		{
			switch(dialog.getType())
			{
				case Empty:
					CreateTrainingData.createEmpty();
					break;
				case ImportCSV:
					CreateTrainingData.createImportCSV();
					break;
				case MarketWindow:
					CreateTrainingData.createMarketWindow();
					break;
				case PredictWindow:
					CreateTrainingData.createPredictWindow();
					break;
				case Random:
					CreateTrainingData.createRandom();
					break;
				case XORTemp:
					CreateTrainingData.createXORTemp();
					break;
				case XOR:
					CreateTrainingData.createXOR();
					break;
			}		
		}
	}

	public void performCloudLogin() {
		CloudProcess.Login();
		
	}

}
