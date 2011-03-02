package org.encog.workbench.tabs;

import org.encog.persist.EncogPersistedObject;
import org.encog.util.HTMLReport;

public class UnknownObjectTab extends HTMLTab {

	public UnknownObjectTab(EncogPersistedObject encogObject) {
		super(encogObject);
		HTMLReport report = new HTMLReport();
		report.beginHTML();
		String title = "Unknown Object Type";
		report.title(title);
		report.beginBody();
		report.h1(title);
		report.para("Unknown object: " + encogObject.getClass().getSimpleName());
		report.endBody();
		report.endHTML();
		this.display(report.toString());
		
	}

}
