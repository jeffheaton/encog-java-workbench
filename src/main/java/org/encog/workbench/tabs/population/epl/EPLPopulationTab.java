/*
 * Encog(tm) Workbench v3.2
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
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
package org.encog.workbench.tabs.population.epl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.ml.ea.genome.Genome;
import org.encog.ml.prg.train.PrgPopulation;
import org.encog.neural.neat.NEATNetwork;
import org.encog.neural.neat.NEATSpecies;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.util.file.FileUtil;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.population.neat.EditNEATPopulationDialog;
import org.encog.workbench.dialogs.population.neat.ExtractGenomes;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.models.population.epl.EPLPopulationModel;
import org.encog.workbench.models.population.neat.GeneralPopulationModel;
import org.encog.workbench.models.population.neat.InnovationModel;
import org.encog.workbench.models.population.neat.SpeciesModel;
import org.encog.workbench.process.CreateNewFile;
import org.encog.workbench.process.TrainBasicNetwork;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.visualize.structure.GenomeStructureTab;

public class EPLPopulationTab extends EncogCommonTab implements ActionListener, MouseListener {

	private JButton btnTrain;
	private JButton btnEdit;
	private JButton btnExtract;
	private JButton btnReset;
	private JTabbedPane tabViews;

	private final JScrollPane populationScroll;
	private final JTable populationTable;
	private final EPLPopulationModel populationModel;

	private PrgPopulation population;
	private final EPLPopulationInfo pi;

	public EPLPopulationTab(ProjectEGFile obj) {
		super(obj);
		setDirty(true);
		this.population = (PrgPopulation) obj.getObject();
		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.add(btnTrain = new JButton("Train"));
		buttonPanel.add(btnEdit = new JButton("Edit Population"));
		buttonPanel.add(btnExtract = new JButton("Extract Top Genomes"));
		buttonPanel.add(btnReset = new JButton("Reset"));
		this.btnTrain.addActionListener(this);
		this.btnExtract.addActionListener(this);
		this.btnEdit.addActionListener(this);
		this.btnReset.addActionListener(this);
		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		JPanel about = new JPanel();
		about.setLayout(new BorderLayout());
		about.add(this.pi = new EPLPopulationInfo(population),
				BorderLayout.CENTER);
		mainPanel.add(about, BorderLayout.NORTH);
		mainPanel.add(tabViews = new JTabbedPane(), BorderLayout.CENTER);

		this.populationModel = new EPLPopulationModel(population);
		this.populationTable = new JTable(this.populationModel);
		this.populationTable.addMouseListener(this);
		this.populationScroll = new JScrollPane(this.populationTable);		

		this.tabViews.addTab("General Population", this.populationScroll);
		//this.tabViews.addTab("Species", this.speciesScroll);
		//this.tabViews.addTab("Innovation", this.innovationScroll);
		
		this.populationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.populationTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		this.populationTable.getColumnModel().getColumn(1).setPreferredWidth(60);
		this.populationTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		this.populationTable.getColumnModel().getColumn(3).setPreferredWidth(90);
		this.populationTable.getColumnModel().getColumn(4).setPreferredWidth(5000);

	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == this.btnTrain) {
				performTrain();
			} else if (e.getSource() == this.btnEdit) {
				//performEdit();
			} else if (e.getSource() == this.btnExtract) {
				//performExtract();
			} else if (e.getSource() == this.btnReset) {
				performReset();
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
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
	        	 NEATGenome genome = (NEATGenome)this.population.get(row);
	        	 GenomeStructureTab tab = new GenomeStructureTab(genome);
	        	 EncogWorkBench.getInstance().getMainWindow().getTabManager().openTab(tab);
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
			CreateNewFile.resetEPLPopulation(this.population,sz);
			this.populationTable.repaint();
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
