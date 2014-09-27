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
package org.encog.workbench.tabs.visualize.grid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GridPanel extends JPanel {

	private final static int WIDTH = 5;
	private double[] data;
	private int gridWidth;
	private int gridHeight;

	public GridPanel() {

	}

	public void paint(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		
		if (data == null) {			
			g.drawString("Nothing selected", 10, 20);
			return;
		}
		
		for(int row = 0; row<gridHeight; row++) {
			for(int col = 0; col<gridWidth; col ++) {
				int index = (row*gridWidth)+col;
				int x = col*WIDTH;
				int y = row * WIDTH;
				boolean selected = false;
				
				if( index < this.data.length ) {
					selected = this.data[index]>0;
				}
				
				if( selected ) {
					g.fillRect(x, y, WIDTH, WIDTH);
				} else {
					g.drawRect(x, y, WIDTH, WIDTH);
				}
			}
		}
		
		
	}

	public void updateData(int gridHeight, int gridWidth, double[] data) {
		this.data = data;
		this.gridHeight = gridHeight;
		this.gridWidth = gridWidth;
		repaint();
	}
}
