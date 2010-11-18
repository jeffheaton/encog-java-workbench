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
package org.encog.workbench.models;

import javax.swing.event.ListDataListener;

import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;

/**
 * Provides the items that are in a document.
 *
 */
public class EncogListModel extends CommonListModel {

	public void addListDataListener(final ListDataListener listener) {
		getListeners().add(listener);
	}

	public Object getElementAt(final int i) {
		if (i >= EncogWorkBench.getInstance().getCurrentFile().getDirectory().size()) {
			return null;
		} 
		Object[] l = EncogWorkBench.getInstance().getCurrentFile().getDirectory().toArray(); 
		return l[i];
		
	}


	public int getSize() {
		return EncogWorkBench.getInstance().getCurrentFile().getDirectory().size();
	}

	public void removeListDataListener(final ListDataListener listener) {
		getListeners().remove(listener);
	}

}
