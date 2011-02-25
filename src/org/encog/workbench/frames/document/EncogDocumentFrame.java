/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.frames.document;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.encog.neural.data.PropertyData;
import org.encog.neural.data.TextData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogPersistedObject;
import org.encog.script.EncogScript;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.splash.EncogWorkbenchSplash;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.document.tree.ProjectTree;
import org.encog.workbench.tabs.AboutTab;
import org.encog.workbench.tabs.ButtonTabComponent;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.EncogScriptTab;
import org.encog.workbench.tabs.EncogTabManager;
import org.encog.workbench.tabs.PropertyDataTab;
import org.encog.workbench.tabs.TextDataTab;
import org.encog.workbench.tabs.analyst.EncogAnalystTab;
import org.encog.workbench.tabs.bnetwork.BasicNetworkTab;
import org.encog.workbench.tabs.files.BinaryDataTab;
import org.encog.workbench.tabs.files.GenericFileTab;
import org.encog.workbench.tabs.files.ImageFileTab;
import org.encog.workbench.tabs.files.TextFileTab;
import org.encog.workbench.util.ExtensionFilter;
import org.encog.workbench.util.FileUtil;

public class EncogDocumentFrame extends EncogCommonFrame {

	private EncogDocumentOperations operations;
	private EncogMenus menus;
	private EncogPopupMenus popupMenus;
	private boolean closed = false;
	private boolean splashed = false;
	private JSplitPane projectSplit;
	private JTabbedPane documentTabs;
	private EncogTabManager tabManager;
	private AboutTab aboutTab;
	private boolean modalTabOpen;

	private JSplitPane documentSplit;
	private EncogOutputPanel outputPanel;
	private ProjectTree tree;

	private final static String[] B = { ".csv", ".xlsx" };
	public static final ExtensionFilter ENCOG_FILTER = new ExtensionFilter(
			"Encog Files (*.eg)", ".eg");
	public static final ExtensionFilter CSV_FILTER = new ExtensionFilter(
			"External Files (*.xlsx,*.csv)", B);
	public static final ExtensionFilter ENCOG_BINARY = new ExtensionFilter(
			"Binary Encog Training Files (*.egb)", ".egb");

	public static final String WINDOW_TITLE = "Encog Workbench";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4161616483326975155L;

	public EncogDocumentFrame() {
		this.setSize(750, 480);

		EncogWorkBench.getInstance().setMainWindow(this);

		this.operations = new EncogDocumentOperations(this);
		this.menus = new EncogMenus(this);
		this.popupMenus = new EncogPopupMenus(this);

		addWindowListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.aboutTab = new AboutTab();

		this.menus.initMenuBar();
		initContents();

	}

	public void actionPerformed(final ActionEvent event) {
		this.menus.actionPerformed(event);
		this.popupMenus.actionPerformed(event);
	}

	private void initContents() {

		this.tree = new ProjectTree(this);
		this.documentTabs = new JTabbedPane();
		this.documentTabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

		this.outputPanel = new EncogOutputPanel();

		this.documentSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				this.documentTabs, this.outputPanel);

		this.projectSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				this.tree, this.documentSplit);
		this.projectSplit.setDividerLocation(150);
		this.documentSplit.setDividerLocation(this.getHeight() - 200);

		getContentPane().add(this.projectSplit);

		this.popupMenus.initPopup();

		this.tabManager = new EncogTabManager(this);

		this.menus.updateMenus();
		redraw();
	}

	public void redraw() {

		// set the title properly
		if (EncogWorkBench.getInstance().getCurrentFileName() == null) {
			setTitle(EncogDocumentFrame.WINDOW_TITLE + " : No Project");
		} else {
			setTitle(EncogDocumentFrame.WINDOW_TITLE + " : "
					+ EncogWorkBench.getInstance().getCurrentFileName());
		}
		getMenus().updateMenus();
		this.tree.refresh();
	}

	public void windowClosed(final WindowEvent e) {
		System.exit(0);

	}

	public void windowOpened(final WindowEvent e) {
		if (!this.splashed) {
			EncogWorkbenchSplash splash = new EncogWorkbenchSplash();
			splash.process();
			this.splashed = true;
		}
	}

	public void windowClosing(final WindowEvent e) {
		if (!this.closed) {
			/*if (EncogWorkBench.displayQuery("Save?",
					"Would you like to save your changes?")) {
				//this.operations.performFileSave();
			}*/
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

	public void openTab(EncogCommonTab tab) {

		openTab(tab, null);
	}

	public void openTab(EncogCommonTab tab, String title) {
		String titleToUse;

		int i = this.documentTabs.getTabCount();

		if (title == null && tab.getEncogObject() != null)
			titleToUse = tab.getEncogObject().getName();
		else
			titleToUse = title;

		this.documentTabs.add(titleToUse, tab);

		if (!this.tabManager.contains(tab)) {
			if (i < this.documentTabs.getTabCount())
				documentTabs.setTabComponentAt(i, new ButtonTabComponent(this,
						tab));
			this.tabManager.add(tab);
		}
		this.documentTabs.setSelectedComponent(tab);
		this.menus.updateMenus();
	}

	public void openModalTab(EncogCommonTab tab, String title) {

		if (this.tabManager.alreadyOpen(tab))
			return;

		int i = this.documentTabs.getTabCount();

		this.documentTabs.add(title, tab);
		documentTabs.setTabComponentAt(i, new ButtonTabComponent(this, tab));
		this.tabManager.add(tab);
		tab.setModal(true);
		this.documentTabs.setSelectedComponent(tab);
		this.documentTabs.setEnabled(false);
		this.tree.setEnabled(false);
		this.modalTabOpen = true;
		this.menus.updateMenus();

	}

	public JTabbedPane getDocumentTabs() {
		return this.documentTabs;
	}

	public void closeTab(EncogCommonTab tab) throws IOException {
		if (tab.close()) {
			this.tabManager.remove(tab);
			getDocumentTabs().remove(tab);

			if (tab.isModal()) {
				this.documentTabs.setEnabled(true);
				this.tree.setEnabled(true);
				this.modalTabOpen = false;
			}
			this.menus.updateMenus();
		}

	}

	public EncogTabManager getTabManager() {
		return this.tabManager;
	}

	public void displayAboutTab() {
		this.openTab(this.aboutTab, "About");
	}

	public boolean isModalTabOpen() {
		return this.modalTabOpen;
	}

	public void beginWait() {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
	}

	public void endWait() {
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	public void componentResized(ComponentEvent e) {
		if (this.documentSplit != null)
			this.documentSplit.setDividerLocation(this.getHeight() - 200);
	}

	public EncogOutputPanel getOutputPane() {
		return this.outputPanel;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the tree
	 */
	public ProjectTree getTree() {
		return tree;
	}

	public void openFile(File file) {
		try {
			EncogWorkBench.getInstance().getMainWindow().beginWait();
			EncogCommonTab tab = this.tabManager.find(file);
			if (tab == null) {
				String extension = FileUtil.getFileExt(file);
				if (extension.equalsIgnoreCase("txt")
						|| extension.equalsIgnoreCase("csv")) {
					tab = new TextFileTab(file);
					this.openTab(tab, file.getName());
				} else if( extension.equals("ega")) {
					tab = new EncogAnalystTab(file);
					this.openTab(tab,file.getName());
				}
				else if (extension.equalsIgnoreCase("egb")) {
					tab = new BinaryDataTab(file);
					this.openTab(tab, file.getName());
				} else if (extension.equalsIgnoreCase("jpg")
						|| extension.equalsIgnoreCase("jpeg")
						|| extension.equalsIgnoreCase("gif")
						|| extension.equalsIgnoreCase("png")) {
					tab = new ImageFileTab(file);
					this.openTab(tab, file.getName());
				} else {
					tab = new GenericFileTab(file);
					this.openTab(tab, file.getName());
				}
			} else {
				this.documentTabs.setSelectedComponent(tab);
				this.menus.updateMenus();
			}
		} finally {
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
	}

	public void openTextFile(File file) {
		try {
			EncogWorkBench.getInstance().getMainWindow().beginWait();
			EncogCommonTab tab = this.tabManager.find(file);
			if (tab == null) {
				tab = new TextFileTab(file);
				this.openTab(tab, file.getName());
			} else {
				this.documentTabs.setSelectedComponent(tab);
				this.menus.updateMenus();
			}
		} finally {
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
	}

	public void open(EncogPersistedObject obj) {
		if (obj instanceof EncogScript) {
			EncogCommonTab tab = EncogWorkBench.getInstance().getMainWindow()
					.getTabManager().find(obj);

			if (tab == null) {
				tab = new EncogScriptTab(obj);
				this.openTab(tab, obj.getName());
			} else {
				this.documentTabs.setSelectedComponent(tab);
			}
		} else if (obj instanceof TextData) {
			EncogCommonTab tab = EncogWorkBench.getInstance().getMainWindow()
					.getTabManager().find(obj);

			if (tab == null) {
				tab = new TextDataTab(obj);
				this.openTab(tab, obj.getName());
			} else {
				this.documentTabs.setSelectedComponent(tab);
			}
		} else if (obj instanceof PropertyData) {
			EncogCommonTab tab = EncogWorkBench.getInstance().getMainWindow()
					.getTabManager().find(obj);

			if (tab == null) {
				tab = new PropertyDataTab((PropertyData) obj);
				this.openTab(tab, obj.getName());
			} else {
				this.documentTabs.setSelectedComponent(tab);
			}
		} else if (obj instanceof BasicNetwork) {
			EncogCommonTab tab = EncogWorkBench.getInstance().getMainWindow()
					.getTabManager().find(obj);

			if (tab == null) {
				tab = new BasicNetworkTab((BasicNetwork) obj);
				this.openTab(tab, obj.getName());
			} else {
				this.documentTabs.setSelectedComponent(tab);
			}
		}

	}

	public void changeDirectory(String path) {
		if( this.tabManager.getTabs().size()>0 ) {
			if( !EncogWorkBench.askQuestion("Changing Directory", "Before you can change the directory, all windows must be closed.\nDo you wish to continue?") ) {
				return;				
			}
			this.tabManager.closeAll();
		}
		
		EncogWorkBench.getInstance().getMainWindow().getTree()
		.refresh(path);		
	}

}
