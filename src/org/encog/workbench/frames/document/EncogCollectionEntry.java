package org.encog.workbench.frames.document;

import java.net.URL;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

import org.encog.persist.DirectoryEntry;

/**
 * BookmarkEntry represents a bookmark. It contains a URL, a user definable
 * string, and two dates, one giving the date the URL was last visited and the
 * other giving the date the bookmark was created.
 */
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
