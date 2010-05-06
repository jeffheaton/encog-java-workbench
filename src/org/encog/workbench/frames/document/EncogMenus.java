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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.process.training.Training;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.TextDataTab;
import org.encog.workbench.tabs.ValidationChart;

public class EncogMenus {
	public static final String FILE_NEW = "New";
	public static final String FILE_CLOSE = "Close";
	public static final String FILE_OPEN = "Open...";
	public static final String FILE_SAVE = "Save";
	public static final String FILE_SAVE_AS = "Save As...";
	public static final String FILE_REVERT = "Revert";
	public static final String FILE_QUIT = "Quit...";
	public static final String FILE_IMPORT = "Import CSV...";

	public static final String EDIT_CUT = "Cut";
	public static final String EDIT_COPY = "Copy";
	public static final String EDIT_PASTE = "Paste";
	public static final String EDIT_CONFIG = "Config...";

	public static final String OBJECTS_CREATE = "Create Object...";
	public static final String OBJECTS_DELETE = "Delete Object...";

	public static final String TOOLS_CLOUD = "Connect to an Encog Cloud...";
	public static final String TOOLS_TRAIN = "Train...";
	public static final String TOOLS_CODE = "Generate Code...";
	public static final String TOOLS_EVALUATE = "Evaluate Network...";
	public static final String TOOLS_BENCHMARK = "Benchmark Encog...";
	public static final String TOOLS_BROWSE = "Browse Web Data...";
	public static final String TOOLS_VALIDATION_CHART = "Validation Chart...";

	public static final String HELP_ABOUT = "About Encog Workbench...";

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JMenu menuObjects;
	private JMenu menuHelp;
	private JMenu menuTools;
	
	private JMenuItem menuFileNew;
	private JMenuItem menuFileClose;
	private JMenuItem menuFileOpen;
	private JMenuItem menuFileSave;
	private JMenuItem menuFileSaveAs;
	private JMenuItem menuFileRevert;
	private JMenuItem menuFileQuit;
	private JMenuItem menuFileImport;

	private JMenuItem menuEditCut;
	private JMenuItem menuEditCopy;
	private JMenuItem menuEditPaste;
	private JMenuItem menuEditConfig;

	private JMenuItem menuObjectsCreate;
	private JMenuItem menuObjectsDelete;

	private JMenuItem menuToolsCloud;
	private JMenuItem menuToolsTrain;
	private JMenuItem menuToolsGenerate;
	private JMenuItem menuToolsEvaluate;
	private JMenuItem menuToolsBenchmark;
	private JMenuItem menuToolsBrowse;
	private JMenuItem menuToolsValidation;

	private JMenuItem menuHelpAbout;

	private EncogDocumentFrame owner;

	public EncogMenus(EncogDocumentFrame owner) {
		this.owner = owner;
	}

	void initMenuBar() {
		// menu bar
		this.menuBar = new JMenuBar();
		this.menuFile = new JMenu("File");
		this.menuFileNew = this.menuFile.add(owner
				.addItem(this.menuFile, EncogMenus.FILE_NEW, 'n'));
		this.menuFileOpen = this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_OPEN,
				'o'));
		this.menuFile.addSeparator();
		this.menuFileClose = this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_CLOSE,
				'c'));
		this.menuFile.addSeparator();
		this.menuFileSave = this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_SAVE,
				's'));
		this.menuFileSaveAs = this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_SAVE_AS,
				'a'));
		this.menuFileRevert = this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_REVERT,
				'r'));
		this.menuFile.addSeparator();
		this.menuFileImport = this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_IMPORT,
				'i'));
		this.menuFile.addSeparator();
		this.menuFileQuit = this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_QUIT,
				'q'));
		this.menuFile.addActionListener(this.owner);
		this.menuBar.add(this.menuFile);

		this.menuEdit = new JMenu("Edit");
		this.menuEditCut = this.menuEdit.add(owner
				.addItem(this.menuEdit, EncogMenus.EDIT_CUT, 'x'));
		this.menuEditCopy = this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_COPY,
				'c'));
		this.menuEditPaste = this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_PASTE,
				'v'));
		this.menuEdit.addSeparator();
		this.menuEditConfig = this.menuEdit.add(owner.addItem(this.menuEdit,
				EncogMenus.EDIT_CONFIG, 'f'));
		this.menuBar.add(this.menuEdit);

		this.menuObjects = new JMenu("Objects");
		this.menuObjectsCreate = this.menuObjects.add(owner.addItem(this.menuObjects,
				EncogMenus.OBJECTS_CREATE, 'c'));
		this.menuObjectsDelete = this.menuObjects.add(owner.addItem(this.menuObjects,
				EncogMenus.OBJECTS_DELETE, 'd'));
		this.menuBar.add(this.menuObjects);

		this.menuTools = new JMenu("Tools");
		this.menuToolsCloud = owner.addItem(this.menuTools, EncogMenus.TOOLS_CLOUD, 'c');
		this.menuToolsGenerate = owner.addItem(this.menuTools, EncogMenus.TOOLS_CODE, 'g');
		this.menuToolsTrain = owner.addItem(this.menuTools, EncogMenus.TOOLS_TRAIN, 't');
		this.menuToolsBenchmark = owner.addItem(this.menuTools, EncogMenus.TOOLS_BENCHMARK, 'k');
		this.menuToolsEvaluate = owner.addItem(this.menuTools, EncogMenus.TOOLS_EVALUATE, 'e');
		this.menuToolsBrowse = owner.addItem(this.menuTools, EncogMenus.TOOLS_BROWSE, 'b');
		this.menuToolsValidation = owner.addItem(this.menuTools, EncogMenus.TOOLS_VALIDATION_CHART, 'v');
		this.menuBar.add(this.menuTools);

		this.menuHelp = new JMenu("Help");
		this.menuHelpAbout = this.menuHelp.add(owner.addItem(this.menuHelp, EncogMenus.HELP_ABOUT,
				'a'));
		this.menuBar.add(this.menuHelp);

		owner.setJMenuBar(this.menuBar);
	}
	
	public void updateMenus()
	{
		boolean modal = this.owner.isModalTabOpen();
		boolean supportsClipboard = false;
		
		JTabbedPane tabs = EncogWorkBench.getInstance().getMainWindow().getDocumentTabs();

		
		EncogCommonTab currentTab = (EncogCommonTab)tabs.getSelectedComponent();
		
		if( currentTab instanceof TextDataTab )
		{
			supportsClipboard = true;
		}
		
		this.menuFileNew.setEnabled(!modal);
		this.menuFileClose.setEnabled(!modal);
		this.menuFileOpen.setEnabled(!modal);
		this.menuFileSave.setEnabled(!modal);
		this.menuFileSaveAs.setEnabled(!modal);
		this.menuFileRevert.setEnabled(!modal);
		this.menuFileQuit.setEnabled(true);
		this.menuFileImport.setEnabled(!modal);

		this.menuEditCut.setEnabled(!modal && supportsClipboard);
		this.menuEditCopy.setEnabled(!modal && supportsClipboard);
		this.menuEditPaste.setEnabled(!modal && supportsClipboard);
		this.menuEditConfig.setEnabled(!modal);

		this.menuObjectsCreate.setEnabled(!modal);
		this.menuObjectsDelete.setEnabled(!modal);

		this.menuToolsCloud.setEnabled(!modal);
		this.menuToolsTrain.setEnabled(!modal);
		this.menuToolsGenerate.setEnabled(!modal);
		this.menuToolsEvaluate.setEnabled(!modal);
		this.menuToolsBenchmark.setEnabled(!modal);
		this.menuToolsBrowse.setEnabled(!modal);
		this.menuToolsValidation.setEnabled(!modal);

		this.menuHelpAbout.setEnabled(!modal);
	}

	public void actionPerformed(final ActionEvent event) {
		EncogWorkBench.getInstance().getMainWindow().endWait();
		if (event.getActionCommand().equals(EncogMenus.FILE_OPEN)) {
			owner.getOperations().performFileOpen();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_SAVE)) {
			owner.getOperations().performFileSave();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_SAVE_AS)) {
			owner.getOperations().performFileSaveAs();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_REVERT)) {
			owner.getOperations().performFileRevert();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_IMPORT)) {
			owner.getOperations().performImport(null);
		} else if (event.getActionCommand().equals(EncogMenus.FILE_QUIT)) {
			owner.getOperations().performQuit();
		} else if (event.getActionCommand().equals(EncogMenus.OBJECTS_CREATE)) {
			owner.getOperations().performObjectsCreate();
		} else if (event.getActionCommand().equals(EncogMenus.OBJECTS_DELETE)) {
			owner.getOperations().performObjectsDelete();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_CLOSE)) {
			owner.getOperations().performFileClose();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_NEW)) {
			owner.getOperations().performFileClose();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_CUT)) {
			owner.getOperations().performEditCut();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_COPY)) {
			owner.getOperations().performEditCopy();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_PASTE)) {
			owner.getOperations().performEditPaste();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_CONFIG)) {
			owner.getOperations().performEditConfig();
		} else if (event.getActionCommand().equals(EncogMenus.HELP_ABOUT)) {
			owner.getOperations().performHelpAbout();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_BENCHMARK)) {
			owner.getOperations().performBenchmark();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_CLOUD)) {
			owner.getOperations().performCloudLogin();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_BROWSE)) {
			owner.getOperations().performBrowse();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_EVALUATE)) {
			owner.getOperations().performEvaluate();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_CODE)) {
			owner.getOperations().performGenerateCode();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_TRAIN)) {
			Training training = new Training();
			training
					.perform(EncogWorkBench.getInstance().getMainWindow(), null);
		} else if (event.getActionCommand().equals(
				EncogMenus.TOOLS_VALIDATION_CHART)) {
			ValidationChart check = new ValidationChart();
			check.perform(EncogWorkBench.getInstance().getMainWindow());
		}
	}
}
