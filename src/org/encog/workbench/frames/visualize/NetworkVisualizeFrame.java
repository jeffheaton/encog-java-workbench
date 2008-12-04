package org.encog.workbench.frames.visualize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;

import org.encog.matrix.Matrix;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;
import org.encog.workbench.frames.manager.EncogCommonFrame;

public class NetworkVisualizeFrame extends EncogCommonFrame {

	private BasicNetwork data;
	private JScrollPane scroll;
	private NetworkVisualizePanel visualize;
	
	
	public NetworkVisualizeFrame(BasicNetwork data)  {
		this.data = data;
		addWindowListener(this);
		setSize(320,200);
		setTitle("Visualize " + this.data.getName());
		this.visualize = new NetworkVisualizePanel(this.data);
		this.scroll = new JScrollPane(this.visualize);
		//this.scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(this.scroll,BorderLayout.CENTER);
		
	}

	public void windowOpened(WindowEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
