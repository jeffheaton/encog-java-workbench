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

public class GenerateJava implements Generate {

	private StringBuilder source;
	private BasicNetwork network;
	private BasicNeuralDataSet training;
	private boolean copy;
	private TrainingMethod trainMethod;
	private Set<String> imports = new TreeSet<String>();

	private String generateImports() {
		StringBuilder results = new StringBuilder();
		for (String c : imports) {
			results.append("import ");
			results.append(c);
			results.append(";\n");
		}
		return results.toString();
	}

	@SuppressWarnings("unchecked")
	private void addClass(Class c) {
		this.imports.add(c.getName());
	}

	private void addObject(Object obj) {
		this.imports.add(obj.getClass().getName());
	}

	private void generateConst() {
		source.append("  // fill these in with your training parameters\n");
		switch (this.trainMethod) {
		case Backpropagation:
			source
					.append("  public static final double LEARNING_RATE = 0.7;\n");
			source.append("  public static final double MOMENTUM = 0.7;\n");
			source.append("  public static final int MAX_ITERATION = 5000;\n");
			source.append("  public static final double MAX_ERROR = 0.01;\n");
			break;

		case Genetic:
			source.append("  public static final int MAX_ITERATION = 1000;\n");
			source
					.append("  public static final int POPULATION_SIZE = 5000;\n");
			source
					.append("  public static final double MUTATION_PERCENT = 0.1;\n");
			source
					.append("  public static final double MATE_PERCENT = 0.25;\n");
			source.append("  public static final double MAX_ERROR = 0.01;\n");
			break;

		case Anneal:
			source.append("  public static final int MAX_ITERATION = 1000;\n");
			source.append("  public static final double MAX_ERROR = 0.01;\n");
			source.append("  public static final double HIGH_TEMP = 10;\n");
			source.append("  public static final double LOW_TEMP = 2;\n");
			source.append("  public static final int CYCLES = 100;\n");
			break;

		case TrainHopfield:
			break;
			
		case TrainSOM:
			addClass(TrainSelfOrganizingMap.class);
			source.append("  public static final double LEARNING_RATE = 0.7;\n");
			source.append("  public static final TrainSelfOrganizingMap.LearningMethod LEARNING_METHOD = TrainSelfOrganizingMap.LearningMethod.SUBTRACTIVE;\n");
			source.append("  public static final int MAX_ITERATION = 5000;\n");
			source.append("  public static final double MAX_ERROR = 0.01;\n");
			break;
		}
	}

	private void generateTrainingData() {
		addClass(NeuralDataSet.class);
		addClass(BasicNeuralDataSet.class);
		source.append("private static NeuralDataSet getTraining() {\n");
		source.append("  final double[][] INPUT = {\n");

		for (NeuralDataPair pair : this.training) {
			source.append("    { ");
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
			source.append("  final double[][] IDEAL = {\n");

			for (NeuralDataPair pair : this.training) {
				source.append("    { ");
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
		addClass(BasicNetwork.class);
		source.append("private static BasicNetwork getNetwork() {\n");
		source.append("  BasicNetwork network = new BasicNetwork();\n");

		for (Layer layer : this.network.getLayers()) {
			source.append("  network.addLayer(new ");
			source.append(layer.getClass().getSimpleName());
			source.append('(');
			source.append(layer.getNeuronCount());
			addObject(layer);

			if (layer instanceof FeedforwardLayer) {
				FeedforwardLayer fflayer = (FeedforwardLayer) layer;
				if (!(fflayer.getActivationFunction() instanceof ActivationSigmoid)) {
					addObject(fflayer.getActivationFunction());
					source.append(", new ");
					source.append(fflayer.getActivationFunction().getClass()
							.getSimpleName());
					source.append("()");
				}
			} else if (layer instanceof SOMLayer) {
				SOMLayer somlayer = (SOMLayer) layer;
				addClass(NormalizeInput.class);

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

		source.append("  network.reset();\n");
		source.append("  return network;\n");
		source.append("}\n");
	}

	public void generateTraining() {

		addClass(Train.class);
		source
				.append("  public static BasicNetwork trainNetwork(BasicNetwork network,NeuralDataSet trainingSet) {\n");

		switch (this.trainMethod) {
		case Backpropagation:
			addClass(Backpropagation.class);
			source.append("    final Train train = new Backpropagation(\n");
			source.append("      network,\n");
			source.append("      trainingSet,\n");
			source.append("      LEARNING_RATE,\n");
			source.append("      MOMENTUM);\n");
			break;

		case Genetic:
			addClass(TrainingSetNeuralGeneticAlgorithm.class);
			source
					.append("    final Train train = new TrainingSetNeuralGeneticAlgorithm(\n");
			source.append("      network,\n");
			source.append("      true,\n");
			source.append("      trainingSet,\n");
			source.append("      POPULATION_SIZE,\n");
			source.append("      MUTATION_PERCENT,\n");
			source.append("      MATE_PERCENT);\n");
			break;
		case Anneal:
			addClass(NeuralSimulatedAnnealing.class);
			source
					.append("    final Train train = new NeuralSimulatedAnnealing(\n");
			source.append("      network, \n");
			source.append("      trainingSet, \n");
			source.append("      HIGH_TEMP, \n");
			source.append("      LOW_TEMP, \n");
			source.append("      CYCLES);\n");
			break;
		case TrainHopfield:
			addClass(TrainHopfield.class);
			source.append("    TrainHopfield train = new TrainHopfield(\n");
			source.append("      trainingSet,\n");
			source.append("      network);\n");
			source.append("\n");
			source.append("    train.iteration();");
			break;
		case TrainSOM:
			source.append("    final TrainSelfOrganizingMap train = new TrainSelfOrganizingMap(\n");
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
			source.append("      train.iteration();\n");
			source
					.append("      System.out.println(\"Iteration #\" + epoch + \" Error:\" + train.getError());\n");
			source.append("      epoch++;\n");
			source
					.append("    } while ((epoch < MAX_ITERATION) && (train.getError() > MAX_ERROR));\n");
			source.append("\n");
		}

		source.append("    return (BasicNetwork)train.getNetwork();\n");
		source.append("  }\n");

	}

	public void generateQuery() {
		addClass(NeuralData.class);
		addClass(NeuralDataPair.class);
		source
				.append("  public static void queryNetwork(BasicNetwork network,NeuralDataSet trainingSet) {\n");
		source.append("    // test the neural network\n");
		source.append("    System.out.println(\"Neural Network Query:\");\n");

		source.append("    for(NeuralDataPair pair: trainingSet ) {\n");
		source
				.append("      final NeuralData output = network.compute(pair.getInput());\n");
		source.append("\n");
		source.append("      System.out.print(\"Input: \");\n");
		source.append("      for(int i=0;i<pair.getInput().size();i++) {\n");
		source.append("        if( i!=0 )\n");
		source.append("          System.out.print(\",\");\n");
		source
				.append("        System.out.print(pair.getInput().getData(i));\n");
		source.append("      }\n");
		source.append("      System.out.print(\", Output: \");\n");
		source.append("      for(int i=0;i<output.size();i++) {\n");
		source.append("        if( i!=0 )\n");
		source.append("          System.out.print(\",\");\n");
		source.append("        System.out.print(output.getData(i));\n");
		source.append("      }\n");
		source.append("\n");
		if (this.training.getIdealSize() != 0) {
			source.append("      System.out.print(\", Expected: \");\n");
			source
					.append("      for(int i=0;i<pair.getIdeal().size();i++) {\n");
			source.append("        if( i!=0 )\n");
			source.append("          System.out.print(\",\");\n");
			source
					.append("        System.out.print(pair.getIdeal().getData(i));\n");
			source.append("      }\n");
			source.append("    System.out.println(\"\");\n");
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
		source.append("  public static void main(final String args[]) {\n");
		source.append("\n");

		if (this.copy) {
			addClass(NeuralDataSet.class);
			source
					.append("    final NeuralDataSet trainingSet = getTraining();\n");
			source.append("    BasicNetwork network = getNetwork();\n");
		} else {
			addClass(EncogPersistedCollection.class);
			addClass(NeuralDataSet.class);
			source
					.append("      EncogPersistedCollection encog = new EncogPersistedCollection();\n");
			source.append("      encog.load(\"");
			source.append(fixPath(EncogWorkBench.getInstance()
					.getCurrentFileName()));
			source.append("\");\n");
			source.append("\n");
			source
					.append("      final NeuralDataSet trainingSet = (NeuralDataSet) encog.find(\"");
			source.append(this.training.getName());
			source.append("\");\n");
			source
					.append("      BasicNetwork network = (BasicNetwork) encog.find(\"");
			source.append(this.network.getName());
			source.append("\");\n");
		}

		if (this.trainMethod != TrainingMethod.NoTraining) {
			source.append("    network = trainNetwork(network,trainingSet);\n");
		}
		source.append("    queryNetwork(network,trainingSet);\n");
		source.append("  }\n");

	}

	public String generate(BasicNetwork network, NeuralDataSet training,
			boolean copy, TrainingMethod trainMethod) {

		this.network = network;
		this.training = (BasicNeuralDataSet) training;
		this.copy = copy;
		this.trainMethod = trainMethod;

		source = new StringBuilder();

		addClass(BasicNetwork.class);

		source.append("\n");
		source.append("public class EncogGeneratedClass {\n");
		source.append("\n");
		generateConst();
		source.append("\n");

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

		String importStr = generateImports();

		return importStr + source.toString();
	}
}
