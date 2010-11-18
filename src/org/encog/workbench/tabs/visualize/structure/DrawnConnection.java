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
	
	private void paintArrow(Graphics g, int x0, int y0, int x1,int y1){
		int deltaX = x1 - x0;
		int deltaY = y1 - y0;
		
		double length = Math.sqrt((deltaX*deltaX)+(deltaY*deltaY));
		
		double frac = 10/length;

		g.drawLine(x0,y0,x1,y1);
		g.drawLine(
			x0 + (int)((1-frac)*deltaX + frac*deltaY),
			y0 + (int)((1-frac)*deltaY - frac*deltaX),
			   x1, y1);
		g.drawLine(x0 + (int)((1-frac)*deltaX - frac*deltaY),
			   y0 + (int)((1-frac)*deltaY + frac*deltaX),
			   x1, y1);

	    }
	
	public void paint(Graphics g, int width, int height)
	{
		int x1 = (int)(this.from.getX()*width);
		int y1 = (int)(this.from.getY()*height);
		int x2 = (int)(this.to.getX()*width);
		int y2 = (int)(this.to.getY()*height);
		g.setColor(Color.black);
		paintArrow(g, 
		  x1+this.from.getSize()/2, 
		  y1+this.from.getSize()/2,
		  x2, 
		  y2+this.from.getSize()/2);
	}
	
	
}
