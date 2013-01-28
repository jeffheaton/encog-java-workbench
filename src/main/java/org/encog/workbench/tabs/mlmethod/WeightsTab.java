/*
 * Encog(tm) Workbench v3.2
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
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
package org.encog.workbench.tabs.mlmethod;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.encog.neural.flat.FlatNetwork;
import org.encog.neural.networks.ContainsFlat;
import org.encog.workbench.models.WeightsModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class WeightsTab extends EncogCommonTab implements ActionListener {

	private WeightsModel model;
	private JToolBar toolbar;
	private JTable table;

	private JButton addInputColumn;
	private JButton delColumn;
	private JButton addIdealColumn;
	private JButton addRow;
	private JButton delRow;
	private JButton export;
	private JButton visualize;
	private JComboBox comboView;
	
	private ContainsFlat network;
	
	public WeightsTab(ContainsFlat theNetwork) {
		super(null);
		
		this.network = theNetwork;
		this.model = new WeightsModel(this.network.getFlat());
		FlatNetwork flat = theNetwork.getFlat();
		
		Collection<String> layers = new ArrayList<String>();
		int count = flat.getLayerCounts().length-2;
		
		String last = "Input";
		
		int hiddenNo = 1;
		while(count>0) {
			String current = "Hidden " + hiddenNo;
			layers.add(last + " -> " + current);
			last = current;
			hiddenNo++;
			count--;
		}
		
		layers.add(last + " -> Output");

		setLayout(new BorderLayout());
		this.toolbar = new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.add(this.addInputColumn = new JButton("Add Input Column"));
		this.toolbar.add(this.delColumn = new JButton("Delete Column"));
		this.toolbar.add(this.addIdealColumn = new JButton("Add Ideal Column"));
		this.toolbar.add(this.addRow = new JButton("Add Row"));
		this.toolbar.add(this.delRow = new JButton("Delete Row"));
		this.toolbar.add(this.export = new JButton("Export"));
		this.toolbar.add(this.visualize = new JButton("Visualize"));
		this.toolbar.add(this.comboView = new JComboBox(layers.toArray()));
		this.addInputColumn.addActionListener(this);
		this.delColumn.addActionListener(this);
		this.addIdealColumn.addActionListener(this);
		this.addRow.addActionListener(this);
		this.delRow.addActionListener(this);
		this.export.addActionListener(this);
		add(this.toolbar, BorderLayout.PAGE_START);
		this.table = new JTable(this.model);
		add(new JScrollPane(this.table), BorderLayout.CENTER);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.visualize.addActionListener(this);
		//new ExcelAdapter( this.table );
	}	
	
	public void actionPerformed(final ActionEvent action) {
	}
	
	public boolean close() throws IOException {
		boolean result = super.close();
		return result;
	}
	
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Weights";
	}
}
