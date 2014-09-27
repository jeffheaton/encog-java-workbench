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
package org.encog.workbench.tabs.query.ocr;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.encog.ml.BasicML;
import org.encog.ml.MLInput;
import org.encog.ml.MLMethod;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;

public class OCRGridPanel extends JPanel implements MouseListener {

	public MLMethod method;
	private boolean grid[];
	private int margin;

	private int gridX;
	private int gridY;
	private int cellWidth;
	private int cellHeight;

	public OCRGridPanel(BasicML method) {

		if (method.getProperties().containsKey("rows")
				&& method.getProperties().containsKey("columns")) {
			this.gridX = (int) method.getPropertyLong("columns");
			this.gridY = (int) method.getPropertyLong("rows");
		} else if( method instanceof MLInput ) {
			this.gridX = ((MLInput)method).getInputCount();
			this.gridY = 1;
			
		} else
		{
			EncogWorkBench.displayError("Error", "OCR Query cannot be used with this method.");
		}
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		this.grid = new boolean[this.gridX * this.gridY];
		
		if( grid.length!=((MLInput)method).getInputCount() ) {
			throw new WorkBenchError("The (rows x columns) must equal the neuron count.");
		}
		
		this.addMouseListener(this);
		this.method = method;
	}

	/**
	 * Clear the grid.
	 */
	public void clear() {
		int index = 0;
		for (int y = 0; y < this.gridY; y++) {
			for (int x = 0; x < this.gridX; x++) {
				this.grid[index++] = false;
			}
		}

		repaint();
	}

	/**
	 * Clear the weight matrix.
	 */
	public void clearMatrix() {
	}

	/**
	 * Run the neural network.
	 */
	public void go() {
		
		repaint();

	}

	public void mouseReleased(final MouseEvent e) {
		final int x = ((e.getX() - this.margin) / this.cellWidth);
		final int y = e.getY() / this.cellHeight;
		if (((x >= 0) && (x < this.gridX)) && ((y >= 0) && (y < this.gridY))) {
			final int index = (y * this.gridX) + x;
			this.grid[index] = !this.grid[index];
		}
		repaint();

	}

	@Override
	public void paint(final Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();
		this.cellHeight = height / this.gridY;
		this.cellWidth = width / this.gridX;

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		this.margin = (this.getWidth() - (this.cellWidth * this.gridX)) / 2;
		int index = 0;
		for (int y = 0; y < this.gridY; y++) {
			for (int x = 0; x < this.gridX; x++) {
				if (this.grid[index++]) {
					g.fillRect(this.margin + (x * this.cellWidth), y
							* this.cellHeight, this.cellWidth, this.cellHeight);
				} else {
					g.drawRect(this.margin + (x * this.cellWidth), y
							* this.cellHeight, this.cellWidth, this.cellHeight);
				}
			}
		}
	}

	/**
	 * Train the neural network.
	 */
	public void train() {
		
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

	public int getGridHeight() {
		return gridY;
	}
	
	public int getGridWidth() {
		return gridX;
	}

	public void setGrid(boolean[] data) {
		this.grid = data;
		repaint();
	}

	public boolean []getGrid() {
		return this.grid;
	}

}
