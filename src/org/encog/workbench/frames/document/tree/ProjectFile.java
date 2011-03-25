package org.encog.workbench.frames.document.tree;

import java.io.File;

import org.encog.util.file.FileUtil;

public class ProjectFile extends ProjectItem {
	private File file;
	private boolean error;
	
	public ProjectFile(File file)
	{
		this(file,false);
	}
	
	public ProjectFile(File file, boolean error)
	{
		this.file = file;
		this.error = error;
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
		if( !error )
			return file.getName();
		else
			return file.getName() + " - Error";
	}

	public String getExtension() {
		return FileUtil.getFileExt(file);
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}
}
