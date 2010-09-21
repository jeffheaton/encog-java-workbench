/*
 * Encog(tm) Workbench v2.5
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
