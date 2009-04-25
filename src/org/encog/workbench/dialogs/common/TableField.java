package org.encog.workbench.dialogs.common;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TableField extends PropertiesField {

	private DefaultTableModel model;
	private int height;
	
	public TableField(String name, String label, boolean required, int height, int rows, int columns) {
		super(name, label, required);
		this.model = new DefaultTableModel(rows,columns);
		this.height = height;
	}
	
	public TableField(String name, String label, boolean required, int rows, int height, String[] columnNames) {
		super(name, label, required);
		this.model = new DefaultTableModel(columnNames,rows);
		this.height = height;
	}

	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.setField(new JTable(this.model));
		this.getField().setLocation(x, y);
		this.getField().setSize(width, this.height);

		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.getField());
		
		return y+this.getField().getHeight();
	}

}
