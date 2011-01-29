package org.encog.workbench.tabs.files;

import java.io.File;

import org.encog.workbench.tabs.EncogCommonTab;

public class BasicFileTab extends EncogCommonTab {

	public final File file;
	
	public BasicFileTab(File file) {
		super(null);
		this.file = file;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	
	

}
