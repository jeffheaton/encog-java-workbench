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
