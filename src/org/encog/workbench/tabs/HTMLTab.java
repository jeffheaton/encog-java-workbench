package org.encog.workbench.tabs;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.encog.workbench.frames.document.tree.ProjectFile;

public class HTMLTab extends EncogCommonTab {

	private final JScrollPane scroll;
	private final JEditorPane editor;
	
	public HTMLTab(ProjectFile encogObject) {
		super(encogObject);
		
		this.editor = new JEditorPane("text/html","");				
		this.editor.setEditable(false);

		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.editor);
		add(this.scroll, BorderLayout.CENTER);
	}
	
	public void display(String text)
	{
		this.editor.setText(text);
		this.editor.setSelectionStart(0);
		this.editor.setSelectionEnd(0);
	}

	

}
