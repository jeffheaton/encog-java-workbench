package org.encog.workbench.dialogs.training.neat;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.solve.genetic.population.Population;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class InputNEAT extends EncogPropertiesDialog {
	
	private final ComboBoxField comboTraining;
	private final ComboBoxField comboPopulation;
	private final ComboBoxField comboNetwork;
		
	private final DoubleField activationMutationRate;
	private final DoubleField chanceAddLink;
	private final DoubleField chanceAddNode;
	private final DoubleField chanceAddRecurrentLink;
	private final DoubleField compatibilityThreshold;
	private final DoubleField crossoverRate;
	private final DoubleField maxActivationPerturbation;
	private final IntegerField maxNumberOfSpecies;
	private final DoubleField maxPermittedNeurons;
	private final DoubleField maxWeightPerturbation;
	private final DoubleField mutationRate;
	private final IntegerField numAddLinkAttempts;
	private final IntegerField numGensAllowedNoImprovement;
	private final IntegerField numTrysToFindLoopedLink;
	private final IntegerField numTrysToFindOldLink;
	private final DoubleField probabilityWeightReplaced;
	
	/**
	 * Text field that holds the maximum training error.
	 */
	private DoubleField maxError;
	
	/**
	 * All available training sets to display in the combo box.
	 */
	private final List<String> trainingSets = new ArrayList<String>();
	
	/**
	 * All available networks to display in the combo box.
	 */
	private final List<String> networks = new ArrayList<String>();
	
	/**
	 * All available populations to display in the combo box.
	 */
	private final List<String> populations = new ArrayList<String>();

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public InputNEAT(final Frame owner) {
		
		super(owner);
		findData();
		setTitle("NeuroEvolution of Augmenting Topologies (NEAT)");
		setSize(400,500);
		setLocation(200,200);
		addProperty(this.comboTraining = new ComboBoxField("training set","Training Set",true,this.trainingSets));
		addProperty(this.comboPopulation = new ComboBoxField("population","Population",true,this.populations));
		addProperty(this.comboNetwork = new ComboBoxField("network","Base Network",true,this.networks));
		addProperty(this.maxError = new DoubleField("max error","Maximum Error",true,0,1));
		
		addProperty(this.activationMutationRate=new DoubleField("activation mutation rate","Activation Mutation Rate",true,0,1));
		addProperty(this.chanceAddLink=new DoubleField("chance add link","Chance to Add Link",true,0,1));
		addProperty(this.chanceAddNode=new DoubleField("chance add node","Chance to Add Node",true,0,1));
		addProperty(this.chanceAddRecurrentLink=new DoubleField("chance add recur link","Chance to Add Recurrent Link",true,0,1));
		addProperty(this.compatibilityThreshold=new DoubleField("compat threshold","Species Compat Threshold",true,0,1));
		addProperty(this.crossoverRate=new DoubleField("crossover rate","Crossover Rate",true,0,1));
		addProperty(this.maxActivationPerturbation=new DoubleField("max act pert","Max Activation Perturb",true,0,1));
		addProperty(this.maxNumberOfSpecies=new IntegerField("max num species","Max Num Species(0=none)",true,0,1));
		addProperty(this.maxPermittedNeurons=new DoubleField("max neurons","Max Neurons",true,0,1));
		addProperty(this.maxWeightPerturbation=new DoubleField("max weight pert","Max Weight Perturb",true,0,1));
		addProperty(this.mutationRate=new DoubleField("mutation rate","Mutation Rate",true,0,1));
		addProperty(this.numAddLinkAttempts=new IntegerField("link add attempts","Num Link Attempts",true,0,1));
		addProperty(this.numGensAllowedNoImprovement=new IntegerField("max gens no improve","Max Generations w/No Improvement",true,0,1));
		addProperty(this.numTrysToFindLoopedLink=new IntegerField("tries for looped link","Tries for Linked Loop",true,0,1));
		addProperty(this.numTrysToFindOldLink=new IntegerField("tries find old link","Tries to Find Old Link",true,0,1));
		addProperty(this.probabilityWeightReplaced=new DoubleField("prob weight replaced","Chance Weight Replaced",true,0,1));
		
		
		render();
	}



	/**
	 * Obtain the data needed to fill in the network and training set
	 * combo boxes.
	 */
	private void findData() {
		for (final DirectoryEntry obj : EncogWorkBench.getInstance()
				.getCurrentFile().getDirectory()) {
			if (obj.getType().equals(EncogPersistedCollection.TYPE_POPULATION) ) {
				this.populations.add(obj.getName());
			} else if (obj.getType().equals(EncogPersistedCollection.TYPE_TRAINING) ) {
				this.trainingSets.add(obj.getName());
			} else if (obj.getType().equals(EncogPersistedCollection.TYPE_BASIC_NET) ) {
				this.networks.add(obj.getName());
			}
		}
	}

	/**
	 * @return The network that the user chose.
	 */
	public Population getPopulation() {
		String networkName = (String)this.comboPopulation.getSelectedValue();
		return (Population)EncogWorkBench.getInstance().getCurrentFile().find(networkName);
	}

	/**
	 * @return The training set that the user chose.
	 */
	public NeuralDataSet getTrainingSet() {
		String trainingName = (String)this.comboTraining.getSelectedValue();
		return (NeuralDataSet)EncogWorkBench.getInstance().getCurrentFile().find(trainingName);
	}
	
	/**
	 * @return The training set that the user chose.
	 */
	public BasicNetwork getNetwork() {
		String name = (String)this.comboNetwork.getSelectedValue();
		return (BasicNetwork)EncogWorkBench.getInstance().getCurrentFile().find(name);
	}



	public DoubleField getMaxError() {
		return maxError;
	}
	
	
	
}
