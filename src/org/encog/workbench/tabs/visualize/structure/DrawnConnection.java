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
