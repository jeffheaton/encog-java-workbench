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
import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationGaussian;
import org.encog.neural.activation.ActivationLOG;
import org.encog.neural.activation.ActivationLinear;
import org.encog.neural.activation.ActivationSIN;
import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.activation.ActivationSoftMax;
import org.encog.neural.activation.ActivationTANH;
import org.encog.util.math.rbf.GaussianFunction;
import org.encog.workbench.dialogs.common.EncogCommonDialog;
import org.encog.workbench.dialogs.common.ValidationException;

public class ActivationDialog extends EncogCommonDialog implements ItemListener {

	public static final String[] ACTIVATION_FUNCTION = { "ActivationBiPolar",
			"ActivationGaussian", "ActivationLinear", "ActivationLOG",
			"ActivationSigmoid", "ActivationSIN", "ActivationSoftMax",
			"ActivationTANH" };

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

		this.gaussian = new JButton("Gaussian");

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
			newActivation = new ActivationGaussian(0, 1, 1);
			break;
		case 2:
			newActivation = new ActivationLinear();
			break;
		case 3:
			newActivation = new ActivationLOG();
			break;
		case 4:
			newActivation = new ActivationSigmoid();
			break;
		case 5:
			newActivation = new ActivationSIN();
			break;
		case 6:
			newActivation = new ActivationSoftMax();
			break;
		case 7:
			newActivation = new ActivationTANH();
			break;

		}
		
		if( this.activation.getClass() != newActivation.getClass() )
		{
			this.equation.setupEquation(newActivation,!der);
			this.activation = newActivation;
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
