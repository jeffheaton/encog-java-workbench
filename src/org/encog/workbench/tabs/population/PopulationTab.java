package org.encog.workbench.tabs.population;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.CalculateScore;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.neat.NEATTraining;
import org.encog.solve.genetic.genome.Genome;
import org.encog.solve.genetic.population.BasicPopulation;
import org.encog.solve.genetic.population.Population;
import org.encog.util.Format;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.EditPopulationDialog;
import org.encog.workbench.dialogs.ExtractGenomes;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.synapse.MatrixTableModel;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.models.GeneralPopulationModel;
import org.encog.workbench.models.InnovationModel;
import org.encog.workbench.models.SpeciesModel;
import org.encog.workbench.process.training.Training;
import org.encog.workbench.tabs.EncogCommonTab;

public class PopulationTab  extends EncogCommonTab implements ActionListener {

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
	private BasicPopulation population;
	
	JTable tableGeneralPopulation;
	private final PopulationInfo pi;
	
	public PopulationTab(BasicPopulation pop) {
		super(pop);
		
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
		add(mainPanel,BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		JPanel about = new JPanel();
		about.setLayout(new BorderLayout());
		about.add(this.pi = new PopulationInfo((Population)getEncogObject()), BorderLayout.CENTER);
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
		
		this.population = pop;
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.btnTrain) {
			performTrain();
		}
		else if( e.getSource()==this.btnEdit) {
			performEdit();
		}
		else if( e.getSource()==this.btnExtract) {
			performExtract();
		}
	}

	private void performExtract() {
		ExtractGenomes dialog = new ExtractGenomes(
				EncogWorkBench.getInstance().getMainWindow(),
				this.population.getPopulationSize());
		
		if( dialog.process())
		{
			String prefix = dialog.getPrefix().getValue();
			int count = dialog.getGenomesToExtract().getValue();
			
			CalculateScore score = new TrainingSetScore(dialog.getTrainingSet());
			
			final NEATTraining train = new NEATTraining(
					score, dialog.getNetwork(),this.population);
			
			for(int i=0;i<count;i++)
			{
				Genome genome = this.population.getGenomes().get(i);
				genome.decode();
				BasicNetwork network = (BasicNetwork)genome.getOrganism();
				network.setDescription("Top genetic neural network, score=" + Format.formatDouble(genome.getScore(),5) );
				String name = prefix + i;
				EncogWorkBench.getInstance().getCurrentFile().add(name,network);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
			
		}
		
	}

	private void performEdit() {
		EditPopulationDialog dialog = new EditPopulationDialog(EncogWorkBench.getInstance().getMainWindow());
		
		dialog.getOldAgePenalty().setValue(this.population.getOldAgePenalty());
		dialog.getOldAgeThreshold().setValue(this.population.getOldAgeThreshold());
		dialog.getPopulationSize().setValue(this.population.getPopulationSize());
		dialog.getSurvivalRate().setValue(this.population.getSurvivalRate());
		dialog.getYoungBonusAgeThreshold().setValue(this.population.getYoungBonusAgeThreshold());
		dialog.getYoungScoreBonus().setValue(this.population.getYoungScoreBonus());
		
		if( dialog.process() )
		{
			this.population.setOldAgePenalty(dialog.getOldAgePenalty().getValue());
			this.population.setOldAgeThreshold(dialog.getOldAgeThreshold().getValue());
			this.population.setPopulationSize(dialog.getPopulationSize().getValue());
			this.population.setSurvivalRate(dialog.getSurvivalRate().getValue());
			this.population.setYoungBonusAgeThreshhold(dialog.getYoungBonusAgeThreshold().getValue());
			this.population.setYoungScoreBonus(dialog.getYoungScoreBonus().getValue());
			this.pi.repaint();
		}
	}

	private void performTrain() {
		Training training = new Training();
		training.perform(EncogWorkBench.getInstance().getMainWindow(), null);
		
	}


}
