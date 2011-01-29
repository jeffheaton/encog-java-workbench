package org.encog.workbench.frames.document.tree;

import java.io.File;

import org.encog.persist.EncogMemoryCollection;

public class ProjectEGFile extends ProjectFile {

	private final EncogMemoryCollection collection;
	
	public ProjectEGFile(File file) {
		super(file);
		this.collection = new EncogMemoryCollection();
		this.collection.load(file.toString());
	}

	/**
	 * @return the collection
	 */
	public EncogMemoryCollection getCollection() {
		return collection;
	}
	
	

}
