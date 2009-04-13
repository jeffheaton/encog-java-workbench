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
package org.encog.workbench.frames.render;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.encog.matrix.Matrix;
import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationLinear;
import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.activation.ActivationTANH;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.Layer;
import org.encog.persist.EncogPersistedObject;
import org.encog.util.NormalizeInput;

public class NetworkLayerRenderer extends JPanel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Layer layer;
	private boolean selected;
	private final Font titleFont;
	private final Font regularFont;
	private final ImageIcon iconFeedforward;
	private final ImageIcon iconHopfield;
	private final ImageIcon iconSOM;
	private final ImageIcon iconSimple;

	public NetworkLayerRenderer() {

		final Object obj = this.getClass().getResource(
				"/resource/iconLayerFeedforward.png");

		obj.getClass();

		this.iconFeedforward = new ImageIcon(this.getClass().getResource(
				"/resource/iconLayerFeedforward.png"));
		this.iconHopfield = new ImageIcon(this.getClass().getResource(
				"/resource/iconLayerHopfield.png"));
		this.iconSOM = new ImageIcon(this.getClass().getResource(
				"/resource/iconLayerSOM.png"));
		this.iconSimple = new ImageIcon(this.getClass().getResource(
				"/resource/iconLayerSimple.png"));
		this.titleFont = new Font("sansserif", Font.BOLD, 12);
		this.regularFont = new Font("serif", 0, 12);
	}

	private String getActivationType(final ActivationFunction a) {
		if (a instanceof ActivationLinear) {
			return "Linear";
		} else if (a instanceof ActivationSigmoid) {
			return "Sigmoid";
		} else if (a instanceof ActivationTANH) {
			return "Hyperbolic Tangent (TANH)";
		} else {
			return "Unknown";
		}
	}

	/**
	 * @return the layer
	 */
	public Layer getLayer() {
		return this.layer;
	}

	private String getLayerType(final Layer layer) {
		/*if (layer.isInput() && layer.isOutput()) {
			return "Input & Output";
		} else if (layer.isHidden()) {
			return "Hidden";
		} else if (layer.isInput()) {
			return "Input";
		} else if (layer.isOutput()) {
			return "Output";
		} else {
			return "Unknown";
		}*/
		return "";
	}

	public Component getListCellRendererComponent(final JList list,
			final Object value, // value
			// to
			// display
			final int index, // cell index
			final boolean isSelected, // is the cell selected
			final boolean cellHasFocus) // the list and the cell have the focus
	{
		setLayer((Layer) value);
		setSelected(isSelected);
		return this;
	}

	public String getMatrix(final Matrix matrix) {
		if (matrix == null) {
			return "N/A";
		}
		return matrix.getRows() + "x" + matrix.getCols() + " (rows x cols)";

	}

	

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return this.selected;
	}

	public void paint(final Graphics g) {
		final int width = getWidth();
		final int height = getHeight();

		if (this.selected) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, width, height);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
		}

		g.setColor(Color.GRAY);
		g.drawRect(0, 0, width - 1, height - 1);

		final FontMetrics titleMetrics = g.getFontMetrics(this.titleFont);
		final FontMetrics regularMetrics = g.getFontMetrics(this.regularFont);

		int y = titleMetrics.getHeight();

		

	}

	/**
	 * @param layer
	 *            the layer to set
	 */
	public void setLayer(final Layer layer) {
		this.layer = layer;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(final boolean selected) {
		this.selected = selected;
	}

	private boolean shouldDisplayName(final EncogPersistedObject obj) {
		if (obj.getName() == null && obj.getDescription() == null) {
			return false;
		}

		if (obj.getName() != null && obj.getName().length() > 0) {
			return true;
		}

		if (obj.getDescription() != null && obj.getDescription().length() > 0) {
			return true;
		}

		return false;
	}

}
