package org.encog.workbench.dialogs.trainingdata;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.createnetwork.NeuralNetworkType;

public class CreateTrainingDataDialog extends EncogCommonDialog implements
	ListSelectionListener {
	
	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private TrainingDataType type;

	public CreateTrainingDataDialog(Frame owner) {
		super(owner);
		setTitle("Create a Neural Network");

		this.setSize(500, 250);
		this.setLocation(50, 100);
		final Container content = getBodyPanel();
		content.setLayout(new GridLayout(1, 2));

		content.add(this.scroll1);
		content.add(this.scroll2);

		this.model.addElement("Empty Training Set");
		this.model.addElement("Import Training Set from CSV");
		this.model.addElement("Import Training Set from EG File");
		this.model.addElement("Market Data Training Set");
		this.model.addElement("Prediction Window from CSV");
		this.model.addElement("Random Training Set");		
		this.model.addElement("XOR Temporal Training Set");
		this.model.addElement("XOR Training Set");
		
		this.list.addListSelectionListener(this);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.text.setEditable(false);
		scroll2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5882600361686632769L;
	

	@Override
	public void collectFields() throws ValidationException {
		switch (list.getSelectedIndex()) {
		case 0:
			this.type = TrainingDataType.Empty;
			break;
		case 1:
			this.type = TrainingDataType.ImportCSV;
			break;
		case 2:
			this.type = TrainingDataType.ImportEG;
			break;			
		case 3:
			this.type = TrainingDataType.MarketWindow;
			break;
		case 4:
			this.type = TrainingDataType.PredictWindow;
			break;
		case 5:
			this.type = TrainingDataType.Random;
			break;
		case 6:
			this.type = TrainingDataType.XORTemp;
			break;
		case 7:
			this.type = TrainingDataType.XOR;
			break;		
		}
	}

	@Override
	public void setFields() {
		switch (type) {
		case Empty:
			this.list.setSelectedIndex(0);
			break;
		case ImportCSV:
			this.list.setSelectedIndex(1);
			break;
		case ImportEG:
			this.list.setSelectedIndex(2);
			break;			
		case MarketWindow:
			this.list.setSelectedIndex(3);
			break;
		case PredictWindow:
			this.list.setSelectedIndex(4);
			break;
		case Random:
			this.list.setSelectedIndex(5);
			break;
		case XORTemp:
			this.list.setSelectedIndex(6);
			break;
		case XOR:
			this.list.setSelectedIndex(7);
			break;		
		}

	}

	public TrainingDataType getType() {
		return type;
	}

	public void setType(TrainingDataType type) {
		this.type = type;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text
					.setText("Empty Neural Train - Creates a blank neural network that you can add layers and synapses to.  This allows you to create a neural network from scratch.");
			break;
		case 1:
			this.text
					.setText("ADALINE Network - The Adaptive Neural Linear Element(ADALINE) is a simple, effectively single layer network that can learn exact pattern matches.");
			break;
			
		case 2:
			this.text
					.setText("Adaptive Resonance Theory (ART1) - The Adaptive Resonance Theory (ART1) is a neural network structure used to classify patterns.");
			break;

		case 3:
			this.text
					.setText("Bidirection Associate Memory  (BAM) - The BAM is almost a neural network \"Hash Table\".  It allows patterns to be mapped, bidirectionally, to each other.  The left and right side of the mapping do not need to be of the same size.");
			break;

		case 4:
			this.text
					.setText("The Boltzmann Machine is a simple singler layer, fully connected, neural network that contains a temperature element.  This temperature element introduces randomness into the boltzmann machine.");
			break;

		case 5:
			this.text
					.setText("The Counterpropagation Network (CPN) is a hybrid neural network that has both self organizing map and feedforward elements.  The SOM part of the CPN network is trained using an Instar algorithm, the feedforward is trained using an Oustar algorithm.");
			break;

		case 6:
			this.text
					.setText("Feed Forward Neural Network - A simple neural network type where synapses are made from an input layer to zero or more hidden layers, and finally to an output layer.  The feedforward neural network is one of the most common types of neural network in use.  It is suitable for many types of problems.  Feedforward neural networks are often trained with simulated annealing, genetic algorithms or one of the propagation techniques.");
			break;
			
		case 7:
			this.text
					.setText("Feedforward Radial Basis Function (RBF) Network - A feedforward network with an input layer, output layer and a hidden layer.  The hidden layer is based on a radial basis function.  The RBF generally used is the gaussian function.  Several RBF's in the hidden layer allow the RBF network to approximate a more complex activation function than a typical feedforward neural network.  RBF networks are used for pattern recognition.  They can be trained using genetic, annealing or one of the propagation techniques.  Other means must be employed to determine the structure of the RBF's used in the hidden layer.");
			break;			

		case 8:
			this.text
					.setText("Self Organizing Map (SOM) - A neural network that contains two layers and implements a winner take all strategy in the output layer.  Rather than taking the output of individual neurons, the neuron with the highest output is considered the winner.  SOM's are typically used for classification, where the output neurons represent groups that the input neurons are to be classified into.  SOM's are usually trained with a competitive learning strategy.");
			break;

		case 9:
			this.text
					.setText("Hopfield Neural Network - A simple single layer recurrent neural network.  The Hopfield neural network is trained with a special algorithm that teaches it to learn to recognize patterns.  The Hopfield network will indicate that the pattern is recognized by echoing it back.  Hopfield neural networks are typically used for pattern recognition.");
			break;

		case 10:
			this.text
					.setText("Simple Recurrent Network (SRN) Elman Style - A recurrent neural network that has a context layer.  The context layer holds the previous output from the hidden layer and then echos that value back to the hidden layer's input.  The hidden layer then always receives input from its previous iteration's output.  Elman neural networks are generally trained using genetic, simulated annealing, or one of the propagation techniques.  Elman neural networks are typically used for prediction.");
			break;

		case 11:
			this.text
					.setText("Simple Recurrent Network (SRN) Jordan Style - A recurrent neural network that has a context layer.  The context layer holds the previous output from the output layer and then echos that value back to the hidden layer's input.  The hidden layer then always receives input from the previous iteration's output layer.  Jordan neural networks are generally trained using genetic, simulated annealing, or one of the propagation techniques.  Jordan neural networks are typically used for prediction.");
			break;

		case 12:
			this.text
					.setText("Simple Recurrent Network (SRN) Self Organizing Map - A recurrent self organizing map that has an input and output layer, just as a regular SOM.  However, the RSOM has a context layer as well.  This context layer echo's the previous iteration's output back to the input layer of the neural network.  RSOM's are trained with a competitive learning algorithm, just as a non-recurrent SOM.  RSOM's can be used to classify temporal data, or to predict.");
			break;
			
		case 13:
			this.text.setText("NeuroEvolution of Augmenting Topologies (NEAT) is a neural network that starts with only an input and ouput layer.  The hidden neurons are evolved as the network trains.");
			break;			
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);

	}


}
