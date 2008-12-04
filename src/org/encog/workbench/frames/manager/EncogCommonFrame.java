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

import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;

public abstract class EncogCommonFrame extends JFrame implements WindowListener,
ActionListener, MouseListener {

	private EncogPersistedObject encogObject;
	private EncogFrameManager subwindows;
	private EncogCommonFrame parent;
	
	public EncogCommonFrame()
	{
		this.subwindows = new EncogFrameManager(this);
		this.addWindowListener(this);
	}
	
	protected JMenuItem addItem(JMenu m, String s, int key) {

		JMenuItem mi = new JMenuItem(s, key);
		mi.addActionListener(this);
		m.add(mi);
		return mi;
	}

	protected JMenuItem addItem(JPopupMenu m, String s, int key) {

		JMenuItem mi = new JMenuItem(s, key);
		mi.addActionListener(this);
		m.add(mi);
		return mi;
	}

	
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public void windowClosing(WindowEvent e) {
		if( this.getParent()!=null )
		{
			this.getParent().getSubwindows().remove(this);
			this.getParent().redraw();
		}
		
		for(EncogCommonFrame frame: this.getSubwindows().getFrames())
		{
			frame.dispose();
		}

	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	public void windowClosed(WindowEvent e) {

		
	}

	/**
	 * @return the encogObject
	 */
	public EncogPersistedObject getEncogObject() {
		return encogObject;
	}

	/**
	 * @param encogObject the encogObject to set
	 */
	public void setEncogObject(EncogPersistedObject encogObject) {
		this.encogObject = encogObject;
	}

	/**
	 * @return the subwindows
	 */
	public EncogFrameManager getSubwindows() {
		return subwindows;
	}

	/**
	 * @return the parent
	 */
	public EncogCommonFrame getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(EncogCommonFrame parent) {
		this.parent = parent;
	}
	
	public void redraw()
	{		
	}
	
	public void cut()
	{
		
	}
	
	public void copy()
	{
		
	}
	
	public void paste()
	{
		
	}
}
