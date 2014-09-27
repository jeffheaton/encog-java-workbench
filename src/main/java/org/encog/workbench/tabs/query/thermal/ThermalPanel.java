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
package org.encog.workbench.tabs.query.thermal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.encog.ml.data.specific.BiPolarNeuralData;
import org.encog.neural.thermal.BoltzmannMachine;
import org.encog.neural.thermal.HopfieldNetwork;
import org.encog.neural.thermal.ThermalNetwork;
import org.encog.util.EngineArray;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;

public class ThermalPanel extends JPanel implements MouseListener {

	public ThermalNetwork network;
	private boolean grid[];
	private int margin;

	private int gridX;
	private int gridY;
	private int cellWidth;
	private int cellHeight;

	public ThermalPanel(ThermalNetwork network) {

		if (network.getProperties().containsKey("rows")
				&& network.getProperties().containsKey("columns")) {
			this.gridX = (int) network.getPropertyLong("columns");
			this.gridY = (int) network.getPropertyLong("rows");
		} else {
			this.gridX = network.getNeuronCount();
			this.gridY = 1;
		}

		this.grid = new boolean[this.gridX * this.gridY];
		
		if( grid.length!=network.getNeuronCount() ) {
			throw new WorkBenchError("The (rows x columns) must equal the neuron count.");
		}
		
		this.addMouseListener(this);
		this.network = network;
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
		EngineArray.fill(this.network.getWeights(), 0);
	}

	/**
	 * Run the neural network.
	 */
	public void go() {
		for (int i = 0; i < this.grid.length; i++) {
			this.network.getCurrentState().setData(i, grid[i]);
		}

		if (this.network instanceof HopfieldNetwork) {
			((HopfieldNetwork) this.network).run();
		} else {
			((BoltzmannMachine) this.network).run();
		}

		for (int i = 0; i < this.grid.length; i++) {
			grid[i] = this.network.getCurrentState().getBoolean(i);
		}
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
		
		if( this.network instanceof BoltzmannMachine ) {
			EncogWorkBench.displayError("Error", "Boltzmann machine training is not supported.");
			return;
		}
		
		BiPolarNeuralData pattern = new BiPolarNeuralData(this.grid.length);

		for (int i = 0; i < this.grid.length; i++) {
			pattern.setData(i, grid[i]);
		}

		((HopfieldNetwork) this.network).addPattern(pattern);

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

}
