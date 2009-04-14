package org.encog.workbench.frames.network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
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
import org.encog.workbench.frames.network.NetworkTool.Type;

public class NetworkToolbar extends JPanel implements MouseListener {

	private Font headerFont;
	private Font textFont;
	private List<NetworkTool> tools = new ArrayList<NetworkTool>();
	private NetworkTool selected;
	
	public NetworkToolbar()
	{
		Dimension d = new Dimension(100,100);
		this.setPreferredSize(d);
		this.addMouseListener(this);
		this.headerFont = new Font("Serif",Font.BOLD,12);
		this.textFont = new Font("SansSerif",0,10);
		tools.add(new NetworkTool("Basic",Type.layer,BasicLayer.class));
		tools.add(new NetworkTool("Context",Type.layer,ContextLayer.class));
		tools.add(new NetworkTool("Radial Function",Type.layer,RadialBasisFunctionLayer.class));
		tools.add(new NetworkTool("Weighted",Type.synapse,WeightedSynapse.class));
		tools.add(new NetworkTool("Weightless",Type.synapse,WeightedSynapse.class));
		tools.add(new NetworkTool("Direct",Type.synapse,DirectSynapse.class));
		tools.add(new NetworkTool("One-To-One",Type.synapse,OneToOneSynapse.class));
	}
	
	public void paint(Graphics g)
	{
		g.setFont(this.headerFont);
		FontMetrics fm = g.getFontMetrics();
		int headerHeight = fm.getHeight();
		int y = headerHeight;
		center(g, y, "Layers");
		
		g.setFont(this.textFont);
		int textHeight = fm.getHeight();
		
		for(NetworkTool tool: this.tools)
		{
			if( tool.getType()==Type.layer)
			{
				drawTool(g,tool,0,y);
				y+=textHeight;
			}
		}		
		y+=textHeight;
		
		g.setFont(this.headerFont);
		center(g, y, "Synapses");
		y+=headerHeight;
		g.setFont(this.textFont);
		
		for(NetworkTool tool: this.tools)
		{
			if( tool.getType()==Type.synapse)
			{
				drawTool(g,tool,0,y);
				y+=textHeight;
			}
		}		
	}
	
	private void drawTool(Graphics g, NetworkTool tool,int x,int y)
	{
		int textHeight = g.getFontMetrics().getHeight();
		tool.setX(x);
		tool.setY(y);
		tool.setWidth(100);
		tool.setHeight(textHeight);
		
		if( tool == this.selected )
		{
			g.setColor(Color.CYAN);			
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect(x, y, tool.getWidth(), tool.getHeight());
		
		g.setColor(Color.BLACK);
		center(g,y+textHeight-3,tool.getName());
	}
	
	private void center(Graphics g, int y, String str)
	{
		FontMetrics fm = g.getFontMetrics();
		int width = this.getWidth();
		g.drawString(str, (width/2)-(fm.stringWidth(str)/2), y);
	}

	public void mouseClicked(MouseEvent e) {
		for(NetworkTool tool: this.tools)
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
	
}
