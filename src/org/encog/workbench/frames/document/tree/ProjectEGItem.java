package org.encog.workbench.frames.document.tree;

import org.encog.persist.EncogMemoryCollection;
import org.encog.persist.EncogPersistedObject;

public class ProjectEGItem extends ProjectItem implements Comparable<ProjectEGItem> {

	private EncogMemoryCollection collection;
	private EncogPersistedObject obj;
	private ProjectEGFile encogFile;
	
	public ProjectEGItem(EncogMemoryCollection collection, EncogPersistedObject obj, ProjectEGFile encogFile)
	{
		this.collection = collection;
		this.obj = obj;
		this.encogFile = encogFile;
	}
	
	public int compareTo(ProjectEGItem o) {
		return this.obj.getName().compareTo(o.getObj().getName());
	}
	
	public String toString() {
		return this.obj.getName() + " (" + this.obj.getClass().getSimpleName() + ")";
	}

	/**
	 * @return the collection
	 */
	public EncogMemoryCollection getCollection() {
		return collection;
	}

	/**
	 * @return the obj
	 */
	public EncogPersistedObject getObj() {
		return obj;
	}

	public ProjectEGFile getEncogFile() {
		return encogFile;
	}
}
