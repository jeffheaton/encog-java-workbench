package org.encog.workbench.tabs.files.text;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JScrollPane;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.tabs.files.BasicFileTab;
import org.encog.workbench.util.EncogFonts;

public class BasicTextTab extends BasicFileTab implements ComponentListener {

	private final NonWrappingTextPane editor;
	private final JScrollPane scroll;
	private final BasicTextDocListener dirty = new BasicTextDocListener();

	public BasicTextTab(ProjectFile file) {
		super(file);

		this.editor = new NonWrappingTextPane();				
		this.editor.setFont(EncogFonts.getInstance().getCodeFont());
		this.editor.setEditable(true);

		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.editor);
		add(this.scroll, BorderLayout.CENTER);
		this.addComponentListener(this);
		loadFile();
	}

	public void loadFile() {
		try {
			InputStream is = new FileInputStream(this.getEncogObject().getFile());
			this.editor.read(is, null);
			is.close();
			this.editor.getDocument().addDocumentListener(this.dirty);
			dirty.setDirty(false);

		} catch (IOException ex) {
			throw new WorkBenchError(ex);
		}
	}

	@Override
	public void save() {
		try {
			FileWriter out = new FileWriter(this.getEncogObject().getFile());
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

	@Override
	public boolean close() throws IOException {
		if (this.dirty.isDirty()) {
			if (EncogWorkBench.askQuestion("Save",
					"Would you like to save this text file?")) {
				this.save();
			}
		}
		return true;
	}

	public boolean isTextSelected() {
		return this.editor.getSelectionEnd() > this.editor.getSelectionStart();
	}

	
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void componentShown(ComponentEvent e) {
		this.dirty.setDirty(false);
		
	}

	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public BasicTextDocListener getDirty() {
		return dirty;
	}
	
	
}
