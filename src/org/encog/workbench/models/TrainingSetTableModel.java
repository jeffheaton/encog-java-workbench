/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
 */

package org.encog.workbench.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralDataSet;

public class TrainingSetTableModel implements TableModel {

	private final BasicNeuralDataSet data;
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();

	public TrainingSetTableModel(final BasicNeuralDataSet data) {
		this.data = data;
	}

	public void addIdealColumn() {
		for (final NeuralDataPair pair : this.data) {
			final BasicNeuralData ideal = (BasicNeuralData) pair.getIdeal();
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
		for (final NeuralDataPair pair : this.data) {
			final BasicNeuralData input = (BasicNeuralData) pair.getInput();
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
		final NeuralData idealData = new BasicNeuralData(idealSize);
		final NeuralData inputData = new BasicNeuralData(inputSize);
		final NeuralDataPair pair = new BasicNeuralDataPair(inputData,
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
			for (final NeuralDataPair pair : this.data) {
				final NeuralData input = pair.getInput();
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
			for (final NeuralDataPair pair : this.data) {
				final NeuralData ideal = pair.getIdeal();
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
		final NeuralDataPair pair : this.data) {
			i++;
		}
		return i;
	}

	public Object getValueAt(int rowIndex, final int columnIndex) {
		for (final NeuralDataPair pair : this.data) {
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

		for (final NeuralDataPair pair : this.data) {
			if (rowIndex == 0) {
				if (columnIndex >= pair.getInput().size()) {
					pair.getIdeal().setData(
							columnIndex - pair.getInput().size(),
							((Double) value).doubleValue());
				} else {
					pair.getInput().setData(columnIndex,
							((Double) value).doubleValue());
				}
				pair.getInput().setData(columnIndex,
						((Double) value).doubleValue());
			}
			rowIndex--;
		}
	}

}
