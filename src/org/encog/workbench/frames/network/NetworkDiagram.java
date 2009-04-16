package org.encog.workbench.frames.network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.Layer;
import org.encog.workbench.WorkbenchFonts;

public class NetworkDiagram extends JPanel implements MouseListener, MouseMotionListener {

	public static final int LAYER_WIDTH = 96;
	public static final int LAYER_HEIGHT = 64;
	public static final int SELECTION_WIDTH = 10;
	public static final int VIRTUAL_WIDTH = 2000;
	public static final int VIRTUAL_HEIGHT = 2000;
	private final NetworkFrame parent;
	private Layer selected;
	private int dragOffsetX;
	private int dragOffsetY;
	private Image offscreen;
	private Graphics offscreenGraphics;
	private List<Layer> layers = new ArrayList<Layer>();
	
	public NetworkDiagram(NetworkFrame parent)
	{
		this.parent = parent;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setPreferredSize(new Dimension(VIRTUAL_HEIGHT,VIRTUAL_WIDTH));
	}
	
	private void obtainOffScreen()
	{
		if( this.offscreen==null)
		{
			this.offscreen = this.createImage(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			this.offscreenGraphics = this.offscreen.getGraphics();
		}
	}
	
	public void paint(Graphics g)
	{
		obtainOffScreen();
		offscreenGraphics.setColor(Color.WHITE);
		offscreenGraphics.fillRect(0,0,getWidth(),getHeight());
		offscreenGraphics.setColor(Color.BLACK);
		
		BasicNetwork network = (BasicNetwork)this.parent.getEncogObject();
		for(Layer layer: network.getStructure().getLayers())
		{
			drawLayer(offscreenGraphics,layer);
			if( this.selected==layer)
			{
				drawSelection(offscreenGraphics,layer);
			}
		}
		
		g.drawImage(this.offscreen, 0,0,this);
		
	}
	
	private NetworkTool findTool(Layer layer)
	{
		for(NetworkTool tool: this.parent.getTools())
		{
			if( tool.getClassType() == layer.getClass() )
			{
				return tool;
			}
		}
		return null;
	}
	
	private void drawSelection(Graphics g, Layer layer)
	{
		g.setColor(Color.CYAN);
		g.drawRect(layer.getX(), layer.getY(), LAYER_WIDTH, LAYER_HEIGHT);
		g.fillRect(layer.getX(), layer.getY(), SELECTION_WIDTH, SELECTION_WIDTH);
		g.fillRect(layer.getX()+LAYER_WIDTH-SELECTION_WIDTH, layer.getY(), SELECTION_WIDTH, SELECTION_WIDTH);
		g.fillRect(layer.getX()+LAYER_WIDTH-SELECTION_WIDTH, layer.getY()+LAYER_HEIGHT-SELECTION_WIDTH, SELECTION_WIDTH, SELECTION_WIDTH);
		g.fillRect(layer.getX(), layer.getY()+LAYER_HEIGHT-SELECTION_WIDTH, SELECTION_WIDTH, SELECTION_WIDTH);
	}
	
	private void drawLayer(Graphics g, Layer layer)
	{
		NetworkTool tool = findTool(layer);
		g.setColor(Color.WHITE);
		g.fillRect(layer.getX(), layer.getY(), LAYER_WIDTH, LAYER_HEIGHT);
		g.setColor(Color.BLACK);
		tool.getIcon().paintIcon(this, g, layer.getX(), layer.getY());
		g.drawRect(layer.getX(), layer.getY(), NetworkTool.WIDTH, NetworkTool.HEIGHT);
		g.drawRect(layer.getX(), layer.getY(), LAYER_WIDTH, LAYER_HEIGHT);
		g.drawRect(layer.getX()-1, layer.getY()-1, LAYER_WIDTH, LAYER_HEIGHT);
		g.setFont(WorkbenchFonts.getTitle2Font());
		FontMetrics fm = g.getFontMetrics();
		int y = layer.getY()+fm.getHeight()+NetworkTool.HEIGHT;
		g.drawString(tool.getName() + " Layer", layer.getX()+2, y);
		y+=fm.getHeight();
		g.setFont(WorkbenchFonts.getTextFont());
		g.drawString(layer.getNeuronCount() + " Neuron" + ((layer.getNeuronCount()>1)?"s":""), layer.getX()+2, y);
		
		//g.fillRect(layer.getX(), layer.getY(), 50,50);
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		
		// was something deselected
		for(Layer layer: this.layers )
		{
			if( contains(layer,e.getX(),e.getY()))
			{
				if( selected==layer)
				{
					selected = null;
					repaint();
					return;
				}
			}
		}
		
		// was something selected
		for(Layer layer: this.layers )
		{
			if( contains(layer,e.getX(),e.getY()))
			{
				selected = layer;
				dragOffsetX = e.getX()-layer.getX();
				dragOffsetY = e.getY()-layer.getY();
				repaint();
				return;
			}
		}
		
	}
	
	private boolean contains(Layer layer,int x, int y)
	{
		return( x>layer.getX() && (x<layer.getX()+LAYER_WIDTH) &&
			y>layer.getY() && (y<layer.getY()+LAYER_HEIGHT) );
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void getLayers()
	{
		this.layers.clear();
		BasicNetwork network = (BasicNetwork)this.parent.getEncogObject();
		for(Layer layer: network.getStructure().getLayers())
		{
			this.layers.add(layer);
		}

	}

	public void mouseDragged(MouseEvent e) {

		if(this.selected!=null)
		{
			this.selected.setX(e.getX()-dragOffsetX);
			this.selected.setY(e.getY()-dragOffsetY);
			repaint();
		}
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
