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
