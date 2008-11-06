package org.encog.workbench.frames;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;

public abstract class EncogListFrame extends EncogCommonFrame {

	protected JList contents;
	
	abstract public void rightMouseClicked(MouseEvent e, Object item);
	abstract protected void openItem(Object item);
	
	public void mouseClicked(MouseEvent e) {

		int index = this.contents.locationToIndex(e.getPoint());
		ListModel dlm = this.contents.getModel();
		Object item = null;
		if (index != -1) {
			item = dlm.getElementAt(index);
		}
		this.contents.ensureIndexIsVisible(index);
		this.contents.setSelectedIndex(index);

		if (e.getButton() == MouseEvent.BUTTON3) {
			rightMouseClicked(e, item);
		}

		if (e.getClickCount() == 2) {

			openItem(item);
		}
	}
	
}
