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
package org.encog.workbench.tabs.mlmethod;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.encog.neural.flat.FlatNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.workbench.models.WeightsModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class WeightsTab extends EncogCommonTab implements ActionListener, ItemListener {

	private WeightsModel model;
	private JToolBar toolbar;
	private JTable table;

	private JButton buttonSparse;
	private JComboBox comboView;
	
	private BasicNetwork network;
	private MLMethodTab owner;
	
	public WeightsTab(MLMethodTab theOwner, BasicNetwork theNetwork) {
		super(null);
		
		this.network = theNetwork;
		this.network.updateProperties();
		this.owner = theOwner;
		this.model = new WeightsModel(this.owner,this.network);
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
		this.toolbar.add(this.buttonSparse = new JButton(""));
		this.toolbar.add(this.comboView = new JComboBox(layers.toArray()));
		add(this.toolbar, BorderLayout.PAGE_START);
		this.table = new JTable(this.model);
		add(new JScrollPane(this.table), BorderLayout.CENTER);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.buttonSparse.addActionListener(this);
		this.comboView.addItemListener(this);
		actionPerformed(null);
	}	
	
	public void actionPerformed(final ActionEvent action) {
		if( action!=null && action.getSource()==this.buttonSparse) {
			this.owner.setDirty(true);
			if( this.network.getStructure().isConnectionLimited() ) {
				this.network.getProperties().remove(BasicNetwork.TAG_LIMIT);
				this.network.updateProperties();
			} else {
				this.network.setProperty(BasicNetwork.TAG_LIMIT, BasicNetwork.DEFAULT_CONNECTION_LIMIT);
				this.network.updateProperties();
			}
		}
		
		if( this.network.getStructure().isConnectionLimited() ) {
			this.buttonSparse.setText("Disable Sparse Connections");
		} else {
			this.buttonSparse.setText("Enable Sparse Connections");
		}
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

	@Override
	public void itemStateChanged(ItemEvent evt) {
		if(evt.getStateChange()==ItemEvent.SELECTED) {
			this.model.setFromLayer(this.comboView.getSelectedIndex());
		}
	}
}
