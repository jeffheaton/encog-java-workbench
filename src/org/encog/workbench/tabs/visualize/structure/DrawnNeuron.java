package org.encog.workbench.tabs.visualize.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class DrawnNeuron {
	
	private final DrawnNeuronType type;
	private final List<DrawnConnection> connections = new ArrayList<DrawnConnection>();
	private final double x;
	private final double y;
	private final int size;
	
	public DrawnNeuron(DrawnNeuronType type, double x, double y, int size) {
		super();
		this.type = type;
		this.x = x;
		this.y = y;
		this.size = size;
	}

	/**
	 * @return the type
	 */
	public DrawnNeuronType getType() {
		return type;
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

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	public void paint(Graphics g, int width, int height)
	{
		switch(this.type)
		{
			case Bias:
				g.setColor(Color.YELLOW);
				break;					
			case Input:
				g.setColor(Color.GREEN);
				break;					
			case Output:
				g.setColor(Color.RED);
				break;
			case Hidden:
				g.setColor(Color.BLACK);
				break;
		}		
					
		g.fillOval(
			(int)(x*width), 
			(int)(y*height), 
			size,
			size);

	}

	/**
	 * @return the connections
	 */
	public List<DrawnConnection> getConnections() {
		return connections;
	}

	
	
}
