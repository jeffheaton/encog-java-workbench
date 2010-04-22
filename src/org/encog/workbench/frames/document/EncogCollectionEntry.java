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
