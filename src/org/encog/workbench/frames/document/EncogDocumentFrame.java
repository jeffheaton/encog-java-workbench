/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.frames.document;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.xml.transform.TransformerConfigurationException;

import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.EncogListFrame;
import org.encog.workbench.frames.render.EncogItemRenderer;
import org.encog.workbench.tabs.AboutTab;
import org.encog.workbench.tabs.ButtonTabComponent;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.EncogTabManager;
import org.encog.workbench.util.ExtensionFilter;
import org.encog.workbench.util.MouseUtil;
import org.encog.workbench.util.treetable.JTreeTable;
import org.encog.workbench.util.treetable.TreeTableModel;
import org.xml.sax.SAXException;

public class EncogDocumentFrame extends EncogCommonFrame {

	private EncogDocumentOperations operations;
	private EncogMenus menus;
	private EncogPopupMenus popupMenus;
	private boolean closed = false;
	private JTree table;
	private JSplitPane split;
	private JTabbedPane documentTabs;
	private EncogTabManager tabManager;
	private AboutTab aboutTab;

	public static final ExtensionFilter ENCOG_FILTER = new ExtensionFilter(
			"Encog Files", ".eg");
	public static final ExtensionFilter CSV_FILTER = new ExtensionFilter(
			"CSV Files", ".csv");
	public static final String WINDOW_TITLE = "Encog Workbench";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4161616483326975155L;

	private final EncogCollectionModel collectionModel;

	public EncogDocumentFrame() {
		this.setSize(640, 480);

		this.operations = new EncogDocumentOperations(this);
		this.menus = new EncogMenus(this);
		this.popupMenus = new EncogPopupMenus(this);

		addWindowListener(this);

		this.collectionModel = createModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.aboutTab = new AboutTab();
		
		this.menus.initMenuBar();
		initContents();
		this.popupMenus.initPopup();
		this.collectionModel.invalidate(EncogWorkBench.getInstance()
				.getCurrentFile());
		this.table.updateUI();
		this.tabManager = new EncogTabManager(this);
	}

	public void actionPerformed(final ActionEvent event) {
		this.menus.actionPerformed(event);
		this.popupMenus.actionPerformed(event);
	}

	private EncogCollectionModel createModel() {
		EncogObjectDirectory root = new EncogObjectDirectory("Encog");
		return new EncogCollectionModel(root);
	}

	private void initContents() {
		// setup the contents list
		this.table = new JTree(this.collectionModel);
		this.table.addMouseListener(this);

		final JScrollPane scrollPane = new JScrollPane(this.table);

		this.documentTabs = new JTabbedPane();
		this.documentTabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		
		this.split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                scrollPane, this.documentTabs);
		this.split.setDividerLocation(150);
		
		getContentPane().add(this.split);
		redraw();
	}

	public void redraw() {

		// set the title properly
		if (EncogWorkBench.getInstance().getCurrentFileName() == null) {
			setTitle(EncogDocumentFrame.WINDOW_TITLE + " : Untitled");
		} else {
			setTitle(EncogDocumentFrame.WINDOW_TITLE + " : "
					+ EncogWorkBench.getInstance().getCurrentFileName());
		}

		this.collectionModel.invalidate(EncogWorkBench.getInstance()
				.getCurrentFile());
		this.table.updateUI();
	}

	public void rightMouseClicked(final MouseEvent e, final Object item) {
		this.popupMenus.rightMouseClicked(e, item);
	}

	public void windowClosed(final WindowEvent e) {
		System.exit(0);

	}

	public void windowOpened(final WindowEvent e) {
	}

	public void windowClosing(final WindowEvent e) {
		if (!this.closed) {
			if (EncogWorkBench.displayQuery("Save?",
					"Would you like to save your changes?")) {
				this.operations.performFileSave();
			}
			this.closed = true;
		}
		super.windowClosing(e);
		EncogWorkBench.saveConfig();
	}

	/**
	 * @return the operations
	 */
	public EncogDocumentOperations getOperations() {
		return operations;
	}

	/**
	 * @return the menus
	 */
	public EncogMenus getMenus() {
		return menus;
	}

	/**
	 * @return the popupMenus
	 */
	public EncogPopupMenus getPopupMenus() {
		return popupMenus;
	}

	protected void openItem(Object item) {
		this.operations.openItem(item);

	}

	public DirectoryEntry getSelectedValue() {
		TreePath path = this.table.getSelectionPath();
		DirectoryEntry result = null;

		if (path != null) {
			Object obj = path.getLastPathComponent();
			if( obj instanceof EncogCollectionEntry)
			{
				result = ((EncogCollectionEntry) obj).getEntry();
			}
		}

		return result;
	}

	public void mouseClicked(MouseEvent e) {
		TreePath path = this.table.getSelectionPath();

		if (path != null) {
			Object obj = path.getLastPathComponent();

			if (obj instanceof EncogCollectionEntry) {
				DirectoryEntry item = ((EncogCollectionEntry) obj).getEntry();

				if (MouseUtil.isRightClick(e)) {
					rightMouseClicked(e, item);
				}

				if (e.getClickCount() == 2) {

					openItem(item);
				}

			}
		}

	}

	public void openTab(EncogCommonTab tab) {
	
		int i = this.documentTabs.getTabCount();
		
        this.documentTabs.add(tab.getEncogObject().getName(), tab);
        documentTabs.setTabComponentAt(i,new ButtonTabComponent(this,tab));	
        this.tabManager.add(tab);
        this.documentTabs.setSelectedComponent(tab);	
	}
	
	public void openTab(EncogCommonTab tab, String title) {
		
		int i = this.documentTabs.getTabCount();
		
        this.documentTabs.add(title, tab);
        documentTabs.setTabComponentAt(i,new ButtonTabComponent(this,tab));	
        this.tabManager.add(tab);
        this.documentTabs.setSelectedComponent(tab);
		
	}
	
	public void openModalTab(EncogCommonTab tab, String title) {
		
		if( this.tabManager.alreadyOpen(tab) )
			return;
		
		int i = this.documentTabs.getTabCount();
		
        this.documentTabs.add(title, tab);
        documentTabs.setTabComponentAt(i,new ButtonTabComponent(this,tab));	
        this.tabManager.add(tab);
        tab.setModal(true);
        this.documentTabs.setSelectedComponent(tab);
        this.documentTabs.setEnabled(false);
        this.table.setEnabled(false);
		
	}
	
	public JTabbedPane getDocumentTabs() {
		return this.documentTabs;
	}

	public void closeTab(EncogCommonTab tab) {
		tab.close();
		this.tabManager.remove(tab);
		getDocumentTabs().remove(tab);
		
		if( tab.isModal() )
		{
	        this.documentTabs.setEnabled(true);
	        this.table.setEnabled(true);
		}
		
	}
	
	public EncogTabManager getTabManager()
	{
		return this.tabManager;
	}
	
	public void displayAboutTab()
	{
		this.openTab(this.aboutTab, "About");
	}

}
