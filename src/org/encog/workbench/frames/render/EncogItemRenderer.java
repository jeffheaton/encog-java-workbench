/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
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
