/*
 * Encog(tm) Workbench v2.5
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

import java.util.ArrayList;
import java.util.List;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.EncogCommonFrame;
import org.encog.workbench.frames.document.EncogDocumentFrame;

public class EncogTabManager {
	private final List<EncogCommonTab> tabs = new ArrayList<EncogCommonTab>();
	private final EncogDocumentFrame owner;

	public EncogTabManager(final EncogDocumentFrame owner) {
		this.owner = owner;
	}

	public void add(final EncogCommonTab tab) {
		this.tabs.add(tab);
		tab.setParent(this.owner);
	}

	public boolean contains(EncogCommonTab tab) {
		return this.tabs.contains(tab);
	}
	
	public boolean checkBeforeOpen(final DirectoryEntry object,
			final Class<?> c) {
		final EncogCommonTab existing = find(object);
		if (existing == null) {
			return true;
		}
		
		this.owner.getDocumentTabs().setSelectedComponent(existing);
		return false;
	}

	public EncogCommonTab find(final DirectoryEntry object) {
		for (final EncogCommonTab tab : this.tabs) {
			
			if( tab.getEncogObject()==null )
				continue;
			
			EncogPersistedObject obj = (EncogPersistedObject)tab.getEncogObject();
			if (obj.getName().equals(object.getName())) {
				return tab;
			}
		}
		return null;
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
		for (final EncogCommonTab tab : this.tabs) {
			EncogPersistedObject obj = (EncogPersistedObject)tab.getEncogObject();
			if( obj instanceof BasicNetwork || obj instanceof NeuralDataSet )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void closeTrainingOrNetwork()
	{
		Object[] list = this.tabs.toArray();
		for(int i=0;i<list.length;i++) {
			EncogCommonTab tab = (EncogCommonTab)list[i];
			
			if( tab.getEncogObject() instanceof BasicNetwork 
					|| tab.getEncogObject() instanceof NeuralDataSet )
			{				
				tab.dispose();
			}
		}
	}
	
	public boolean checkTrainingOrNetworkOpen()
	{
		if( isTrainingOrNetworkOpen() )
		{
			if( !EncogWorkBench.askQuestion("Windows Open", "There are training and/or network windows open.\nBefore training can begin, these must be closed.  Do you wish to close them now?"))
			{
				return false;
			}
			closeTrainingOrNetwork();
		}
		
		return true;
	}

	public boolean alreadyOpen(EncogCommonTab tab) {
		return this.tabs.contains(tab);
	}

}
