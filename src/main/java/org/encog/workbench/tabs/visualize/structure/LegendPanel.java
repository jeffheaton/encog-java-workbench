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
package org.encog.workbench.tabs.visualize.structure;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LegendPanel extends JPanel {
	public LegendPanel(boolean neat) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(new JLabel("Legend: "));
		create(Color.white, "Input",false);
		create(Color.yellow, "Bias",false);
		
		if( neat ) {
			create(Color.blue, "Linear",false);
			create(Color.magenta, "Sigmoid",false);
			create(Color.cyan, "Gaussian",false);
			create(Color.gray, "Sin",false);
		} else {
			create(Color.red, "Hidden",false);	
		}
		
		create(Color.green, "Output",true);
		
	}
	
	private void create(Color c, String text, boolean isLast) {
		JPanel panel = new JPanel();
		panel.setBackground(c);
		this.add(panel);
		if( !isLast) {
			text+=", ";
		}
		add(new JLabel(text));
	}
}
