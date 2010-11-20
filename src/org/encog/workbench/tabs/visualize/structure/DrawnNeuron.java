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

}
