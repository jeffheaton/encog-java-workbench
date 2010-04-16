package org.encog.workbench.tabs.population;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.solve.genetic.population.BasicPopulation;
import org.encog.solve.genetic.population.Population;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.models.GeneralPopulationModel;
import org.encog.workbench.models.InnovationModel;
import org.encog.workbench.models.MatrixTableModel;
import org.encog.workbench.models.SpeciesModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class PopulationTab  extends EncogCommonTab {

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
	
	JTable tableGeneralPopulation;
	
	public PopulationTab(BasicPopulation pop) {
		super(pop);
		
		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.add(btnTrain = new JButton("Train"));
		buttonPanel.add(btnEdit = new JButton("Edit Population"));
		buttonPanel.add(btnExtract = new JButton("Extract Top Genomes"));
		JPanel mainPanel = new JPanel();
		add(mainPanel,BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		JPanel about = new JPanel();
		about.add(new PopulationInfo((Population)getEncogObject()));
		mainPanel.add(about,BorderLayout.NORTH);
		mainPanel.add(tabViews = new JTabbedPane(),BorderLayout.CENTER);
		
		this.populationModel = new GeneralPopulationModel(pop);
		this.populationTable = new JTable(this.populationModel);
		this.populationScroll = new JScrollPane(this.populationTable);
		
		this.speciesModel = new SpeciesModel(pop);
		this.speciesTable = new JTable(this.speciesModel);
		this.speciesScroll = new JScrollPane(this.speciesTable);
		
		this.innovationModel = new InnovationModel(pop);
		this.innovationTable = new JTable(this.innovationModel);
		this.innovationScroll = new JScrollPane(this.innovationTable);		
		
		this.tabViews.addTab("General Population", this.populationScroll);
		this.tabViews.addTab("Species", this.speciesScroll);
		this.tabViews.addTab("Innovation", this.innovationScroll);
	}


}
