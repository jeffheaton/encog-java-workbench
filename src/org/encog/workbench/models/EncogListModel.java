package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.encog.neural.persist.EncogPersistedCollection;

public class EncogListModel implements ListModel {

	private EncogPersistedCollection encogCollection;
	private List<ListDataListener> listeners = new ArrayList<ListDataListener>();
	
	public EncogListModel(EncogPersistedCollection theEncogCollection)
	{
		this.encogCollection = theEncogCollection;
	}
	
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);		
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
		this.listeners.remove(listener);		
	}

	/**
	 * @return the encogCollection
	 */
	public EncogPersistedCollection getEncogCollection() {
		return encogCollection;
	}

	public void invalidate() {
		for(ListDataListener listener: this.listeners)
		{
			listener.contentsChanged(null);
		}
		
	}
	
	

}
