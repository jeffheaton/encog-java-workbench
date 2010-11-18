/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
	
}
