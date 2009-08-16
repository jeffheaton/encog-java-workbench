/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.encog.workbench.dialogs.training;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;
import org.encog.workbench.dialogs.createnetwork.NeuralNetworkType;
import org.encog.workbench.frames.network.TrainingType;

public class ChooseTrainingMethodDialog extends EncogCommonDialog implements
		ListSelectionListener {

	private DefaultListModel model = new DefaultListModel();
	private JList list = new JList(model);
	private JTextArea text = new JTextArea();
	private JScrollPane scroll1 = new JScrollPane(list);
	private JScrollPane scroll2 = new JScrollPane(text);
	private TrainingType type;

	public ChooseTrainingMethodDialog(Frame owner, BasicNetwork network) {
		super(owner);
		setTitle("Create a Training Method");

		this.setSize(500, 250);
		this.setLocation(50, 100);
		final Container content = getBodyPanel();
		content.setLayout(new GridLayout(1, 2));

		content.add(this.scroll1);
		content.add(this.scroll2);

		this.model.addElement("Propagation - Resilient");
		this.model.addElement("Propagation - Backpropagation");
		this.model.addElement("Propagation - Manhattan");
		this.model.addElement("Genetic Algorithm");
		this.model.addElement("Simulated Annealing");
		this.model.addElement("Self Organizing Map Training(SOM)");
		this.model.addElement("ADALINE");
		this.model.addElement("Instar");
		this.model.addElement("Outstar");

		this.list.addListSelectionListener(this);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.text.setEditable(false);
		scroll2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.type = TrainingType.PropagationResilient;
	}

	@Override
	public void collectFields() throws ValidationException {
		switch (list.getSelectedIndex()) {
		case 0:
			this.type = TrainingType.PropagationResilient;
			break;
		case 1:
			this.type = TrainingType.PropagationBack;
			break;
		case 2:
			this.type = TrainingType.PropagationManhattan;
			break;
		case 3:
			this.type = TrainingType.Genetic;
			break;
		case 4:
			this.type = TrainingType.Annealing;
			break;
		case 5:
			this.type = TrainingType.SOM;
			break;
		case 6:
			this.type = TrainingType.ADALINE;
			break;
		case 7:
			this.type = TrainingType.Instar;
			break;
		case 8:
			this.type = TrainingType.Outstar;
			break;
		}
	}

	@Override
	public void setFields() {
		switch (type) {
		case PropagationResilient:
			this.list.setSelectedIndex(0);
			break;
		case PropagationBack:
			this.list.setSelectedIndex(1);
			break;
		case PropagationManhattan:
			this.list.setSelectedIndex(2);
			break;
		case Genetic:
			this.list.setSelectedIndex(3);
			break;
		case Annealing:
			this.list.setSelectedIndex(4);
			break;
		case SOM:
			this.list.setSelectedIndex(5);
			break;
		case ADALINE:
			this.list.setSelectedIndex(6);
			break;
		case Instar:
			this.list.setSelectedIndex(7);
			break;
		case Outstar:
			this.list.setSelectedIndex(8);
			break;
		}
	}

	public TrainingType getType() {
		return type;
	}

	public void setType(TrainingType type) {
		this.type = type;
	}

	public void valueChanged(ListSelectionEvent e) {
		switch (list.getSelectedIndex()) {
		case 0:
			this.text
					.setText("Resilient Propagation is one of the fastest training algorithms available for Encog.  Resilient propagation is a supervised learning method.  It works similarly to Backpropagation, except that an individual delta is calculated for each connection.  These delta values are gradually changed until the neural network weight matrix converges on a potentially ideal weight matrix.  Resilient propagation allows several parameters to be set, but it is rare that these training parameters need to be changed beyond their default values.  Resilient propagation can be used with feedforward and simple recurrent neural networks.");
			break;
		case 1:
			this.text
					.setText("Backpropagation is one of the oldest training techniques for feedforward and simple recurrent neural networks.  It is a supervised learning method, and is an implementation of the Delta rule. The term is an abbreviation for \"backwards propagation of errors\". Backpropagation requires that the activation function used by the layers is differentiable.  For most training situations where backpropagation could be applied, resilient propagation is a better solution.");
			break;
		case 2:
			this.text
					.setText("The Manhattan update rule works similarly to Backpropagation, except a single update delta is provided.  It is a supervised learning method.  This update value must be chosen correctly for the neural network to properly train.  The Manhattan update rule can be used with feedforward and simple recurrent neural networks.  For most cases where the Manhattan update rule could be applied, Resilient propagation is a better choice.");
			break;
		case 3:
			this.text
					.setText("A genetic algorithm trains a neural network with a process that emulates biological mutation and natural selection.  This is implemented as a supervised training method.  Many neural networks are created that will simulate different organisms.  These neural networks will compete to see which has the lowest error rate.  Those neural networks that have the lowest error rates will have elements of their weight matrix combined to produce offspring.  Some offspring will have random mutations introduced.  This cycle continues and hopefully produces lower error rates from the top neural networks with each iteration.");
			break;
		case 4:
			this.text
					.setText("Simulated annealing is a process where the weights are randomized according to a temperature.  As this temperature decreases, the weights are kept if they improve the error rate of the neural network.  This training technique is implemented as supervised training.  This process simulates the metallurgical process of annealing where metals are slowly cooled to produce a more stable molecular structure.");
			break;
		case 5:
			this.text
					.setText("Self Organizing Maps(SOM) are trained in such a way that similar input patterns will cause a single neuron to become the winner.  The winner is the neuron with the highest activation.  This is an unsupervised training technique.  The input data will be grouped into categories defined by the number of output neurons.");
			break;
		case 6:
			this.text
					.setText("An Adaptive Linear Neural (ADALINE) network is trained with a very simple method based on the delta rule.  This is a supervised training technique that allows each Adaline element to recognize a single input pattern.)");
			break;
		case 7:
			this.text
					.setText("Instar training is generally used to train a counterpropagation neural network (CPN). Instar training is unsupervised, and teaches the Instar layer of the neural network to categorize the input into a number of categories expressed by the number of neurons in the instar layer. Instar training should be done before outstar, though they are accomplished in two discrete steps.");
			break;
		case 8:
			this.text
					.setText("Outstar training is generally used to train a counterpropagation neural network (CPN).  Outstar training is supervised, and teaches the Outstar layer of the neural network to produce output that is close to the expected output.  Outstar training should be done after instar, though they are accomplished in two discrete steps.");
			break;

		}

		this.text.setSelectionStart(0);
		this.text.setSelectionEnd(0);

	}

}
