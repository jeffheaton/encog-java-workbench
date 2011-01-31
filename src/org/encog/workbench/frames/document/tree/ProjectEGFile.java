package org.encog.workbench.frames.document.tree;

import java.io.File;
import java.util.Arrays;

import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogMemoryCollection;

public class ProjectEGFile extends ProjectFile {

	private final EncogMemoryCollection collection;
	private ProjectEGItem[] children;
	
	public ProjectEGFile(File file) {
		super(file);
		this.collection = new EncogMemoryCollection();
		this.collection.load(file.toString());
		generateChildrenList();
	}

	/**
	 * @return the collection
	 */
	public EncogMemoryCollection getCollection() {
		return collection;
	}
	
	public void generateChildrenList()
	{
		this.children = new ProjectEGItem[this.collection.getDirectory().size()];
		int index = 0;
		for(DirectoryEntry entry : this.collection.getDirectory() )
		{
			this.children[index++] = new ProjectEGItem(this.collection, collection.find(entry));
		}
		
		Arrays.sort(this.children);
	}
	
	public ProjectEGItem[] getChildren() {
		return this.children;
	}
}
