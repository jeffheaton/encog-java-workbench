/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.tabs.incremental;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.encog.workbench.dialogs.training.BasicTrainingProgress;

/**
 * Panel to display the current training status.
 * @author jheaton
 *
 */
public class IncrementalPruneStatusPanel extends JPanel {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	private final IncrementalPruneTab parent;

	/**
	 * Construct the panel.
	 * @param parent The parent.
	 */
	public IncrementalPruneStatusPanel(final IncrementalPruneTab parent) {
		this.parent = parent;
		setPreferredSize(new Dimension(640, 65));
	}

	/**
	 * Paint the panel, use the parent to do the painting.
	 */
	public void paint(final Graphics g) {
		this.parent.paintStatus(g);
	}
}
