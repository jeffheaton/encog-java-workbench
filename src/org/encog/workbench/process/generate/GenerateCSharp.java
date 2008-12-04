package org.encog.workbench.process.generate;

import java.util.Set;
import java.util.TreeSet;

import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.neural.networks.training.genetic.TrainingSetNeuralGeneticAlgorithm;
import org.encog.neural.networks.training.hopfield.TrainHopfield;
import org.encog.neural.networks.training.som.TrainSelfOrganizingMap;
import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.util.NormalizeInput;
import org.encog.workbench.EncogWorkBench;

public class GenerateCSharp implements Generate {

	private StringBuilder source;
	private BasicNetwork network;
	private BasicNeuralDataSet training;
	private boolean copy;
	private TrainingMethod trainMethod;
	private Set<String> using = new TreeSet<String>();

	private String generateImports() {
		StringBuilder results = new StringBuilder();
		for (String c : using) {
			results.append("using ");
			results.append(c);
			results.append(";\n");
		}
		return results.toString();
	}

	private void addUsing(String str)
	{
		using.add(str);
	}

	private void generateConst() {
		source.append("  // fill these in with your training parameters\n");
		switch (this.trainMethod) {
		case Backpropagation:
			source
					.append("  public const double LEARNING_RATE = 0.7;\n");
			source.append("  public const double MOMENTUM = 0.7;\n");
			source.append("  public const int MAX_ITERATION = 5000;\n");
			source.append("  public const double MAX_ERROR = 0.01;\n");
			break;

		case Genetic:
			source.append("  public const int MAX_ITERATION = 1000;\n");
			source
					.append("  public const int POPULATION_SIZE = 5000;\n");
			source
					.append("  public const double MUTATION_PERCENT = 0.1;\n");
			source
					.append("  public const double MATE_PERCENT = 0.25;\n");
			source.append("  public const double MAX_ERROR = 0.01;\n");
			break;

		case Anneal:
			source.append("  public const int MAX_ITERATION = 1000;\n");
			source.append("  public const double MAX_ERROR = 0.01;\n");
			source.append("  public const double HIGH_TEMP = 10;\n");
			source.append("  public const double LOW_TEMP = 2;\n");
			source.append("  public const int CYCLES = 100;\n");
			break;

		case TrainHopfield:
			break;
			
		case TrainSOM:
			addUsing("Encog.Neural.Networks.Training.SOM");
			source.append("  public const double LEARNING_RATE = 0.7;\n");
			source.append("  public const TrainSelfOrganizingMap.LearningMethod LEARNING_METHOD = TrainSelfOrganizingMap.LearningMethod.SUBTRACTIVE;\n");
			source.append("  public const int MAX_ITERATION = 5000;\n");
			source.append("  public const double MAX_ERROR = 0.01;\n");
			break;
		}
	}

	private void generateTrainingData() {
		addUsing("Encog.Neural.NeuralData");
		addUsing("Encog.Neural.Data.Basic");
		source.append("private static INeuralDataSet GetTraining() {\n");
		source.append("  double[][] INPUT = {\n");

		for (NeuralDataPair pair : this.training) {
			source.append("    new double[");
			source.append(pair.getInput().size());
			source.append("] { ");
			for (int i = 0; i < pair.getInput().size(); i++) {
				if (i != 0)
					source.append(',');
				source.append(pair.getInput().getData(i));
			}
			source.append(" },\n");
		}

		source.append("  };\n");
		source.append("\n");

		if (this.training.getIdealSize() > 0) {
			source.append("  double[][] IDEAL = {\n");

			for (NeuralDataPair pair : this.training) {
				source.append("    new double[");
				source.append(pair.getIdeal().size());
				source.append("] { ");
				for (int i = 0; i < pair.getIdeal().size(); i++) {
					if (i != 0)
						source.append(',');
					source.append(pair.getIdeal().getData(i));
				}
				source.append(" },\n");
			}

			source.append("  };\n");
			source.append("  return new BasicNeuralDataSet(INPUT, IDEAL);\n");
		}
		else
			source.append("  return new BasicNeuralDataSet(INPUT, null);\n");
		source.append("}\n");
	}

	private void generateNetwork() {
		addUsing("Encog.Neural.Networks.Layers");
		addUsing("Encog.Neural.Networks");
		source.append("private static BasicNetwork GetNetwork() {\n");
		source.append("  BasicNetwork network = new BasicNetwork();\n");

		for (Layer layer : this.network.getLayers()) {
			source.append("  network.AddLayer(new ");
			source.append(layer.getClass().getSimpleName());
			source.append('(');
			source.append(layer.getNeuronCount());

			if (layer instanceof FeedforwardLayer) {
				FeedforwardLayer fflayer = (FeedforwardLayer) layer;
				if (!(fflayer.getActivationFunction() instanceof ActivationSigmoid)) {
					addUsing("Encog.Neural.Activation");
					source.append(", new ");
					source.append(fflayer.getActivationFunction().getClass()
							.getSimpleName());
					source.append("()");
				}
			} else if (layer instanceof SOMLayer) {
				SOMLayer somlayer = (SOMLayer) layer;
				addUsing("Encog.Util");

				source.append(", ");
				if (somlayer.getNormalizationType() == NormalizeInput.NormalizationType.Z_AXIS) {
					source.append("NormalizeInput.NormalizationType.Z_AXIS");
				} else if (somlayer.getNormalizationType() == NormalizeInput.NormalizationType.MULTIPLICATIVE) {
					source
							.append("NormalizeInput.NormalizationType.MULTIPLICATIVE");
				}
			}

			source.append("));\n");
		}

		source.append("  network.Reset();\n");
		source.append("  return network;\n");
		source.append("}\n");
	}

	public void generateTraining() {

		addUsing("Encog.Neural.Networks.Training");
		source
				.append("  public static BasicNetwork TrainNetwork(BasicNetwork network,INeuralDataSet trainingSet) {\n");

		switch (this.trainMethod) {
		case Backpropagation:
			addUsing("Encog.Neural.Networks.Training.Backpropagation");
			source.append("    ITrain train = new Backpropagation(\n");
			source.append("      network,\n");
			source.append("      trainingSet,\n");
			source.append("      LEARNING_RATE,\n");
			source.append("      MOMENTUM);\n");
			break;

		case Genetic:
			addUsing("Encog.Neural.Networks.Training.Genetic");
			source
					.append("    ITrain train = new TrainingSetNeuralGeneticAlgorithm(\n");
			source.append("      network,\n");
			source.append("      true,\n");
			source.append("      trainingSet,\n");
			source.append("      POPULATION_SIZE,\n");
			source.append("      MUTATION_PERCENT,\n");
			source.append("      MATE_PERCENT);\n");
			break;
		case Anneal:
			addUsing("Encog.Neural.Networks.Training.Anneal");
			source
					.append("    ITrain train = new NeuralSimulatedAnnealing(\n");
			source.append("      network, \n");
			source.append("      trainingSet, \n");
			source.append("      HIGH_TEMP, \n");
			source.append("      LOW_TEMP, \n");
			source.append("      CYCLES);\n");
			break;
		case TrainHopfield:
			addUsing("Encog.Neural.Networks.Training.Hopfield");
			source.append("    TrainHopfield train = new TrainHopfield(\n");
			source.append("      trainingSet,\n");
			source.append("      network);\n");
			source.append("\n");
			source.append("    train.iteration();");
			break;
		case TrainSOM:
			source.append("    ITrain train = new TrainSelfOrganizingMap(\n");
			source.append("      network, \n");
			source.append("      trainingSet,\n");
			source.append("      LEARNING_METHOD,\n");
			source.append("      LEARNING_RATE);\n");
			break;

		}

		if (this.trainMethod != TrainingMethod.TrainHopfield) {
			source.append("\n");
			source.append("    int epoch = 1;\n");
			source.append("\n");
			source.append("    do {\n");
			source.append("      train.Iteration();\n");
			source
					.append("      Console.WriteLine(\"Iteration #\" + epoch + \" Error:\" + train.Error);\n");
			source.append("      epoch++;\n");
			source
					.append("    } while ((epoch < MAX_ITERATION) && (train.Error > MAX_ERROR));\n");
			source.append("\n");
		}

		source.append("    return (BasicNetwork)train.TrainedNetwork;\n");
		source.append("  }\n");

	}

	public void generateQuery() {
		addUsing("Encog.Neural.NeuralData");

		source
				.append("  public static void QueryNetwork(BasicNetwork network,INeuralDataSet trainingSet) {\n");
		source.append("    // test the neural network\n");
		source.append("    Console.WriteLine(\"Neural Network Query:\");\n");

		source.append("    foreach(INeuralDataPair pair in trainingSet ) {\n");
		source
				.append("      INeuralData output = network.Compute(pair.Input);\n");
		source.append("\n");
		source.append("      Console.Write(\"Input: \");\n");
		source.append("      for(int i=0;i<pair.Input.Count;i++) {\n");
		source.append("        if( i!=0 )\n");
		source.append("          Console.Write(\",\");\n");
		source
				.append("        Console.Write(pair.Input[i]);\n");
		source.append("      }\n");
		source.append("      Console.Write(\", Output: \");\n");
		source.append("      for(int i=0;i<output.Count;i++) {\n");
		source.append("        if( i!=0 )\n");
		source.append("          Console.Write(\",\");\n");
		source.append("        Console.Write(output.Data[i]);\n");
		source.append("      }\n");
		source.append("\n");
		if (this.training.getIdealSize() != 0) {
			source.append("      Console.Write(\", Expected: \");\n");
			source
					.append("      for(int i=0;i<pair.Ideal.Count;i++) {\n");
			source.append("        if( i!=0 )\n");
			source.append("          Console.Write(\",\");\n");
			source
					.append("        Console.Write(pair.Ideal[i]);\n");
			source.append("      }\n");
			source.append("    Console.WriteLine(\"\");\n");
		}
		source.append("  }\n");
		source.append("  }\n");
	}

	private String fixPath(String path) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < path.length(); i++) {
			char ch = path.charAt(i);
			if (ch == '\\')
				result.append("\\\\");
			else
				result.append(ch);
		}
		return result.toString();
	}

	public void generateMain() {
		source.append("  public static void Main() {\n");
		source.append("\n");

		if (this.copy) {
			addUsing("Encog.Neural.NeuralData");
			source
					.append("    INeuralDataSet trainingSet = GetTraining();\n");
			source.append("    BasicNetwork network = GetNetwork();\n");
		} else {
			addUsing("Encog.Neural.Persist");
			addUsing("Encog.Neural.NeuralData");
			source
					.append("      EncogPersistedCollection encog = new EncogPersistedCollection();\n");
			source.append("      encog.Load(\"");
			source.append(fixPath(EncogWorkBench.getInstance()
					.getCurrentFileName()));
			source.append("\");\n");
			source.append("\n");
			source
					.append("      INeuralDataSet trainingSet = (INeuralDataSet) encog.Find(\"");
			source.append(this.training.getName());
			source.append("\");\n");
			source
					.append("      BasicNetwork network = (BasicNetwork) encog.Find(\"");
			source.append(this.network.getName());
			source.append("\");\n");
		}

		if (this.trainMethod != TrainingMethod.NoTraining) {
			source.append("    network = TrainNetwork(network,trainingSet);\n");
		}
		source.append("    QueryNetwork(network,trainingSet);\n");
		source.append("  }\n");

	}

	public String generate(BasicNetwork network, NeuralDataSet training,
			boolean copy, TrainingMethod trainMethod) {

		this.network = network;
		this.training = (BasicNeuralDataSet) training;
		this.copy = copy;
		this.trainMethod = trainMethod;

		source = new StringBuilder();

		addUsing("Encog.Neural.Networks.Layers");
		addUsing("System");

		
		source.append("namespace EncogSandbox\n");
		source.append("{\n");
		source.append("    class Program\n");
		source.append("    {\n");
		source.append("\n");
		
		generateConst();

		if (this.copy) {
			generateNetwork();
			source.append("\n");
			generateTrainingData();
			source.append("\n");
		}

		if (this.trainMethod != TrainingMethod.NoTraining) {
			generateTraining();
		}
		source.append("\n");
		generateQuery();
		source.append("\n");
		generateMain();
		source.append("}\n");
		source.append("}\n");

		String importStr = generateImports();

		return importStr + source.toString();
	}
}
