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
package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.ml.data.buffer.EncogEGBFile;
import org.encog.workbench.EncogWorkBench;

public class BufferedDataSetTableModel implements TableModel {

	private final BufferedMLDataSet data;
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private EncogEGBFile egb;


	public BufferedDataSetTableModel(final BufferedMLDataSet data) {
		this.data = data;
		this.egb = data.getEGB();
	}

	public void addIdealColumn() {
		this.egb.addColumn(this.egb.getRecordCount()-1,false);

		final TableModelEvent tce = new TableModelEvent(this,
				TableModelEvent.HEADER_ROW);
		notifyListeners(tce);

	}

	public void addInputColumn() {
		this.egb.addColumn(this.egb.getInputCount()-1,true);

		final TableModelEvent tce = new TableModelEvent(this,
				TableModelEvent.HEADER_ROW);
		notifyListeners(tce);
	}

	public void addRow(final int row) {
		if( row<0 ) {
			this.egb.addRow(0);
		} else {
			this.egb.addRow(row);
		}

		final TableModelEvent tce = new TableModelEvent(this);
		notifyListeners(tce);
	}

	public void addTableModelListener(final TableModelListener listner) {
		this.listeners.add(listner);
	}

	public void delColumn(final int col) {
		
		if( col==(this.getColumnCount()-1) ) {
			EncogWorkBench.displayError("Error", "You can't delete the significance column.");
			return;
		}
		
		if( col==0 && this.egb.getInputCount()==1 )
		{
			EncogWorkBench.displayError("Error", "You can't delete the last input column.  There must be at least one input.");
			return;
		}
		
		if( this.egb.getRecordCount()<2 )
		{
			{
				EncogWorkBench.displayError("Error", "There must be at least one column.");
				return;
			}
		}
		
		this.egb.deleteCol(col);

		final TableModelEvent tce = new TableModelEvent(this,
				TableModelEvent.HEADER_ROW);
		notifyListeners(tce);
	}

	public void delRow(final int row) {

		this.egb.deleteRow(row);

		final TableModelEvent tce = new TableModelEvent(this);
		notifyListeners(tce);
	}

	public Class<?> getColumnClass(final int arg0) {
		return Double.class;
	}

	public int getColumnCount() {
		return this.egb.getRecordCount();
	}

	public String getColumnName(final int columnIndex) {
		if (columnIndex < this.data.getInputSize()) {
			return "Input " + (columnIndex + 1);
		}
		
		if( columnIndex <  (this.data.getInputSize()+ this.data.getIdealSize()) ) {
			return "Ideal " + (columnIndex + 1 - this.data.getInputSize());
		}
		
		return "Significance";
	}

	public int getRowCount() {
		return this.egb.getNumberOfRecords();
	}

	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return true;
	}

	private void notifyListeners(final TableModelEvent tce) {
		for (final TableModelListener listener : this.listeners) {
			listener.tableChanged(tce);
		}
	}

	public void removeTableModelListener(final TableModelListener l) {
		this.listeners.remove(l);
	}

	public Object getValueAt(int rowIndex, final int columnIndex) {
		return this.egb.read(rowIndex, columnIndex);
	}

	public void setValueAt(final Object rawValue, int rowIndex,
			final int columnIndex) {
		double d = 0.0;

		if (rawValue instanceof Double) {
			d = (Double) rawValue;
		} else {
			d = (Double.parseDouble(rawValue.toString()));
		}

		this.egb.write(rowIndex, columnIndex, d);

	}

}
