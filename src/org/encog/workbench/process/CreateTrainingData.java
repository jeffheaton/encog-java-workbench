package org.encog.workbench.process;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.encog.neural.data.market.TickerSymbol;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.data.market.MarketDataDescription;
import org.encog.neural.data.market.MarketDataType;
import org.encog.neural.data.market.MarketNeuralDataSet;
import org.encog.neural.data.market.loader.MarketLoader;
import org.encog.neural.data.market.loader.YahooFinanceLoader;
import org.encog.util.benchmark.RandomTrainingFactory;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.trainingdata.CreateEmptyTrainingDialog;
import org.encog.workbench.dialogs.trainingdata.CreateMarketTrainingDialog;
import org.encog.workbench.dialogs.trainingdata.RandomTrainingDataDialog;
import org.encog.workbench.frames.document.EncogDocumentOperations;
import org.encog.workbench.util.NeuralConst;
import org.encog.workbench.util.TemporalXOR;

public class CreateTrainingData {

	public static void createEmpty() {
		CreateEmptyTrainingDialog dialog = new CreateEmptyTrainingDialog(
				EncogWorkBench.getInstance().getMainWindow());

		if (dialog.process()) {
			int input = dialog.getInput().getValue();
			int output = dialog.getIdeal().getValue();

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
						inputData,null);
				trainingData.setDescription("Empty Training data");
				EncogWorkBench.getInstance().getCurrentFile().add(
						EncogDocumentOperations.generateNextID("data-"),
						trainingData);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	public static void createImportCSV() {

	}

	public static void createImportEG() {

	}

	public static void createMarketWindow() {
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
		
		if( dialog.process() )
		{
			String ticker = dialog.getTicker().getValue();
			int fromDay = dialog.getFromDay().getValue();
			int fromMonth = dialog.getFromMonth().getValue();
			int fromYear = dialog.getFromYear().getValue();
			
			int toDay = dialog.getToDay().getValue();
			int toMonth = dialog.getToMonth().getValue();
			int toYear = dialog.getToYear().getValue();
			
			int inputWindow = dialog.getInputWindow().getValue();
			int outputWindow = dialog.getOutputWindow().getValue();
			
			Calendar begin = new GregorianCalendar(fromYear, fromMonth-1, fromDay);
			Calendar end = new GregorianCalendar(toYear, toMonth-1, toDay);
			
			final MarketLoader loader = new YahooFinanceLoader();
			final MarketNeuralDataSet market = new MarketNeuralDataSet(loader,
					inputWindow, outputWindow);
			final MarketDataDescription desc = new MarketDataDescription(
					new TickerSymbol(ticker), MarketDataType.ADJUSTED_CLOSE, true, true);
			market.addDescription(desc);

			market.load(begin.getTime(), end.getTime());
			market.generate();
			
			BasicNeuralDataSet training = new BasicNeuralDataSet();
			
			for(NeuralDataPair data: market)
			{
				training.add(data);
			}
			
			
			training.setDescription("Market data for: " + ticker);
			
			
			
			EncogWorkBench.getInstance().getCurrentFile().add(
					EncogDocumentOperations.generateNextID("data-"),
					training);
			EncogWorkBench.getInstance().getMainWindow().redraw();
			
			
		}
		
	}

	public static void createPredictWindow() {

	}

	public static void createRandom() {
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
			EncogWorkBench.getInstance().getCurrentFile().add(
					EncogDocumentOperations.generateNextID("data-"),
					trainingData);
			EncogWorkBench.getInstance().getMainWindow().redraw();

		}
	}

	public static void createXORTemp() {

		String str = EncogWorkBench.displayInput("How many training elements in the XOR temporal data set?");
		
		if( str!=null )
		{	
			int count = 0;
			
			try
			{
				count = Integer.parseInt(str);
			}
			catch(NumberFormatException e)
			{
				EncogWorkBench.displayError("Error", "Must enter a valid number.");
			}
			TemporalXOR temp = new TemporalXOR();
			BasicNeuralDataSet trainingData = (BasicNeuralDataSet)temp.generate(count);
			trainingData.setDescription("Random Training data");
			EncogWorkBench.getInstance().getCurrentFile().add(
					EncogDocumentOperations.generateNextID("data-"), trainingData);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}

	public static void createXOR() {
		final BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
				NeuralConst.XOR_INPUT, NeuralConst.XOR_IDEAL);
		trainingData.setDescription("Training data");
		EncogWorkBench.getInstance().getCurrentFile().add(
				EncogDocumentOperations.generateNextID("data-"), trainingData);
		EncogWorkBench.getInstance().getMainWindow().redraw();

	}
	
}
