/*
 * Encog(tm) Workbench v2.6 
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
