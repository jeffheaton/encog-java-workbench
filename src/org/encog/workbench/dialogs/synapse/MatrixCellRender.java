package org.encog.workbench.dialogs.synapse;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MatrixCellRender extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (value instanceof Double) {
			double d = ((Double)value).doubleValue();
						
			if ( Math.abs(d)<0.0000000001 ) {
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
