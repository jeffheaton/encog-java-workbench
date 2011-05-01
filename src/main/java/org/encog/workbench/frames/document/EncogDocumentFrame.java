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

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import org.encog.ml.MLMethod;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.networks.training.propagation.TrainingContinuation;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.splash.EncogWorkbenchSplash;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.frames.document.tree.ProjectTree;
import org.encog.workbench.tabs.AboutTab;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.EncogTabManager;
import org.encog.workbench.tabs.TrainingContTab;
import org.encog.workbench.tabs.UnknownObjectTab;
import org.encog.workbench.tabs.analyst.EncogAnalystTab;
import org.encog.workbench.tabs.files.BinaryDataTab;
import org.encog.workbench.tabs.files.GenericFileTab;
import org.encog.workbench.tabs.files.HTMLFileTab;
import org.encog.workbench.tabs.files.ImageFileTab;
import org.encog.workbench.tabs.files.TextFileTab;
import org.encog.workbench.tabs.mlmethod.MLMethodTab;
import org.encog.workbench.tabs.population.neat.NEATPopulationTab;
import org.encog.workbench.util.ExtensionFilter;
import org.encog.workbench.util.FileUtil;

public class EncogDocumentFrame extends EncogCommonFrame {

	private EncogDocumentOperations operations;
	private EncogMenus menus;
	private EncogPopupMenus popupMenus;
	private boolean closed = false;
	private boolean splashed = false;
	private JSplitPane projectSplit;
	private EncogTabManager tabManager;
	private AboutTab aboutTab;

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

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.aboutTab = new AboutTab();

		this.menus.initMenuBar();
		initContents();

	}

	public void actionPerformed(final ActionEvent event) {
		try {
		this.menus.actionPerformed(event);
		this.popupMenus.actionPerformed(event);
		} catch(Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
	}

	private void initContents() {

		this.tree = new ProjectTree(this);
		this.tabManager = new EncogTabManager(this);

		this.outputPanel = new EncogOutputPanel();

		this.documentSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				this.tabManager.getDocumentTabs(), this.outputPanel);

		this.projectSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				this.tree, this.documentSplit);
		this.projectSplit.setDividerLocation(150);
		this.documentSplit.setDividerLocation(this.getHeight() - 200);

		getContentPane().add(this.projectSplit);

		this.menus.updateMenus();
		redraw();
	}

	public void redraw() {

		// set the title properly
		if (EncogWorkBench.getInstance().getProjectDirectory() == null) {
			setTitle(EncogDocumentFrame.WINDOW_TITLE + " : No Project");
		} else {
			setTitle(EncogDocumentFrame.WINDOW_TITLE + " : "
					+ EncogWorkBench.getInstance().getProjectDirectory());
		}
		getMenus().updateMenus();
		this.tree.refresh();
	}

	public void windowClosed(final WindowEvent e) {
		getOperations().performQuit();
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
		EncogWorkBench.getInstance().getConfig().saveConfig();
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

	public EncogTabManager getTabManager() {
		return this.tabManager;
	}

	public void displayAboutTab() {
		this.tabManager.openTab(this.aboutTab);
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
	
	public void openEGFile(ProjectEGFile file) {
		Object obj = file.getObject();
		EncogCommonTab tab = null;
			
		if( obj instanceof MLMethod ) {
			tab = new MLMethodTab(file);
		} else if( obj instanceof NEATPopulation ) {
			tab = new NEATPopulationTab(file);
		} else if( obj instanceof TrainingContinuation ){ 
			tab = new TrainingContTab(file);
		} else {
			tab = new UnknownObjectTab(file);
		}
		
		if(tab!=null)
			this.tabManager.openTab(tab);
	}

	public void openFile(ProjectFile file) {
		try {
			EncogWorkBench.getInstance().getMainWindow().beginWait();
			EncogCommonTab tab = this.tabManager.find(file.getFile());
			
			if( tab!=null ) {
				this.tabManager.selectTab(tab);
				return;
			}
			
			if( file instanceof ProjectEGFile ) {
				openEGFile((ProjectEGFile) file);
				return;
			}
			
			if (tab == null) {
				String extension = FileUtil.getFileExt(file.getFile());
				if (extension.equalsIgnoreCase("txt")
						|| extension.equalsIgnoreCase("csv")) {
					tab = new TextFileTab(file);
					this.tabManager.openTab(tab);
				} else if (extension.equals("ega")) {
					tab = new EncogAnalystTab(file);
					this.tabManager.openTab(tab);
				} else if (extension.equalsIgnoreCase("html")) {
					tab = new HTMLFileTab(file);
					this.tabManager.openTab(tab);
				} else if (extension.equalsIgnoreCase("egb")) {
					tab = new BinaryDataTab(file);
					this.tabManager.openTab(tab);
				} else if (extension.equalsIgnoreCase("jpg")
						|| extension.equalsIgnoreCase("jpeg")
						|| extension.equalsIgnoreCase("gif")
						|| extension.equalsIgnoreCase("png")) {
					tab = new ImageFileTab(file);
					this.tabManager.openTab(tab);
				} else {
					tab = new GenericFileTab(file);
					this.tabManager.openTab(tab);
				}
			} 
		} catch(Throwable t) {
			EncogWorkBench.displayError("Error Reading File", t);
		} finally {
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
	}

	public void openTextFile(ProjectFile file) {
		try {
			EncogWorkBench.getInstance().getMainWindow().beginWait();
			EncogCommonTab tab = this.tabManager.find(file.getFile());
			if (tab == null) {
				tab = new TextFileTab(file);
				this.tabManager.openTab(tab);
			} else {
				this.tabManager.selectTab(tab);
				this.menus.updateMenus();
			}
		} finally {
			EncogWorkBench.getInstance().getMainWindow().endWait();
		}
	}

	public void changeDirectory(File path) {
		if (this.tabManager.getTabs().size() > 0) {
			if (!EncogWorkBench
					.askQuestion(
							"Changing Directory",
							"Before you can change the directory, all windows must be closed.\nDo you wish to continue?")) {
				return;
			}
			this.tabManager.closeAll();
		}

		EncogWorkBench.getInstance().getMainWindow().getTree().refresh(path);
	}
	
}
