/*
 * Encog(tm) Workbanch v3.1 - Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
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
package org.encog.workbench.tabs.visualize.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class DrawnNeuron {

	private final DrawnNeuronType type;
	private final List<DrawnConnection> outbound = new ArrayList<DrawnConnection>();
	private final List<DrawnConnection> inbound = new ArrayList<DrawnConnection>();
	private String name;
	private final double x;
	private final double y;

	private int layerIndex;
	private int neuronIndex;
	
	public DrawnNeuron(DrawnNeuronType type, String name, double x, double y) {
		super();
		this.type = type;
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return name;
	}

	/**
	 * @return the type
	 */
	public DrawnNeuronType getType() {
		return type;
	}

	/**
	 * @return the outbound connections
	 */
	public List<DrawnConnection> getOutbound() {
		return outbound;
	}

	/**
	 * @return the inbound connections
	 */
	public List<DrawnConnection> getInbound() {
		return outbound;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	public String getToolTip() {
		StringBuilder result = new StringBuilder();
		switch (this.type) {
		case Input:
			result.append("Input");
			break;
		case Hidden:
			result.append("Hidden");
			break;
		case Context:
			result.append("Context");
			break;
		case Bias:
			result.append("Bias");
			break;
		case Output:
			result.append("Output");
			break;
		}
		result.append(" : ");
		result.append(this.name);
		return result.toString();
	}

	public Object getName() {
		return name;
	}

	public int getNeuronIndex() {
		return neuronIndex;
	}

	public void setNeuronIndex(int neuronIndex) {
		this.neuronIndex = neuronIndex;
	}

	public int getLayerIndex() {
		return layerIndex;
	}

	public void setLayerIndex(int layerIndex) {
		this.layerIndex = layerIndex;
	}

}
