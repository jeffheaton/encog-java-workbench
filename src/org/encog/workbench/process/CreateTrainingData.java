package org.encog.workbench.process;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.util.benchmark.RandomTrainingFactory;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.trainingdata.RandomTrainingDataDialog;
import org.encog.workbench.frames.document.EncogDocumentOperations;
import org.encog.workbench.util.NeuralConst;

public class CreateTrainingData {
	
	public static void createEmpty()
	{
		
	}
	
	public static void createImportCSV()
	{
		
	}
	
	public static void createImportEG()
	{
		
	}
	
	public static void createMarketWindow()
	{
		
	}
	
	public static void createPredictWindow()
	{
		
	}
	
	public static void createRandom()
	{
		RandomTrainingDataDialog dialog = new RandomTrainingDataDialog(
				EncogWorkBench.getInstance().getMainWindow());
		
		dialog.getHigh().setValue(1);
		dialog.getLow().setValue(-1);
		
		if( dialog.process() )
		{
			double high = dialog.getHigh().getValue();
			double low = dialog.getLow().getValue();
			int elements = dialog.getElements().getValue();
			int input = dialog.getInput().getValue();
			int output = dialog.getIdeal().getValue();
			
			BasicNeuralDataSet trainingData = (BasicNeuralDataSet)RandomTrainingFactory.generate(elements, input, output, low, high);
			trainingData.setDescription("Random Training data");
			EncogWorkBench.getInstance().getCurrentFile().add(EncogDocumentOperations.generateNextID("data-") ,trainingData);
			EncogWorkBench.getInstance().getMainWindow().redraw();
			
		}
	}
	
	public static void createXORTemp()
	{
		
	}
	
	public static void createXOR()
	{
		final BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
				NeuralConst.XOR_INPUT, NeuralConst.XOR_IDEAL);
		trainingData.setDescription("Training data");
		EncogWorkBench.getInstance().getCurrentFile().add(EncogDocumentOperations.generateNextID("data-") ,trainingData);
		EncogWorkBench.getInstance().getMainWindow().redraw();

	}
}
