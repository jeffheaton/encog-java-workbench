package org.encog.workbench.tabs.files;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.neural.data.TextData;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.EncogFonts;
import org.encog.workbench.util.FileUtil;

public class TextFileTab extends BasicFileTab {
	
	private final JTextArea text;
	private final JScrollPane scroll;
	
	public TextFileTab(File file) {
		super(file);
		
		this.text = new JTextArea();
		this.text.setFont(EncogFonts.getInstance().getCodeFont());
		this.text.setEditable(true);
		this.text.setLineWrap(true);
		this.text.setWrapStyleWord(true);
		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.text);
		add(this.scroll, BorderLayout.CENTER);
	
		try {
			this.text.setText(FileUtil.readFileAsString(file));
		} catch (IOException e) {
			EncogWorkBench.displayError("Error Reading File", e);
		}
	}
	


	public void setText(final String t) {
		this.text.setText(t);
	}
	
	public String getText()
	{
		return this.text.getText();
	}
	
	public boolean close()
	{
		//((TextData)getEncogObject()).setText(this.getText());
		return true;
	}



	public boolean isTextSelected() {
		return this.text.getSelectionEnd()>this.text.getSelectionStart();
	}
	
}
