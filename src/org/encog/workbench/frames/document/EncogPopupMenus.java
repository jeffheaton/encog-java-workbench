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
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.Network;
import org.encog.persist.DirectoryEntry;
import org.encog.persist.EncogPersistedCollection;
import org.encog.persist.EncogPersistedObject;
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
	private JMenuItem popupDataImport;
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
		this.popupDataImport = owner.addItem(this.popupData, "Import...", 'i');
		this.popupDataExport = owner.addItem(this.popupData, "Export...", 'e');
		
		this.popupGeneral = new JPopupMenu();
		this.popupGeneralDelete = owner.addItem(this.popupGeneral, "Delete", 'd');
		this.popupGeneralOpen = owner.addItem(this.popupGeneral, "Open", 'o');
		this.popupGeneralProperties = owner.addItem(this.popupGeneral, "Properties", 'p');
	}


	public void actionPerformed(final ActionEvent event) {
		DirectoryEntry selected = (DirectoryEntry) this.owner.getContents().getSelectedValue();

		if (event.getSource() == this.popupNetworkDelete) {
			owner.getOperations().performObjectsDelete();
		} else if (event.getSource() == this.popupNetworkQuery) {
			owner.getOperations().performNetworkQuery();
		} else if (event.getSource() == this.popupNetworkOpen) {
			owner.getOperations().openItem(selected);
		} else if (event.getSource() == this.popupNetworkProperties) {
			final EditEncogObjectProperties dialog = new EditEncogObjectProperties(
					owner, selected);
			dialog.process();
		} else if (event.getSource() == this.popupDataDelete) {
			owner.getOperations().performObjectsDelete();
		} else if (event.getSource() == this.popupDataOpen) {
			owner.getOperations().openItem(selected);
		} else if (event.getSource() == this.popupDataProperties) {
			final EditEncogObjectProperties dialog = new EditEncogObjectProperties
			(owner, selected);
			dialog.process();
		} else if (event.getSource() == this.popupDataImport) {
			owner.getOperations().performImport(selected);
		} else if (event.getSource() == this.popupDataExport) {
			owner.getOperations().performExport(selected);
		} else if (event.getSource() == this.popupGeneralDelete) {
			owner.getOperations().performObjectsDelete();
		} else if (event.getSource() == this.popupGeneralOpen) {
			owner.getOperations().openItem(selected);
		} else if (event.getSource() == this.popupGeneralProperties) {
			owner.getOperations().performObjectsProperties();
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
	
}
