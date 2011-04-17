package org.encog.workbench.tabs.visualize.scatter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import org.jfree.chart.LegendItemSource;
import org.jfree.chart.block.BlockParams;
import org.jfree.chart.block.LineBorder;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

public class LegendPanel extends JPanel {
	
	private LegendTitle legend;
	
	public LegendPanel(LegendItemSource source) {
		this.legend = new LegendTitle(source);
        legend.setMargin(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        legend.setFrame(new LineBorder());
        legend.setBackgroundPaint(Color.white);
        legend.setPosition(RectangleEdge.BOTTOM);        
		setPreferredSize(new Dimension(1024, 35));
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
        BlockParams p = new BlockParams();
        p.setGenerateEntities(true);
		Rectangle area = new Rectangle(0,0,this.getWidth(),this.getHeight());
		legend.arrange(g2d);
		legend.draw(g2d, area, p);
	}
}
