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
package org.encog.workbench.frames.document;

import java.net.URL;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

import org.encog.persist.DirectoryEntry;

public class EncogCollectionEntry extends DefaultMutableTreeNode {

	private DirectoryEntry entry;

	public EncogCollectionEntry(DirectoryEntry entry) {
		this.entry = entry;
	}

	public void setName(String name) {

	}

	public String getName() {
		return entry.getName();
	}
	
	public String getDescription() {
		return entry.getDescription();
	}


	public String toString() {
		return getName();
	}

	public DirectoryEntry getEntry() {
		return this.entry;
	}
}
