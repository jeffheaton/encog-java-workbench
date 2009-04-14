package org.encog.workbench.frames.network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class NetworkDiagram extends JPanel implements MouseListener {

	public NetworkDiagram()
	{
		this.addMouseListener(this);
		this.setPreferredSize(new Dimension(5000,5000));
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.BLACK);
		g.drawLine(0,0,getWidth(),getHeight());
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

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
