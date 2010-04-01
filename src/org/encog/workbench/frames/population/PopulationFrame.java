package org.encog.workbench.frames.population;

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
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.models.GeneralPopulationModel;
import org.encog.workbench.models.MatrixTableModel;

public class PopulationFrame  extends EncogCommonFrame {

	private JButton btnTrain;
	private JButton btnEdit;
	private JButton btnExtract;
	private JTabbedPane tabViews;
	
	private final JScrollPane populationScroll;
	private final JTable populationTable;
	private final GeneralPopulationModel populationModel;
	
	JTable tableGeneralPopulation;
	
	public PopulationFrame(BasicPopulation pop) {
		this.setEncogObject(pop);
		this.setTitle("Editing Population: " + pop.getName());
		setSize(640,480);
		
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		content.add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.add(btnTrain = new JButton("Train"));
		buttonPanel.add(btnEdit = new JButton("Edit Population"));
		buttonPanel.add(btnExtract = new JButton("Extract Top Genomes"));
		JPanel mainPanel = new JPanel();
		content.add(mainPanel,BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		JPanel about = new JPanel();
		about.add(new PopulationInfo((Population)getEncogObject()));
		mainPanel.add(about,BorderLayout.NORTH);
		mainPanel.add(tabViews = new JTabbedPane(),BorderLayout.CENTER);
		
		this.populationModel = new GeneralPopulationModel(pop);
		this.populationTable = new JTable(this.populationModel);
		this.populationScroll = new JScrollPane(this.populationTable);
		
		this.tabViews.addTab("General Population", this.populationScroll);
		this.tabViews.addTab("Species", null);
		this.tabViews.addTab("Innovation", null);
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
