package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public abstract class CommonListModel  implements ListModel {
	private List<ListDataListener> listeners = new ArrayList<ListDataListener>();

	public void invalidate() {
	for(ListDataListener listener: this.listeners)
	{
			listener.contentsChanged(null);
		}
	}

	/**
	 * @return the listeners
	 */
	public List<ListDataListener> getListeners() {
		return listeners;
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(List<ListDataListener> listeners) {
		this.listeners = listeners;
	}
	
	public void addListDataListener(ListDataListener listener) {
		this.getListeners().add(listener);		
	}
	
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
