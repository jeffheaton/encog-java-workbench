/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
 */

package org.encog.workbench.dialogs.training;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Panel to display the current training status.
 * @author jheaton
 *
 */
public class TrainingStatusPanel extends JPanel {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	private final BasicTrainingProgress parent;

	/**
	 * Construct the panel.
	 * @param parent The parent.
	 */
	public TrainingStatusPanel(final BasicTrainingProgress parent) {
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
