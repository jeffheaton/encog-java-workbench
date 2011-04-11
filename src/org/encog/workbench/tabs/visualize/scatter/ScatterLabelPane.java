package org.encog.workbench.tabs.visualize.scatter;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScatterLabelPane extends JPanel {
	public ScatterLabelPane(String text) {
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel(text,JLabel.CENTER);
		this.add(label,BorderLayout.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
