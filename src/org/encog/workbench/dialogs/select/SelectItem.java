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

	/**
	 * Construct this item with the specified text.
	 * @param text The text to display for this item.
	 */
	public SelectItem(final String text) {
		this.text = text;
	}

	/**
	 * @return The text to display.
	 */
	public String getText() {
		return this.text;
	}

}
