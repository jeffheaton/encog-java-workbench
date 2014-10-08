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
package org.encog.workbench.tabs.visualize.ThermalGrid;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.encog.neural.thermal.ThermalNetwork;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.frames.document.tree.ProjectEGFile;
import org.encog.workbench.models.ThermalModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class ThermalGridTab extends EncogCommonTab {

	private JTable table;
	private ThermalModel model;
	
	public ThermalGridTab(ProjectEGFile file) {
		super(file);
		
		if( !(file.getObject() instanceof ThermalNetwork) ) {
			throw new WorkBenchError("Can't view network as thermal.");
		}
		
		setLayout(new BorderLayout());
		this.model = new ThermalModel((ThermalNetwork) file.getObject());
		this.table = new JTable(this.model);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		add(new JScrollPane(this.table), BorderLayout.CENTER);		
	}
	
	@Override
	public String getName() {
		return "Visualize :" + this.getEncogObject().getName();
	}

}
