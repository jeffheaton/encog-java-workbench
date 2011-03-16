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

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.encog.ml.MLMethod;
import org.encog.persist.EncogPersistedObject;
import org.encog.util.file.FileUtil;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.tree.ProjectEGItem;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.frames.document.tree.ProjectItem;
import org.encog.workbench.process.CreateNewFile;
import org.encog.workbench.process.EncogAnalystWizard;
import org.encog.workbench.process.ImportExport;

public class EncogPopupMenus {

	private JPopupMenu popupFile;
	private JMenuItem popupFileRefresh;
	private JMenuItem popupFileDelete;
	private JMenuItem popupFileOpen;
	private JMenuItem popupFileProperties;
	private JMenuItem popupFileQuery;
	private JMenuItem popupFileExport;
	private JMenuItem popupFileOpenText;
	private JMenuItem popupFileCSVExport;
	private JMenuItem popupFileCSVWizard;
	private JMenuItem popupFileNewFile;
	private JMenuItem popupFileNewObject;
	
	private EncogDocumentFrame owner;
	
	public EncogPopupMenus(EncogDocumentFrame owner) {
		this.owner = owner;
	}

	public void actionPerformed(final ActionEvent event) {
		performPopupMenu(event.getSource());
	}

	public void performPopupMenu(final Object source) {

		if (source == this.popupFileRefresh ) {
			EncogWorkBench.getInstance().getMainWindow().getTree().refresh();
		}
		
		else if (source == this.popupFileNewFile ) {
			try {
				CreateNewFile.performCreateFile();
			} catch (IOException e) {
				EncogWorkBench.displayError("Error", e);
			}
		}
		else if( source==this.popupFileNewObject ) {
			try {
			EncogWorkBench.getInstance().getMainWindow().getOperations().performObjectsCreate();
			}  catch (IOException e) {
				EncogWorkBench.displayError("Error", e);
			}
		}

		boolean first = true;
		List<ProjectItem> list = this.owner.getTree().getSelectedValue();

		if (list == null)
			return;

		for (ProjectItem selected : list) {
			if (source == this.popupFileDelete  ) {
				if (first
						&& !EncogWorkBench
								.askQuestion("Warning",
										"Are you sure you want to delete these file(s)?")) {
					return;
				}
				first = false;
				if (selected instanceof ProjectFile) {
					((ProjectFile) selected).getFile().delete();
				}
				EncogWorkBench.getInstance().getMainWindow().getTree()
						.refresh();
			} else if (source == this.popupFileOpen ) {
				if (selected instanceof ProjectFile) {
					EncogWorkBench.getInstance().getMainWindow()
							.openFile(((ProjectFile) selected).getFile());
				}
			} else if (source == this.popupFileOpenText) {
				if (selected instanceof ProjectFile) {
					EncogWorkBench.getInstance().getMainWindow()
							.openTextFile(((ProjectFile) selected).getFile());
				}
			} else if (source == this.popupFileDelete) {
				if (first
						&& !EncogWorkBench
								.askQuestion("Warning",
										"Are you sure you want to delete these object(s)?")) {
					return;
				}
				owner.getOperations().performObjectsDelete(selected);
			} else if (source == this.popupFileCSVExport) {
				String sourceFile = ((ProjectFile) selected).getFile()
						.toString();
				String targetFile = FileUtil.forceExtension(sourceFile, "egb");
				ImportExport.performExternal2Bin(new File(sourceFile),
						new File(targetFile), null);
			} else if (source == this.popupFileCSVWizard) {
				File sourceFile = ((ProjectFile) selected).getFile();
				EncogAnalystWizard.createEncogAnalyst(sourceFile);
			} 
			first = false;
		}
	}

	public void rightMouseClicked(final MouseEvent e, final Object item) {

		File file = null;
		String ext = null;
		EncogPersistedObject encogObj = null;
		
		if( item instanceof ProjectFile ) {
			file = ((ProjectFile)item).getFile();
			ext = FileUtil.getFileExt(file);
		}
		
		if( item instanceof ProjectEGItem ) {
			encogObj = ((ProjectEGItem)item).getObj();			
		}
		
		// build network popup menu
		this.popupFile = new JPopupMenu();
		this.popupFileRefresh = owner.addItem(this.popupFile, "Refresh", 'r');
		
		if( ( file!=null || encogObj!=null ) && !"eg".equalsIgnoreCase(ext) ) {
			this.popupFileOpen = owner.addItem(this.popupFile, "Open", 'o');
		} else {
			this.popupFileOpen = null;
		}
		
		if( file!=null || encogObj!=null ) {
			this.popupFileDelete = owner.addItem(this.popupFile, "Delete",'d');			
			this.popupFileProperties = owner.addItem(this.popupFile,"Properties", 'p');
		} else {
			this.popupFileDelete = null;
			this.popupFileOpen = null;
			this.popupFileProperties = null;
		}
		
		if( encogObj instanceof MLMethod ) {
			this.popupFileQuery = owner.addItem(this.popupFile, "Query", 'q');
		} else {
			this.popupFileQuery = null;
		}
		
		//this.popupFileExport = owner.addItem(this.popupFile, "Export...", 'e');
		
		if( ext!=null && !"txt".equalsIgnoreCase(ext) ) {
			this.popupFileOpenText = owner.addItem(this.popupFile, "Open as Text",'t');
		} else {
			this.popupFileOpenText = null;
		}
		
		if( ext!=null && "csv".equalsIgnoreCase(ext) ) {
			this.popupFileCSVExport = owner.addItem(this.popupFile, "Export to Training(EGB)", 'x');
			this.popupFileCSVWizard = owner.addItem(this.popupFile, "Analyst Wizard...", 'w');
		} else {
			this.popupFileCSVExport = null;
			this.popupFileCSVWizard = null;
		}
		
		if(ext!=null && "eg".equalsIgnoreCase(ext)) {
			this.popupFileNewObject = owner.addItem(this.popupFile, "New Object", 'n');
		} else {
			this.popupFileNewObject = null;
		}
		
		if(file==null && encogObj==null) {
			this.popupFileNewFile = owner.addItem(this.popupFile, "New File", 'n');
		} else {
			this.popupFileNewFile = null;
		}

		this.popupFile.show(e.getComponent(), e.getX(), e.getY());

	}


}
