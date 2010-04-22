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

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.mathutil.matrices.Matrix;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.synapse.Synapse;

public class MatrixTableModel implements TableModel {

	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private final Matrix matrix;
	private final BasicNetwork network;
	private final MatrixTableField field;

	public MatrixTableModel(final MatrixTableField field, final BasicNetwork network, final Matrix synapse) {
		this.matrix = synapse;
		this.network = network;
		this.field = field;
	}

	public void addTableModelListener(final TableModelListener l) {
		this.listeners.add(l);
	}

	public Class<?> getColumnClass(final int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return this.matrix.getCols() + 1;
	}

	public String getColumnName(final int columnIndex) {
		if( columnIndex==0 )
		{
			return "";
		}
		else
		{
			return "To Neuron: " + columnIndex;
		}

	}

	public int getRowCount() {
		return this.matrix.getRows();
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if(columnIndex==0)
		{
			return "From Neuron: " + (rowIndex+1);
		}
		else
		{
			return new Double(this.matrix.get(rowIndex, columnIndex-1));
		}
		
	}

	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}
		
		return true;

	}

	public void removeTableModelListener(final TableModelListener l) {
		this.listeners.remove(l);
	}

	public void setValueAt(final Object value, final int rowIndex,
			final int columnIndex) {
		double v = 0;
		
		try
		{
			v = Double.parseDouble(value.toString());
		}
		catch(NumberFormatException e)
		{
			// just ignore and let v remain 0
		}
		this.matrix.set(rowIndex,columnIndex-1,v);
		
	}
}
