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
