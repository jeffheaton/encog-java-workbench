/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.dialogs.select;

/**
 * Used to provide individual items to the SelectDialog.  Currently
 * just holds the text for an item, but will likely be expanded later
 * to hold icons as well.
 * @author jeff
 *
 */
public class SelectItem {

	/**
	 * The text to display.
	 */
	private final String text;
	
	private final String description;

	/**
	 * Construct this item with the specified text.
	 * @param text The text to display for this item.
	 */
	public SelectItem(final String text, final String description) {
		this.text = text;
		this.description = description;
	}

	/**
	 * @return The text to display.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	

}
