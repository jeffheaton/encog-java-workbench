/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.layers;

import java.awt.Frame;

import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TableField;

public class EditRadialLayer extends EditLayerDialog  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751974232848993273L;
	
	private final static String[] RADIUS_HEADS = {"Neuron","Radius"};
	private final static String[] CENTER_HEADS = {"Neuron","Dimension","Center"};
	
	private IntegerField dimensions;
	private TableField radius;
	private TableField center;

	public EditRadialLayer(Frame owner, RadialBasisFunctionLayer layer) {
		super(owner);
		setTitle("Edit Radial Basis Layer");
		setSize(600, 500);
		setLocation(200, 200);
		addProperty(this.dimensions = new IntegerField("dimensions", "Dimensions(previous layer count)", true,0,1000));
		addProperty(this.radius  = new TableField("radial basis functions",
				"Radius",true,100,layer.getNeuronCount(),RADIUS_HEADS));
		addProperty(this.center = new TableField("radial basis functions",
			"Centers",true,100,layer.getNeuronCount()*layer.getDimensions(),CENTER_HEADS));

		render();
	}

	/**
	 * @return the radius
	 */
	public TableField getRadius() {
		return radius;
	}

	/**
	 * @return the center
	 */
	public TableField getCenter() {
		return center;
	}

	/**
	 * @return the dimensions
	 */
	public IntegerField getDimensions() {
		return dimensions;
	}
	
	
}

