package org.encog.workbench.tabs.files;

import java.io.File;

import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.tabs.EncogCommonTab;

public class BasicFileTab extends EncogCommonTab {

	public final File file;
	
	public BasicFileTab(EncogPersistedObject encogObject, File file) {
		super(encogObject);
		this.file = file;
	}
	
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
