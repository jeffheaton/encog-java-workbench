/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.encog.workbench.frames.manager;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;

public abstract class EncogCommonFrame extends JFrame implements
		WindowListener, ActionListener, MouseListener {

	private Object encogObject;
	private final EncogFrameManager subwindows;
	private EncogCommonFrame parent;
	private boolean closed;
	private boolean closeSilent;

	public EncogCommonFrame(boolean closeSilent) {
		this.closed = false;
		this.subwindows = new EncogFrameManager(this);
		addWindowListener(this);
		this.closeSilent = closeSilent;
	}
	
	public EncogCommonFrame()
	{
		this(false);
	}

	public JMenuItem addItem(final JMenu m, final String s, final int key) {

		final JMenuItem mi = new JMenuItem(s, key);
		mi.addActionListener(this);
		m.add(mi);
		return mi;
	}

	public JMenuItem addItem(final JPopupMenu m, final String s,
			final int key) {

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

	/**
	 * @return the subwindows
	 */
	public EncogFrameManager getSubwindows() {
		return this.subwindows;
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
		
		if(!this.closed)
		{
			this.closed = true;
			
		if (getParent() != null) {
			getParent().getSubwindows().remove(this);
			getParent().redraw();
		}

		for (final EncogCommonFrame frame : getSubwindows().getFrames()) {
			frame.dispose();
		}
		
		if( this.encogObject instanceof EncogPersistedObject && !this.closeSilent)
		{
			if( EncogWorkBench.displayQuery("Save?","Would you like to save your changes to this object?"))
			{
			EncogPersistedObject eobj = (EncogPersistedObject)this.encogObject;
			EncogWorkBench.getInstance().getCurrentFile().add(eobj.getName(), eobj);
			}
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
}
