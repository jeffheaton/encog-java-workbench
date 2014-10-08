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

public abstract class CommonListModel implements ListModel {
	private List<ListDataListener> listeners = new ArrayList<ListDataListener>();

	public void addListDataListener(final ListDataListener listener) {
		getListeners().add(listener);
	}

	/**
	 * @return the listeners
	 */
	public List<ListDataListener> getListeners() {
		return this.listeners;
	}

	public void invalidate() {
		for (final ListDataListener listener : this.listeners) {
			listener.contentsChanged(null);
		}
	}

	public void removeListDataListener(final ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param listeners
	 *            the listeners to set
	 */
	public void setListeners(final List<ListDataListener> listeners) {
		this.listeners = listeners;
	}

}
