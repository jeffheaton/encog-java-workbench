package org.encog.workbench.tabs.files.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.encog.workbench.tabs.EncogCommonTab;

public class BasicTextDocListener implements  DocumentListener  {

	private EncogCommonTab owner;
	
	public BasicTextDocListener(EncogCommonTab owner) {
		this.owner = owner;
	}
	
	public void changedUpdate(DocumentEvent arg0) {
		owner.setDirty(true);	
	}

	public void insertUpdate(DocumentEvent arg0) {
		owner.setDirty(true);
	}

	public void removeUpdate(DocumentEvent arg0) {
		owner.setDirty(true);
	}

}
