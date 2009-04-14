package org.encog.workbench.frames.network;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JPanel;

public class NetworkToolbar extends JPanel {

	public NetworkToolbar()
	{
		this.setLayout(new BorderLayout());
		Dimension d = new Dimension(100,100);
		this.setMinimumSize(d);
		
	}
	
	public void paint(Graphics g)
	{
		g.fillRect(0,0,100,100);
	}
	
}
