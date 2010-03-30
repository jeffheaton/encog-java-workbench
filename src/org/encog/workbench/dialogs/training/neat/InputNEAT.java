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

public class InputNEAT extends EncogPropertiesDialog {
	
	private ComboBoxField comboTraining;
	private ComboBoxField comboPopulation;
	private ComboBoxField comboNetwork;
	
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
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.comboTraining = new ComboBoxField("training set","Training Set",true,this.trainingSets));
		addProperty(this.comboPopulation = new ComboBoxField("population","Population",true,this.populations));
		addProperty(this.comboNetwork = new ComboBoxField("network","Base Network",true,this.networks));
		addProperty(this.maxError = new DoubleField("max error","Maximum Error",true,0,1));
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
