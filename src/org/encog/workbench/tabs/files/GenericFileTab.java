package org.encog.workbench.tabs.files;

import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.nio.ByteOrder;
import java.util.Date;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.encog.Encog;
import org.encog.engine.util.Format;
import org.encog.util.HTMLReport;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.util.EncogFonts;

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
