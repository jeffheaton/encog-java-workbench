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

package org.encog.workbench.process;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.encog.neural.data.market.TickerSymbol;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.data.external.ExternalDataSource;
import org.encog.neural.data.market.MarketDataDescription;
import org.encog.neural.data.market.MarketDataType;
import org.encog.neural.data.market.MarketNeuralDataSet;
import org.encog.neural.data.market.loader.LoaderError;
import org.encog.neural.data.market.loader.MarketLoader;
import org.encog.neural.data.market.loader.YahooFinanceLoader;
import org.encog.neural.data.temporal.TemporalDataDescription;
import org.encog.neural.data.temporal.TemporalNeuralDataSet;
import org.encog.neural.data.temporal.TemporalPoint;
import org.encog.neural.data.temporal.TemporalDataDescription.Type;
import org.encog.persist.EncogPersistedObject;
import org.encog.util.benchmark.RandomTrainingFactory;
import org.encog.util.csv.ReadCSV;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.InputAndIdealDialog;
import org.encog.workbench.dialogs.trainingdata.CreateMarketTrainingDialog;
import org.encog.workbench.dialogs.trainingdata.CreateTemporalDataDialog;
import org.encog.workbench.dialogs.trainingdata.RandomTrainingDataDialog;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.frames.document.EncogDocumentOperations;
import org.encog.workbench.util.ImportExportUtility;
import org.encog.workbench.util.NeuralConst;
import org.encog.workbench.util.TemporalXOR;

public class CreateTrainingData {

	public static void createEmpty() {
		InputAndIdealDialog dialog = new InputAndIdealDialog(EncogWorkBench
				.getInstance().getMainWindow());

		if (dialog.process()) {
			int input = dialog.getInputCount().getValue();
			int output = dialog.getIdealCount().getValue();

			if (output > 0) {
				double inputData[][] = new double[1][input];
				double outputData[][] = new double[1][output];

				BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
						inputData, outputData);
				trainingData.setDescription("Empty Training data");
				EncogWorkBench.getInstance().getCurrentFile().add(
						EncogDocumentOperations.generateNextID("data-"),
						trainingData);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			} else {
				double inputData[][] = new double[1][input];

				BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
						inputData, null);
				trainingData.setDescription("Empty Training data");
				EncogWorkBench.getInstance().getCurrentFile().add(
						EncogDocumentOperations.generateNextID("data-"),
						trainingData);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	public static void createImportCSV() {
		ImportExport.performImport(null);
	}

	public static void linkCSV() {
		final JFrame frame = EncogWorkBench.getInstance().getMainWindow();
		final JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(EncogDocumentFrame.CSV_FILTER);
		final int result = fc.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			InputAndIdealDialog dialog = new InputAndIdealDialog(EncogWorkBench
					.getInstance().getMainWindow());
			if (dialog.process()) {
				ExternalDataSource link = new ExternalDataSource();
				link.setInputCount(dialog.getInputCount().getValue());
				link.setIdealCount(dialog.getIdealCount().getValue());
				link.setLink(fc.getSelectedFile());

				EncogWorkBench.getInstance().getCurrentFile().add(
						EncogDocumentOperations.generateNextID("link-"), link);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	public static void createMarketWindow() {
		NeuralDataSet training = generateMarketWindow();

		EncogWorkBench.getInstance().getCurrentFile().add(
				EncogDocumentOperations.generateNextID("data-"),
				(EncogPersistedObject) training);
		EncogWorkBench.getInstance().getMainWindow().redraw();
	}

	public static void linkMarketWindow() throws IOException {
		NeuralDataSet training = generateMarketWindow();

		File parentDir = new File(EncogWorkBench.getInstance()
				.getCurrentFileName()).getParentFile();
		String resourceName = EncogDocumentOperations.generateNextID("data-");
		File externalFile = new File(parentDir, resourceName + ".csv");

		ImportExportUtility.exportCSV(training, externalFile.toString());

		ExternalDataSource link = new ExternalDataSource();
		link.setInputCount(training.getInputSize());
		link.setIdealCount(training.getIdealSize());
		link.storeRelativeLink(new File(externalFile.toString()),
				EncogWorkBench.getInstance().getCurrentFile());

		EncogWorkBench.getInstance().getCurrentFile().add(resourceName, link);
		EncogWorkBench.getInstance().getMainWindow().redraw();

	}

	private static NeuralDataSet generateMarketWindow() {
		CreateMarketTrainingDialog dialog = new CreateMarketTrainingDialog(
				EncogWorkBench.getInstance().getMainWindow());

		dialog.getFromDay().setValue(1);
		dialog.getFromMonth().setValue(1);
		dialog.getFromYear().setValue(1995);

		dialog.getToDay().setValue(31);
		dialog.getToMonth().setValue(12);
		dialog.getToYear().setValue(2005);

		dialog.getInputWindow().setValue(7);
		dialog.getOutputWindow().setValue(1);

		((JComboBox) dialog.getNormalizationType().getField())
				.setSelectedIndex(1);

		if (dialog.process()) {
			String ticker = dialog.getTicker().getValue();
			int fromDay = dialog.getFromDay().getValue();
			int fromMonth = dialog.getFromMonth().getValue();
			int fromYear = dialog.getFromYear().getValue();

			int toDay = dialog.getToDay().getValue();
			int toMonth = dialog.getToMonth().getValue();
			int toYear = dialog.getToYear().getValue();

			int inputWindow = dialog.getInputWindow().getValue();
			int outputWindow = dialog.getOutputWindow().getValue();

			Calendar begin = new GregorianCalendar(fromYear, fromMonth - 1,
					fromDay);
			Calendar end = new GregorianCalendar(toYear, toMonth - 1, toDay);

			Type type;

			switch (((JComboBox) dialog.getNormalizationType().getField())
					.getSelectedIndex()) {
			case 0:
				type = Type.RAW;
				break;
			case 1:
				type = Type.PERCENT_CHANGE;
				break;
			case 2:
				type = Type.DELTA_CHANGE;
				break;
			default:
				type = Type.RAW;
				break;
			}

			try {
				final MarketLoader loader = new YahooFinanceLoader();
				final MarketNeuralDataSet market = new MarketNeuralDataSet(
						loader, inputWindow, outputWindow);
				final MarketDataDescription desc = new MarketDataDescription(
						new TickerSymbol(ticker),
						MarketDataType.ADJUSTED_CLOSE, type, true, true);
				market.addDescription(desc);

				if (end.getTimeInMillis() < begin.getTimeInMillis()) {
					EncogWorkBench.displayError("Dates",
							"Ending date should not be before begin date.");
					return null;
				}

				market.load(begin.getTime(), end.getTime());
				market.generate();

				BasicNeuralDataSet training = new BasicNeuralDataSet();

				for (NeuralDataPair data : market) {
					training.add(data);
				}

				training.setDescription("Market data for: " + ticker);

				return training;
			} catch (LoaderError e) {
				EncogWorkBench.displayError("Ticker Symbol",
						"Invalid ticker symbol.");
			}

		}

		return null;

	}

	private static BasicNeuralDataSet generatePredictWindow() {
		final JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(EncogDocumentFrame.CSV_FILTER);
		final int result = fc.showOpenDialog(EncogWorkBench.getInstance()
				.getMainWindow());
		if (result == JFileChooser.APPROVE_OPTION) {
			CreateTemporalDataDialog dialog = new CreateTemporalDataDialog(
					EncogWorkBench.getInstance().getMainWindow());
			((JComboBox) dialog.getNormalizationType().getField())
					.setSelectedIndex(1);

			if (dialog.process()) {
				ReadCSV read = new ReadCSV(fc.getSelectedFile().toString(),
						false, ',');
				int inputWindow = dialog.getInputWindow().getValue();
				int predictWindow = dialog.getOutputWindow().getValue();
				TemporalNeuralDataSet temp = new TemporalNeuralDataSet(
						inputWindow, predictWindow);

				Type type;

				switch (((JComboBox) dialog.getNormalizationType().getField())
						.getSelectedIndex()) {
				case 0:
					type = Type.RAW;
					break;
				case 1:
					type = Type.PERCENT_CHANGE;
					break;
				case 2:
					type = Type.DELTA_CHANGE;
					break;
				default:
					type = Type.RAW;
					break;
				}

				temp.addDescription(new TemporalDataDescription(type, true,
						true));
				int index = 0;
				while (read.next()) {
					double value = read.getDouble(0);
					TemporalPoint point = temp.createPoint(index++);
					point.setData(0, value);
				}

				temp.generate();

				BasicNeuralDataSet training = new BasicNeuralDataSet();

				for (NeuralDataPair data : temp) {
					training.add(data);
				}

				training.setDescription("Temporal data for: "
						+ fc.getSelectedFile().toString());

				return training;
			}
		}
		return null;
	}

	private static BasicNeuralDataSet generateRandom() {
		RandomTrainingDataDialog dialog = new RandomTrainingDataDialog(
				EncogWorkBench.getInstance().getMainWindow());

		dialog.getHigh().setValue(1);
		dialog.getLow().setValue(-1);

		if (dialog.process()) {
			double high = dialog.getHigh().getValue();
			double low = dialog.getLow().getValue();
			int elements = dialog.getElements().getValue();
			int input = dialog.getInput().getValue();
			int output = dialog.getIdeal().getValue();

			BasicNeuralDataSet trainingData = (BasicNeuralDataSet) RandomTrainingFactory
					.generate(elements, input, output, low, high);
			trainingData.setDescription("Random Training data");
			return trainingData;
		}

		return null;
	}

	public static BasicNeuralDataSet generateXORTemp() {

		String str = EncogWorkBench
				.displayInput("How many training elements in the XOR temporal data set?");

		if (str != null) {
			int count = 0;

			try {
				count = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				EncogWorkBench.displayError("Error",
						"Must enter a valid number.");
			}
			TemporalXOR temp = new TemporalXOR();
			BasicNeuralDataSet trainingData = (BasicNeuralDataSet) temp
					.generate(count);
			trainingData.setDescription("Random Training data");
			return trainingData;
		}

		return null;
	}

	private static BasicNeuralDataSet generateXOR() {
		final BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
				NeuralConst.XOR_INPUT, NeuralConst.XOR_IDEAL);
		trainingData.setDescription("Training data");
		return trainingData;
	}

	public static void linkEmpty() {
		EncogWorkBench.displayError("Error", "Can't link an empty dataset.");

	}

	public static void createXOR() {
		NeuralDataSet training = generateXOR();

		if (training == null)
			return;

		EncogWorkBench.getInstance().getCurrentFile().add(
				EncogDocumentOperations.generateNextID("data-"),
				(EncogPersistedObject) training);
		EncogWorkBench.getInstance().getMainWindow().redraw();
	}

	public static void linkXOR() throws IOException {
		NeuralDataSet training = generateXOR();
		saveLink(training);
	}

	public static void createXORTemp() {
		NeuralDataSet training = generateXOR();
		importResource(training);
	}

	public static void linkXORTemp() throws IOException {
		NeuralDataSet training = generateXOR();
		saveLink(training);
	}

	private static void importResource(NeuralDataSet training) {
		if (training != null) {
			EncogWorkBench.getInstance().getCurrentFile().add(
					EncogDocumentOperations.generateNextID("data-"),
					(EncogPersistedObject) training);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}

	}

	private static void saveLink(NeuralDataSet training) throws IOException {
		if( training!=null ) {
		File parentDir = new File(EncogWorkBench.getInstance()
				.getCurrentFileName()).getParentFile();
		String resourceName = EncogDocumentOperations.generateNextID("data-");
		File externalFile = new File(parentDir, resourceName + ".csv");

		ImportExportUtility.exportCSV(training, externalFile.toString());

		ExternalDataSource link = new ExternalDataSource();
		link.setInputCount(training.getInputSize());
		link.setIdealCount(training.getIdealSize());
		link.setLink(externalFile.toString());

		EncogWorkBench.getInstance().getCurrentFile().add(resourceName, link);
		EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}

	public static void createPredictWindow() {
		NeuralDataSet training = generatePredictWindow();
		importResource(training);
	}

	public static void linkPredictWindow() throws IOException {
		NeuralDataSet training = generatePredictWindow();
		saveLink(training);
	}

	public static void createRandom() {
		NeuralDataSet training = generateRandom();
		importResource(training);		
	}

	public static void linkRandom() throws IOException {
		NeuralDataSet training = generateRandom();
		saveLink(training);
	}

}
