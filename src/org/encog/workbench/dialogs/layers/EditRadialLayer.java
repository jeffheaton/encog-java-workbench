/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
 */

package org.encog.workbench.dialogs.layers;

import java.awt.Frame;

import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.ChartField;
import org.encog.workbench.dialogs.common.ChartListener;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TableField;

public class EditRadialLayer extends EditLayerDialog implements ChartListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751974232848993273L;
	
	private final static String[] COLUMN_HEADS = {"Neuron","Center","Peak","Width"};
	
	private ChartField chart;
	private TableField radial;

	public EditRadialLayer(Frame owner, RadialBasisFunctionLayer layer) {
		super(owner);
		setTitle("Edit Radial Basis Layer");
		setSize(600, 500);
		setLocation(200, 200);
		addProperty(this.chart = new ChartField("chart",new RBFChartGenerator(this),200));
		addProperty(this.radial = new TableField("radial basis functions",
			"Radial Basis Functions",true,100,layer.getNeuronCount(),COLUMN_HEADS));
		this.chart.setListener(this);
		render();
	}

	public TableField getRadial() {
		return radial;
	}


	public void refresh(ChartField chart) {
		chart.refresh();
	}


	public ChartField getChart() {
		return this.chart;
	}

	
	
	
}

