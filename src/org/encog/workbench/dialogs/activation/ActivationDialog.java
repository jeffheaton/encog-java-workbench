/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.activation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.encog.engine.network.activation.ActivationBiPolar;
import org.encog.engine.network.activation.ActivationCompetitive;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationGaussian;
import org.encog.engine.network.activation.ActivationLOG;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationRamp;
import org.encog.engine.network.activation.ActivationSIN;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.engine.network.activation.ActivationStep;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public class ActivationDialog extends EncogCommonDialog implements ItemListener {

	public static final String[] ACTIVATION_FUNCTION = { "ActivationBiPolar", "ActivationCompetitive",
			"ActivationGaussian", "ActivationLinear", "ActivationLOG",
			"ActivationSigmoid", "ActivationSIN", "ActivationSoftMax",
			"ActivationStep", "ActivationTANH", "ActivationRamp" };

	private JComboBox select = new JComboBox(ACTIVATION_FUNCTION);
	private EquationPanel equation;
	private JCheckBox derivative;
	private JButton params;
	private ActivationFunction activation;

	public ActivationDialog(JFrame owner) {
		super(owner);
		init();
	}

	public ActivationDialog(JDialog owner) {
		super(owner);
		init();
	}

	private void init() {
		this.setSize(600, 300);
		JPanel contents = this.getBodyPanel();
		contents.setLayout(new BorderLayout());
		contents.add(this.equation = new EquationPanel(), BorderLayout.CENTER);
		JPanel upper = new JPanel();
		upper.setLayout(new BorderLayout());
		contents.add(upper, BorderLayout.NORTH);
		this.select.addItemListener(this);
		this.derivative = new JCheckBox("View Derivative");
		upper.add(select, BorderLayout.CENTER);

		this.params = new JButton("Params");

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.add(this.derivative);
		buttons.add(this.params);

		upper.add(buttons, BorderLayout.EAST);
		this.derivative.addActionListener(this);
		this.params.addActionListener(this);
	}

	@Override
	public void collectFields() throws ValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub

	}

	public void changeEquation() {
		boolean der = this.derivative.isSelected();
		ActivationFunction newActivation = null;
		
		switch (this.select.getSelectedIndex()) {
		case 0:
			newActivation = new ActivationBiPolar();
			break;
		case 1:
			newActivation = new ActivationCompetitive();
			break;
		case 2:
			newActivation = new ActivationGaussian(0, 1, 1);
			break;
		case 3:
			newActivation = new ActivationLinear();
			break;
		case 4:
			newActivation = new ActivationLOG();
			break;
		case 5:
			newActivation = new ActivationSigmoid();
			break;
		case 6:
			newActivation = new ActivationSIN();
			break;
		case 7:
			newActivation = new ActivationSoftMax();
			break;
		case 8:
			newActivation = new ActivationStep();
			break;						
		case 9:
			newActivation = new ActivationTANH();
			break;
		case 10:
			newActivation = new ActivationRamp();
			break;			

		}
		
		if( this.activation.getClass() != newActivation.getClass() )
		{
			this.activation = newActivation;
		}
		
		this.equation.setupEquation(newActivation,!der);
		
		this.params.setEnabled(this.activation.getParams().length>0);
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == this.select) {
			changeEquation();
		}
	}

	public void actionPerformed(final ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.derivative) {
			changeEquation();
		} else if (e.getSource() == this.params) {
			
			ParamsDialog dlg = new ParamsDialog(this,this.activation);
			dlg.load(this.activation);
			if( dlg.process() )
			{
				dlg.save(this.activation);
				this.equation.setupEquation(this.activation,!this.derivative.isSelected());
			}
		}
	}

	public ActivationFunction getActivation() {
		return activation;
	}

	public void setActivation(ActivationFunction activation) {
		this.activation = activation;
		for (int i = 0; i < ACTIVATION_FUNCTION.length; i++) {
			if (ACTIVATION_FUNCTION[i].equals(activation.getClass()
					.getSimpleName())) {
				this.select.setSelectedIndex(i);
			}
		}
		this.equation.setupEquation(this.activation,!this.derivative.isSelected());
	}

}
