package org.encog.workbench.frames.document.tree;

import org.encog.persist.EncogMemoryCollection;
import org.encog.persist.EncogPersistedObject;

public class ProjectEGItem extends ProjectItem implements Comparable<ProjectEGItem> {

	private EncogMemoryCollection collection;
	private EncogPersistedObject obj;
	
	public ProjectEGItem(EncogMemoryCollection collection, EncogPersistedObject obj)
	{
		this.collection = collection;
		this.obj = obj;
	}
	
	public int compareTo(ProjectEGItem o) {
		return this.obj.getName().compareTo(o.getObj().getName());
	}
	
	public String toString() {
		return this.obj.getName();
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
	
	

}
