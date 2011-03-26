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
package org.encog.workbench.tabs.population.neat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.buffer.BufferedNeuralDataSet;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.training.NEATTraining;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.ExtractGenomes;
import org.encog.workbench.dialogs.population.EditNEATPopulationDialog;
import org.encog.workbench.dialogs.training.methods.InputNEAT;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.models.GeneralPopulationModel;
import org.encog.workbench.models.InnovationModel;
import org.encog.workbench.models.SpeciesModel;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.training.BasicTrainingProgress;

public class NEATPopulationTab extends EncogCommonTab implements ActionListener {

	private JButton btnTrain;
	private JButton btnEdit;
	private JButton btnExtract;
	private JTabbedPane tabViews;

	private final JScrollPane populationScroll;
	private final JTable populationTable;
	private final GeneralPopulationModel populationModel;

	private final JScrollPane speciesScroll;
	private final JTable speciesTable;
	private final SpeciesModel speciesModel;

	private final JScrollPane innovationScroll;
	private final JTable innovationTable;
	private final InnovationModel innovationModel;

	private JTable tableGeneralPopulation;
	private NEATPopulation population;
	private final NEATPopulationInfo pi;

	public NEATPopulationTab(ProjectEGFile obj) {
		super(obj);
		setDirty(true);
		this.population = (NEATPopulation) obj.getObject();
		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.add(btnTrain = new JButton("Train"));
		buttonPanel.add(btnEdit = new JButton("Edit Population"));
		buttonPanel.add(btnExtract = new JButton("Extract Top Genomes"));
		this.btnTrain.addActionListener(this);
		this.btnExtract.addActionListener(this);
		this.btnEdit.addActionListener(this);
		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		JPanel about = new JPanel();
		about.setLayout(new BorderLayout());
		about.add(this.pi = new NEATPopulationInfo(population),
				BorderLayout.CENTER);
		mainPanel.add(about, BorderLayout.NORTH);
		mainPanel.add(tabViews = new JTabbedPane(), BorderLayout.CENTER);

		this.populationModel = new GeneralPopulationModel(population);
		this.populationTable = new JTable(this.populationModel);
		this.populationScroll = new JScrollPane(this.populationTable);

		this.speciesModel = new SpeciesModel(population);
		this.speciesTable = new JTable(this.speciesModel);
		this.speciesScroll = new JScrollPane(this.speciesTable);

		this.innovationModel = new InnovationModel(population);
		this.innovationTable = new JTable(this.innovationModel);
		this.innovationScroll = new JScrollPane(this.innovationTable);

		this.tabViews.addTab("General Population", this.populationScroll);
		this.tabViews.addTab("Species", this.speciesScroll);
		this.tabViews.addTab("Innovation", this.innovationScroll);

	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == this.btnTrain) {
				performTrain();
			} else if (e.getSource() == this.btnEdit) {
				performEdit();
			} else if (e.getSource() == this.btnExtract) {
				performExtract();
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void performExtract() {
		ExtractGenomes dialog = new ExtractGenomes(EncogWorkBench.getInstance()
				.getMainWindow(), this.population.getPopulationSize());

		if (dialog.process()) {
			String prefix = dialog.getPrefix().getValue();
			int count = dialog.getGenomesToExtract().getValue();

			CalculateScore score = new TrainingSetScore(dialog.getTrainingSet());

			/*final NEATTraining train = new NEATTraining(
					score, dialog.getNetwork(),this.population);
			
			for(int i=0;i<count;i++)
			{
				Genome genome = this.population.getGenomes().get(i);
				genome.decode();
				BasicNetwork network = (BasicNetwork)genome.getOrganism();
				network.setDescription("Top genetic neural network, score=" + Format.formatDouble(genome.getScore(),5) );
				String name = prefix + i;
				//EncogWorkBench.getInstance().getCurrentFile().add(name,network);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
			*/
		}

	}

	private void performEdit() {
		EditNEATPopulationDialog dialog = new EditNEATPopulationDialog();

		dialog.getOldAgePenalty().setValue(this.population.getOldAgePenalty());
		dialog.getOldAgeThreshold().setValue(
				this.population.getOldAgeThreshold());
		dialog.getPopulationSize()
				.setValue(this.population.getPopulationSize());
		dialog.getSurvivalRate().setValue(this.population.getSurvivalRate());
		dialog.getYoungBonusAgeThreshold().setValue(
				this.population.getYoungBonusAgeThreshold());
		dialog.getYoungScoreBonus().setValue(
				this.population.getYoungScoreBonus());
		dialog.setNeatActivationFunction(this.population
				.getNeatActivationFunction());
		dialog.setOutputActivationFunction(this.population
				.getOutputActivationFunction());

		if (dialog.process()) {
			this.population.setOldAgePenalty(dialog.getOldAgePenalty()
					.getValue());
			this.population.setOldAgeThreshold(dialog.getOldAgeThreshold()
					.getValue());
			this.population.setPopulationSize(dialog.getPopulationSize()
					.getValue());
			this.population
					.setSurvivalRate(dialog.getSurvivalRate().getValue());
			this.population.setYoungBonusAgeThreshhold(dialog
					.getYoungBonusAgeThreshold().getValue());
			this.population.setYoungScoreBonus(dialog.getYoungScoreBonus()
					.getValue());
			this.population.setNeatActivationFunction(dialog
					.getNeatActivationFunction());
			this.population.setOutputActivationFunction(dialog
					.getOutputActivationFunction());
			this.pi.repaint();
		}
	}

	private void performTrain() {
		InputNEAT dialog = new InputNEAT();
		if (dialog.process()) {
			ProjectEGFile popFile = dialog.getPopulation();
			NEATPopulation pop = (NEATPopulation) popFile.getObject();

			pop.setInputCount(2);
			pop.setOutputCount(1);
			NeuralDataSet training = dialog.getTrainingSet();

			if (dialog.getLoadToMemory().getValue()) {
				training = ((BufferedNeuralDataSet) training).loadToMemory();
			}

			CalculateScore score = new TrainingSetScore(training);
			NEATTraining train = new NEATTraining(score, pop);

			BasicTrainingProgress tab = new BasicTrainingProgress(train,
					popFile, train.getTraining());
			tab.setMaxError(dialog.getMaxError().getValue() / 100);
			EncogWorkBench.getInstance().getMainWindow().openTab(tab);

		}
	}

	@Override
	public String getName() {
		return "Population: " + this.getEncogObject().getName();
	}

}
