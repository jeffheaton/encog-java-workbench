/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.training.methods;

import java.util.ArrayList;
import java.util.List;

import org.encog.mathutil.rbf.GaussianFunction;
import org.encog.mathutil.rbf.InverseMultiquadricFunction;
import org.encog.mathutil.rbf.MexicanHatFunction;
import org.encog.mathutil.rbf.MultiquadricFunction;
import org.encog.mathutil.rbf.RBFEnum;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodBubble;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodFunction;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodRBF;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodRBF1D;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodSingle;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.training.DialogMaxError;

/**
 * Input data to train a SOM network.
 * @author jheaton
 *
 */
public class InputSOM extends DialogMaxError {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;


	private final DoubleField learningRate;
	private final ComboBoxField neighborhoodType;
	private final DoubleField rbfWidth;
	private final IntegerField rows;
	private final IntegerField cols;
	private final CheckField forceWinner;
	

	/**
	 * Construct the dialog box.
	 * @param owner The owner.
	 */
	public InputSOM() {
		super(false);
		
		List<String> list = new ArrayList<String>();		
		list.add("1D RBF - Gaussian");
		list.add("1D RBF - MexicanHat");
		list.add("1D RBF - Multiquadric");
		list.add("1D RBF - InverseMultiquadric");
		list.add("1D - Bubble");
		list.add("1D - Single");
		list.add("2D RBF - Gaussian");
		list.add("2D RBF - MexicanHat");
		list.add("2D RBF - Multiquadric");
		list.add("2D RBF - InverseMultiquadric");
		
		setTitle("Train SOM Network");
		addProperty(this.learningRate = new DoubleField("learning rate","Learning Rate",true,0,-1));
		addProperty(this.neighborhoodType = new ComboBoxField("neighborhood","Neighborhood Type",true,list));
		addProperty(this.rbfWidth = new DoubleField("rbf width","RBF Width",true,0,1000));
		addProperty(this.cols = new IntegerField("cols", "2D RBF Columns",true,0,1000000));
		addProperty(this.rows = new IntegerField("rows", "2D RBF Rows",true,0,1000000));
		addProperty(this.forceWinner = new CheckField("force winner","Force Winner"));
		render();
		this.learningRate.setValue(0.25);
		this.getRBFWidth().setValue(2);
		this.getCols().setValue(1);
		this.getRows().setValue(1);
		this.getForceWinner().setValue(true);

	}


	public DoubleField getLearningRate() {
		return learningRate;
	}
	
	
	public NeighborhoodFunction getNeighborhoodFunction() {
		switch( neighborhoodType.getSelectedIndex() )
		{
			case 0:
				return new NeighborhoodRBF1D( new GaussianFunction(0,1,this.rbfWidth.getValue()));
			case 1:
				return new NeighborhoodRBF1D( new MexicanHatFunction(0,1,this.rbfWidth.getValue()));
			case 2:
				return new NeighborhoodRBF1D( new MultiquadricFunction(0,1,this.rbfWidth.getValue()));
			case 3:
				return new NeighborhoodRBF1D( new InverseMultiquadricFunction(0,1,this.rbfWidth.getValue()));
			case 4:
				return new NeighborhoodBubble( (int)this.rbfWidth.getValue() );
			case 5:
				return new NeighborhoodSingle( );
			case 6: 
				return new NeighborhoodRBF(RBFEnum.Gaussian, this.cols.getValue(), this.rows.getValue());
			case 7: 
				return new NeighborhoodRBF(RBFEnum.MexicanHat, this.cols.getValue(), this.rows.getValue());
			case 8: 
				return new NeighborhoodRBF(RBFEnum.Multiquadric, this.cols.getValue(), this.rows.getValue());
			case 9: 
				return new NeighborhoodRBF(RBFEnum.InverseMultiquadric, this.cols.getValue(), this.rows.getValue());
			default:
				return null;
		}
	}


	public DoubleField getRBFWidth() {
		return rbfWidth;
	}


	public IntegerField getRows() {
		return rows;
	}


	public IntegerField getCols() {
		return cols;
	}


	public CheckField getForceWinner() {
		return forceWinner;
	}



}
