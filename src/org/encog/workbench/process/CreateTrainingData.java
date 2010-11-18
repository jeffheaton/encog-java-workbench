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
package org.encog.workbench.process;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import org.encog.EncogError;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.data.buffer.BinaryDataLoader;
import org.encog.neural.data.buffer.BufferedNeuralDataSet;
import org.encog.neural.data.buffer.codec.DataSetCODEC;
import org.encog.neural.data.buffer.codec.NeuralDataSetCODEC;
import org.encog.neural.data.market.MarketDataDescription;
import org.encog.neural.data.market.MarketDataType;
import org.encog.neural.data.market.MarketNeuralDataSet;
import org.encog.neural.data.market.TickerSymbol;
import org.encog.neural.data.market.loader.LoaderError;
import org.encog.neural.data.market.loader.MarketLoader;
import org.encog.neural.data.market.loader.YahooFinanceLoader;
import org.encog.neural.data.temporal.TemporalDataDescription;
import org.encog.neural.data.temporal.TemporalDataDescription.Type;
import org.encog.neural.data.temporal.TemporalNeuralDataSet;
import org.encog.neural.data.temporal.TemporalPoint;
import org.encog.util.benchmark.RandomTrainingFactory;
import org.encog.util.csv.ReadCSV;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.trainingdata.CreateEmptyTrainingDialog;
import org.encog.workbench.dialogs.trainingdata.CreateMarketTrainingDialog;
import org.encog.workbench.dialogs.trainingdata.CreateTemporalDataDialog;
import org.encog.workbench.dialogs.trainingdata.RandomTrainingDataDialog;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.util.NeuralConst;
import org.encog.workbench.util.TaskComplete;
import org.encog.workbench.util.TemporalXOR;

public class CreateTrainingData {

	public static void linkMarketWindow(String name) throws IOException {
		NeuralDataSet training = generateMarketWindow();

		saveLink(name,training);

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

				EncogWorkBench.getInstance().getMainWindow().beginWait();
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
						"Invalid ticker symbol, or cannot connect.");
			}
			finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
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

	public static void linkEmpty(String name) {
		try {
			CreateEmptyTrainingDialog dialog = new CreateEmptyTrainingDialog(
					EncogWorkBench.getInstance().getMainWindow());

			if (dialog.process()) {
				int elements = dialog.getElements().getValue();
				int input = dialog.getInput().getValue();
				int output = dialog.getIdeal().getValue();

				BufferedNeuralDataSet trainingData = createLink(name);
				trainingData.setDescription("Training data");
				NeuralDataPair pair = BasicNeuralDataPair.createPair(input,
						output);
				trainingData.beginLoad(input, output);
				for (int i = 0; i < elements; i++) {
					trainingData.add(pair);
				}
				trainingData.endLoad();
			}
		} catch (IOException ex) {
			throw new EncogError(ex);
		}
	}

	public static void linkXOR(String name) throws IOException {
		NeuralDataSet training = generateXOR();
		saveLink(name, training);
	}

	public static void linkXORTemp(String name) throws IOException {
		NeuralDataSet training = generateXOR();
		saveLink(name, training);
	}

	private static void saveLink(String name, NeuralDataSet training)
			throws IOException {
		if (training != null) {
			File parentDir = new File(EncogWorkBench.getInstance()
					.getCurrentFileName()).getParentFile();

			File binaryFile = new File(parentDir, name + ".egb");

			DataSetCODEC codec = new NeuralDataSetCODEC(training);
			BinaryDataLoader loader = new BinaryDataLoader(codec);
			loader.external2Binary(binaryFile);

			BufferedNeuralDataSet item = new BufferedNeuralDataSet(binaryFile);

			EncogWorkBench.getInstance().getCurrentFile().add(name, item);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}

	public static void linkPredictWindow(String name) throws IOException {
		NeuralDataSet training = generatePredictWindow();
		saveLink(name, training);
	}

	public static void linkRandom(String name) throws IOException {
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

			BufferedNeuralDataSet trainingData = createLink(name);
			trainingData.setDescription("Random Training data");
			trainingData.beginLoad(input, output);
			RandomTrainingFactory.generate(trainingData,
					System.currentTimeMillis(), elements, low, high);
			trainingData.endLoad();

		}
	}

	private static BufferedNeuralDataSet createLink(String name)
			throws IOException {

		File parentDir = new File(EncogWorkBench.getInstance()
				.getCurrentFileName()).getParentFile();

		File binaryFile = new File(parentDir, name + ".egb");

		BufferedNeuralDataSet result = new BufferedNeuralDataSet(binaryFile);

		EncogWorkBench.getInstance().getCurrentFile().add(name, result);
		EncogWorkBench.getInstance().getMainWindow().redraw();
		return result;
	}

	public static void linkCSV(String name) {

		File parentDir = new File(EncogWorkBench.getInstance()
				.getCurrentFileName()).getParentFile();

		File binaryFile = new File(parentDir, name + ".egb");
		
		Object[] list = new Object[2];
		list[0] = binaryFile;
		list[1] = name;
		
		binaryFile = ImportExport.performExternal2Bin(binaryFile, new TaskComplete(list) {
			public void complete() {
				BufferedNeuralDataSet result = new BufferedNeuralDataSet((File) params[0]);
				EncogWorkBench.getInstance().getCurrentFile().add((String) params[1], result);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		});
	}

}
