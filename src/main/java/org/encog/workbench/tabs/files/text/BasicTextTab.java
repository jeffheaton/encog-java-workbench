package org.encog.workbench.tabs.files.text;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.tabs.files.BasicFileTab;
import org.encog.workbench.util.EncogFonts;

public class BasicTextTab extends BasicFileTab implements ComponentListener, CaretListener {

	private final NonWrappingTextPane editor;
	private final JScrollPane scroll;
	private final BasicTextDocListener dirty = new BasicTextDocListener(this);
	private final JLabel status = new JLabel();

	public BasicTextTab(ProjectFile file) {
		super(file);

		this.editor = new NonWrappingTextPane();				
		this.editor.setFont(EncogFonts.getInstance().getCodeFont());
		this.editor.setEditable(true);
		this.editor.addCaretListener(this);

		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.editor);
		add(this.scroll, BorderLayout.CENTER);
		this.addComponentListener(this);
		add(this.status,BorderLayout.SOUTH);		
		loadFile();
	}

	public void loadFile() {
		try {
			InputStream is = new FileInputStream(this.getEncogObject().getFile());
			this.editor.read(is, null);
			is.close();
			this.editor.getDocument().addDocumentListener(this.dirty);
			setDirty(false);

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
			setDirty(false);
		} catch (IOException ex) {
			throw new WorkBenchError(ex);
		}
	}

	public void setText(final String t) {
		this.editor.setText(t);
		this.editor.setCaretPosition(0);
	}

	public String getText() {
		return this.editor.getText();
	}

	@Override
	public boolean close() throws IOException {
		if (isDirty()) {
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
		setDirty(false);
		
	}

	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public BasicTextDocListener getDirty() {
		return dirty;
	}
	
	public static int getRow(int pos, JTextComponent editor) {
        int rn = (pos==0) ? 1 : 0;
        try {
            int offs=pos;
            while( offs>0) {
                offs=Utilities.getRowStart(editor, offs)-1;
                rn++;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return rn;
    }

    public static int getColumn(int pos, JTextComponent editor) {
        try {
            return pos-Utilities.getRowStart(editor, pos)+1;
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return -1;
    }


	@Override
	public void caretUpdate(CaretEvent e) {
		StringBuilder s = new StringBuilder();
		s.append("Row: ");
		int pos = this.editor.getCaretPosition();
		s.append(getRow(pos,this.editor));
		s.append(", Col: ");
		s.append(getColumn(pos,this.editor));
		this.status.setText(s.toString());
		
	}

	public void find() {
		String text = EncogWorkBench.displayInput("Search:");
		if( text!=null ) {
			int start = this.editor.getCaretPosition();
			int idx = this.getText().indexOf(text,start);
			if( idx==-1 ) {
				EncogWorkBench.displayError("Not Found", "Could not find, searching from current position.");
			} else {
				this.editor.setSelectionStart(idx);
				this.editor.setSelectionEnd(idx+text.length());
			}
			
		}
		
	}
	
	
}
