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
package org.encog.workbench.tabs.query.thermal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.encog.neural.thermal.ThermalNetwork;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.tabs.EncogCommonTab;

public class QueryThermalTab extends EncogCommonTab implements ActionListener {

	private ThermalNetwork network;
	
	public QueryThermalTab(ProjectEGFile network) {
		super(network);
		this.network = (ThermalNetwork) network.getObject();
		
		this.setLayout(new BorderLayout());
		
		this.buttonTrain = new JButton("Train");
		this.buttonGo = new JButton("Go");
		this.buttonClear = new JButton("Clear");
		this.buttonClearMatrix = new JButton("Clear Matrix");		
		this.buttonPanel = new JPanel();
		this.buttonPanel.add(this.buttonTrain);
		this.buttonPanel.add(this.buttonGo);
		this.buttonPanel.add(this.buttonClear);
		this.buttonPanel.add(this.buttonClearMatrix);
		this.add(this.buttonPanel, BorderLayout.SOUTH);

		this.buttonTrain.addActionListener(this);
		this.buttonGo.addActionListener(this);
		this.buttonClear.addActionListener(this);
		this.buttonClearMatrix.addActionListener(this);
		
		this.panel = new ThermalPanel(this.network);
		this.add(this.panel, BorderLayout.CENTER);
		
	}
	

	private ThermalPanel panel;
	private JPanel buttonPanel;
	private JButton buttonTrain;
	private JButton buttonGo;
	private JButton buttonClear;
	private JButton buttonClearMatrix;

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.buttonClear) {
			this.panel.clear();
		} else if (e.getSource() == this.buttonClearMatrix) {
			this.panel.clearMatrix();
		} else if (e.getSource() == this.buttonGo) {
			this.panel.go();
		} else if (e.getSource() == this.buttonTrain) {
			setDirty(true);
			this.panel.train();
		}
	}

	@Override
	public String getName() {
		return "Thrm :" + this.getEncogObject().getName();
	}

}
