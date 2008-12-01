package org.encog.workbench.process.generate;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;

public class GenerateJava implements Generate {

	private StringBuilder result;
	
	private void generateImports()
	{
		result.append("import org.encog.neural.data.NeuralData;\n");
		result.append("import org.encog.neural.data.NeuralDataPair;\n");
		result.append("import org.encog.neural.data.NeuralDataSet;\n");
		result.append("import org.encog.neural.data.basic.BasicNeuralDataSet;\n");
		result.append("import org.encog.neural.networks.BasicNetwork;\n");
		result.append("import org.encog.neural.networks.Train;\n");
		result.append("import org.encog.neural.networks.layers.FeedforwardLayer;\n");
		result.append("import org.encog.neural.networks.training.backpropagation.Backpropagation;\n");		
	}
	
	
	public String generate(BasicNetwork network, NeuralDataSet training,
			boolean copy, TrainingMethod trainMethod) {

		
		result = new StringBuilder();
		
		result.append("package org.encog.examples.neural.xorbackprop;\n");
		result.append("\n");
		generateImports();
		result.append("\n");
		result.append("public class EncogGeneratedClass {\n");
		result.append("\n");
		result.append("  // fill these in with your training parameters\n");
		result.append("  public final double LEARNING_RATE = 0.7;\n");
		result.append("  public final double MOMENTUM = 0.7;\n");
		result.append("\n");
		result.append("  public static double INPUT[][] = {\n"); 
		result.append("    { 0.0, 0.0 },\n");
		result.append("    { 1.0, 0.0 },\n");
		result.append("    { 0.0, 1.0 },\n"); 
		result.append("    { 1.0, 1.0 }\n"); 
		result.append("  };\n");
		result.append("\n");
		result.append("  public static double IDEAL[][] = {\n"); 
		result.append("    { 0.0 },\n");
		result.append("    { 1.0 },\n"); 
		result.append("    { 1.0 },\n"); 
		result.append("    { 0.0 }\n"); 
		result.append("  };\n");
		result.append("\n");
		result.append("  public static void main(final String args[]) {\n");
		result.append("\n");
		result.append("    BasicNetwork network = new BasicNetwork();\n");
		result.append("    network.addLayer(new FeedforwardLayer(2));\n");
		result.append("    network.addLayer(new FeedforwardLayer(3));\n");
		result.append("    network.addLayer(new FeedforwardLayer(1));\n");
		result.append("    network.reset();\n");
		result.append("\n");
		result.append("    NeuralDataSet trainingSet = new BasicNeuralDataSet(INPUT, IDEAL);\n");
		result.append("\n");
		result.append("    // train the neural network\n");
		result.append("    final Train train = new Backpropagation(network, trainingSet, 0.7, 0.9);\n");
		result.append("\n");
		result.append("    int epoch = 1;\n");
		result.append("\n");
		result.append("    do {\n");
		result.append("      train.iteration();\n");
		result.append("      System.out.println(\"Epoch #\" + epoch + \" Error:\" + train.getError());\n");
		result.append("      epoch++;\n");
		result.append("    } while ((epoch < 5000) && (train.getError() > 0.001));\n");
		result.append("\n");
		result.append("    // test the neural network\n");
		result.append("    System.out.println(\"Neural Network Results:\");\n");
		result.append("    for(NeuralDataPair pair: trainingSet ) {\n");
		result.append("    final NeuralData output = network.compute(pair.getInput());\n");
		result.append("    System.out.println(pair.getInput().getData(0) + \",\" + pair.getInput().getData(1)\n");
		result.append("    + \", actual=\" + output.getData(0) + \",ideal=\" + pair.getIdeal().getData(0));\n");
		result.append("  }\n");
		result.append("}\n");
		
		return result.toString();
	}
}
