package org.encog.workbench.tabs;

import org.encog.util.HTMLReport;
import org.encog.workbench.frames.document.tree.ProjectEGFile;

public class UnknownObjectTab extends HTMLTab {

	public UnknownObjectTab(ProjectEGFile encogObject) {
		super(encogObject);
		HTMLReport report = new HTMLReport();
		report.beginHTML();
		String title = "Unknown Encog Object Type";
		report.title(title);
		report.beginBody();
		report.h1(title);
		report.para("Unknown Object: " + encogObject.getEncogType());
		report.endBody();
		report.endHTML();
		this.display(report.toString());
		
	}
	
	@Override
	public String getName() {
		return "Unknown :" + this.getEncogObject().getName();
	}

}
