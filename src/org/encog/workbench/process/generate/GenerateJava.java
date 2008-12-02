package org.encog.workbench.process.generate;

import java.util.Set;
import java.util.TreeSet;

import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.util.NormalizeInput;

public class GenerateJava implements Generate {

	private StringBuilder source;
	private BasicNetwork network; 
	private NeuralDataSet training;
	private boolean copy; 
	private TrainingMethod trainMethod;
	private Set<String> imports = new TreeSet<String>();
	
	private String generateImports()
	{
		StringBuilder results = new StringBuilder();
		for(String c: imports)
		{
			results.append("import ");
			results.append(c);
			results.append(";\n");
		}
		return results.toString();
	}
	
	@SuppressWarnings("unchecked")
	private void addClass(Class c)
	{
		this.imports.add(c.getName());
	}
	
	private void addObject(Object obj)
	{
		this.imports.add(obj.getClass().getName());
	}
	
	private void generateTraining()
	{
		addClass(NeuralDataSet.class);
		source.append("private static NeuralDataSet getTraining() {\n");
		source.append("  final double[][] INPUT = {\n"); 
		
		for(NeuralDataPair pair: this.training )
		{
			source.append("    { ");
			for(int i=0;i<pair.getInput().size();i++)
			{
				if( i!=0 )
					source.append(',');
				source.append(pair.getInput().getData(i));
			}
			source.append(" },\n");
		}
		
		source.append("  };\n");
		source.append("\n");
		source.append("  final double[][] IDEAL = {\n"); 
		
		for(NeuralDataPair pair: this.training )
		{
			source.append("    { ");
			for(int i=0;i<pair.getIdeal().size();i++)
			{
				if( i!=0 )
					source.append(',');
				source.append(pair.getIdeal().getData(i));
			}
			source.append(" },\n");
		} 
		
		source.append("  };\n");
		source.append("  return new BasicNeuralDataSet(INPUT, IDEAL);\n");
		source.append("}\n");
	}
	
	private void generateNetwork()
	{
		source.append("private static BasicNetwork getNetwork() {\n");
		source.append("  BasicNetwork network = new BasicNetwork();\n");
		
		for( Layer layer: this.network.getLayers() )
		{
			source.append("  network.addLayer(new ");
			source.append(layer.getClass().getSimpleName());
			source.append('(');
			source.append(layer.getNeuronCount());
			addObject(layer);
			
			if( layer instanceof FeedforwardLayer )
			{
				FeedforwardLayer fflayer = (FeedforwardLayer)layer;
				if( !(fflayer.getActivationFunction() instanceof ActivationSigmoid) )
				{
					addObject(fflayer.getActivationFunction());
					source.append(", new ");
					source.append(fflayer.getActivationFunction().getClass().getSimpleName());
					source.append("()");
				}
			}
			else if( layer instanceof SOMLayer )
			{
				SOMLayer somlayer = (SOMLayer)layer;

				source.append(", ");
				if( somlayer.getNormalizationType()==NormalizeInput.NormalizationType.Z_AXIS )
				{
					source.append("NormalizeInput.NormalizationType.Z_AXIS");
				}
				else if( somlayer.getNormalizationType()==NormalizeInput.NormalizationType.MULTIPLICATIVE )
				{
					source.append("NormalizeInput.NormalizationType.MULTIPLICATIVE");
				}	
			}
			
			source.append("));\n");
		}

		source.append("  network.reset();\n");
		source.append("  return network;\n");
		source.append("}\n");
	}
	
	
	public String generate(BasicNetwork network, NeuralDataSet training,
			boolean copy, TrainingMethod trainMethod) {

		this.network = network; 
		this.training = training;
		this.copy = copy; 
		this.trainMethod = trainMethod;
		
		source = new StringBuilder();
	
		source.append("\n");
		source.append("public class EncogGeneratedClass {\n");
		source.append("\n");
		source.append("  // fill these in with your training parameters\n");
		source.append("  public final double LEARNING_RATE = 0.7;\n");
		source.append("  public final double MOMENTUM = 0.7;\n");
		source.append("\n");
		generateTraining();
		source.append("\n");
		generateNetwork();
		source.append("\n");
		source.append("  public static void main(final String args[]) {\n");
		source.append("\n");

		source.append("\n");
		
		source.append("\n");
		source.append("    // train the neural network\n");
		source.append("    final NeuralDataSet trainingSet = getTraining();\n");
		source.append("    final BasicNetwork network = getNetwork();\n");
		source.append("    final Train train = new Backpropagation(network, trainingSet, 0.7, 0.9);\n");
		source.append("\n");
		source.append("    int epoch = 1;\n");
		source.append("\n");
		source.append("    do {\n");
		source.append("      train.iteration();\n");
		source.append("      System.out.println(\"Epoch #\" + epoch + \" Error:\" + train.getError());\n");
		source.append("      epoch++;\n");
		source.append("    } while ((epoch < 5000) && (train.getError() > 0.001));\n");
		source.append("\n");
		source.append("    // test the neural network\n");
		source.append("    System.out.println(\"Neural Network sources:\");\n");
		source.append("    for(NeuralDataPair pair: trainingSet ) {\n");
		source.append("      final NeuralData output = network.compute(pair.getInput());\n");
		source.append("      System.out.println(pair.getInput().getData(0) + \",\" + pair.getInput().getData(1)\n");
		source.append("      + \", actual=\" + output.getData(0) + \",ideal=\" + pair.getIdeal().getData(0));\n");
		source.append("    }\n");
		source.append("  }\n");
		source.append("}\n");
		
		String importStr = generateImports();
		
		return importStr + source.toString();
	}
}
