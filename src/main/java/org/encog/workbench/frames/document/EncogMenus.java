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
package org.encog.workbench.frames.document;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import org.encog.Encog;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.process.CreateNewFile;
import org.encog.workbench.process.ImportExport;
import org.encog.workbench.process.TrainBasicNetwork;
import org.encog.workbench.tabs.EncogCommonTab;
import org.encog.workbench.tabs.SupportsClipboard;
import org.encog.workbench.tabs.ValidationChart;

public class EncogMenus {
	public static final String FILE_CHANGE_DIR = "Change Directory/Open Project";
	public static final String FILE_NEW_PROJECT = "New Project...";
	public static final String FILE_NEW_FILE = "New File...";
	public static final String FILE_IMPORT = "Import File...";
	public static final String FILE_SAVE = "Save";
	public static final String FILE_QUIT = "Quit...";

	public static final String EDIT_CUT = "Cut";
	public static final String EDIT_COPY = "Copy";
	public static final String EDIT_PASTE = "Paste";
	public static final String EDIT_SELECT_ALL = "Select All";
	public static final String EDIT_CONFIG = "Config...";
	public static final String EDIT_FIND = "Find...";

	public static final String VIEW_RBF = "Chart RBF...";
	
	public static final String TOOLS_TRAIN = "Train...";
	public static final String TOOLS_GENERATE_TRAINING = "Generate Training Data...";
	public static final String TOOLS_EVALUATE = "Evaluate Method...";
	public static final String TOOLS_BENCHMARK = "Benchmark Encog...";
	public static final String TOOLS_VALIDATION_CHART = "Validation Chart...";
	public static final String TOOLS_BIN2EXTERNAL = "Convert Encog Binary to Other File...";
	public static final String TOOLS_EXTERNAL2BIN = "Convert Other File to Encog Binary...";
	public static final String TOOLS_PROBEN = "Proben1...";
	public static final String TOOLS_NOISE = "Generate Noise...";
	public static final String TOOLS_WIZARD = "Wizards...";
	
		
	public static final String HELP_ABOUT = "About Encog Workbench...";

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JMenu menuHelp;
	private JMenu menuTools;
	private JMenu menuView;
	
	private JMenuItem menuFileNew;
	private JMenuItem menuFileNewProject;
	private JMenuItem menuFileImport;
	private JMenuItem menuFileChangeDir;
	private JMenuItem menuFileSeve;
	private JMenuItem menuFileQuit;

	private JMenuItem menuEditCut;
	private JMenuItem menuEditCopy;
	private JMenuItem menuEditPaste;
	private JMenuItem menuEditSelectAll;
	private JMenuItem menuEditConfig;
	private JMenuItem menuEditFind;
	
	private JMenuItem menuViewRBF;
	
	private JMenuItem menuToolsTrain;
	private JMenuItem menuToolsGenerate;
	private JMenuItem menuToolsEvaluate;
	private JMenuItem menuToolsBenchmark;
	private JMenuItem menuToolsBrowse;
	private JMenuItem menuToolsValidation;
	private JMenuItem menuToolsBin2Ext;
	private JMenuItem menuToolsExt2Bin;	
	private JMenuItem menuToolsProben;
	private JMenuItem menuToolsNoise;
	private JMenuItem menuToolsWizard;
	
	private JMenuItem menuHelpAbout;

	private EncogDocumentFrame owner;

	public EncogMenus(EncogDocumentFrame owner) {
		this.owner = owner;
	}

	void initMenuBar() {
		// menu bar
		this.menuBar = new JMenuBar();
		this.menuFile = new JMenu("File");
		this.menuFileNewProject = this.menuFile.add(owner
				.addItem(this.menuFile, EncogMenus.FILE_NEW_PROJECT, 0));
		this.menuFileChangeDir = this.menuFile.add(owner
				.addItem(this.menuFile, EncogMenus.FILE_CHANGE_DIR, KeyEvent.VK_C));
		this.menuFileNew = this.menuFile.add(owner
				.addItem(this.menuFile, EncogMenus.FILE_NEW_FILE, KeyEvent.VK_N));
		this.menuFileImport = this.menuFile.add(owner.addItem(this.menuFile,EncogMenus.FILE_IMPORT,KeyEvent.VK_I));
		this.menuFileSeve = this.menuFile.add(owner
				.addItem(this.menuFile, EncogMenus.FILE_SAVE, KeyEvent.VK_S));
		
		if (!Encog.isOSX()) {
			this.menuFile.addSeparator();
			this.menuFileQuit = this.menuFile.add(owner.addItem(this.menuFile, EncogMenus.FILE_QUIT,
					KeyEvent.VK_Q));
		}
		
		this.menuFile.addActionListener(this.owner);
		this.menuBar.add(this.menuFile);

		this.menuEdit = new JMenu("Edit");
		this.menuEditCut = this.menuEdit.add(owner
				.addItem(this.menuEdit, EncogMenus.EDIT_CUT, KeyEvent.VK_X));
		this.menuEditCopy = this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_COPY,
				KeyEvent.VK_C));
		this.menuEditPaste = this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_PASTE,
				KeyEvent.VK_V));
		this.menuEditSelectAll = this.menuEdit.add(owner.addItem(this.menuEdit, EncogMenus.EDIT_SELECT_ALL,
				KeyEvent.VK_A));
		this.menuEdit.addSeparator();
		this.menuEditConfig = this.menuEdit.add(owner.addItem(this.menuEdit,
				EncogMenus.EDIT_CONFIG, 0));
		this.menuEditFind = this.menuEdit.add(owner.addItem(this.menuEdit,
				EncogMenus.EDIT_FIND, KeyEvent.VK_F));		
		this.menuBar.add(this.menuEdit);
		
		this.menuView = new JMenu("View");
		this.menuViewRBF = owner.addItem(this.menuView, EncogMenus.VIEW_RBF, 0);
		this.menuBar.add(this.menuView);

		this.menuTools = new JMenu("Tools");
		//
		this.menuToolsGenerate = owner.addItem(this.menuTools, EncogMenus.TOOLS_GENERATE_TRAINING, 0);
		this.menuToolsTrain = owner.addItem(this.menuTools, EncogMenus.TOOLS_TRAIN, 0);
		this.menuToolsBenchmark = owner.addItem(this.menuTools, EncogMenus.TOOLS_BENCHMARK, 0);
		this.menuToolsEvaluate = owner.addItem(this.menuTools, EncogMenus.TOOLS_EVALUATE, 0);
		this.menuToolsValidation = owner.addItem(this.menuTools, EncogMenus.TOOLS_VALIDATION_CHART, 0);
		this.menuToolsBin2Ext = owner.addItem(this.menuTools, EncogMenus.TOOLS_BIN2EXTERNAL, 0);
		this.menuToolsExt2Bin = owner.addItem(this.menuTools, EncogMenus.TOOLS_EXTERNAL2BIN, 0);
		this.menuToolsProben = owner.addItem(this.menuTools, EncogMenus.TOOLS_PROBEN, 0);
		this.menuToolsNoise = owner.addItem(this.menuTools, EncogMenus.TOOLS_NOISE, 0);
		this.menuToolsWizard = owner.addItem(this.menuTools, EncogMenus.TOOLS_WIZARD, 0);
		this.menuBar.add(this.menuTools);		

		this.menuHelp = new JMenu("Help");
		this.menuHelpAbout = this.menuHelp.add(owner.addItem(this.menuHelp, EncogMenus.HELP_ABOUT,
				'a'));
		this.menuBar.add(this.menuHelp);

		owner.setJMenuBar(this.menuBar);
	}
	
	public void updateMenus()
	{
		boolean modal = this.owner.getTabManager().isModalTabOpen();
		boolean documentOpen = EncogWorkBench.getInstance().getProjectDirectory()!=null;
		JTabbedPane tabs = this.owner.getTabManager().getDocumentTabs();
		EncogCommonTab currentTab = (EncogCommonTab)tabs.getSelectedComponent();
		
		boolean supportsClipboard = currentTab instanceof SupportsClipboard;
			
		this.menuFileNew.setEnabled(!modal && documentOpen);
		this.menuFileChangeDir.setEnabled(!modal);
		this.menuFileNewProject.setEnabled(!modal);
		
		if (!Encog.isOSX()) {
			this.menuFileQuit.setEnabled(true);
		}
		this.menuFileImport.setEnabled(!modal && documentOpen);
		this.menuFileSeve.setEnabled(!modal && documentOpen && currentTab!=null);
		this.menuEditCut.setEnabled(!modal && supportsClipboard && documentOpen);
		this.menuEditCopy.setEnabled(!modal && supportsClipboard && documentOpen);
		this.menuEditPaste.setEnabled(!modal && supportsClipboard && documentOpen);
		this.menuEditSelectAll.setEnabled(!modal && supportsClipboard && documentOpen);
		this.menuEditConfig.setEnabled(!modal);

		this.menuViewRBF.setEnabled(!modal);
		
		this.menuToolsTrain.setEnabled(!modal && documentOpen);
		this.menuToolsGenerate.setEnabled(!modal && documentOpen);
		this.menuToolsEvaluate.setEnabled(!modal && documentOpen);
		this.menuToolsBenchmark.setEnabled(!modal);
		this.menuToolsValidation.setEnabled(!modal && documentOpen);
		this.menuToolsExt2Bin.setEnabled(!modal);		
		this.menuToolsBin2Ext.setEnabled(!modal);
		this.menuToolsWizard.setEnabled(!modal && documentOpen);

		this.menuHelpAbout.setEnabled(!modal);
	}

	public void actionPerformed(final ActionEvent event) {
		
		try {
		EncogWorkBench.getInstance().getMainWindow().endWait();
		if (event.getActionCommand().equals(EncogMenus.FILE_CHANGE_DIR)) {
			owner.getOperations().performFileChooseDirectory();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_NEW_PROJECT)) {
			owner.getOperations().performFileNewProject();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_NEW_FILE)) {
			CreateNewFile.performCreateFile();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_IMPORT)) {
			owner.getOperations().importFile();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_SAVE)) {
			owner.getOperations().performSave();
		} else if (event.getActionCommand().equals(EncogMenus.FILE_QUIT)) {
			owner.getOperations().performQuit();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_CUT)) {
			owner.getOperations().performEditCut();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_COPY)) {
			owner.getOperations().performEditCopy();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_PASTE)) {
			owner.getOperations().performEditPaste();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_SELECT_ALL)) {
			owner.getOperations().performEditSelectAll();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_CONFIG)) {
			owner.getOperations().performEditConfig();
		} else if (event.getActionCommand().equals(EncogMenus.EDIT_FIND)) {
			owner.getOperations().performEditFind();
		} else if (event.getActionCommand().equals(EncogMenus.HELP_ABOUT)) {
			owner.getOperations().performHelpAbout();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_BENCHMARK)) {
			owner.getOperations().performBenchmark();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_PROBEN)) {
			owner.getOperations().performProben();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_NOISE)) {
			owner.getOperations().performNoise();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_EVALUATE)) {
			owner.getOperations().performEvaluate();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_TRAIN)) {
			TrainBasicNetwork t = new TrainBasicNetwork(null,null);
			t.performTrain();
		} else if (event.getActionCommand().equals(
				EncogMenus.TOOLS_VALIDATION_CHART)) {
			ValidationChart check = new ValidationChart();
			check.perform(EncogWorkBench.getInstance().getMainWindow());
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_BIN2EXTERNAL)) {
			ImportExport.performBin2External();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_EXTERNAL2BIN)) {
			ImportExport.performExternal2Bin(null,null,null);
		} else if (event.getActionCommand().equals(EncogMenus.VIEW_RBF)) {
			owner.getOperations().performRBF();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_GENERATE_TRAINING)) {
			owner.getOperations().performCreateTrainingData();
		} else if (event.getActionCommand().equals(EncogMenus.TOOLS_WIZARD)) {
			owner.getOperations().performWizard();
		}
	
		
		}
		catch(Throwable t)
		{
			EncogWorkBench.displayError("Error", t);
		}
	}
}
