package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.encog.neural.persist.EncogPersistedCollection;

public class EncogListModel extends CommonListModel {

	private EncogPersistedCollection encogCollection;	
	
	public EncogListModel(EncogPersistedCollection theEncogCollection)
	{
		this.encogCollection = theEncogCollection;
	}
	
	public void addListDataListener(ListDataListener listener) {
		this.getListeners().add(listener);		
	}

	public Object getElementAt(int i) {
		if( i>=this.encogCollection.getList().size() )
			return null;
		else
			return this.encogCollection.getList().get(i);
	}

	public int getSize() {
		return this.encogCollection.getList().size();
	}

	public void removeListDataListener(ListDataListener listener) {
		this.getListeners().remove(listener);		
	}

	/**
	 * @return the encogCollection
	 */
	public EncogPersistedCollection getEncogCollection() {
		return encogCollection;
	}

	
	

}
