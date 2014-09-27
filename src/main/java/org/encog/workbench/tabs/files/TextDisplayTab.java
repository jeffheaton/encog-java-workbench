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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.files.text.BasicTextDocListener;
import org.encog.workbench.tabs.files.text.NonWrappingTextPane;
import org.encog.workbench.util.EncogFonts;

public class TextDisplayTab extends EncogCommonTab implements ComponentListener,
		CaretListener {

	private final NonWrappingTextPane editor;
	private final JScrollPane scroll;
	private final BasicTextDocListener dirty = new BasicTextDocListener(this);
	private final JLabel status = new JLabel();
	private final String name;

	public TextDisplayTab(String theName) {
		super(null);
		this.name = theName;
		this.editor = new NonWrappingTextPane();
		this.editor.setFont(EncogFonts.getInstance().getCodeFont());
		this.editor.setEditable(true);
		this.editor.addCaretListener(this);

		this.setLayout(new BorderLayout());
		this.scroll = new JScrollPane(this.editor);
		add(this.scroll, BorderLayout.CENTER);
		this.addComponentListener(this);
		this.status.setText(" ");
		add(this.status, BorderLayout.SOUTH);
		
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
		int rn = (pos == 0) ? 1 : 0;
		try {
			int offs = pos;
			while (offs > 0) {
				offs = Utilities.getRowStart(editor, offs) - 1;
				rn++;
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return rn;
	}

	public static int getColumn(int pos, JTextComponent editor) {
		try {
			return pos - Utilities.getRowStart(editor, pos) + 1;
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
		s.append(getRow(pos, this.editor));
		s.append(", Col: ");
		s.append(getColumn(pos, this.editor));
		this.status.setText(s.toString());

	}

	public void find() {
		try {
			String editorText = this.editor.getDocument().getText(0,
					this.editor.getDocument().getLength());

			String text = EncogWorkBench.displayInput("Search:");
			if (text != null) {
				int start = this.editor.getCaretPosition();
				int idx = editorText.indexOf(text, start);
				if (idx == -1) {
					EncogWorkBench.displayError("Not Found",
							"Could not find, searching from current position.");
				} else {
					this.editor.setSelectionStart(idx);
					this.editor.setSelectionEnd(idx + text.length());
				}

			}
		} catch (BadLocationException ex) {
			// ignroe
		}

	}

	@Override
	public String getName() {
		return this.name;
	}

}
