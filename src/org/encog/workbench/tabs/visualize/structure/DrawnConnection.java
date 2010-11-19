package org.encog.workbench.tabs.visualize.structure;


import java.awt.Color;
import java.awt.Graphics;

public class DrawnConnection {
	private final DrawnNeuron from;
	private final DrawnNeuron to;
	
	public DrawnConnection(DrawnNeuron from, DrawnNeuron to) {
		super();
		this.from = from;
		this.to = to;
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
		
}
