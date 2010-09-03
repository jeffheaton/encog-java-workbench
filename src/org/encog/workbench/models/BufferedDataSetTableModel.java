/*
 * Encog(tm) Workbench v2.5
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

package org.encog.workbench.models;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.encog.EncogError;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.neural.data.buffer.BufferedDataError;
import org.encog.neural.data.buffer.BufferedNeuralDataSet;
import org.encog.neural.data.buffer.EncogEGBFile;

public class BufferedDataSetTableModel implements TableModel {

	private final BufferedNeuralDataSet data;
	private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	private RandomAccessFile raf;
	private FileChannel fileChannel;

	/**
	 * The record count.
	 */
	private int recordCount;

	/**
	 * The size of input data.
	 */
	private int inputSize;

	/**
	 * The size of ideal data.
	 */
	private int idealSize;

	/**
	 * The size of a record.
	 */
	private int recordSize;

	/**
	 * The byte buffer.
	 */
	private ByteBuffer byteBuffer;

	public BufferedDataSetTableModel(final BufferedNeuralDataSet data) {
		this.data = data;
		open();
	}

	public void open() {
		try {
			File file = this.data.getFile();
			this.raf = new RandomAccessFile(file, "rw");
			this.fileChannel = this.raf.getChannel();

			ByteBuffer bb = ByteBuffer
					.allocate(EncogEGBFile.HEADER_SIZE);
			bb.order(ByteOrder.LITTLE_ENDIAN);

			this.fileChannel.read(bb);

			boolean isEncogFile = true;

			bb.position(0);
			isEncogFile = isEncogFile ? bb.get() == 'E' : false;
			isEncogFile = isEncogFile ? bb.get() == 'N' : false;
			isEncogFile = isEncogFile ? bb.get() == 'C' : false;
			isEncogFile = isEncogFile ? bb.get() == 'O' : false;
			isEncogFile = isEncogFile ? bb.get() == 'G' : false;
			isEncogFile = isEncogFile ? bb.get() == '-' : false;

			if (!isEncogFile)
				throw new BufferedDataError(
						"File is not a valid Encog binary file.");

			char v1 = (char) bb.get();
			char v2 = (char) bb.get();
			String versionStr = "" + v1 + v2;

			try {
				int version = Integer.parseInt(versionStr);
				if (version > 0)
					throw new BufferedDataError(
							"File is from a newer version of Encog than is currently in use.");
			} catch (NumberFormatException ex) {
				throw new BufferedDataError("File has invalid version number.");
			}

			this.inputSize = (int) bb.getDouble();
			this.idealSize = (int) bb.getDouble();

			this.recordSize = (inputSize + idealSize)
					* EncogEGBFile.DOUBLE_SIZE;

			this.recordCount = (int) ((file.length() - (EncogEGBFile.DOUBLE_SIZE * 3)) / this.recordSize);

			this.byteBuffer = ByteBuffer.allocate(this.recordSize);
			this.byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

		} catch (IOException e) {
			throw new EncogError(e);
		}
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
		/*
		 * if (row == -1) { this.data.add(pair); } else { this.data.add(row,
		 * pair); }
		 */

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

		try {

			for (int i = row; i < this.recordCount - 1; i++) {
				int s = (int) EncogEGBFile.HEADER_SIZE
						+ (this.recordSize * i) + this.recordSize;
				int t = (int) EncogEGBFile.HEADER_SIZE
						+ (this.recordSize * i);

				this.fileChannel.read(this.byteBuffer, s);
				this.byteBuffer.flip();
				this.fileChannel.write(this.byteBuffer, t);

				s += EncogEGBFile.DOUBLE_SIZE;
				t += EncogEGBFile.DOUBLE_SIZE;
			}

			this.recordCount--;

			this.raf.setLength((int) (this.recordCount * EncogEGBFile.DOUBLE_SIZE)
							+ EncogEGBFile.HEADER_SIZE);

			close();
			open();
		} catch (IOException e) {
			throw (new EncogError(e));
		}

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
		return this.recordCount;
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
		int index = (int) ((this.recordSize * rowIndex)
				+ (columnIndex * EncogEGBFile.DOUBLE_SIZE) + EncogEGBFile.HEADER_SIZE);
		double result;
		try {
			this.byteBuffer.clear();
			this.byteBuffer.order(ByteOrder.BIG_ENDIAN);
			this.byteBuffer.limit(EncogEGBFile.DOUBLE_SIZE * 2);
			this.fileChannel.read(this.byteBuffer, index);
			this.byteBuffer.position(0);
			result = this.byteBuffer.getDouble();
		} catch (IOException e) {
			throw new EncogError(e);
		}

		return result;
	}

	public void setValueAt(final Object rawValue, int rowIndex,
			final int columnIndex) {
		double d = 0.0;

		if (rawValue instanceof Double) {
			d = (Double) rawValue;
		} else {
			d = (Double.parseDouble(rawValue.toString()));
		}

		int index = (int) ((this.recordSize * rowIndex)
				+ (columnIndex * EncogEGBFile.DOUBLE_SIZE) + EncogEGBFile.HEADER_SIZE);

		try {
			this.byteBuffer.clear();
			this.byteBuffer.order(ByteOrder.BIG_ENDIAN);
			this.byteBuffer.putDouble(d);
			this.byteBuffer.flip();
			this.fileChannel.write(this.byteBuffer, index);
		} catch (IOException e) {
			throw new EncogError(e);
		}

	}

	public void close() {
		try {
			this.fileChannel.close();
			this.raf.close();
		} catch (IOException e) {
			throw new EncogError(e);
		}

	}

}
