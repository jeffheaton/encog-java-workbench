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
package org.encog.workbench.frames;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;

import org.encog.workbench.util.MouseUtil;

public abstract class EncogListFrame extends EncogCommonFrame {

	protected JList contents;

	public void mouseClicked(final MouseEvent e) {

		final int index = this.contents.locationToIndex(e.getPoint());
		final ListModel dlm = this.contents.getModel();
		Object item = null;
		if (index != -1) {
			item = dlm.getElementAt(index);
		}
		this.contents.ensureIndexIsVisible(index);
		this.contents.setSelectedIndex(index);

		if (MouseUtil.isRightClick(e)) {
			rightMouseClicked(e, item);
		}

		if (e.getClickCount() == 2) {

			openItem(item);
		}
	}

	abstract protected void openItem(Object item);

	abstract public void rightMouseClicked(MouseEvent e, Object item);

}
