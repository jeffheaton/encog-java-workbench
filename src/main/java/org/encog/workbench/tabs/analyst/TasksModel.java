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
