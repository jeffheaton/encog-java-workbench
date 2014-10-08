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
package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.encog.ml.data.buffer.BufferedMLDataSet;

public class TrainingListModel implements ListModel {

	private BufferedMLDataSet data;
	private final List<ListDataListener> listeners = new ArrayList<ListDataListener>();
	
	public TrainingListModel(BufferedMLDataSet data) {
		this.data = data;
	}
	
	public int getSize() {
		return (int)data.getRecordCount();
	}

	public Object getElementAt(int index) {
		return "Element #" + index;
	}

	public void addListDataListener(ListDataListener l) {
		listeners.add(l);		
	}

	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
