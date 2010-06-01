package org.encog.workbench.dialogs.activation;

import java.awt.Frame;

import javax.swing.JDialog;

import org.encog.workbench.dialogs.common.DecimalField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;

public class RampDialog extends EncogPropertiesDialog {

	private DecimalField lowThreshold;
	private DecimalField highThreshold;
	private DecimalField lowValue;
	private DecimalField highValue;
	
	
	public RampDialog(Frame owner) {
		super(owner);
		init();
	}
	
	public RampDialog(JDialog owner) {
		super(owner);
		init();
	}
	
	private void init()
	{
		setTitle("Ramp Parameters");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.lowThreshold = new DecimalField("low threshold","Low Threshold",true,-1,1));
		addProperty(this.highThreshold = new DecimalField("high threshold","High Threshold",true,-1,1));
		addProperty(this.lowValue = new DecimalField("low threshold","Low Value",true,-1,1));
		addProperty(this.highValue = new DecimalField("high threshold","High Value",true,-1,1));
		render();
	}

	/**
	 * @return the lowThreshold
	 */
	public DecimalField getLowThreshold() {
		return lowThreshold;
	}

	/**
	 * @return the highThreshold
	 */
	public DecimalField getHighThreshold() {
		return highThreshold;
	}


	/**
	 * @return the lowValue
	 */
	public DecimalField getLowValue() {
		return lowValue;
	}

	/**
	 * @return the highValue
	 */
	public DecimalField getHighValue() {
		return highValue;
	}
	
	




}
