package org.encog.workbench.dialogs.activation;

import java.awt.Frame;

import javax.swing.JDialog;

import org.encog.workbench.dialogs.common.DecimalField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.PropertyType;

public class GaussianDialog extends EncogPropertiesDialog {

	private DecimalField width;
	private DecimalField peak;
	private DecimalField center;
	
	public GaussianDialog(Frame owner) {
		super(owner);
		init();
	}
	
	public GaussianDialog(JDialog owner) {
		super(owner);
		init();
	}
	
	private void init()
	{
		setTitle("Gaussian Parameters");
		setSize(400,400);
		setLocation(200,200);
		addProperty(this.width = new DecimalField("width","Gaussian Width",true,-1,1));
		addProperty(this.peak = new DecimalField("peak","Gaussian Peak",true,-1,1));
		addProperty(this.center = new DecimalField("center","Gaussian Center",true,-1,1));
		render();
	}

	public DecimalField getGaussianWidth() {
		return width;
	}

	public DecimalField getGaussianPeak() {
		return peak;
	}

	public DecimalField getGaussianCenter() {
		return center;
	}


	
	

}
