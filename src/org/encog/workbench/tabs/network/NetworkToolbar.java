/*
 * Encog(tm) Workbench v2.4
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

package org.encog.workbench.tabs.network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.neural.networks.synapse.DirectSynapse;
import org.encog.neural.networks.synapse.OneToOneSynapse;
import org.encog.neural.networks.synapse.WeightedSynapse;
import org.encog.workbench.WorkbenchFonts;
import org.encog.workbench.tabs.network.NetworkTool.Type;

public class NetworkToolbar extends JPanel implements MouseListener {

	private NetworkTool selected;
	private final NetworkTab parent;
	public final static int WIDTH = 100;
	public final Color COLOR_BACKGROUND = new Color(200,200,200);
	public final Color TOOL_BACKGROUND = new Color(150,150,150);
	
	public NetworkToolbar(NetworkTab parent)
	{
		Dimension d = new Dimension(100,100);
		this.setPreferredSize(d);
		this.addMouseListener(this);
		this.parent = parent;

	}
	
	public void paint(Graphics g)
	{
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.BLACK);
		g.setFont(WorkbenchFonts.getTitle1Font());
		FontMetrics fm = g.getFontMetrics();
		int headerHeight = fm.getHeight();
		int y = headerHeight+4;
		center(g, y, "Layers");
		
		g.setFont(WorkbenchFonts.getTextFont());
		int textHeight = fm.getHeight();
		
		y+=4;
		
		for(NetworkTool tool: this.parent.getTools())
		{
			if( tool.getType()==Type.layer)
			{
				y = drawTool(g,tool,0,y);
				
			}
		}		
		y+=textHeight;
		y+=5;
		g.setFont(WorkbenchFonts.getTitle1Font());
		center(g, y, "Synapses");
		y+=10;

		g.setFont(WorkbenchFonts.getTextFont());
		
		for(NetworkTool tool: this.parent.getTools())
		{
			if( tool.getType()==Type.synapse)
			{
				y = drawTool(g,tool,0,y);
				
			}
		}		
	}
	
	private int drawTool(Graphics g, NetworkTool tool,int x,int y)
	{
		int textHeight = g.getFontMetrics().getHeight();
		tool.setX(x);
		tool.setY(y);
		tool.setWidth(WIDTH);
		tool.setHeight(textHeight+32+6);
		
		if( tool == this.selected )
		{
			g.setColor(Color.CYAN);			
		}
		else
		{
			g.setColor(TOOL_BACKGROUND);
		}
		g.fillRect(x, y, tool.getWidth(), tool.getHeight());
		
		int iconX = (WIDTH/2)-32;
		int iconY = y+6;
		tool.getIcon().paintIcon(this, g, iconX , iconY );
		g.setColor(Color.BLACK);
		g.drawRect(iconX, iconY, 64, 32);
		
		
		
		center(g,y+textHeight+35,tool.getName());
		
		g.drawRect(tool.getX(), tool.getY(), tool.getWidth(), tool.getHeight());
		
		y+=tool.getHeight();
		return y;
	}
	
	private void center(Graphics g, int y, String str)
	{
		FontMetrics fm = g.getFontMetrics();
		int width = this.getWidth();
		g.drawString(str, (width/2)-(fm.stringWidth(str)/2), y);
	}

	public void mouseClicked(MouseEvent e) {
		for(NetworkTool tool: this.parent.getTools())
		{
			if( tool.contains(e.getX(),e.getY()) )
			{
				if( tool!=this.selected )
				{
					this.selected = tool;
				}
				else
				{
					this.selected = null;
				}
				this.repaint();	
				this.parent.getNetworkDiagram().clearSelection();

				
			}
		}
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public NetworkTool getSelected() {
		return selected;
	}

	public void setSelected(NetworkTool selected) {
		this.selected = selected;
		repaint();
	}

	public void clearSelection() {
		if( this.selected!=null)
		{
			this.selected = null;
			repaint();
		}
		
	}
	
	
	
}
