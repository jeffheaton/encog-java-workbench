/*
 * Encog(tm) Workbench v2.4
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

package org.encog.workbench.dialogs.synapse;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.encog.mathutil.matrices.Matrix;
import org.encog.neural.networks.synapse.Synapse;
import org.encog.workbench.dialogs.common.PropertiesField;
import org.encog.workbench.dialogs.common.TableFieldModel;
import org.encog.workbench.dialogs.common.ValidationException;

public class MatrixTableField extends PropertiesField {

	private MatrixTableModel model;
	private JTable table;
	private int height;
	private Synapse synapse;
	private Matrix tempMatrix;
	
	public MatrixTableField(String name, String label, Synapse synapse) {
		super(name, label, true);
		this.synapse = synapse;
		this.tempMatrix = synapse.getMatrix().clone();
		this.model = new MatrixTableModel(tempMatrix);
		this.height = 300;
	}


	@Override
	public void collect() throws ValidationException {
		// TODO Auto-generated method stub
		
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.table = new JTable(this.model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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

	public String getValue(int row, int col) {
		
		return ""+this.model.getValueAt(row, col);
	}

}
