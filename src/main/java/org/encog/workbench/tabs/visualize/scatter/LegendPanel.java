/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
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
