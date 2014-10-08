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
package org.encog.workbench.dialogs.training;

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

import org.encog.ml.MLMethod;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public class ChooseBasicNetworkTrainingMethod extends EncogCommonDialog implements
		ListSelectionListener {

	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private BasicNetworkTrainingType type;

	public ChooseBasicNetworkTrainingMethod(Frame owner, MLMethod network) {
		super(owner);
		setTitle("Choose a Training Method");

		this.setSize(500, 250);
		this.setLocation(50, 100);
		final Container content = getBodyPanel();
		content.setLayout(new GridLayout(1, 2));

		content.add(this.scroll1);
		content.add(this.scroll2);

		this.model.addElement("Propagation - Scaled Conjugate Gradient (SCG)");
		this.model.addElement("Propagation - Resilient (RPROP)");
		this.model.addElement("Propagation - Backpropagation (BPROP)");
		this.model.addElement("Propagation - Quick Propagation (QPROP)");
		this.model.addElement("Propagation - Manhattan (MPROP)");
		this.model.addElement("Levenberg-Marquardt");
		this.model.addElement("Genetic Algorithm");
		this.model.addElement("Simulated Annealing");
		this.model.addElement("ADALINE Training");
		this.model.addElement("Singular Value Decomposition (SVD)");
		this.model.addElement("Particle Swarm Optimization (PSO)");
		this.model.addElement("Nelder Mead");

		this.list.addListSelectionListener(this);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.text.setEditable(false);
		scroll2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.type = BasicNetworkTrainingType.PropagationResilient;
	}

	@Override
	public void collectFields() throws ValidationException {
		switch (list.getSelectedIndex()) {
		case 0:
			this.type = BasicNetworkTrainingType.SCG;
			break;
		case 1:
			this.type = BasicNetworkTrainingType.PropagationResilient;
			break;
		case 2:
			this.type = BasicNetworkTrainingType.PropagationBack;
			break;
		case 3:
			this.type = BasicNetworkTrainingType.PropagationQuick;
			break;
		case 4:
			this.type = BasicNetworkTrainingType.PropagationManhattan;
			break;
		case 5:
			this.type = BasicNetworkTrainingType.LevenbergMarquardt;
			break;	
		case 6:
			this.type = BasicNetworkTrainingType.Genetic;
			break;
		case 7:
			this.type = BasicNetworkTrainingType.Annealing;
			break;
		case 8:
			this.type = BasicNetworkTrainingType.ADALINE;
			break;
		case 9:
			this.type = BasicNetworkTrainingType.SVD;
			break;		
		case 10:
			this.type = BasicNetworkTrainingType.PSO;
			break;
		case 11:
			this.type = BasicNetworkTrainingType.NelderMead;
			break;
		}
	}

	@Override
	public void setFields() {
		switch (type) {
		case SCG:
			this.list.setSelectedIndex(0);
			break;
		case PropagationResilient:
			this.list.setSelectedIndex(1);
			break;
		case PropagationBack:
			this.list.setSelectedIndex(2);
			break;
		case PropagationQuick:
			this.list.setSelectedIndex(3);
			break;
		case PropagationManhattan:
			this.list.setSelectedIndex(4);
			break;
		case LevenbergMarquardt:
			this.list.setSelectedIndex(5);
			break;
		case Genetic:
			this.list.setSelectedIndex(6);
			break;
		case Annealing:
			this.list.setSelectedIndex(7);
			break;
		case ADALINE:
			this.list.setSelectedIndex(8);
			break;
		case SVD:
			this.list.setSelectedIndex(9);
			break;
		case PSO:
			this.list.setSelectedIndex(10);
			break;
		case NelderMead:
			this.list.setSelectedIndex(11);
			break;
		}
	}

	public BasicNetworkTrainingType getTheType() {
		return type;
	}

	public void setType(BasicNetworkTrainingType type) {
		this.type = type;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text.setText("Scaled Conjugate Gradient (SCG) is an effective training algorithms available for Encog.  It is a supervised learning method. Training is accomplished by use of the Scaled Conjugate Gradient function minimization technique.");
			break;
		case 1:
			this.text
					.setText("Resilient Propagation is one of the fastest training algorithms available for Encog.  Resilient propagation is a supervised learning method.  It works similarly to Backpropagation, except that an individual delta is calculated for each connection.  These delta values are gradually changed until the neural network weight matrix converges on a potentially ideal weight matrix.  Resilient propagation allows several parameters to be set, but it is rare that these training parameters need to be changed beyond their default values.  Resilient propagation can be used with feedforward and simple recurrent neural networks.");
			break;
		case 2:
			this.text
					.setText("Backpropagation is one of the oldest training techniques for feedforward and simple recurrent neural networks.  It is a supervised learning method, and is an implementation of the Delta rule. The term is an abbreviation for \"backwards propagation of errors\". Backpropagation requires that the activation function used by the layers is differentiable.  For most training situations where backpropagation could be applied, resilient propagation is a better solution.");
			break;
		case 3:
			this.text
					.setText("QPROP is a training method based on Newton's  method.  It can be very quick to train, just like RPROP.  However, it has many of the same local minimum issues that other trainers have.");
			break;
		case 4:
			this.text
					.setText("The Manhattan update rule works similarly to Backpropagation, except a single update delta is provided.  It is a supervised learning method.  This update value must be chosen correctly for the neural network to properly train.  The Manhattan update rule can be used with feedforward and simple recurrent neural networks.  For most cases where the Manhattan update rule could be applied, Resilient propagation is a better choice.");
			break;
		case 5:
			this.text
					.setText("The Levenberg Marquardt training algorithm is one of the fastest training algorithms available for Encog.  It is based on the LevenbergMarquardt method for minimizing a function.  This training algorithm can only be used for neural networks that contain a single output neuron.  This is a supervised training method.");
			break;
		case 6:
			this.text
					.setText("A genetic algorithm trains a neural network with a process that emulates biological mutation and natural selection.  This is implemented as a supervised training method.  Many neural networks are created that will simulate different organisms.  These neural networks will compete to see which has the lowest error rate.  Those neural networks that have the lowest error rates will have elements of their weight matrix combined to produce offspring.  Some offspring will have random mutations introduced.  This cycle continues and hopefully produces lower error rates from the top neural networks with each iteration.");
			break;
		case 7:
			this.text
					.setText("Simulated annealing is a process where the weights are randomized according to a temperature.  As this temperature decreases, the weights are kept if they improve the error rate of the neural network.  This training technique is implemented as supervised training.  This process simulates the metallurgical process of annealing where metals are slowly cooled to produce a more stable molecular structure.");
			break;
			
		case 8:
			this.text
					.setText("An ADALINE neural network is a very simple 2-layer network.  This training type should be used with ADALINE neural networks.");
			break;
		case 9:
			this.text
					.setText("SVD training can only be used for RBF networks at this time.  Further, the RBF network must have a single output neuron.  SVD is very fast, and will accomplish training in a single iteration.");
			break;
			
		case 10:
			this.text.setText("PSO can be a very effective training algorithm.  PSO performs global search, and is nearly as susceptible to local minima as propagation training.");
			break;
		case 11:
			this.text.setText("Nelder Mead is an optimization method that typically provides good results for neural network.  Nelder Mead does require considerable memory, as it must allocate a grid equal to approximately the square of the number of weights in your neural network.");
			break;
		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);
	}
}
