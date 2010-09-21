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
package org.encog.workbench.dialogs;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class ExtractGenomes extends EncogPropertiesDialog {
		
	private final ComboBoxField comboTraining;
	private final IntegerField genomesToExtract;
	private final TextField prefix;
	private final ComboBoxField comboNetwork;
	private final List<String> networks = new ArrayList<String>();
	private final List<String> trainingSets = new ArrayList<String>();
	
	public ExtractGenomes(Frame owner, int populationSize) {
		super(owner);

		setTitle("Extract Top Genomes");
		setSize(400,200);
		setLocation(200,200);
		
		findData();
		
		addProperty(this.genomesToExtract = new IntegerField("genomes to extract", "Genomes to Extract", true, 0 , populationSize));
		addProperty(this.prefix = new TextField("prefix","Neural Network Prefix",true));
		addProperty(this.comboNetwork = new ComboBoxField("network","Base Network",true,this.networks));
		addProperty(this.comboTraining = new ComboBoxField("training set","Training Set",true,this.trainingSets));
		
		render();
		this.prefix.setValue("network-");
	}
	
	public NeuralDataSet getTrainingSet() {
		String trainingName = (String)this.comboTraining.getSelectedValue();
		return (NeuralDataSet)EncogWorkBench.getInstance().getCurrentFile().find(trainingName);
	}

	
	private void findData() {
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_TRAINING) ) {
				this.trainingSets.add(obj.getName());
			}
			else if (obj.getType().equals(EncogPersistedCollection.TYPE_BASIC_NET) ) {
				this.networks.add(obj.getName());
			}
		}
	}
	
	public BasicNetwork getNetwork() {
		String name = (String)this.comboNetwork.getSelectedValue();
		return (BasicNetwork)EncogWorkBench.getInstance().getCurrentFile().find(name);
	}

	public IntegerField getGenomesToExtract() {
		return genomesToExtract;
	}

	public TextField getPrefix() {
		return prefix;
	}
	
	
}
