package org.encog.workbench.tabs;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.tabs.files.text.NonWrappingTextPane;
import org.encog.workbench.util.EncogFonts;

public class HTMLTab extends EncogCommonTab {

	private final JScrollPane scroll;
	private final JEditorPane editor;
	
	public HTMLTab(EncogPersistedObject encogObject) {
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
