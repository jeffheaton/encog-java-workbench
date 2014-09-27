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
	private int depth = -1;
	private double x;
	private double y;

	public DrawnNeuron(DrawnNeuronType type, String name) {
		super();
		this.type = type;
		this.name = name;
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

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	
	

}
