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
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.Network;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.process.training.Training;

public class EncogPopupMenus {

	private JPopupMenu popupNetwork;
	private JMenuItem popupNetworkDelete;

	private JMenuItem popupNetworkProperties;
	private JMenuItem popupNetworkOpen;
	private JMenuItem popupNetworkQuery;
	private JPopupMenu popupData;
	private JMenuItem popupDataDelete;

	private JMenuItem popupDataProperties;
	private JMenuItem popupDataOpen;
	private JMenuItem popupDataExport;
	
	private JPopupMenu popupGeneral;
	private JMenuItem popupGeneralOpen;
	private JMenuItem popupGeneralDelete;
	private JMenuItem popupGeneralProperties;
	private EncogDocumentFrame owner;
	
	public EncogPopupMenus(EncogDocumentFrame owner) {
		this.owner = owner;
	}
	
	void initPopup() {
		// build network popup menu
		this.popupNetwork = new JPopupMenu();
		this.popupNetworkDelete = owner.addItem(this.popupNetwork, "Delete", 'd');
		this.popupNetworkOpen = owner.addItem(this.popupNetwork, "Open", 'o');
		this.popupNetworkProperties = owner.addItem(this.popupNetwork, "Properties",
				'p');
		this.popupNetworkQuery = owner.addItem(this.popupNetwork, "Query", 'q');

		this.popupData = new JPopupMenu();
		this.popupDataDelete = owner.addItem(this.popupData, "Delete", 'd');
		this.popupDataOpen = owner.addItem(this.popupData, "Open", 'o');
		this.popupDataProperties = owner.addItem(this.popupData, "Properties", 'p');
		this.popupDataExport = owner.addItem(this.popupData, "Export...", 'e');
		
		this.popupGeneral = new JPopupMenu();
		this.popupGeneralDelete = owner.addItem(this.popupGeneral, "Delete", 'd');
		this.popupGeneralOpen = owner.addItem(this.popupGeneral, "Open", 'o');
		this.popupGeneralProperties = owner.addItem(this.popupGeneral, "Properties", 'p');
	}

	public void actionPerformed(final ActionEvent event) {
		performPopupMenu(event.getSource());
	}
	
	public void performPopupMenu(final Object source) {
		boolean first = true;
		List<DirectoryEntry> list = this.owner.getSelectedValue();
		
		if( list==null)
			return;

		for(DirectoryEntry selected: list )
		{		
			if( (source == this.popupNetworkDelete) ||
				(source == this.popupDataDelete) ||
				(source == this.popupGeneralDelete) )
			{			
				if ( first && !EncogWorkBench.askQuestion(
					"Warning", "Are you sure you want to delete these object(s)?") ) {
					return;
				}
				owner.getOperations().performObjectsDelete(selected);
			} else if (source == this.popupNetworkQuery) {
				owner.getOperations().performNetworkQuery(selected);
			} else if (source == this.popupNetworkOpen) {
				owner.getOperations().openItem(selected);
			} else if (source == this.popupNetworkProperties) {
				this.owner.getOperations().performObjectsProperties(selected);
			} else if (source == this.popupDataOpen) {
				owner.getOperations().openItem(selected);
			} else if (source == this.popupDataProperties) {
				this.owner.getOperations().performObjectsProperties(selected);
			} else if (source == this.popupGeneralOpen) {
				owner.getOperations().openItem(selected);
			} else if (source == this.popupGeneralProperties) {
				owner.getOperations().performObjectsProperties(selected);
			}
			
			first = false;
		}
	}
	
	public void rightMouseClicked(final MouseEvent e, final Object item) {
		
		if( item instanceof DirectoryEntry )
		{
			DirectoryEntry entry = (DirectoryEntry)item;
			if( EncogPersistedCollection.TYPE_BASIC_NET.equals(entry.getType()) )
			{
				this.popupNetwork.show(e.getComponent(), e.getX(), e.getY());
			}
			else if( EncogPersistedCollection.TYPE_BASIC_NET.equals(entry.getType()) )
			{
				this.popupData.show(e.getComponent(), e.getX(), e.getY());
			}
			else
			{
				this.popupGeneral.show(e.getComponent(), e.getX(), e.getY());
			}
		}
		
	}

	public void performPopupDelete() {
		this.performPopupMenu(this.popupNetworkDelete);
		
	}
	
}
