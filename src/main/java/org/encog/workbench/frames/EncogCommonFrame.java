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
package org.encog.workbench.frames;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

public abstract class EncogCommonFrame extends JFrame implements
		WindowListener, ActionListener, MouseListener, ComponentListener {

	private Object encogObject;
	private EncogCommonFrame parent;
	private boolean closed;
	private boolean closeSilent;

	public EncogCommonFrame(boolean closeSilent) {
		this.closed = false;
		addWindowListener(this);
		addComponentListener(this);
		this.closeSilent = closeSilent;
	}

	public EncogCommonFrame() {
		this(false);
	}

	public JMenuItem addItem(final JMenu m, final String s, final int key) {
		
		final JMenuItem mi = new JMenuItem(s);
		mi.addActionListener(this);
		if( key>0 ) {
			KeyStroke stroke = KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
			mi.setAccelerator(stroke);
		}
		m.add(mi);
		return mi;
	}

	public JMenuItem addItem(final JPopupMenu m, final String s, final int key) {

		final JMenuItem mi = new JMenuItem(s, key);
		mi.addActionListener(this);
		m.add(mi);
		return mi;
	}

	public void copy() {

	}

	public void cut() {

	}

	/**
	 * @return the encogObject
	 */
	public Object getEncogObject() {
		return this.encogObject;
	}

	/**
	 * @return the parent
	 */
	public EncogCommonFrame getParent() {
		return this.parent;
	}


	public void mouseEntered(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(final MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void paste() {

	}

	public void redraw() {
	}

	/**
	 * @param encogObject
	 *            the encogObject to set
	 */
	public void setEncogObject(final Object encogObject) {
		this.encogObject = encogObject;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(final EncogCommonFrame parent) {
		this.parent = parent;
	}

	public void windowActivated(final WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void windowClosed(final WindowEvent e) {

	}

	public void windowClosing(final WindowEvent e) {

		if (!this.closed) {
			this.closed = true;

			if (getParent() != null) {
				getParent().redraw();
			}
		}

	}

	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
