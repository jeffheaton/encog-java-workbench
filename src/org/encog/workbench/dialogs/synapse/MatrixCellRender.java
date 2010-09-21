/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
