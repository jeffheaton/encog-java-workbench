package org.encog.workbench.frames.document;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.Network;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.workbench.dialogs.EditEncogObjectProperties;
import org.encog.workbench.process.Training;

public class EncogPopupMenus {

	private JPopupMenu popupNetwork;
	private JMenuItem popupNetworkDelete;

	private JMenuItem popupNetworkProperties;
	private JMenuItem popupNetworkOpen;
	private JMenuItem popupNetworkVisualize;
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
		this.popupNetworkVisualize = owner.addItem(this.popupNetwork, "Visualize",
				'v');
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
	}


	public void actionPerformed(final ActionEvent event) {
		EncogPersistedObject selected = (EncogPersistedObject) this.owner.getContents().getSelectedValue();

		if (event.getSource() == this.popupNetworkDelete) {
			owner.getOperations().performObjectsDelete();
		} else if (event.getSource() == this.popupNetworkQuery) {
			owner.getOperations().performNetworkQuery();
		} else if (event.getSource() == this.popupNetworkVisualize) {
			owner.getOperations().performNetworkVisualize();
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
		}
	}
	
	public void rightMouseClicked(final MouseEvent e, final Object item) {
		if (item instanceof Network) {
			this.popupNetwork.show(e.getComponent(), e.getX(), e.getY());
		} else if (item instanceof NeuralDataSet) {
			this.popupData.show(e.getComponent(), e.getX(), e.getY());
		} else if (item instanceof EncogPersistedObject ) {
			this.popupGeneral.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
}
