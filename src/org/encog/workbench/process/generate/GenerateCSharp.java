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
package org.encog.workbench.process.generate;

import java.util.Set;
import java.util.TreeSet;

import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.NormalizeInput;
import org.encog.workbench.EncogWorkBench;

public class GenerateCSharp implements Generate {

	private StringBuilder source;
	private BasicNetwork network;
	private BasicNeuralDataSet training;
	private boolean copy;
	private TrainingMethod trainMethod;
	private final Set<String> using = new TreeSet<String>();

	private void addUsing(final String str) {
		this.using.add(str);
	}

	private String fixPath(final String path) {
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < path.length(); i++) {
			final char ch = path.charAt(i);
			if (ch == '\\') {
				result.append("\\\\");
			} else {
				result.append(ch);
			}
		}
		return result.toString();
	}

	public String generate(final BasicNetwork network,
			final NeuralDataSet training, final boolean copy,
			final TrainingMethod trainMethod) {

		this.network = network;
		this.training = (BasicNeuralDataSet) training;
		this.copy = copy;
		this.trainMethod = trainMethod;

		this.source = new StringBuilder();

		addUsing("Encog.Neural.Networks.Layers");
		addUsing("System");

		this.source.append("namespace EncogSandbox\n");
		this.source.append("{\n");
		this.source.append("    class Program\n");
		this.source.append("    {\n");
		this.source.append("\n");

		generateConst();

		if (this.copy) {
			generateNetwork();
			this.source.append("\n");
			generateTrainingData();
			this.source.append("\n");
		}

		if (this.trainMethod != TrainingMethod.NoTraining) {
			generateTraining();
		}
		this.source.append("\n");
		generateQuery();
		this.source.append("\n");
		generateMain();
		this.source.append("}\n");
		this.source.append("}\n");

		final String importStr = generateImports();

		return importStr + this.source.toString();
	}

	private void generateConst() {
		this.source
				.append("  // fill these in with your training parameters\n");
		switch (this.trainMethod) {
		case Backpropagation:
			this.source.append("  public const double LEARNING_RATE = 0.7;\n");
			this.source.append("  public const double MOMENTUM = 0.7;\n");
			this.source.append("  public const int MAX_ITERATION = 5000;\n");
			this.source.append("  public const double MAX_ERROR = 0.01;\n");
			break;

		case Genetic:
			this.source.append("  public const int MAX_ITERATION = 1000;\n");
			this.source.append("  public const int POPULATION_SIZE = 5000;\n");
			this.source
					.append("  public const double MUTATION_PERCENT = 0.1;\n");
			this.source.append("  public const double MATE_PERCENT = 0.25;\n");
			this.source.append("  public const double MAX_ERROR = 0.01;\n");
			break;

		case Anneal:
			this.source.append("  public const int MAX_ITERATION = 1000;\n");
			this.source.append("  public const double MAX_ERROR = 0.01;\n");
			this.source.append("  public const double HIGH_TEMP = 10;\n");
			this.source.append("  public const double LOW_TEMP = 2;\n");
			this.source.append("  public const int CYCLES = 100;\n");
			break;

		case TrainHopfield:
			break;

		case TrainSOM:
			addUsing("Encog.Neural.Networks.Training.SOM");
			this.source.append("  public const double LEARNING_RATE = 0.7;\n");
			this.source
					.append("  public const TrainSelfOrganizingMap.LearningMethod LEARNING_METHOD = TrainSelfOrganizingMap.LearningMethod.SUBTRACTIVE;\n");
			this.source.append("  public const int MAX_ITERATION = 5000;\n");
			this.source.append("  public const double MAX_ERROR = 0.01;\n");
			break;
			
		case NoTraining:
			break;
		}
	}

	private String generateImports() {
		final StringBuilder results = new StringBuilder();
		for (final String c : this.using) {
			results.append("using ");
			results.append(c);
			results.append(";\n");
		}
		return results.toString();
	}

	public void generateMain() {
		this.source.append("  public static void Main() {\n");
		this.source.append("\n");

		if (this.copy) {
			addUsing("Encog.Neural.NeuralData");
			this.source
					.append("    INeuralDataSet trainingSet = GetTraining();\n");
			this.source.append("    BasicNetwork network = GetNetwork();\n");
		} else {
			addUsing("Encog.Neural.Persist");
			addUsing("Encog.Neural.NeuralData");
			this.source
					.append("      EncogPersistedCollection encog = new EncogPersistedCollection();\n");
			this.source.append("      encog.Load(\"");
			this.source.append(fixPath(EncogWorkBench.getInstance()
					.getCurrentFileName()));
			this.source.append("\");\n");
			this.source.append("\n");
			this.source
					.append("      INeuralDataSet trainingSet = (INeuralDataSet) encog.Find(\"");
			this.source.append(this.training.getName());
			this.source.append("\");\n");
			this.source
					.append("      BasicNetwork network = (BasicNetwork) encog.Find(\"");
			this.source.append(this.network.getName());
			this.source.append("\");\n");
		}

		if (this.trainMethod != TrainingMethod.NoTraining) {
			this.source
					.append("    network = TrainNetwork(network,trainingSet);\n");
		}
		this.source.append("    QueryNetwork(network,trainingSet);\n");
		this.source.append("  }\n");

	}

	private void generateNetwork() {
		addUsing("Encog.Neural.Networks.Layers");
		addUsing("Encog.Neural.Networks");
		this.source.append("private static BasicNetwork GetNetwork() {\n");
		this.source.append("  BasicNetwork network = new BasicNetwork();\n");
/*
		for (final Layer layer : this.network.getLayers()) {
			this.source.append("  network.AddLayer(new ");
			this.source.append(layer.getClass().getSimpleName());
			this.source.append('(');
			this.source.append(layer.getNeuronCount());

			if (layer instanceof FeedforwardLayer) {
				final FeedforwardLayer fflayer = (FeedforwardLayer) layer;
				if (!(fflayer.getActivationFunction() instanceof ActivationSigmoid)) {
					addUsing("Encog.Neural.Activation");
					this.source.append(", new ");
					this.source.append(fflayer.getActivationFunction()
							.getClass().getSimpleName());
					this.source.append("()");
				}
			} else if (layer instanceof SOMLayer) {
				final SOMLayer somlayer = (SOMLayer) layer;
				addUsing("Encog.Util");

				this.source.append(", ");
				if (somlayer.getNormalizationType() == NormalizeInput.NormalizationType.Z_AXIS) {
					this.source
							.append("NormalizeInput.NormalizationType.Z_AXIS");
				} else if (somlayer.getNormalizationType() == NormalizeInput.NormalizationType.MULTIPLICATIVE) {
					this.source
							.append("NormalizeInput.NormalizationType.MULTIPLICATIVE");
				}
			}

			this.source.append("));\n");
		}

		this.source.append("  network.Reset();\n");
		this.source.append("  return network;\n");
		this.source.append("}\n");*/
	}

	public void generateQuery() {
		addUsing("Encog.Neural.NeuralData");

		this.source
				.append("  public static void QueryNetwork(BasicNetwork network,INeuralDataSet trainingSet) {\n");
		this.source.append("    // test the neural network\n");
		this.source
				.append("    Console.WriteLine(\"Neural Network Query:\");\n");

		this.source
				.append("    foreach(INeuralDataPair pair in trainingSet ) {\n");
		this.source
				.append("      INeuralData output = network.Compute(pair.Input);\n");
		this.source.append("\n");
		this.source.append("      Console.Write(\"Input: \");\n");
		this.source.append("      for(int i=0;i<pair.Input.Count;i++) {\n");
		this.source.append("        if( i!=0 )\n");
		this.source.append("          Console.Write(\",\");\n");
		this.source.append("        Console.Write(pair.Input[i]);\n");
		this.source.append("      }\n");
		this.source.append("      Console.Write(\", Output: \");\n");
		this.source.append("      for(int i=0;i<output.Count;i++) {\n");
		this.source.append("        if( i!=0 )\n");
		this.source.append("          Console.Write(\",\");\n");
		this.source.append("        Console.Write(output.Data[i]);\n");
		this.source.append("      }\n");
		this.source.append("\n");
		if (this.training.getIdealSize() != 0) {
			this.source.append("      Console.Write(\", Expected: \");\n");
			this.source.append("      for(int i=0;i<pair.Ideal.Count;i++) {\n");
			this.source.append("        if( i!=0 )\n");
			this.source.append("          Console.Write(\",\");\n");
			this.source.append("        Console.Write(pair.Ideal[i]);\n");
			this.source.append("      }\n");
			this.source.append("    Console.WriteLine(\"\");\n");
		}
		this.source.append("  }\n");
		this.source.append("  }\n");
	}

	public void generateTraining() {

		addUsing("Encog.Neural.Networks.Training");
		this.source
				.append("  public static BasicNetwork TrainNetwork(BasicNetwork network,INeuralDataSet trainingSet) {\n");

		switch (this.trainMethod) {
		case Backpropagation:
			addUsing("Encog.Neural.Networks.Training.Backpropagation");
			this.source.append("    ITrain train = new Backpropagation(\n");
			this.source.append("      network,\n");
			this.source.append("      trainingSet,\n");
			this.source.append("      LEARNING_RATE,\n");
			this.source.append("      MOMENTUM);\n");
			break;

		case Genetic:
			addUsing("Encog.Neural.Networks.Training.Genetic");
			this.source
					.append("    ITrain train = new TrainingSetNeuralGeneticAlgorithm(\n");
			this.source.append("      network,\n");
			this.source.append("      true,\n");
			this.source.append("      trainingSet,\n");
			this.source.append("      POPULATION_SIZE,\n");
			this.source.append("      MUTATION_PERCENT,\n");
			this.source.append("      MATE_PERCENT);\n");
			break;
		case Anneal:
			addUsing("Encog.Neural.Networks.Training.Anneal");
			this.source
					.append("    ITrain train = new NeuralSimulatedAnnealing(\n");
			this.source.append("      network, \n");
			this.source.append("      trainingSet, \n");
			this.source.append("      HIGH_TEMP, \n");
			this.source.append("      LOW_TEMP, \n");
			this.source.append("      CYCLES);\n");
			break;
		case TrainHopfield:
			addUsing("Encog.Neural.Networks.Training.Hopfield");
			this.source
					.append("    TrainHopfield train = new TrainHopfield(\n");
			this.source.append("      trainingSet,\n");
			this.source.append("      network);\n");
			this.source.append("\n");
			this.source.append("    train.iteration();");
			break;
		case TrainSOM:
			this.source
					.append("    ITrain train = new TrainSelfOrganizingMap(\n");
			this.source.append("      network, \n");
			this.source.append("      trainingSet,\n");
			this.source.append("      LEARNING_METHOD,\n");
			this.source.append("      LEARNING_RATE);\n");
			break;
			
		case NoTraining:
			break;

		}

		if (this.trainMethod != TrainingMethod.TrainHopfield) {
			this.source.append("\n");
			this.source.append("    int epoch = 1;\n");
			this.source.append("\n");
			this.source.append("    do {\n");
			this.source.append("      train.Iteration();\n");
			this.source
					.append("      Console.WriteLine(\"Iteration #\" + epoch + \" Error:\" + train.Error);\n");
			this.source.append("      epoch++;\n");
			this.source
					.append("    } while ((epoch < MAX_ITERATION) && (train.Error > MAX_ERROR));\n");
			this.source.append("\n");
		}

		this.source.append("    return (BasicNetwork)train.TrainedNetwork;\n");
		this.source.append("  }\n");

	}

	private void generateTrainingData() {
		addUsing("Encog.Neural.NeuralData");
		addUsing("Encog.Neural.Data.Basic");
		this.source.append("private static INeuralDataSet GetTraining() {\n");
		this.source.append("  double[][] INPUT = {\n");

		for (final NeuralDataPair pair : this.training) {
			this.source.append("    new double[");
			this.source.append(pair.getInput().size());
			this.source.append("] { ");
			for (int i = 0; i < pair.getInput().size(); i++) {
				if (i != 0) {
					this.source.append(',');
				}
				this.source.append(pair.getInput().getData(i));
			}
			this.source.append(" },\n");
		}

		this.source.append("  };\n");
		this.source.append("\n");

		if (this.training.getIdealSize() > 0) {
			this.source.append("  double[][] IDEAL = {\n");

			for (final NeuralDataPair pair : this.training) {
				this.source.append("    new double[");
				this.source.append(pair.getIdeal().size());
				this.source.append("] { ");
				for (int i = 0; i < pair.getIdeal().size(); i++) {
					if (i != 0) {
						this.source.append(',');
					}
					this.source.append(pair.getIdeal().getData(i));
				}
				this.source.append(" },\n");
			}

			this.source.append("  };\n");
			this.source
					.append("  return new BasicNeuralDataSet(INPUT, IDEAL);\n");
		} else {
			this.source
					.append("  return new BasicNeuralDataSet(INPUT, null);\n");
		}
		this.source.append("}\n");
	}
}
