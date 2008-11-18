package org.encog.workbench.dialogs.training;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TrainingStatusPanel extends JPanel {
	
	private BasicTrainingProgress parent;
	
	public TrainingStatusPanel(BasicTrainingProgress parent)
	{
		this.parent = parent;
		this.setPreferredSize(new Dimension(320,200));
	}
	
	public void paint(Graphics g)
	{
		parent.paintStatus(g);
	}
}
