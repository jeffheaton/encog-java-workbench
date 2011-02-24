package org.encog.workbench.tabs.analyst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.encog.app.analyst.EncogAnalyst;

public class TasksModel implements ComboBoxModel {

	private EncogAnalyst analyst;
	private Object selectedItem;
	private List<String> tasks = new ArrayList<String>();
	private List<ListDataListener> listeners = new ArrayList<ListDataListener>();
	
	public TasksModel(EncogAnalyst analyst) {
		this.analyst = analyst;
	}
	
	public int getSize() {
		return this.tasks.size();
	}

	public Object getElementAt(int index) {
		return this.tasks.get(index);
	}

	public void addListDataListener(ListDataListener l) {
		this.listeners.add(l);		
	}

	public void removeListDataListener(ListDataListener l) {
		this.listeners.remove(l);		
	}

	public void setSelectedItem(Object anItem) {
		this.selectedItem = anItem;
		
	}

	public Object getSelectedItem() {
		return this.selectedItem;
	}
	
	public void refresh()
	{
		// refresh our list
		this.tasks.clear();
		for( String task : this.analyst.getScript().getTasks().keySet()) {
			this.tasks.add(task);
		}
		Collections.sort(this.tasks);
		
		// refresh the controls
		ListDataEvent e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, Integer.MAX_VALUE);
		for(ListDataListener listener: this.listeners) {
			listener.contentsChanged(e);
		}
	}

}
