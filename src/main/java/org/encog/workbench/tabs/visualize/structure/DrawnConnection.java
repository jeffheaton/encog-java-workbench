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


import org.encog.util.Format;

public class DrawnConnection {
	private final DrawnNeuron from;
	private final DrawnNeuron to;
	private final double weight;
	private boolean context;
	
	public DrawnConnection(DrawnNeuron from, DrawnNeuron to, double weight) {
		super();
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.context = false;
	}

	/**
	 * @return the from
	 */
	public DrawnNeuron getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public DrawnNeuron getTo() {
		return to;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public String getToolTip() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.from.getName());
		builder.append("->");
		builder.append(this.to.getName());
		builder.append(" : ");
		builder.append(Format.formatDouble(this.weight, 4));
		return builder.toString();
	}

	/**
	 * @return the context
	 */
	public boolean isContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(boolean context) {
		this.context = context;
	}
		
}
