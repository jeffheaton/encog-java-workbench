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
package org.encog.workbench.dialogs.common;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TableField extends PropertiesField {

	private TableFieldModel model;
	private JTable table;
	private int height;
	
	public TableField(String name, String label, boolean required, int height, int rows, int columns) {
		super(name, label, required);
		this.model = new TableFieldModel(Double.class,rows,columns);
		this.height = height;
	}
	
	public TableField(String name, String label, boolean required, int height, int rows, String[] columnNames) {
		super(name, label, required);
		this.model = new TableFieldModel(String.class,columnNames, rows);
		this.height = height;
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.table = new JTable(this.model);
		this.setField(new JScrollPane(this.table));
		this.getField().setLocation(x, y);
		this.getField().setSize(width, this.height);

		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.getField());
		
		return y+this.getField().getHeight();
	}

	public void setValue(int row, int col, String str) {
		this.model.setValueAt(str,row,col);
		
	}

	public TableFieldModel getModel() {
		return this.model;
	}

	public String getValue(int row, int col) {
		
		return ""+this.model.getValueAt(row, col);
	}

	public void setEditable(int i, boolean b) {
		this.model.setEditable(i, b);
		
	}

}
