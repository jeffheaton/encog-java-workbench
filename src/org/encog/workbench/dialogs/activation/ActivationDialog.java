package org.encog.workbench.dialogs.activation;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.encog.neural.activation.ActivationBiPolar;
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

	public ActivationDialog(Frame owner) {
		super(owner);
		this.setSize(600,300);
		JPanel contents = this.getBodyPanel();
		contents.setLayout(new BorderLayout());
		contents.add(this.equation = new EquationPanel(),BorderLayout.CENTER);
		contents.add(select, BorderLayout.NORTH);
		this.select.addItemListener(this);
	}

	@Override
	public void collectFields() throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		
	}

	public void itemStateChanged(ItemEvent e) {
		if( e.getSource()==this.select)
		{
			switch(this.select.getSelectedIndex())
			{
				case 0:
					this.equation.setupEquation(new ActivationBiPolar(), true);
					break;
				case 1:
					this.equation.setupEquation(new ActivationGaussian(0,1,1), true);
					break;
				case 2:
					this.equation.setupEquation(new ActivationLinear(), true);
					break;
				case 3:
					this.equation.setupEquation(new ActivationLOG(), true);
					break;
				case 4:
					this.equation.setupEquation(new ActivationSigmoid(), true);
					break;
				case 5:
					this.equation.setupEquation(new ActivationSIN(), true);
					break;
				case 6:
					this.equation.setupEquation(new ActivationSoftMax(), true);
					break;
				case 7:
					this.equation.setupEquation(new ActivationTANH(), true);
					break;
					
			}
		}
		
	}

}
