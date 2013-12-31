/*
 * Encog(tm) Workbench v3.2
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2013 Heaton Research, Inc.
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.ml.ea.genome.Genome;
import org.encog.ml.ea.species.Species;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.population.neat.EditNEATPopulationDialog;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.models.population.neat.GeneralPopulationModel;
import org.encog.workbench.models.population.neat.InnovationModel;
import org.encog.workbench.models.population.neat.SpeciesModel;
import org.encog.workbench.process.TrainBasicNetwork;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.visualize.structure.GenomeStructureTab;

public class NEATPopulationTab extends EncogCommonTab implements ActionListener, MouseListener {

	private JButton btnTrain;
	private JButton btnEdit;
	private JButton btnReset;
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
	private List<Genome> list;

	public NEATPopulationTab(ProjectEGFile obj) {
		super(obj);
		setDirty(true);
		this.population = (NEATPopulation) obj.getObject();
		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.add(btnTrain = new JButton("Train"));
		buttonPanel.add(btnEdit = new JButton("Edit Population"));
		buttonPanel.add(btnReset = new JButton("Reset"));
		this.btnTrain.addActionListener(this);
		this.btnEdit.addActionListener(this);
		this.btnReset.addActionListener(this);
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
		this.populationTable.addMouseListener(this);
		this.populationScroll = new JScrollPane(this.populationTable);		

		this.speciesModel = new SpeciesModel(population);
		this.speciesTable = new JTable(this.speciesModel);
		this.speciesScroll = new JScrollPane(this.speciesTable);
		this.speciesTable.addMouseListener(this);

		this.innovationModel = new InnovationModel(population);
		this.innovationTable = new JTable(this.innovationModel);
		this.innovationScroll = new JScrollPane(this.innovationTable);

		this.tabViews.addTab("General Population", this.populationScroll);
		this.tabViews.addTab("Species", this.speciesScroll);
		this.tabViews.addTab("Innovation", this.innovationScroll);

		this.list = this.population.flatten();
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == this.btnTrain) {
				performTrain();
			} else if (e.getSource() == this.btnEdit) {
				performEdit();
			} else if (e.getSource() == this.btnReset) {
				performReset();
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void performEdit() {
		EditNEATPopulationDialog dialog = new EditNEATPopulationDialog();

		dialog.getPopulationSize()
				.setValue(this.population.getPopulationSize());
		dialog.getSurvivalRate().setValue(this.population.getSurvivalRate());
		
		if( !this.population.isHyperNEAT()) {
			ActivationFunction af = population.getActivationFunctions().getList().get(0).getObj();
		}
	
		dialog.getActivationCycles().setValue(population.getActivationCycles());

		if (dialog.process()) {
			this.population.setPopulationSize(dialog.getPopulationSize()
					.getValue());
			this.population
					.setSurvivalRate(dialog.getSurvivalRate().getValue());
			if(!this.population.isHyperNEAT()) {
				this.population.setNEATActivationFunction(dialog
					.getNeatActivationFunction());
			}

			this.population.setActivationCycles(dialog.getActivationCycles().getValue());
			this.pi.repaint();
		}
	}

	private void performTrain() {
		
		TrainBasicNetwork t = new TrainBasicNetwork((ProjectEGFile)this.getEncogObject(),this);
		t.performTrain();
	}

	@Override
	public String getName() {
		return "Population: " + this.getEncogObject().getName();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
	         JTable target = (JTable)e.getSource();
	         int row = target.getSelectedRow();
	         if( target==this.populationTable) {
	        	 NEATGenome genome = (NEATGenome)this.list.get(row);
	        	 GenomeStructureTab tab = new GenomeStructureTab(genome);
	        	 EncogWorkBench.getInstance().getMainWindow().getTabManager().openTab(tab);
	         } else if( target==this.speciesTable ) {
	        	 Species species = this.population.getSpecies().get(row);
	        	 NEATGenome genome = (NEATGenome)species.getLeader();
	        	 if(genome!=null) {
	        		 GenomeStructureTab tab = new GenomeStructureTab(genome);
	        		 EncogWorkBench.getInstance().getMainWindow().getTabManager().openTab(tab);
	        	 }
	         }
	   }
		
	}
	
	public void performReset() {
		String str = EncogWorkBench.getInstance().displayInput("New population size");
		try {
			int sz = Integer.parseInt(str);
			if( sz<10 ) {
				EncogWorkBench.displayError("Error", "Population size must be at least 10.");				
				return;
			}
			this.population.reset();
			this.repaint();
			this.pi.repaint();
		} catch(NumberFormatException ex) {
			EncogWorkBench.displayError("Error", "Invalid population size.");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
