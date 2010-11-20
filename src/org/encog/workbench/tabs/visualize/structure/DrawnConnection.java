package org.encog.workbench.tabs.visualize.structure;


import java.awt.Color;
import java.awt.Graphics;

import org.encog.engine.util.Format;

public class DrawnConnection {
	private final DrawnNeuron from;
	private final DrawnNeuron to;
	private final double weight;
	
	public DrawnConnection(DrawnNeuron from, DrawnNeuron to, double weight) {
		super();
		this.from = from;
		this.to = to;
		this.weight = weight;
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
		
}
