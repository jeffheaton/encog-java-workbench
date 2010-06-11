/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.tabs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.encog.mathutil.EncogMath;
import org.encog.neural.networks.synapse.neat.NEATLink;
import org.encog.neural.networks.synapse.neat.NEATNeuron;
import org.encog.neural.networks.synapse.neat.NEATSynapse;
import org.encog.persist.EncogPersistedObject;

public class NEATTab extends EncogCommonTab  {

	public NEATSynapse synapse;
	
	public NEATTab(EncogPersistedObject encogObject, NEATSynapse synapse) {
		super(encogObject);
		this.synapse = synapse;
	}
	
	private Point plotLocation(NEATNeuron neuron)
	{
		int x = (int)(neuron.getSplitX()*(getWidth()-10));
		int y = (int)(neuron.getSplitY()*(getHeight()-10));
		return new Point(x,y);
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
	
	public void paint(Graphics g)
	{
		int height = getHeight()-10;
		int width = getWidth()-10;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		g.setColor(Color.BLUE);
		
		NEATLink l;
		
		for( NEATNeuron neuron: this.synapse.getNeurons())
		{
			g.setColor(Color.black);
			Point pt = plotLocation(neuron);
			
			for( NEATLink link: neuron.getOutputboundLinks() )
			{
				if( link.getFromNeuron()==link.getToNeuron())
				{
					g.drawOval(pt.x-23, pt.y-23, 32, 32);
				}
				else
				{
					Point p2 = plotLocation(link.getToNeuron());
					paintArrow(g,pt.x+5,pt.y+10,p2.x+5,p2.y);
				}
			}
			
			switch(neuron.getNeuronType())
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
					pt.x, 
					pt.y, 
					10, 10);
		}		
	}
}
