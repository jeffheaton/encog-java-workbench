package org.encog.workbench.dialogs.activation;

import java.awt.Frame;

import javax.swing.JDialog;

import org.encog.workbench.dialogs.common.DecimalField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;

public class StepDialog extends EncogPropertiesDialog {

	private DecimalField low;
	private DecimalField high;
	private DecimalField center;
	
	public StepDialog(Frame owner) {
		super(owner);
		init();
	}
	
	public StepDialog(JDialog owner) {
		super(owner);
		init();
	}
	
	private void init()
	{
		setTitle("Step Parameters");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.center = new DecimalField("center","Step Center",true,-1,1));
		addProperty(this.low = new DecimalField("low","Step Low",true,-1,1));
		addProperty(this.high = new DecimalField("high","Step High",true,-1,1));
		render();
	}

	

	public DecimalField getLow() {
		return low;
	}

	public DecimalField getHigh() {
		return high;
	}

	public DecimalField getCenter() {
		return center;
	}

	
}
