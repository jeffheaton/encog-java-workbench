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
package org.encog.workbench.tabs;

import java.io.IOException;

import javax.swing.JPanel;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.frames.document.tree.ProjectFile;

public abstract class EncogCommonTab extends JPanel {

	private ProjectFile encogObject;
	private EncogDocumentFrame owner;
	private boolean modal;
	private boolean dirty = false;
	private EncogCommonTab parentTab;

	public EncogCommonTab(final ProjectFile encogObject) {
		this.encogObject = encogObject;
	}

	public ProjectFile getEncogObject() {
		return encogObject;
	}

	public boolean close() throws IOException {
		// 
		
		if( this.getEncogObject()!=null && dirty ) {
			if (EncogWorkBench.askQuestion("Save",
					"Would you like to save your changes?")) {
				// are we closing a parent or a child	
				if( this.parentTab!=null ) {
					this.parentTab.setEncogObject(encogObject);
					getEncogObject().save();
				} else {
					getEncogObject().save();	
				}
			}  else {
				((ProjectEGFile)getEncogObject()).revert();
			}
		}
		return true;
	}

	public void dispose() {
		try {
			owner.getTabManager().closeTab(this);
		} catch (Throwable t) {
			EncogWorkBench.displayError("Error", t);
		}
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

	public void setEncogObject(ProjectFile encogObject) {
		this.encogObject = encogObject;
	}
	
	public abstract String getName();

	/**
	 * @return the dirty
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * @param dirty the dirty to set
	 */
	public void setDirty(boolean dirty) {
		boolean needUpdate = this.dirty!=dirty;
		this.dirty = dirty;
		if( needUpdate )
			updateTitle();
	}

	/**
	 * @return the parentTab
	 */
	public EncogCommonTab getParentTab() {
		return parentTab;
	}

	/**
	 * @param parentTab the parentTab to set
	 */
	public void setParentTab(EncogCommonTab parentTab) {
		this.parentTab = parentTab;
	}

	public void save() {
		if( this.getEncogObject()!=null) {
			this.getEncogObject().save();
		}
		this.setDirty(false);
	}
	
	public void updateTitle() {
		StringBuilder t = new StringBuilder();
		if( this.isDirty() )
			t.append("* ");
		t.append(this.getName());
		EncogWorkBench.getInstance().getMainWindow().getTabManager().renameTab(this, t.toString());
	}
}
