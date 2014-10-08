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
package org.encog.workbench.dialogs.createnetwork;

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

public class CreateNeuralNetworkDialog extends EncogCommonDialog implements
		ListSelectionListener {

	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private NeuralNetworkType theType;

	public CreateNeuralNetworkDialog(Frame owner) {
		super(owner);
		setTitle("Create a Network");

		this.setSize(500, 250);
		this.setLocation(50, 100);
		final Container content = getBodyPanel();
		content.setLayout(new GridLayout(1, 2));

		content.add(this.scroll1);
		content.add(this.scroll2);

		this.model.addElement("Create from Encog Factory Code");
		this.model.addElement("Automatic Perceptron from Training Set");
		this.model.addElement("ADALINE Neural Network");
		this.model.addElement("Adaptive Resonance Theory 1 (ART1)");
		this.model.addElement("Bayesian Network");
		this.model.addElement("Bidirectional Associate Memory (BAM)");
		this.model.addElement("Boltzmann Machine");
		this.model.addElement("Counterpropagation Neural Network (CPN)");
		this.model.addElement("Feedforward Neural Network");
		this.model.addElement("Feedforward - Radial Basis");
		this.model.addElement("Self Organizing Map (SOM)");
		this.model.addElement("Hopfield Neural Network");
		this.model.addElement("PNN/GRNN - Probabilistic/General Regression Neural Network");
		this.model.addElement("Recurrent - Elman");
		this.model.addElement("Recurrent - Jordan");
		this.model.addElement("Support Vector Machine (SVM)");
		

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
			this.theType = NeuralNetworkType.EncogFactoryCode;
			break;	
		case 1:
			this.theType = NeuralNetworkType.Automatic;
			break;			
		case 2:
			this.theType = NeuralNetworkType.ADALINE;
			break;
		case 3:
			this.theType = NeuralNetworkType.ART1;
			break;	
		case 4:
			this.theType = NeuralNetworkType.BayesianNetwork;
			break;	
		case 5:
			this.theType = NeuralNetworkType.BAM;
			break;
		case 6:
			this.theType = NeuralNetworkType.Boltzmann;
			break;
		case 7:
			this.theType = NeuralNetworkType.CPN;
			break;
		case 8:
			this.theType = NeuralNetworkType.Feedforward;
			break;
		case 9:
			this.theType = NeuralNetworkType.RBF;
			break;
		case 10:
			this.theType = NeuralNetworkType.SOM;
			break;
		case 11:
			this.theType = NeuralNetworkType.Hopfield;
			break;
		case 12:
			this.theType = NeuralNetworkType.PNN;
			break;
		case 13:
			this.theType = NeuralNetworkType.Elman;
			break;
		case 14:
			this.theType = NeuralNetworkType.Jordan;
			break;
		case 15:
			this.theType = NeuralNetworkType.SVM;
			break;
		}
	}

	@Override
	public void setFields() {
		switch (theType) {
		case EncogFactoryCode:
			this.list.setSelectedIndex(0);
			break;
		case Automatic:
			this.list.setSelectedIndex(1);
			break;			
		case ADALINE:
			this.list.setSelectedIndex(2);
			break;
		case ART1:
			this.list.setSelectedIndex(3);
			break;		
		case BayesianNetwork:
			this.list.setSelectedIndex(4);
			break;
		case BAM:
			this.list.setSelectedIndex(5);
			break;
		case Boltzmann:
			this.list.setSelectedIndex(6);
			break;
		case CPN:
			this.list.setSelectedIndex(7);
			break;
		case Feedforward:
			this.list.setSelectedIndex(8);
			break;
		case RBF:
			this.list.setSelectedIndex(9);
			break;
		case SOM:
			this.list.setSelectedIndex(10);
			break;
		case Hopfield:
			this.list.setSelectedIndex(11);
			break;
		case PNN:
			this.list.setSelectedIndex(12);
			break;
		case Elman:
			this.list.setSelectedIndex(13);
			break;
		case Jordan:
			this.list.setSelectedIndex(14);
			break;
		case SVM:
			this.list.setSelectedIndex(15);
			break;
		}

	}

	public NeuralNetworkType getTheType() {
		return theType;
	}

	public void setTheType(NeuralNetworkType t) {
		this.theType = t;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text
					.setText("Encog factory code allows you define many differnt types of machine learning method with a type and architecture code.  This most commonly used for Analyst scripts, however, you can create a method using these codes here.");
			break;
		case 1:
			this.text
					.setText("Automatic from Training Set - Creates a blank neural network that you can add layers and synapses to.  This allows you to create a neural network from scratch.");
			break;
			
		case 2:			
			this.text
					.setText("ADALINE Network - The Adaptive Neural Linear Element(ADALINE) is a simple, effectively single layer network that can learn exact pattern matches.");
			break;
			
		case 3:
			this.text
					.setText("Adaptive Resonance Theory (ART1) - The Adaptive Resonance Theory (ART1) is a neural network structure used to classify patterns.");
			break;
			
		case 4:
			this.text
					.setText("Bayesian Network - The Bayesian Network is a machine learning method based on Bayes Rule.  The output from a Bayes net is always a probability of the query that was presented to it.  The query of a Bayesian network allows the definition of input and output elements to change at will (without retraining).  Additionally, missing information is handled very well.");
			break;

		case 5:
			this.text
					.setText("Bidirection Associate Memory  (BAM) - The BAM is almost a neural network \"Hash Table\".  It allows patterns to be mapped, bidirectionally, to each other.  The left and right side of the mapping do not need to be of the same size.");
			break;

		case 6:
			this.text
					.setText("The Boltzmann Machine is a simple singler layer, fully connected, neural network that contains a temperature element.  This temperature element introduces randomness into the boltzmann machine.");
			break;

		case 7:
			this.text
					.setText("The Counterpropagation Network (CPN) is a hybrid neural network that has both self organizing map and feedforward elements.  The SOM part of the CPN network is trained using an Instar algorithm, the feedforward is trained using an Oustar algorithm.");
			break;

		case 8:
			this.text
					.setText("Feed Forward Neural Network - A simple neural network type where synapses are made from an input layer to zero or more hidden layers, and finally to an output layer.  The feedforward neural network is one of the most common types of neural network in use.  It is suitable for many types of problems.  Feedforward neural networks are often trained with simulated annealing, genetic algorithms or one of the propagation techniques.");
			break;
			
		case 9:
			this.text
					.setText("Feedforward Radial Basis Function (RBF) Network - A feedforward network with an input layer, output layer and a hidden layer.  The hidden layer is based on a radial basis function.  The RBF generally used is the gaussian function.  Several RBF's in the hidden layer allow the RBF network to approximate a more complex activation function than a typical feedforward neural network.  RBF networks are used for pattern recognition.  They can be trained using genetic, annealing or one of the propagation techniques.  Other means must be employed to determine the structure of the RBF's used in the hidden layer.");
			break;			

		case 10:
			this.text
					.setText("Self Organizing Map (SOM) - A neural network that contains two layers and implements a winner take all strategy in the output layer.  Rather than taking the output of individual neurons, the neuron with the highest output is considered the winner.  SOM's are typically used for classification, where the output neurons represent groups that the input neurons are to be classified into.  SOM's are usually trained with a competitive learning strategy.");
			break;

		case 11:
			this.text
					.setText("Hopfield Neural Network - A simple single layer recurrent neural network.  The Hopfield neural network is trained with a special algorithm that teaches it to learn to recognize patterns.  The Hopfield network will indicate that the pattern is recognized by echoing it back.  Hopfield neural networks are typically used for pattern recognition.");
			break;
			
		case 12:
			this.text.setText("Probabilistic (PNN) and General Regression Neural Networks (GRNN) have similar architectures.  Encog represents both as the same object type.  The PNN is used for classification, the GRNN is used for regression.  ");
			break;
		case 13:
			this.text
					.setText("Simple Recurrent Network (SRN) Elman Style - A recurrent neural network that has a context layer.  The context layer holds the previous output from the hidden layer and then echos that value back to the hidden layer's input.  The hidden layer then always receives input from its previous iteration's output.  Elman neural networks are generally trained using genetic, simulated annealing, or one of the propagation techniques.  Elman neural networks are typically used for prediction.");
			break;

		case 14:
			this.text
					.setText("Simple Recurrent Network (SRN) Jordan Style - A recurrent neural network that has a context layer.  The context layer holds the previous output from the output layer and then echos that value back to the hidden layer's input.  The hidden layer then always receives input from the previous iteration's output layer.  Jordan neural networks are generally trained using genetic, simulated annealing, or one of the propagation techniques.  Jordan neural networks are typically used for prediction.");
			break;
		case 15:
			this.text.setText("A Support Vector Machine (SVM) is not really a neural network.  However, SVM�s work very similar in terms of their input and output.  SVM�s can often be trained faster and with better accuracy than neural networks.");
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);

	}

}
