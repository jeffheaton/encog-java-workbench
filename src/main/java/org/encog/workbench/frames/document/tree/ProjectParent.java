package org.encog.workbench.frames.document.tree;

import java.io.File;

public class ProjectParent extends ProjectDirectory {

	public ProjectParent(File file) {
		super(file);		
	}
	
	public String toString()
	{
		return "..";
	}

}
