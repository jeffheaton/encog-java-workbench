/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.encog.workbench.frames.visualize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.encog.matrix.Matrix;
import org.encog.neural.networks.BasicNetwork;

public class NetworkVisualizePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int NEURON_SIZE = 50;
	public static final int NEURON_BOUNDS = 80;
	private final BasicNetwork data;
	private Font neuronFont;
	private int layerStarts[];

	public NetworkVisualizePanel(final BasicNetwork data) {
		this.data = data;
		centerLayers(NEURON_BOUNDS);
		setPreferredSize(new Dimension(5000, 5000));
	}

	public void centerLayers(final int neuronWidth) {
		/*int maxLayers = 0;
		int layerCount = 0;
		for (final Layer layer : this.data.getLayers()) {
			if (layer.getNeuronCount() > maxLayers) {
				maxLayers = layer.getNeuronCount();
			}
			layerCount++;
		}

		final int center = maxLayers * neuronWidth / 2;

		this.layerStarts = new int[layerCount];
		int i = 0;
		for (final Layer layer : this.data.getLayers()) {
			this.layerStarts[i++] = center - layer.getNeuronCount()
					* neuronWidth / 2;
		}*/
	}

	public void drawNeuron(final Graphics g, final int x, final int y,
			final int size, final String name, final String threshold,
			final double t) {
		final Color c = getNeuronColor(t);
		final Color shadow = c.darker().darker();

		g.setColor(shadow);
		g.fillOval(x, y, size + 5, size + 5);
		g.setColor(c);
		g.fillOval(x, y, size, size);

		g.setColor(Color.white);
		g.setFont(this.neuronFont);
		final FontMetrics fm = g.getFontMetrics();
		int width;
		final int yloc = y + size / 2;
		width = fm.stringWidth(name);
		g.drawString(name, x + size / 2 - width / 2, yloc);
		width = fm.stringWidth(threshold);
		g
				.drawString(threshold, x + size / 2 - width / 2, yloc
						+ fm.getHeight());

	}

	public Color getNeuronColor(final double threshold) {
		double d = 0;

		d = 128.0 + threshold * 128.0;
		int i = (int) d;
		if (i < 0) {
			i = 0;
		} else if (i > 255) {
			i = 255;
		}
		return new Color(i, 0, 255 - i);
	}

	public void paint(final Graphics g) {
		final int width = getWidth();
		final int height = getHeight();

		int layerNum = 0;

		this.neuronFont = new Font("SansSerif", Font.BOLD,
				(int) (NEURON_SIZE * 0.19));

		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		int hidden = 0;

		int y = 0;
		/*for (final Layer layer : this.data.getLayers()) {
			if (layer.isHidden()) {
				hidden++;
			}
			for (int i = 0; i < layer.getNeuronCount(); i++) {
				String name = "";
				if (layer.isInput()) {
					name = "I-" + (i + 1);
				} else if (layer.isOutput()) {
					name = "O-" + (i + 1);
				} else if (layer.isHidden()) {
					name = "H" + hidden + "-" + (i + 1);
				}

				double t = 0;
				String threshold = "";
				if (layer.getPrevious() != null) {
					final Matrix matrix = layer.getPrevious().getMatrix();
					final int finalRow = matrix.getRows() - 1;
					t = matrix.get(finalRow, i);
					final double t2 = (int) (t * 1000) / 1000.0;
					threshold = "T=" + t2;
				}

				final int x = this.layerStarts[layerNum] + i * NEURON_BOUNDS;

				if (layer.hasMatrix()) {
					final Matrix matrix = layer.getMatrix();
					final int y2 = y + NEURON_BOUNDS * 2;

					for (int j = 0; j < layer.getNext().getNeuronCount(); j++) {
						final double weight = matrix.get(i, j);
						g.setColor(getNeuronColor(weight));

						final int x2 = this.layerStarts[layerNum + 1] + j
								* NEURON_BOUNDS;
						g.drawLine(x + NEURON_SIZE / 2 + 0, y + NEURON_SIZE / 2
								+ 0, x2 + NEURON_SIZE / 2 + 0, y2 + NEURON_SIZE
								/ 2 + 0);
						g.drawLine((x + NEURON_SIZE / 2), y + NEURON_SIZE / 2
								+ 1, (x2 + NEURON_SIZE / 2), y2 + NEURON_SIZE
								/ 2 + 1);
					}
				}

				drawNeuron(g, x, y, NEURON_SIZE, name, threshold, t);
			}
			layerNum++;
			y += NEURON_BOUNDS * 2;
		}*/
	}
}
