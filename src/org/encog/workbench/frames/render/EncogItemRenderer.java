/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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

import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.WorkbenchFonts;

public class EncogItemRenderer extends JPanel implements ListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 987233162876263335L;

	private DirectoryEntry encogObject;
	private boolean selected;
	private final ImageIcon iconNeuralNet;
	private final ImageIcon iconTrainingSet;
	private final ImageIcon iconProp;
	private final ImageIcon iconText;
	private final ImageIcon iconUnknown;
	private final ImageIcon iconTrainingCont;
	private final ImageIcon iconPopulation;

	public EncogItemRenderer() {
		this.iconNeuralNet = new ImageIcon(this.getClass().getResource(
				"/resource/iconNeuralNet.png"));
		this.iconTrainingSet = new ImageIcon(this.getClass().getResource(
				"/resource/iconTrain.png"));
		this.iconText = new ImageIcon(this.getClass().getResource(
			"/resource/iconText.png"));
		this.iconProp = new ImageIcon(this.getClass().getResource(
			"/resource/iconProperties.png"));
		this.iconUnknown = new ImageIcon(this.getClass().getResource(
			"/resource/iconUnknown.png"));
		this.iconTrainingCont = new ImageIcon(this.getClass().getResource(
			"/resource/iconTrainingCont.png"));
		this.iconPopulation = new ImageIcon(this.getClass().getResource(
			"/resource/iconPopulation.png"));
	}

	/**
	 * @return the encogObject
	 */
	public DirectoryEntry getEncogObject() {
		return this.encogObject;
	}

	public Component getListCellRendererComponent(final JList list,
			final Object value, // value to display
			final int index, // cell index
			final boolean isSelected, // is the cell selected
			final boolean cellHasFocus) // the list and the cell have the focus
	{
		setEncogObject((DirectoryEntry) value);
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

		final FontMetrics titleMetrics = g.getFontMetrics(WorkbenchFonts.getTitle2Font());
		final FontMetrics regularMetrics = g.getFontMetrics(WorkbenchFonts.getTextFont());

		int y = titleMetrics.getHeight();
		
		getIcon().paintIcon(this, g, 4, 4);
		
		g.setFont(WorkbenchFonts.getTitle2Font());
		g.setColor(Color.BLACK);
		
		
		g.drawString(getObjectName(), 70, y);
		y += titleMetrics.getHeight();
		g.setFont(WorkbenchFonts.getTextFont());
		g.drawString(getEncogObject().getDescription() + "("
				+ getEncogObject().getName() + ")", 70, y);
		y += regularMetrics.getHeight();
		
		
	}
	
	private ImageIcon getIcon()
	{
		if( EncogPersistedCollection.TYPE_BASIC_NET.equals(this.getEncogObject().getType()) )
		{
			return this.iconNeuralNet;
		}
		else if( EncogPersistedCollection.TYPE_TRAINING.equals(this.getEncogObject().getType()) )
		{
			return this.iconTrainingSet;
		}
		else if( EncogPersistedCollection.TYPE_TEXT.equals(this.getEncogObject().getType()) )
		{
			return this.iconText;
		}
		else if( EncogPersistedCollection.TYPE_PROPERTY.equals(this.getEncogObject().getType()) )
		{
			return this.iconProp;
		}
		else if( EncogPersistedCollection.TYPE_TRAINING_CONTINUATION.equals(this.getEncogObject().getType()) )
		{
			return this.iconTrainingCont;
		}
		else if( EncogPersistedCollection.TYPE_POPULATION.equals(this.getEncogObject().getType()) )
		{
			return this.iconPopulation;
		}
		else
		{
			return this.iconUnknown;
		}
	}
	
	private String getObjectName()
	{
		if( EncogPersistedCollection.TYPE_BASIC_NET.equals(this.getEncogObject().getType()) )
		{
			return "Neural Network";
		}
		else if( EncogPersistedCollection.TYPE_TRAINING.equals(this.getEncogObject().getType()) )
		{
			return "Training Set";
		}
		else if( EncogPersistedCollection.TYPE_TEXT.equals(this.getEncogObject().getType()) )
		{
			return "Text";
		}
		else if( EncogPersistedCollection.TYPE_PROPERTY.equals(this.getEncogObject().getType()) )
		{
			return "Property Data";
		}
		else if( EncogPersistedCollection.TYPE_TRAINING_CONTINUATION.equals(this.getEncogObject().getType()) )
		{
			return "Training Continuation";
		}
		else if( EncogPersistedCollection.TYPE_POPULATION.equals(this.getEncogObject().getType()) )
		{
			return "Population";
		}
		else
		{
			return "Unknown Object: " + getEncogObject().getType();
		}
	}

	/**
	 * @param encogObject
	 *            the encogObject to set
	 */
	public void setEncogObject(final DirectoryEntry encogObject) {
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
