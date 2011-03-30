package org.encog.workbench.tabs.files;

import java.io.IOException;

import org.encog.util.file.FileUtil;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.tabs.HTMLTab;

public class HTMLFileTab extends HTMLTab  {

	
	public HTMLFileTab(ProjectFile encogObject) {
		super(encogObject);
		String contents;
		try {
			contents = FileUtil.readFileAsString(encogObject.getFile());
			display(contents);
		} catch (IOException e) {
			throw new WorkBenchError(e);
		}
		
	}
	
	@Override
	public String getName() {
		return this.getEncogObject().getFile().getName();
	}

	
}
