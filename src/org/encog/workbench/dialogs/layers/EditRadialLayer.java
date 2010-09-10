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

