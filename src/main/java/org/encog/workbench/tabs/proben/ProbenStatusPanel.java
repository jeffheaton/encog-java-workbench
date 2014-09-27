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
package org.encog.workbench.tabs.proben;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Panel to display the current training status.
 * @author jheaton
 *
 */
public class ProbenStatusPanel extends JPanel {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	private final ProbenStatusTab parent;

	/**
	 * Construct the panel.
	 * @param parent The parent.
	 */
	public ProbenStatusPanel(final ProbenStatusTab parent) {
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
