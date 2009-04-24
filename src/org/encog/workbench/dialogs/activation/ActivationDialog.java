package org.encog.workbench.dialogs.activation;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

public class ActivationDialog extends EncogCommonDialog implements ItemListener  {
	
	public static final String[] ACTIVATION_FUNCTION = {
			"ActivationBiPolar",
			"ActivationGaussian",
			"ActivationLinear",
			"ActivationLOG",
			"ActivationSigmoid",
			"ActivationSIN",
			"ActivationSoftMax",
			"ActivationTANH"
	};
	
	private JComboBox select = new JComboBox(ACTIVATION_FUNCTION);
	private EquationPanel equation;
	private JCheckBox derivative;
	private ActivationFunction activation;

	public ActivationDialog(Frame owner) {
		super(owner);
		this.setSize(600,300);
		JPanel contents = this.getBodyPanel();
		contents.setLayout(new BorderLayout());
		contents.add(this.equation = new EquationPanel(),BorderLayout.CENTER);
		JPanel upper = new JPanel();
		upper.setLayout(new BorderLayout());
		contents.add(upper, BorderLayout.NORTH);
		this.select.addItemListener(this);
		this.derivative = new JCheckBox("View Derivative");
		upper.add(select, BorderLayout.CENTER);
		upper.add(this.derivative, BorderLayout.EAST);
		this.derivative.addActionListener(this);
	}

	@Override
	public void collectFields() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		
	}
	
	public void changeEquation()
	{
		boolean der = this.derivative.isSelected();
			switch(this.select.getSelectedIndex())
			{
				case 0:
					this.equation.setupEquation(this.activation = new ActivationBiPolar(), !der);
					break;
				case 1:
					this.equation.setupEquation(this.activation = new ActivationGaussian(0,Math.sqrt(1),Math.sqrt(1)), !der);
					break;
				case 2:
					this.equation.setupEquation(this.activation = new ActivationLinear(), !der);
					break;
				case 3:
					this.equation.setupEquation(this.activation = new ActivationLOG(), !der);
					break;
				case 4:
					this.equation.setupEquation(this.activation = new ActivationSigmoid(), !der);
					break;
				case 5:
					this.equation.setupEquation(this.activation = new ActivationSIN(), !der);
					break;
				case 6:
					this.equation.setupEquation(this.activation = new ActivationSoftMax(), !der);
					break;
				case 7:
					this.equation.setupEquation(this.activation = new ActivationTANH(), !der);
					break;
					
			}
		

	}

	public void itemStateChanged(ItemEvent e) {
		if( e.getSource()==this.select)
		{
		changeEquation();	
		}
	}
	
	public void actionPerformed(final ActionEvent e) {
		super.actionPerformed(e);
		if( e.getSource()==this.derivative)
		{
		changeEquation();
		}
	}

	public ActivationFunction getActivation() {
		return activation;
	}

	public void setActivation(ActivationFunction activation) {
		this.activation = activation;
		for(int i=0;i<ACTIVATION_FUNCTION.length;i++)
		{
			if( ACTIVATION_FUNCTION[i].equals(activation.getClass().getSimpleName()))
			{
				this.select.setSelectedIndex(i);
			}
		}
		this.changeEquation();
	}
	

}
