package org.encog.workbench.tabs.visualize.structure;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LegendPanel extends JPanel {
	public LegendPanel(boolean neat) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(new JLabel("Legend: "));
		create(Color.white, "Input",false);
		create(Color.yellow, "Bias",false);
		
		if( neat ) {
			create(Color.blue, "Linear",false);
			create(Color.magenta, "Sigmoid",false);
			create(Color.cyan, "Gaussian",false);
			create(Color.gray, "Sin",false);
		} else {
			create(Color.red, "Hidden",false);	
		}
		
		create(Color.green, "Output",true);
		
	}
	
	private void create(Color c, String text, boolean isLast) {
		JPanel panel = new JPanel();
		panel.setBackground(c);
		this.add(panel);
		if( !isLast) {
			text+=", ";
		}
		add(new JLabel(text));
	}
}
