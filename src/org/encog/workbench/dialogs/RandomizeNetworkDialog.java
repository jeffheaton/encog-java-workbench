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

package org.encog.workbench.dialogs;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.process.generate.Generate.GenerateLanguage;

public class RandomizeNetworkDialog extends EncogPropertiesDialog  {
	private final DoubleField high;
	private final DoubleField low;
	private final DoubleField mean;
	private final DoubleField deviation;
	private final ComboBoxField type;
	private final DoubleField perturbPercent;
	private final IntegerField seedValue;
	private final DoubleField constantValue;
	private final DoubleField constHigh;
	private final DoubleField constLow;
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;


	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public RandomizeNetworkDialog(final Frame owner) {
		
		super(owner);

		setTitle("Randomize Network");
		setSize(400,400);
		setLocation(200,200);
		
		List<String> types = new ArrayList<String>();
		types.add("Random");
		types.add("Nguyen-Widrow");
		types.add("Fan-In");
		
		this.setCollectCurrentTabOnly(true);
				
		this.beginTab("Randomize");
		addProperty(this.high = new DoubleField("high","High Range",true,0,-1));
		addProperty(this.low = new DoubleField("low","Low Range",true,0,-1));
		addProperty(this.type = new ComboBoxField("type","Low Range",true,types));
		this.beginTab("Perturb");
		addProperty(this.perturbPercent = new DoubleField("perturb percent","Perturb Percent",true,0,-1));
		this.beginTab("Gaussian");
		addProperty(this.mean = new DoubleField("mean","Mean",true,0,-1));
		addProperty(this.deviation = new DoubleField("standard deviation","Standard Deviation",true,0,-1));
		this.beginTab("Consistent");
		addProperty(this.seedValue = new IntegerField("seed value","Seed Value",true,0,-1));
		addProperty(this.constHigh = new DoubleField("high","High Range",true,0,-1));
		addProperty(this.constLow = new DoubleField("low","Low Range",true,0,-1));		
		this.beginTab("Constant");
		addProperty(this.constantValue = new DoubleField("constant value","Constant Value",true,0,-1));
		render();
	}


	public DoubleField getHigh() {
		return high;
	}


	public DoubleField getLow() {
		return low;
	}


	public ComboBoxField getType() {
		return type;
	}


	public DoubleField getPerturbPercent() {
		return perturbPercent;
	}


	public IntegerField getSeedValue() {
		return seedValue;
	}


	public DoubleField getConstantValue() {
		return constantValue;
	}


	public DoubleField getConstHigh() {
		return constHigh;
	}


	public DoubleField getConstLow() {
		return constLow;
	}


	public DoubleField getMean() {
		return mean;
	}


	public DoubleField getDeviation() {
		return deviation;
	}

	

	


}
