package org.encog.workbench.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import org.encog.matrix.Matrix;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;

public class NetworkVisualizeFrame extends EncogCommonFrame {

	private BasicNetwork data;
	private Font neuronFont;
	private int layerStarts[];
	
	
	public NetworkVisualizeFrame(BasicNetwork data)  {
		this.data = data;
		addWindowListener(this);
		
	}

	public void windowOpened(WindowEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void drawNeuron(Graphics g,int x,int y,int size,String name,String threshold,double t)
	{
		Color c = getNeuronColor(t); 
		Color shadow = c.darker().darker();
		
		g.setColor(shadow);
		g.fillOval(x, y, size+5, size+5);
		g.setColor(c);
		g.fillOval(x, y, size, size);
		
		
		
		
		g.setColor(Color.white);
		g.setFont(this.neuronFont);
		FontMetrics fm = g.getFontMetrics();
		int width;
		int yloc = y+(size/2);
		width=fm.stringWidth(name);
		g.drawString(name, (x+size/2)-(width/2), yloc );
		width=fm.stringWidth(threshold);
		g.drawString(threshold, (x+size/2)-(width/2),yloc+fm.getHeight());
		
		
	}
	
	public Color getNeuronColor(double threshold)
	{
		double d = 0;
		
		d = 128.0 + (threshold*128.0);
		int i = (int)d;
		if(i<0)
			i=0;
		else if( i>255)
			i=255;
		return new Color(i,0,255-i);
	}
	
	public void centerLayers(int neuronWidth)
	{
		int maxLayers = 0;
		int layerCount = 0;
		for(Layer layer:this.data.getLayers())
		{
			if( layer.getNeuronCount()>maxLayers)
				maxLayers = layer.getNeuronCount();
			layerCount++;
		}
		
		int center = (maxLayers*neuronWidth)/2;
		
		layerStarts = new int[layerCount];
		int i=0;
		for(Layer layer:this.data.getLayers())
		{
			layerStarts[i++]=center-(layer.getNeuronCount()*neuronWidth/2);
		}
	}
	
	public void paint(Graphics g)
	{
		Container content = this.getContentPane();
		int topX = content.getX();
		int topY = content.getY()+24;
		int width = content.getWidth();
		int height = content.getHeight();
		int neuronSize = 50;
		int neuronBounds = 80;
		
		int layerNum = 0;
		
		neuronFont = new Font("SansSerif",Font.BOLD,(int)(neuronSize*0.19));
		
		centerLayers(neuronBounds);
		g.setColor(Color.black);
		g.fillRect(0,0, width, height);
		int hidden = 0;
		
		int y=topY;
		for(Layer layer:this.data.getLayers())
		{
			if( layer.isHidden() )
			{
				hidden++;
			}
			for(int i=0;i<layer.getNeuronCount();i++)
			{
				String name="";
				if( layer.isInput() )
				{
					name = "I-"+(i+1);
				}
				else if( layer.isOutput() )
				{
					name = "O-"+(i+1);
				}
				else if( layer.isHidden() )
				{
					name = "H"+hidden+"-"+(i+1);
				}
				
				double t=0;
				String threshold = "";
				if( layer.getPrevious()!=null)
				{
					Matrix matrix = layer.getPrevious().getMatrix();
					int finalRow = matrix.getRows()-1;
					t = matrix.get(finalRow, i);
					double t2 = ((int)(t*1000))/1000.0;
					threshold = "T="+t2;
				}
				
				int x = topX+this.layerStarts[layerNum]+(i*neuronBounds);
				
				if( layer.hasMatrix())
				{
					Matrix matrix = layer.getMatrix();
					int y2 = y + (neuronBounds*2);
					
					for(int j=0;j<layer.getNext().getNeuronCount();j++)
					{
						double weight = matrix.get(i,j);
						g.setColor(getNeuronColor(weight));
						
						int x2 = topX+this.layerStarts[layerNum+1]+(j*neuronBounds);
						g.drawLine(
								(x+neuronSize/2)+0, 
								(y+neuronSize/2)+0, 
								(x2+neuronSize/2)+0, 
								(y2+neuronSize/2)+0);
						g.drawLine(
								(x+neuronSize/2), 
								(y+neuronSize/2)+1, 
								(x2+neuronSize/2), 
								(y2+neuronSize/2)+1);
					}
				}
				
				drawNeuron(g,x,y,neuronSize,name,threshold,t);
			}
			layerNum++;
			y+=neuronBounds*2;
		}
	}

}
