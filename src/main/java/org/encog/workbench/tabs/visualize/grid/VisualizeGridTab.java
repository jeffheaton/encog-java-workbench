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
package org.encog.workbench.tabs.visualize.grid;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.models.TrainingListModel;
import org.encog.workbench.tabs.EncogCommonTab;

public class VisualizeGridTab extends EncogCommonTab implements ListSelectionListener, ActionListener {
	
	private BufferedMLDataSet data;
	private JScrollPane scroll;
	private JList list;
	private TrainingListModel model;
	private JTextField fieldWidth;
	private JTextField fieldHeight;
	private GridPanel grid;
	private JButton btnUpdate;
	
	public VisualizeGridTab(BufferedMLDataSet data) {
		super(null);
		this.data= data;
		setLayout(new BorderLayout());
		this.list = new JList(this.model = new TrainingListModel(data));
		this.scroll = new JScrollPane(this.list);
		this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(this.scroll,BorderLayout.WEST);
		this.list.addListSelectionListener(this);
		
		JPanel rightPanel = new JPanel();
		this.add(rightPanel,BorderLayout.CENTER);
		rightPanel.setLayout(new BorderLayout());
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		rightPanel.add(upperPanel,BorderLayout.NORTH);
		upperPanel.add(new JLabel("Grid Dimensions(h x w):"));
		upperPanel.add(this.fieldHeight = new JTextField(5));
		upperPanel.add(new JLabel(" x "));
		upperPanel.add(this.fieldWidth = new JTextField(5));
		upperPanel.add(this.btnUpdate = new JButton("Update"));
		rightPanel.add(this.grid = new GridPanel(),BorderLayout.CENTER);
		
		int width = (int)Math.sqrt(this.data.getInputSize());
		int height = width;
		while( (width*height) < this.data.getInputSize() ) {
			height++;
		}
		this.fieldHeight.setText(""+height);
		this.fieldWidth.setText(""+width);
		
		this.btnUpdate.addActionListener(this);
	}

	public void valueChanged(ListSelectionEvent e) {
		refresh();
	}
	
	public void refresh() {
		int selected = this.list.getSelectedIndex();
		if( selected==-1 )
			return;
		
		MLDataPair pair = BasicMLDataPair.createPair(this.data.getInputSize(), this.data.getIdealSize());
		this.data.getRecord(selected, pair);
		int gridHeight;
		int gridWidth;
		
		try {
			gridHeight = Integer.parseInt(this.fieldHeight.getText());
			if( gridHeight<=0 ) 
				throw new NumberFormatException();
		} catch(NumberFormatException ex) {
			EncogWorkBench.displayError("Error", "Invalid height.");
			return;
		}
		
		try {
			gridWidth = Integer.parseInt(this.fieldWidth.getText());
			if( gridHeight<=0 ) 
				throw new NumberFormatException();
		} catch(NumberFormatException ex) {
			EncogWorkBench.displayError("Error", "Invalid width.");
			return;
		}
		
		this.grid.updateData(gridHeight,gridWidth,pair.getInput().getData());
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.btnUpdate) {
			refresh();
		}
	}
	
	@Override
	public String getName() {
		if( this.getEncogObject() == null ) {
			return "Visualize";
		} else {
			return "Visualize :" + this.getEncogObject().getName();
		}
	}
}
