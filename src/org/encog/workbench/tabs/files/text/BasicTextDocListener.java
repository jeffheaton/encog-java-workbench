package org.encog.workbench.tabs.files.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BasicTextDocListener implements  DocumentListener  {

	private boolean dirty;
	
	public void changedUpdate(DocumentEvent arg0) {
		this.dirty = true;		
	}

	public void insertUpdate(DocumentEvent arg0) {
		this.dirty = true;
	}

	public void removeUpdate(DocumentEvent arg0) {
		this.dirty = true;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	

}
