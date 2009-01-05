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

import org.encog.bot.spider.SpiderOptions;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.PropertyData;
import org.encog.neural.data.TextData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Network;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.parse.ParseTemplate;

public class EncogItemRenderer extends JPanel implements ListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 987233162876263335L;

	private EncogPersistedObject encogObject;
	private boolean selected;
	private final Font titleFont;
	private final Font regularFont;
	private final ImageIcon iconNeuralNet;
	private final ImageIcon iconTrainingSet;
	private final ImageIcon iconSpiderTemplate;
	private final ImageIcon iconParseTemplate;
	private final ImageIcon iconProp;
	private final ImageIcon iconText;

	public EncogItemRenderer() {
		this.iconNeuralNet = new ImageIcon(this.getClass().getResource(
				"/resource/iconNeuralNet.png"));
		this.iconTrainingSet = new ImageIcon(this.getClass().getResource(
				"/resource/iconTrain.png"));
		this.iconSpiderTemplate = new ImageIcon(this.getClass().getResource(
				"/resource/iconSpiderTemplate.png"));
		this.iconParseTemplate = new ImageIcon(this.getClass().getResource(
				"/resource/iconParseTemplate.png"));
		this.iconText = new ImageIcon(this.getClass().getResource(
			"/resource/iconText.png"));
		this.iconProp = new ImageIcon(this.getClass().getResource(
			"/resource/iconOptions.png"));
		this.titleFont = new Font("sansserif", Font.BOLD, 12);
		this.regularFont = new Font("serif", 0, 12);
	}

	/**
	 * @return the encogObject
	 */
	public EncogPersistedObject getEncogObject() {
		return this.encogObject;
	}

	public Component getListCellRendererComponent(final JList list,
			final Object value, // value to display
			final int index, // cell index
			final boolean isSelected, // is the cell selected
			final boolean cellHasFocus) // the list and the cell have the focus
	{
		setEncogObject((EncogPersistedObject) value);
		setSelected(isSelected);
		return this;
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

		if (getEncogObject() instanceof Network) {
			this.iconNeuralNet.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Neural Network", 70, y);
			y += titleMetrics.getHeight();
			g.setFont(this.regularFont);
			g.drawString(getEncogObject().getDescription() + "("
					+ getEncogObject().getName() + ")", 70, y);
			y += regularMetrics.getHeight();
			final BasicNetwork network = (BasicNetwork) getEncogObject();
			g.drawString("Layers: " + network.getLayers().size(), 70, y);
		} else if (getEncogObject() instanceof NeuralDataSet) {
			this.iconTrainingSet.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Training Data", 70, y);
			y += titleMetrics.getHeight();
			g.setFont(this.regularFont);
			g.drawString(getEncogObject().getDescription() + "("
					+ getEncogObject().getName() + ")", 70, y);
			y += regularMetrics.getHeight();
			final NeuralDataSet data = (NeuralDataSet) getEncogObject();
			g.drawString("Ideal Size: " + data.getIdealSize() + ","
					+ "Input Size: " + data.getInputSize(), 70, y);

		} else if (getEncogObject() instanceof SpiderOptions) {
			this.iconSpiderTemplate.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Spider Options", 70, y);
			y += titleMetrics.getHeight();
			g.setFont(this.regularFont);
			g.drawString(getEncogObject().getDescription() + "("
					+ getEncogObject().getName() + ")", 70, y);
			y += regularMetrics.getHeight();
			final SpiderOptions data = (SpiderOptions) getEncogObject();
			/*g.drawString("Ideal Size: " + data.getIdealSize() + ","
					+ "Input Size: " + data.getInputSize(), 70, y);*/
		} else if (getEncogObject() instanceof ParseTemplate) {
			this.iconParseTemplate.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Parse Template", 70, y);
			y += titleMetrics.getHeight();
			g.setFont(this.regularFont);
			g.drawString(getEncogObject().getDescription() + "("
					+ getEncogObject().getName() + ")", 70, y);
			y += regularMetrics.getHeight();
			final ParseTemplate data = (ParseTemplate) getEncogObject();
			/*g.drawString("Ideal Size: " + data.getIdealSize() + ","
					+ "Input Size: " + data.getInputSize(), 70, y);*/

		} else if (getEncogObject() instanceof TextData ) {
			this.iconText.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Text File", 70, y);
			y += titleMetrics.getHeight();
			g.setFont(this.regularFont);
			g.drawString(getEncogObject().getDescription() + "("
					+ getEncogObject().getName() + ")", 70, y);
			y += regularMetrics.getHeight();
			final TextData data = (TextData) getEncogObject();
			/*g.drawString("Ideal Size: " + data.getIdealSize() + ","
					+ "Input Size: " + data.getInputSize(), 70, y);*/
		} else if (getEncogObject() instanceof PropertyData ) {
			this.iconProp.paintIcon(this, g, 4, 4);
			g.setFont(this.titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Property Data", 70, y);
			y += titleMetrics.getHeight();
			g.setFont(this.regularFont);
			g.drawString(getEncogObject().getDescription() + "("
					+ getEncogObject().getName() + ")", 70, y);
			y += regularMetrics.getHeight();
			final PropertyData data = (PropertyData) getEncogObject();
			/*g.drawString("Ideal Size: " + data.getIdealSize() + ","
					+ "Input Size: " + data.getInputSize(), 70, y);*/
		}
		
	}

	/**
	 * @param encogObject
	 *            the encogObject to set
	 */
	public void setEncogObject(final EncogPersistedObject encogObject) {
		this.encogObject = encogObject;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(final boolean selected) {
		this.selected = selected;
	}
}
