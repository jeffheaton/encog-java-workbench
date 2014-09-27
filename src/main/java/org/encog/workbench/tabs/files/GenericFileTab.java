/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.tabs.files;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.encog.util.Format;
import org.encog.util.HTMLReport;
import org.encog.workbench.frames.document.tree.ProjectFile;

public class GenericFileTab extends BasicFileTab {
	
	private final JScrollPane scroll;
	private final JEditorPane editor;
	
	public GenericFileTab(ProjectFile file) {
		super(file);
		
		this.editor = new JEditorPane("text/html","");				
		this.editor.setEditable(false);

		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.editor);
		add(this.scroll, BorderLayout.CENTER);
		init();
	}
	
	public void init() {
		HTMLReport report = new HTMLReport();
		report.beginHTML();
		String title = "Unknown File Type";
		report.title(title);
		report.beginBody();
		report.h1(title);
		report.para("Unknown file type.  Do not know how to display.");
		
		report.beginTable();
		report.tablePair("File Size", Format.formatMemory(this.getEncogObject().getFile().length()));
		report.tablePair("Last Modified", new Date(this.getEncogObject().getFile().lastModified()).toString());
		
		report.endTable();

		report.endBody();
		report.endHTML();
		this.display(report.toString());

	}
	
	public void display(String text)
	{
		this.editor.setText(text);
		this.editor.setSelectionStart(0);
		this.editor.setSelectionEnd(0);
	}
	

}
