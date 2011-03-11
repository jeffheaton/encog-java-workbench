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

import javax.swing.JFileChooser;

import org.encog.app.quant.QuantError;
import org.encog.app.quant.loader.yahoo.YahooDownload;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.data.buffer.BinaryDataLoader;
import org.encog.neural.data.buffer.BufferedNeuralDataSet;
import org.encog.neural.data.buffer.codec.DataSetCODEC;
import org.encog.neural.data.buffer.codec.NeuralDataSetCODEC;
import org.encog.util.benchmark.RandomTrainingFactory;
import org.encog.util.csv.CSVFormat;
import org.encog.util.file.FileUtil;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.trainingdata.CreateMarketTrainingDialog;
import org.encog.workbench.dialogs.trainingdata.RandomTrainingDataDialog;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.util.NeuralConst;
import org.encog.workbench.util.TemporalXOR;

public class CreateTrainingData {


	public static void downloadMarketData(String name) {
		CreateMarketTrainingDialog dialog = new CreateMarketTrainingDialog(
				EncogWorkBench.getInstance().getMainWindow());

		dialog.getFromDay().setValue(1);
		dialog.getFromMonth().setValue(1);
		dialog.getFromYear().setValue(1995);

		dialog.getToDay().setValue(31);
		dialog.getToMonth().setValue(12);
		dialog.getToYear().setValue(2005);

		if (dialog.process()) {
			String ticker = dialog.getTicker().getValue();
			int fromDay = dialog.getFromDay().getValue();
			int fromMonth = dialog.getFromMonth().getValue();
			int fromYear = dialog.getFromYear().getValue();

			int toDay = dialog.getToDay().getValue();
			int toMonth = dialog.getToMonth().getValue();
			int toYear = dialog.getToYear().getValue();

			Calendar begin = new GregorianCalendar(fromYear, fromMonth - 1,
					fromDay);
			Calendar end = new GregorianCalendar(toYear, toMonth - 1, toDay);

			try {
				final YahooDownload loader = new YahooDownload();

				if (end.getTimeInMillis() < begin.getTimeInMillis()) {
					EncogWorkBench.displayError("Dates",
							"Ending date should not be before begin date.");
					return;
				}
				
				File targetFile = new File(EncogWorkBench.getInstance().getProjectDirectory(),name);

				EncogWorkBench.getInstance().getMainWindow().beginWait();
				loader.loadAllData(dialog.getTicker().getValue(),
						targetFile.toString(),
						CSVFormat.ENGLISH,
						begin.getTime(), end.getTime());

				EncogWorkBench.getInstance().refresh();

			} catch (QuantError e) {
				EncogWorkBench.displayError("Ticker Symbol",
						"Invalid ticker symbol, or cannot connect.");
			}
			finally {
				EncogWorkBench.getInstance().getMainWindow().endWait();
			}

		}
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
			return trainingData;
		}

		return null;
	}

	private static BasicNeuralDataSet generateXOR() {
		final BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
				NeuralConst.XOR_INPUT, NeuralConst.XOR_IDEAL);
		return trainingData;
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

			//EncogWorkBench.getInstance().getCurrentFile().add(name, item);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
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

		//EncogWorkBench.getInstance().getCurrentFile().add(name, result);
		EncogWorkBench.getInstance().getMainWindow().redraw();
		return result;
	}

	public static void copyCSV(String name) {
		final JFileChooser fc = new JFileChooser();
		if(EncogWorkBench.getInstance().getCurrentFileName()!=null )
			fc.setCurrentDirectory(new File(EncogWorkBench.getInstance().getCurrentFileName()));
		fc.addChoosableFileFilter(EncogDocumentFrame.CSV_FILTER);
		final int result = fc.showOpenDialog(EncogWorkBench.getInstance().getMainWindow());
		if (result == JFileChooser.APPROVE_OPTION) {
			String file = fc.getSelectedFile().getAbsolutePath();
			File sourceFile = new File(file);
			File targetFile = new File(EncogWorkBench.getInstance().getProjectDirectory(),name);
			FileUtil.copy(sourceFile, targetFile);
			EncogWorkBench.getInstance().refresh();
		}		
	}

}
