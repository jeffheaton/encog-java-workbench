package org.encog.workbench.tabs.files.text;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.tabs.files.BasicFileTab;
import org.encog.workbench.util.EncogFonts;

public class BasicTextTab extends BasicFileTab {

	private final NonWrappingTextPane editor;
	private final JScrollPane scroll;
	private final BasicTextDocListener dirty = new BasicTextDocListener();

	public BasicTextTab(File file) {
		super(file);

		this.editor = new NonWrappingTextPane();				
		this.editor.setFont(EncogFonts.getInstance().getCodeFont());
		this.editor.setEditable(true);
		//this.text.setPreferredSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		// this.text.setLineWrap(true);
		// this.text.setWrapStyleWord(true);

		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.editor);
		add(this.scroll, BorderLayout.CENTER);	
		
		loadFile();
	}

	public void loadFile() {
		try {
			InputStream is = new FileInputStream(getFile());
			this.editor.read(is, null);
			is.close();
			this.editor.getDocument().addDocumentListener(this.dirty);
			dirty.setDirty(false);

		} catch (IOException ex) {
			throw new WorkBenchError(ex);
		}
	}

	public void saveFile() {
		try {
			FileWriter out = new FileWriter(getFile());
			this.editor.write(out);
			out.close();
			dirty.setDirty(false);
		} catch (IOException ex) {
			throw new WorkBenchError(ex);
		}
	}

	public void setText(final String t) {
		this.editor.setText(t);
	}

	public String getText() {
		return this.editor.getText();
	}

	public boolean close() throws IOException {
		if (this.dirty.isDirty()) {
			if (EncogWorkBench.askQuestion("Save",
					"Would you like to save this text file?")) {
				this.saveFile();
			}
		}
		return true;
	}

	public boolean isTextSelected() {
		return this.editor.getSelectionEnd() > this.editor.getSelectionStart();
	}
}
