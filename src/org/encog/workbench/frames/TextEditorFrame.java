package org.encog.workbench.frames;

import java.awt.event.WindowEvent;

import org.encog.neural.data.TextData;

public class TextEditorFrame extends TextFrame {

	private TextData data;
	
	public TextEditorFrame(TextData data) {
		super("Edit Text Data", false);
		this.data = data;
		this.setText(data.getText());
	}
	
	@Override
	public void windowClosing(final WindowEvent e) {
		super.windowClosing(e);
		this.data.setText(this.getText());
	}
	

}
