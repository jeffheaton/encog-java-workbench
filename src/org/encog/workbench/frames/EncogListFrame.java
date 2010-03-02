/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.frames;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;

import org.encog.workbench.frames.manager.EncogCommonFrame;
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
