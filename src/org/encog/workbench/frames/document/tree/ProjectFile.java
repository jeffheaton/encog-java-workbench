package org.encog.workbench.frames.document.tree;

import java.io.File;

import org.encog.util.file.FileUtil;

public class ProjectFile extends ProjectItem {
	private File file;
	
	public ProjectFile(File file)
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

	public String getExtension() {
		return FileUtil.getFileExt(file);
	}
}
