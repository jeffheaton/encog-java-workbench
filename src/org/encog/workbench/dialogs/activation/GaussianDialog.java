/*
 * Encog(tm) Workbench v2.5
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
