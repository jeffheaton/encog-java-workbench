/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.dialogs.activation;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.encog.neural.activation.ActivationBiPolar;
import org.encog.neural.activation.ActivationCompetitive;
import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationGaussian;
import org.encog.neural.activation.ActivationLOG;
import org.encog.neural.activation.ActivationLinear;
import org.encog.neural.activation.ActivationRamp;
import org.encog.neural.activation.ActivationSIN;
import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.activation.ActivationSoftMax;
import org.encog.neural.activation.ActivationStep;
import org.encog.neural.activation.ActivationTANH;
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
	private JButton gaussian;
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

		this.gaussian = new JButton("Params");

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.add(this.derivative);
		buttons.add(this.gaussian);

		upper.add(buttons, BorderLayout.EAST);
		this.derivative.addActionListener(this);
		this.gaussian.addActionListener(this);
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
		
		if( this.activation instanceof ActivationGaussian ||
			this.activation instanceof ActivationStep || 
			this.activation instanceof ActivationRamp )
		{
			this.gaussian.setEnabled(true);
		}
		else
		{
			this.gaussian.setEnabled(false);
		}

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
		} else if (e.getSource() == this.gaussian) {
			if (this.activation instanceof ActivationGaussian) {
				GaussianDialog dialog = new GaussianDialog(this);
				ActivationGaussian gaussian = (ActivationGaussian) this.activation;
				dialog.getGaussianCenter().setValue(
						gaussian.getGausian().getCenter());
				dialog.getGaussianPeak().setValue(
						gaussian.getGausian().getPeak());
				dialog.getGaussianWidth().setValue(
						gaussian.getGausian().getWidth());
				if (dialog.process()) {
					double peak = dialog.getGaussianPeak().getValue();
					double width = dialog.getGaussianWidth().getValue();
					double center = dialog.getGaussianCenter().getValue();
					this.activation = new ActivationGaussian(center,peak,width);
					this.equation.setupEquation(activation,!this.derivative.isSelected());
				}
			}
			else if (this.activation instanceof ActivationStep) {
				StepDialog dialog = new StepDialog(this);
				ActivationStep step = (ActivationStep) this.activation;
				dialog.getCenter().setValue(
						step.getCenter());
				dialog.getLow().setValue(
						step.getLow());
				dialog.getHigh().setValue(
						step.getHigh());
				if (dialog.process()) {
					double low = dialog.getLow().getValue();
					double high = dialog.getHigh().getValue();
					double center = dialog.getCenter().getValue();
					this.activation = new ActivationStep();
					((ActivationStep)this.activation).setCenter( center );
					((ActivationStep)this.activation).setLow(low );
					((ActivationStep)this.activation).setHigh( high);
					this.equation.setupEquation(activation,!this.derivative.isSelected());
				}
			}
			else if (this.activation instanceof ActivationRamp) {
				RampDialog dialog = new RampDialog(this);
				ActivationRamp step = (ActivationRamp) this.activation;

				dialog.getLowThreshold().setValue(
						step.getLow());
				dialog.getHighThreshold().setValue(
						step.getHigh());
				dialog.getLowValue().setValue(
						step.getLow());
				dialog.getHighValue().setValue(
						step.getHigh());
				if (dialog.process()) {
					double lowThreshold = dialog.getLowThreshold().getValue();
					double highThreshold = dialog.getHighThreshold().getValue();
					double lowValue = dialog.getLowThreshold().getValue();
					double highValue = dialog.getHighThreshold().getValue();
					
					this.activation = new ActivationRamp();
					((ActivationRamp)this.activation).setThresholdLow(lowThreshold );
					((ActivationRamp)this.activation).setThresholdHigh(highThreshold);
					((ActivationRamp)this.activation).setLow(lowValue );
					((ActivationRamp)this.activation).setHigh(highValue);
					
					this.equation.setupEquation(activation,!this.derivative.isSelected());
				}
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
