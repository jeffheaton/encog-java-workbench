/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
 */

package org.encog.workbench.frames.network;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.workbench.WorkbenchFonts;

public class DrawLayer {
	
	public static final int LAYER_WIDTH = 96;
	public static final int LAYER_HEIGHT = 64;
	public static final int SELECTION_WIDTH = 10;
	
	private static void drawNeuronCount(Graphics g, Layer layer)
	{
		int x = layer.getX()+NetworkTool.WIDTH;
		int y = layer.getY();
		int width = LAYER_WIDTH-NetworkTool.WIDTH;
		int height = NetworkTool.HEIGHT;
		g.setColor(Color.BLACK);
		g.setFont(WorkbenchFonts.getTitle2Font());
		FontMetrics fm = g.getFontMetrics();
		String str = ""+layer.getNeuronCount();
		int textX = x+((width/2)-(fm.stringWidth(str)/2));
		int textY = y+fm.getHeight()+((height/2)-(fm.getHeight()/2));
		g.drawString(str, textX, textY );
		g.drawRect(x, y, width, height);
	}
	
	public static void drawLayer(NetworkDiagram diagram, Graphics g, Layer layer)
	{
		NetworkTool tool = diagram.getNetworkFrame().findTool( layer);
		g.setColor(Color.WHITE);
		g.fillRect(layer.getX(), layer.getY(), LAYER_WIDTH, LAYER_HEIGHT);
		g.setColor(Color.BLACK);
		tool.getIcon().paintIcon(diagram, g, layer.getX(), layer.getY());
		g.drawRect(layer.getX(), layer.getY(), NetworkTool.WIDTH, NetworkTool.HEIGHT);
		g.drawRect(layer.getX(), layer.getY(), LAYER_WIDTH, LAYER_HEIGHT);
		g.drawRect(layer.getX()-1, layer.getY()-1, LAYER_WIDTH, LAYER_HEIGHT);
		g.setFont(WorkbenchFonts.getTitle2Font());
		FontMetrics fm = g.getFontMetrics();
		//int y = layer.getY()+fm.getHeight()+NetworkTool.HEIGHT;
		int y = layer.getY()+NetworkTool.HEIGHT+fm.getAscent();
		g.drawString(tool.getName() + " Layer", layer.getX()+2, y);
		y+=fm.getAscent();
		g.setFont(WorkbenchFonts.getTextFont());
		
		String act = "";
		
		if( !(layer instanceof RadialBasisFunctionLayer))
		{
			final String a = "Activation";
			
			act = layer.getActivationFunction().getClass().getSimpleName();
			if( act.startsWith(a))
			{
				act = act.substring(a.length());
			}
		}
		
		g.drawString(act, layer.getX()+2, y);
		drawNeuronCount(g,layer);
		//g.fillRect(layer.getX(), layer.getY(), 50,50);
	}

	public static void drawSelection(Graphics g, Layer layer)
	{
		g.setColor(Color.CYAN);
		g.drawRect(layer.getX(), layer.getY(), DrawLayer.LAYER_WIDTH, DrawLayer.LAYER_HEIGHT);
		g.fillRect(layer.getX(), layer.getY(), SELECTION_WIDTH, SELECTION_WIDTH);
		g.fillRect(layer.getX()+DrawLayer.LAYER_WIDTH-SELECTION_WIDTH, layer.getY(), SELECTION_WIDTH, SELECTION_WIDTH);
		g.fillRect(layer.getX()+DrawLayer.LAYER_WIDTH-SELECTION_WIDTH, layer.getY()+DrawLayer.LAYER_HEIGHT-SELECTION_WIDTH, SELECTION_WIDTH, SELECTION_WIDTH);
		g.fillRect(layer.getX(), layer.getY()+DrawLayer.LAYER_HEIGHT-SELECTION_WIDTH, SELECTION_WIDTH, SELECTION_WIDTH);
	}
	
	public static void drawFromSelection(Graphics g, Layer layer)
	{
		g.setColor(Color.RED);
		g.setFont(WorkbenchFonts.getTextFont());
		g.drawString("Choose a layer to build a synapse to", layer.getX(), layer.getY());
	}
	
}
