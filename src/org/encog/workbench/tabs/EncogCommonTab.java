package org.encog.workbench.tabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.document.EncogDocumentFrame;

public class EncogCommonTab extends JPanel {
	
	private final EncogPersistedObject encogObject;
	private EncogDocumentFrame owner;
	private boolean modal;
	
	public EncogCommonTab(final EncogPersistedObject encogObject)
	{
		this.encogObject = encogObject;

	}

	public EncogPersistedObject getEncogObject() {
		return encogObject;
	}

	public void close()
	{
		
	}
	
	public void dispose()
	{
		owner.closeTab(this);
	}

	public void setParent(EncogDocumentFrame owner) {
		this.owner = owner;		
	}

	public boolean isModal() {
		return modal;
	}

	public void setModal(boolean modal) {
		this.modal = modal;
	}
	
	
	
}
