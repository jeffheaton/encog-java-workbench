/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
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
package org.encog.workbench.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.persist.EncogPersistedCollection;
import org.encog.util.ReadCSV;
import org.encog.workbench.WorkBenchError;

public class ImportExportUtility {
	public static void exportCSV(final NeuralDataSet set, final String filename)
			throws IOException {
		final FileOutputStream fos = new FileOutputStream(filename);
		final PrintStream out = new PrintStream(fos);
		for (final NeuralDataPair pair : set) {
			final StringBuilder line = new StringBuilder();

			final NeuralData input = pair.getInput();
			final NeuralData ideal = pair.getIdeal();

			// write input
			if (input != null) {
				for (int i = 0; i < input.size(); i++) {
					if (i != 0) {
						line.append(',');
					}
					line.append("" + input.getData(i));
				}
			}

			// write ideal
			if (ideal != null) {
				for (int i = 0; i < ideal.size(); i++) {
					line.append(',');
					line.append("" + ideal.getData(i));
				}
			}
			out.println(line.toString());
		}
		out.close();
		fos.close();
	}

	public static void exportXML(final BasicNeuralDataSet obj,
			final String filename) {
		//final EncogPersistedCollection save = new EncogPersistedCollection();
		//save.add(obj);
		//save.save(filename);
	}

	public static void importCSV(final BasicNeuralDataSet set,
			final String filename, final boolean clear) throws IOException {
		final int inputSize = set.getInputSize();
		final int idealSize = set.getIdealSize();

		if (clear) {
			set.getData().clear();
		}

		int line = 0;
		final ReadCSV csv = new ReadCSV(filename, false, ',');
		while (csv.next()) {
			line++;
			BasicNeuralData input = null, ideal = null;

			if (inputSize + idealSize != csv.getColumnCount()) {
				throw new WorkBenchError("Line #" + line + " has "
						+ csv.getColumnCount()
						+ " columns, but dataset expects "
						+ (inputSize + idealSize) + " columns.");
			}

			if (inputSize > 0) {
				input = new BasicNeuralData(inputSize);
			}
			if (idealSize > 0) {
				ideal = new BasicNeuralData(idealSize);
			}

			final BasicNeuralDataPair pair = new BasicNeuralDataPair(input,
					ideal);
			int index = 0;

			for (int i = 0; i < inputSize; i++) {
				if (input != null)
					input.setData(i, csv.getDouble(index++));
			}
			for (int i = 0; i < idealSize; i++) {
				if (ideal != null)
					ideal.setData(i, csv.getDouble(index++));
			}

			set.add(pair);
		}
		csv.close();
	}

}
