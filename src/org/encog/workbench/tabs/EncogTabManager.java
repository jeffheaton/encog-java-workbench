/*
 * Encog(tm) Workbench v2.5
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
