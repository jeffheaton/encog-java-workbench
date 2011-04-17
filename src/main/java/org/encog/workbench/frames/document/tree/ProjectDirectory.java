package org.encog.workbench.frames.document.tree;

import java.io.File;

public class ProjectDirectory extends ProjectItem {
	
	private File file;
	
	public ProjectDirectory(File file)
	{
		this.file = file;
	}
		
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	public String toString()
	{
		return file.getName();
	}
}
