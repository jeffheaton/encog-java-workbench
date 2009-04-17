package org.encog.workbench.frames.network;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.synapse.Synapse;
import org.encog.workbench.frames.network.NetworkDiagram.Side;

public class DrawArrow {
	
	
	
	private static Point findSide(Side side,Layer layer)
	{
		switch(side)
		{
			case Top:
				return new Point(layer.getX()+(NetworkDiagram.LAYER_WIDTH/2),layer.getY());
			case Bottom:
				return new Point(layer.getX()+(NetworkDiagram.LAYER_WIDTH/2),layer.getY()+NetworkDiagram.LAYER_HEIGHT);
			case Left:
				return new Point(layer.getX(),layer.getY()+(NetworkDiagram.LAYER_HEIGHT/2));
			case Right:
				return new Point(layer.getX()+NetworkDiagram.LAYER_WIDTH,layer.getY()+(NetworkDiagram.LAYER_HEIGHT/2));
		}
		
		return null;
	}
	
	/**
	 * Move the "to" part of an arrow back so the arrowhead is outside the layer.
	 * @param side
	 * @param p
	 * @return
	 */
	private static Point moveArrowBack(Side side,Point p)
	{
		switch(side)
		{
		case Top:
			return new Point((int)p.getX(),(int)p.getY()-NetworkDiagram.ARROWHEAD_WIDTH);
		case Bottom:
			return new Point((int)p.getX(),(int)p.getY()+NetworkDiagram.ARROWHEAD_WIDTH);
		case Left:
			return new Point((int)p.getX()-NetworkDiagram.ARROWHEAD_WIDTH,(int)p.getY());
		case Right:
			return new Point((int)p.getX()+NetworkDiagram.ARROWHEAD_WIDTH,(int)p.getY());
		}
		return null;
	}
	
	public static void drawArrow(Graphics g,Synapse synapse)
	{
		Point from;
		Point to;
		Side fromSide;
		Side toSide;
		
		
		if( (synapse.getToLayer().getX()>=synapse.getFromLayer().getX()) && 
				(synapse.getToLayer().getX()<=(synapse.getFromLayer().getX()+NetworkDiagram.LAYER_WIDTH*1.5)) ||
		
				(synapse.getFromLayer().getX()>=synapse.getToLayer().getX()) && 
				(synapse.getFromLayer().getX()<=(synapse.getToLayer().getX()+NetworkDiagram.LAYER_WIDTH*1.5))
		)
		{
			if(synapse.getToLayer().getY()>synapse.getFromLayer().getY())
			{
				fromSide = Side.Bottom;
				toSide = Side.Top;
			}
			else
			{
				fromSide = Side.Top;
				toSide = Side.Bottom;
			}
		}
		else
		{
			if(synapse.getToLayer().getX()>synapse.getFromLayer().getX())
			{
				fromSide = Side.Right;
				toSide = Side.Left;
			}
			else
			{
				fromSide = Side.Left;
				toSide = Side.Right;
			}
		}
		
		from = findSide(fromSide,synapse.getFromLayer());
		to = findSide(toSide,synapse.getToLayer());
		
		to = moveArrowBack(toSide, to);
		
		drawArrow(g,from,to,toSide);
	}
	
	/**
	 * Generate a polygon for an arrow head that points the right way.
	 * @param side
	 * @param x
	 * @param y
	 * @return
	 */
	private static Polygon generateArrowHead(Side side, int x,int y)
	{
		int[] polyX = new int[3];
		int[] polyY = new int[3];
		
		switch(side)
		{
			case Top:
				polyX[0] = x-NetworkDiagram.ARROWHEAD_WIDTH/2;
				polyY[0] = y;
				polyX[1] = x+NetworkDiagram.ARROWHEAD_WIDTH/2;
				polyY[1] = y;
				polyX[2] = x;
				polyY[2] = y+NetworkDiagram.ARROWHEAD_WIDTH;
				break;
			case Bottom:
				polyX[0] = x-NetworkDiagram.ARROWHEAD_WIDTH/2;
				polyY[0] = y;
				polyX[1] = x+NetworkDiagram.ARROWHEAD_WIDTH/2;
				polyY[1] = y;
				polyX[2] = x;
				polyY[2] = y-NetworkDiagram.ARROWHEAD_WIDTH;
				break;
			case Right:
				polyX[0] = x;
				polyY[0] = y-NetworkDiagram.ARROWHEAD_WIDTH/2;
				polyX[1] = x;
				polyY[1] = y+NetworkDiagram.ARROWHEAD_WIDTH/2;
				polyX[2] = x-NetworkDiagram.ARROWHEAD_WIDTH;
				polyY[2] = y;
				break;
			case Left:
				polyX[0] = x;
				polyY[0] = y-NetworkDiagram.ARROWHEAD_WIDTH/2;
				polyX[1] = x;
				polyY[1] = y+NetworkDiagram.ARROWHEAD_WIDTH/2;
				polyX[2] = x+NetworkDiagram.ARROWHEAD_WIDTH;
				polyY[2] = y;
				break;
		}
		
		return new Polygon(polyX,polyY,3);
	}
	
	/**
	 * Draw an arrow, with an arrow head between the two points.
	 * @param g
	 * @param from
	 * @param to
	 * @param toSide
	 */
	private static void drawArrow(Graphics g,Point from,Point to, Side toSide)
	{
		Polygon arrowHead = generateArrowHead(toSide,to.x,to.y);
		
		g.setColor(Color.BLACK);
		g.drawLine(from.x,from.y,to.x,to.y);	
		g.fillPolygon(arrowHead);
	}
}
