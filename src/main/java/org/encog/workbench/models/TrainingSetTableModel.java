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

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;

public class TrainingSetTableModel implements TableModel {

	private final BasicMLDataSet data;
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();

	public TrainingSetTableModel(final BasicMLDataSet data) {
		this.data = data;
	}

	public void addIdealColumn() {
		for (final MLDataPair pair : this.data) {
			final BasicMLData ideal = (BasicMLData) pair.getIdeal();
			final double[] d = new double[ideal.size() + 1];
			for (int i = 0; i < ideal.size(); i++) {
				d[i] = ideal.getData(i);
			}
			ideal.setData(d);

		}

		final TableModelEvent tce = new TableModelEvent(this,
				TableModelEvent.HEADER_ROW);
		notifyListeners(tce);

	}

	public void addInputColumn() {
		for (final MLDataPair pair : this.data) {
			final BasicMLData input = (BasicMLData) pair.getInput();
			final double[] d = new double[input.size() + 1];
			for (int i = 0; i < input.size(); i++) {
				d[i] = input.getData(i);
			}
			input.setData(d);

		}

		final TableModelEvent tce = new TableModelEvent(this,
				TableModelEvent.HEADER_ROW);
		notifyListeners(tce);
	}

	public void addRow(final int row) {
		final int idealSize = this.data.getIdealSize();
		final int inputSize = this.data.getInputSize();
		final MLData idealData = new BasicMLData(idealSize);
		final MLData inputData = new BasicMLData(inputSize);
		final MLDataPair pair = new BasicMLDataPair(inputData,
				idealData);
		if (row == -1) {
			this.data.getData().add(pair);
		} else {
			this.data.getData().add(row, pair);
		}

		final TableModelEvent tce = new TableModelEvent(this);
		notifyListeners(tce);
	}

	public void addTableModelListener(final TableModelListener listner) {
		this.listeners.add(listner);
	}

	public void delColumn(final int col) {
		final int inputSize = this.data.getInputSize();

		// does it fall inside of input or ideal?
		if (col < inputSize) {
			for (final MLDataPair pair : this.data) {
				final MLData input = pair.getInput();
				final double[] d = new double[input.size() - 1];
				int t = 0;
				for (int i = 0; i < input.size(); i++) {
					if (i != col) {
						d[t] = pair.getInput().getData(i);
						t++;
					}
				}
				input.setData(d);
			}
		} else {
			for (final MLDataPair pair : this.data) {
				final MLData ideal = pair.getIdeal();
				final double[] d = new double[ideal.size() - 1];
				int t = 0;
				for (int i = 0; i < ideal.size(); i++) {
					if (i != col - inputSize) {
						d[t] = pair.getInput().getData(i);
						t++;
					}

				}
				ideal.setData(d);
			}
		}

		final TableModelEvent tce = new TableModelEvent(this,
				TableModelEvent.HEADER_ROW);
		notifyListeners(tce);

	}

	public void delRow(final int row) {
		this.data.getData().remove(row);
		final TableModelEvent tce = new TableModelEvent(this);
		notifyListeners(tce);
	}

	public Class<?> getColumnClass(final int arg0) {
		return Double.class;
	}

	public int getColumnCount() {
		return this.data.getIdealSize() + this.data.getInputSize();
	}

	public String getColumnName(final int columnIndex) {
		if (columnIndex < this.data.getInputSize()) {
			return "Input " + (columnIndex + 1);
		}

		return "Ideal " + (columnIndex + 1 - this.data.getInputSize());
	}

	public int getRowCount() {
		int i = 0;
		for (@SuppressWarnings("unused")
		final MLDataPair pair : this.data) {
			i++;
		}
		return i;
	}

	public Object getValueAt(int rowIndex, final int columnIndex) {
		for (final MLDataPair pair : this.data) {
			if (rowIndex == 0) {
				if (columnIndex >= pair.getInput().size()) {
					return pair.getIdeal().getData(
							columnIndex - pair.getInput().size());
				}
				return pair.getInput().getData(columnIndex);
			}
			rowIndex--;
		}
		return null;
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

	public void setValueAt(final Object rawValue, int rowIndex,
			final int columnIndex) {
		Double value = null;
		if (rawValue instanceof Double) {
			value = (Double) rawValue;
		} else {
			value = Double.parseDouble(rawValue.toString());
		}

		for (final MLDataPair pair : this.data) {
			if (rowIndex == 0) {
				if (columnIndex >= pair.getInput().size()) {
					pair.getIdeal().setData(
							columnIndex - pair.getInput().size(),
							((Double) value).doubleValue());
				} else {
					pair.getInput().setData(columnIndex,
							((Double) value).doubleValue());
				}
			}
			rowIndex--;
		}
	}

}
