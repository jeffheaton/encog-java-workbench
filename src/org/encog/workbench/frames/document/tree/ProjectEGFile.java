package org.encog.workbench.frames.document.tree;

import java.io.File;

public class ProjectEGFile extends ProjectFile {
	
	private Object obj;
	
	public ProjectEGFile(File file) {
		super(file);
	}
	
	public Object getObject() {
		return obj;
	}
}
