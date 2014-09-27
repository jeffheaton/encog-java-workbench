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
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.encog.app.generate.EncogCodeGeneration;
import org.encog.app.generate.TargetLanguage;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataSet;
import org.encog.util.file.FileUtil;
import org.encog.util.simple.EncogUtility;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.wizard.generate.CodeGenerateDialog;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.frames.document.tree.ProjectItem;
import org.encog.workbench.process.CreateNewFile;
import org.encog.workbench.process.EncogAnalystWizard;
import org.encog.workbench.process.ImportExport;
import org.encog.workbench.tabs.files.TextDisplayTab;

public class EncogPopupMenus {

	private JPopupMenu popupFile;
	private JMenuItem popupFileRefresh;
	private JMenuItem popupFileDelete;
	private JMenuItem popupFileOpen;
	private JMenuItem popupFileProperties;
	private JMenuItem popupFileOpenText;
	private JMenuItem popupFileCSVExport;
	private JMenuItem popupFileCSVNormalize;
	private JMenuItem popupFileCSVWizard;
	private JMenuItem popupFileNewFile;
	private JMenuItem popupFileGenerate;
	private JMenuItem popupFileCopy;

	private EncogDocumentFrame owner;

	public EncogPopupMenus(EncogDocumentFrame owner) {
		this.owner = owner;
	}

	public void actionPerformed(final ActionEvent event) {
		performPopupMenu(event.getSource());
	}

	public void performPopupMenu(final Object source) {
		try {

			if (source == this.popupFileRefresh) {
				EncogWorkBench.getInstance().getMainWindow().getTree()
						.refresh();
				return;
			}

			else if (source == this.popupFileNewFile) {
				CreateNewFile.performCreateFile();
				return;
			} else if (source == this.popupFileDelete) {
				EncogWorkBench.getInstance().getMainWindow().getOperations().performDelete();
				return;
			} 

			boolean first = true;
			List<ProjectItem> list = this.owner.getTree().getSelectedValue();

			if (list == null)
				return;

			for (ProjectItem selected : list) {
				if (source == this.popupFileOpen) {
					if (selected instanceof ProjectFile) {
						EncogWorkBench.getInstance().getMainWindow()
								.openFile((ProjectFile)selected);
					}
				} else if (source == this.popupFileOpenText) {
					if (selected instanceof ProjectFile) {
						EncogWorkBench
								.getInstance()
								.getMainWindow()
								.openTextFile((ProjectFile) selected);
					}
				} else if (source == this.popupFileCSVExport) {
					String sourceFile = ((ProjectFile) selected).getFile()
							.toString();
					String targetFile = FileUtil.forceExtension(sourceFile,
							"egb");
					ImportExport.performExternal2Bin(new File(sourceFile),
							new File(targetFile), null);
				} else if (source == this.popupFileCSVNormalize) {
					String sourceFile = ((ProjectFile) selected).getFile()
							.toString();
					String targetFile = FileUtil.forceExtension(sourceFile,
							"egb");
					ImportExport.performNormalize2Bin(new File(sourceFile),
							new File(targetFile), null);
					EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
				} else if (source == this.popupFileCSVWizard) {
					File sourceFile = ((ProjectFile) selected).getFile();
					EncogAnalystWizard.createEncogAnalyst(sourceFile);
				} else if (source == this.popupFileProperties) {
					if (selected instanceof ProjectFile) {
						EncogWorkBench.getInstance().getMainWindow()
								.getOperations()
								.performFileProperties((ProjectFile) selected);
					}
				} else if( source==this.popupFileCopy ) {
					performCopy((ProjectFile) selected);
				} else if( source==this.popupFileGenerate) {
					performGenerate((ProjectFile) selected);
				}
				first = false;
			}
		} catch (IOException e) {
			EncogWorkBench.displayError("Error", e);
		}
	}

	private void performGenerate(ProjectFile selected) {		
		if( selected.getExtension().equalsIgnoreCase("eg") ) {
			performGenerateMethod(selected);
		} else {
			performGenerateData(selected);
		}				
	}
	
	private void performGenerateData(ProjectFile selected) {		
		
		CodeGenerateDialog dialog = new CodeGenerateDialog();
		if( dialog.process() ) {
			TargetLanguage targetLang = dialog.getTargetLanguage();
			EncogCodeGeneration gen = new EncogCodeGeneration(targetLang);
			gen.setEmbedData(dialog.getEmbed());
			gen.generate(null, selected.getFile());
			String code = gen.save();
			TextDisplayTab tab = new TextDisplayTab(targetLang.toString());
			tab.setText(code);
			EncogWorkBench.getInstance().getMainWindow().getTabManager().openTab(tab);
		}
	}
	
	
	private void performGenerateMethod(ProjectFile selected) {
		MLMethod method = (MLMethod)((ProjectEGFile)selected).getObject();
		
		if( !EncogCodeGeneration.isSupported(method) ) {
			EncogWorkBench.displayError("Error", "Code generation for " + method.getClass().getSimpleName() + " is not currently supported.");
			return;
		}
		
		CodeGenerateDialog dialog = new CodeGenerateDialog();
		if( dialog.process() ) {
			TargetLanguage targetLang = dialog.getTargetLanguage();
			EncogCodeGeneration gen = new EncogCodeGeneration(targetLang);
			gen.setEmbedData(dialog.getEmbed());
			gen.generate(selected.getFile(), null);
			String code = gen.save();
			TextDisplayTab tab = new TextDisplayTab(targetLang.toString());
			tab.setText(code);
			EncogWorkBench.getInstance().getMainWindow().getTabManager().openTab(tab);
		}
		
	}

	private void performCopy(ProjectFile selected) {
		
		try {
			File t = FileUtil.addFilenameBase(selected.getFile(), "-copy");
			String targetStr;

			if ((targetStr = EncogWorkBench.displayInput(
					"Name to copy file to", t.getName())) != null) {
				File targetFile = new File(EncogWorkBench.getInstance()
						.getProjectDirectory(), new File(targetStr).getName());
				if( targetFile.exists() ) {
					if( EncogWorkBench.askQuestion("File Exists", "Do you wish to overwrite the file of the same name?") ) {
						FileUtil.copy(selected.getFile(), targetFile);
					}
				} else {
					FileUtil.copy(selected.getFile(), targetFile);
				}
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
			
		} catch (Exception ex) {
			EncogWorkBench.displayError("Error", ex);
		}		
	}

	public void rightMouseClicked(final MouseEvent e, final Object item) {

		File file = null;
		String ext = null;

		if (item instanceof ProjectFile) {
			file = ((ProjectFile) item).getFile();
			ext = FileUtil.getFileExt(file);
		}

		// build network popup menu
		this.popupFile = new JPopupMenu();
		this.popupFileRefresh = owner.addItem(this.popupFile, "Refresh", 'r');

		if ((file != null ) && !"eg".equalsIgnoreCase(ext)) {
			this.popupFileOpen = owner.addItem(this.popupFile, "Open", 'o');
		} else {
			this.popupFileOpen = null;
		}

		if (file != null ) {
			this.popupFileDelete = owner.addItem(this.popupFile, "Delete", 'd');
			this.popupFileCopy = owner.addItem(this.popupFile, "Copy", 'c');
			this.popupFileProperties = owner.addItem(this.popupFile,
					"Rename...", 'p');
		} else {
			this.popupFileDelete = null;
			this.popupFileOpen = null;
			this.popupFileProperties = null;
		}

		if (ext != null && !"txt".equalsIgnoreCase(ext)) {
			this.popupFileOpenText = owner.addItem(this.popupFile,
					"Open as Text", 't');
		} else {
			this.popupFileOpenText = null;
		}

		if (ext != null && "csv".equalsIgnoreCase(ext)) {
			this.popupFileCSVExport = owner.addItem(this.popupFile,"Export to Training(EGB)", 'x');
			this.popupFileCSVNormalize = owner.addItem(this.popupFile,"Normalize to Training(EGB)", 'n');
			this.popupFileCSVWizard = owner.addItem(this.popupFile,"Analyst Wizard...", 'w');
		} else {
			this.popupFileCSVExport = null;
			this.popupFileCSVWizard = null;
		}		

		if (ext != null && ("eg".equalsIgnoreCase(ext) || "egb".equalsIgnoreCase(ext)) ) {
			this.popupFileGenerate = owner.addItem(this.popupFile,
					"Generate Code...", 'n');
		} else {
			this.popupFileGenerate = null;
		}

		if (file == null ) {
			this.popupFileNewFile = owner.addItem(this.popupFile, "New File",
					'n');
		} else {
			this.popupFileNewFile = null;
		}

		this.popupFile.show(e.getComponent(), e.getX(), e.getY());

	}

}
