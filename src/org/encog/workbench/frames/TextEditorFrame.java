package org.encog.workbench.frames;

import java.awt.event.WindowEvent;

import org.encog.neural.data.TextData;

public class TextEditorFrame extends TextFrame {

	
	public TextEditorFrame(TextData data) {
		super("Edit Text Data", false);
		this.setEncogObject(data);
		this.setText(data.getText());
	}
	
	@Override
	public void windowClosing(final WindowEvent e) {
		((TextData)getEncogObject()).setText(this.getText());
		super.windowClosing(e);
		
	}
	

}
