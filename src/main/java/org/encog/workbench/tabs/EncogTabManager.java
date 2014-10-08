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
package org.encog.workbench.tabs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.frames.document.tree.ProjectFile;

public class EncogTabManager {
	private final List<EncogCommonTab> tabs = new ArrayList<EncogCommonTab>();
	private final EncogDocumentFrame owner;
	private final JTabbedPane documentTabs;
	private boolean modalTabOpen;

	public EncogTabManager(final EncogDocumentFrame owner) {
		this.owner = owner;
		this.documentTabs = new JTabbedPane();
		this.documentTabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
	}

	public void add(final EncogCommonTab tab) {
		this.tabs.add(tab);
		tab.setParent(this.owner);
	}

	public boolean contains(EncogCommonTab tab) {
		return this.tabs.contains(tab);
	}

	/**
	 * @return the frames
	 */
	public List<EncogCommonTab> getTabs() {
		return this.tabs;
	}

	/**
	 * @return the owner
	 */
	public EncogCommonFrame getOwner() {
		return this.owner;
	}

	public void remove(final EncogCommonTab frame) {
		this.tabs.remove(frame);
	}
	
	public boolean isTrainingOrNetworkOpen()
	{

		return false;
	}
	

	public boolean alreadyOpen(EncogCommonTab tab) {
		return this.tabs.contains(tab);
	}

	public EncogCommonTab find(File file) {
		for (final EncogCommonTab tab : this.tabs) {
			ProjectFile pf = (ProjectFile)tab.getEncogObject();
			if( pf==null )
				continue;
			
			if( file.equals(pf.getFile()))
				return tab;
						
		}
		return null;
		
	}

	public void closeAll() {
		Object[] list = this.tabs.toArray();
		for(int i=0;i<list.length;i++) {
			EncogCommonTab tab = (EncogCommonTab)list[i];
			tab.dispose();
		}
	}

	public void closeAll(File f) {
		Object[] list = this.tabs.toArray();
		for(int i=0;i<list.length;i++) {
			EncogCommonTab tab = (EncogCommonTab)list[i];
			if( tab.getEncogObject()!=null ) {
				if( tab.getEncogObject().getFile() !=null ) {
					if( tab.getEncogObject().getFile().equals(f)) {
						tab.dispose();						
					}
				}
			}
		}
		
	}
	
	public boolean queryViews(File f) {
		if( !checkViews(f))
			return true;
		
		if( !EncogWorkBench.askQuestion("Views Open", "There are view(s) open to the file:\n"+f.toString()+"\nClose any views first?")) {
			return false;
		}
		
		closeAll(f);
		
		return true;
	}
	
	
	public boolean checkViews(File f) {
		Object[] list = this.tabs.toArray();
		for(int i=0;i<list.length;i++) {
			EncogCommonTab tab = (EncogCommonTab)list[i];
			if( tab.getEncogObject()!=null ) {
				if( tab.getEncogObject().getFile() !=null ) {
					if( tab.getEncogObject().getFile().equals(f)) {
						return true;					
					}
				}
			}
		}
		return false;
	}

	/**
	 * @return the documentTabs
	 */
	public JTabbedPane getDocumentTabs() {
		return documentTabs;
	}
	
	public EncogCommonTab getCurrentTab() {		
		EncogCommonTab currentTab = (EncogCommonTab)this.documentTabs.getSelectedComponent();
		return currentTab;
	}

	public void openTab(EncogCommonTab tab) {

		int i = this.documentTabs.getTabCount();

		this.documentTabs.add(tab.getName(), tab);

		if (!this.contains(tab)) {
			if (i < this.documentTabs.getTabCount())
				documentTabs.setTabComponentAt(i, new ButtonTabComponent(this, tab));
			add(tab);
		}
		selectTab(tab);
		EncogWorkBench.getInstance().getMainWindow().getMenus().updateMenus();
	}
	
	public void selectTab(EncogCommonTab tab) {
		this.documentTabs.setSelectedComponent(tab);
	}

	public void openModalTab(EncogCommonTab tab, String title) {

		if (alreadyOpen(tab))
			return;

		int i = this.documentTabs.getTabCount();

		this.documentTabs.add(title, tab);
		documentTabs.setTabComponentAt(i, new ButtonTabComponent(this, tab));
		add(tab);
		tab.setModal(true);
		this.documentTabs.setSelectedComponent(tab);
		this.documentTabs.setEnabled(false);
		EncogWorkBench.getInstance().getMainWindow().getTree().setEnabled(false);
		this.modalTabOpen = true;
		EncogWorkBench.getInstance().getMainWindow().getMenus().updateMenus();

	}

	public void closeTab(EncogCommonTab tab) throws IOException {
		if (tab.close()) {
			remove(tab);
			getDocumentTabs().remove(tab);

			if (tab.isModal()) {
				this.documentTabs.setEnabled(true);
				EncogWorkBench.getInstance().getMainWindow().getTree().setEnabled(true);
				this.modalTabOpen = false;
			}
			EncogWorkBench.getInstance().getMainWindow().getMenus().updateMenus();
		}

	}

	/**
	 * @return the modalTabOpen
	 */
	public boolean isModalTabOpen() {
		return modalTabOpen;
	}
	
	public boolean notWithModalTabOpen() {
		if( this.modalTabOpen ) {
			EncogWorkBench.displayError("Error", "Please close modal tab first.");
		}
		return modalTabOpen;
	}
	

	public void renameTab(EncogCommonTab tab, String name) {
		int index = this.documentTabs.indexOfComponent(tab);
		if( index!=-1)
			this.documentTabs.setTitleAt(index, name);
	}
	
}
