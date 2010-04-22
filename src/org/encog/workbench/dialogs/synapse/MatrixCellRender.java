package org.encog.workbench.dialogs.synapse;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MatrixCellRender extends DefaultTableCellRenderer {

	private final MatrixTableField field;
	
	public MatrixCellRender(MatrixTableField field)
	{
		this.field = field;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (value instanceof Double && field.isShouldLimit() ) {
			double d = ((Double)value).doubleValue();
						
			if ( Math.abs(d)<field.getLimitValue() ) {
				c.setForeground(Color.black);
				c.setBackground(Color.black);
			}
			else {
				c.setForeground(Color.black);
				c.setBackground(Color.white);				
			}
		}
		else {
			c.setForeground(Color.black);
			c.setBackground(Color.white);
		}

		return c;
	}

}
