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
package org.encog.workbench.tabs.population.epl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.ml.CalculateScore;
import org.encog.ml.ea.genome.Genome;
import org.encog.ml.ea.score.AdjustScore;
import org.encog.ml.ea.score.parallel.ParallelScore;
import org.encog.ml.prg.EncogProgram;
import org.encog.ml.prg.PrgCODEC;
import org.encog.ml.prg.train.PrgPopulation;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.population.epl.RescoreDialog;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.models.population.epl.EPLPopulationModel;
import org.encog.workbench.models.population.epl.OpcodeModel;
import org.encog.workbench.models.population.epl.SymbolicModel;
import org.encog.workbench.models.population.neat.SpeciesModel;
import org.encog.workbench.process.CreateNewFile;
import org.encog.workbench.process.TrainBasicNetwork;
import org.encog.workbench.tabs.EncogCommonTab;

public class EPLPopulationTab extends EncogCommonTab implements ActionListener,
		MouseListener {

	private JButton btnTrain;
	private JButton btnRescore;
	private JButton btnSort;
	private JButton btnReset;
	private JTabbedPane tabViews;

	private final JScrollPane populationScroll;
	private final JTable populationTable;
	private final EPLPopulationModel populationModel;

	private PrgPopulation population;
	private List<Genome> list;
	private final EPLPopulationInfo pi;
	
	private final JScrollPane speciesScroll;
	private final JTable speciesTable;
	private final SpeciesModel speciesModel;

	private final JScrollPane opcodesScroll;
	private final JTable opcodesTable;
	private final OpcodeModel opcodesModel;
	
	private final JScrollPane symbolicScroll;
	private final JTable symbolicTable;
	private final SymbolicModel symbolicModel;

	public EPLPopulationTab(ProjectEGFile obj) {
		super(obj);
		this.population = (PrgPopulation) obj.getObject();
		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.add(btnTrain = new JButton("Train"));
		buttonPanel.add(btnRescore = new JButton("Rescore"));
		buttonPanel.add(btnSort = new JButton("Sort"));
		buttonPanel.add(btnReset = new JButton("Reset"));
		this.btnTrain.addActionListener(this);
		this.btnSort.addActionListener(this);
		this.btnRescore.addActionListener(this);
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

		this.populationModel = new EPLPopulationModel(this);
		this.populationTable = new JTable(this.populationModel);
		this.populationTable.addMouseListener(this);
		this.populationScroll = new JScrollPane(this.populationTable);
		
		this.speciesModel = new SpeciesModel(population);
		this.speciesTable = new JTable(this.speciesModel);
		this.speciesScroll = new JScrollPane(this.speciesTable);
		this.speciesTable.addMouseListener(this);

		this.opcodesModel = new OpcodeModel(population);
		this.opcodesTable = new JTable(this.opcodesModel);
		this.opcodesScroll = new JScrollPane(this.opcodesTable);
		
		this.symbolicModel = new SymbolicModel(population);
		this.symbolicTable = new JTable(this.symbolicModel);
		this.symbolicScroll = new JScrollPane(this.symbolicTable);

		this.tabViews.addTab("General Population", this.populationScroll);
		this.tabViews.addTab("Species", this.speciesScroll);
		this.tabViews.addTab("Opcodes", this.opcodesScroll);
		this.tabViews.addTab("Symbolic", this.symbolicScroll);

		this.populationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.populationTable.getColumnModel().getColumn(0)
				.setPreferredWidth(30);
		this.populationTable.getColumnModel().getColumn(1)
				.setPreferredWidth(60);
		this.populationTable.getColumnModel().getColumn(2)
				.setPreferredWidth(90);
		this.populationTable.getColumnModel().getColumn(3)
				.setPreferredWidth(90);
		this.populationTable.getColumnModel().getColumn(4)
			.setPreferredWidth(90);
		this.populationTable.getColumnModel().getColumn(5)
				.setPreferredWidth(5000);
		
		this.list = this.population.flatten();

	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == this.btnTrain) {
				performTrain();
			} else if (e.getSource() == this.btnRescore) {
				performRescore();
			} else if (e.getSource() == this.btnSort) {
				performSort();
			} else if (e.getSource() == this.btnReset) {
				performReset();
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void performRescore() {
		RescoreDialog dialog = new RescoreDialog();

		if (dialog.process()) {
			List<AdjustScore> adjusters = new ArrayList<AdjustScore>();
			CalculateScore score = new TrainingSetScore(dialog.getTrainingSet());
			ParallelScore ps = new ParallelScore(this.population, new PrgCODEC(), adjusters,
					score, 0);
			ps.process();
			this.populationTable.repaint();
		}
	}

	private void performTrain() {

		TrainBasicNetwork t = new TrainBasicNetwork(
				(ProjectEGFile) this.getEncogObject(), this);
		t.performTrain();
		setDirty(true);
	}

	@Override
	public String getName() {
		return "Population: " + this.getEncogObject().getName();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getClickCount() == 2) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				if (target == this.populationTable) {
					EncogProgram genome = (EncogProgram) this.list
							.get(row);
					EncogProgramTab tab = new EncogProgramTab(genome);
					EncogWorkBench.getInstance().getMainWindow()
							.getTabManager().openTab(tab);
				}
			}
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}

	}

	public void performReset() {
		CreateNewFile.createPopulationEPL(null, this.population);
		setDirty(true);
		refresh();
	}

	@Override
	public void refresh() {
		this.population = (PrgPopulation)((ProjectEGFile)getEncogObject()).getObject();
		this.list = this.population.flatten();
		this.populationTable.repaint();
		this.repaint();
		this.pi.repaint();
	}

	private void performSort() {
		//this.population.sort(new MinimizeScoreComp());
		this.populationTable.repaint();
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

	/**
	 * @return The flattened list of genomes.
	 */
	public List<Genome> getList() {
		return list;
	}

	public PrgPopulation getPopulation() {
		return this.population;
	}
	
	

}
