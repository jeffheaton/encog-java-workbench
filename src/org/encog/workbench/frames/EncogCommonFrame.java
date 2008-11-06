package org.encog.workbench.frames;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public abstract class EncogCommonFrame extends JFrame implements WindowListener,
ActionListener, MouseListener {

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		
	}
	
}
