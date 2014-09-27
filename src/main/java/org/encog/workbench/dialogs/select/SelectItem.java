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
