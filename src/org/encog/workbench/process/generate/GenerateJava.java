/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
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

import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;

public class GenerateJava implements Generate {

	private StringBuilder source;
	private BasicNetwork network;
	private BasicNeuralDataSet training;
	private boolean copy;
	private TrainingMethod trainMethod;
	private final Set<String> imports = new TreeSet<String>();

	@SuppressWarnings("unchecked")
	private void addClass(final Class c) {
		this.imports.add(c.getName());
	}

	private void addObject(final Object obj) {
		this.imports.add(obj.getClass().getName());
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

	public String generate(final BasicNetwork network) {

		this.network = network;
		this.training = (BasicNeuralDataSet) training;
		this.copy = copy;
		this.trainMethod = trainMethod;

		this.source = new StringBuilder();

		addClass(BasicNetwork.class);

		this.source.append("\n");
		this.source.append("public class EncogGeneratedClass {\n");
		this.source.append("\n");
		generateConst();
		this.source.append("\n");

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

		final String importStr = generateImports();

		return importStr + this.source.toString();
	}

	private void generateConst() {
/*		this.source
				.append("  // fill these in with your training parameters\n");
		switch (this.trainMethod) {
		case Backpropagation:
			this.source
					.append("  public static final double LEARNING_RATE = 0.7;\n");
			this.source
					.append("  public static final double MOMENTUM = 0.7;\n");
			this.source
					.append("  public static final int MAX_ITERATION = 5000;\n");
			this.source
					.append("  public static final double MAX_ERROR = 0.01;\n");
			break;

		case Genetic:
			this.source
					.append("  public static final int MAX_ITERATION = 1000;\n");
			this.source
					.append("  public static final int POPULATION_SIZE = 5000;\n");
			this.source
					.append("  public static final double MUTATION_PERCENT = 0.1;\n");
			this.source
					.append("  public static final double MATE_PERCENT = 0.25;\n");
			this.source
					.append("  public static final double MAX_ERROR = 0.01;\n");
			break;

		case Anneal:
			this.source
					.append("  public static final int MAX_ITERATION = 1000;\n");
			this.source
					.append("  public static final double MAX_ERROR = 0.01;\n");
			this.source
					.append("  public static final double HIGH_TEMP = 10;\n");
			this.source.append("  public static final double LOW_TEMP = 2;\n");
			this.source.append("  public static final int CYCLES = 100;\n");
			break;

		case TrainHopfield:
			break;

		case TrainSOM:
			addClass(TrainSelfOrganizingMap.class);
			this.source
					.append("  public static final double LEARNING_RATE = 0.7;\n");
			this.source
					.append("  public static final TrainSelfOrganizingMap.LearningMethod LEARNING_METHOD = TrainSelfOrganizingMap.LearningMethod.SUBTRACTIVE;\n");
			this.source
					.append("  public static final int MAX_ITERATION = 5000;\n");
			this.source
					.append("  public static final double MAX_ERROR = 0.01;\n");
			break;
			
		case NoTraining:
			break;
		}*/
	}

	private String generateImports() {
		final StringBuilder results = new StringBuilder();
		for (final String c : this.imports) {
			results.append("import ");
			results.append(c);
			results.append(";\n");
		}
		return results.toString();
	}

	public void generateMain() {
		this.source
				.append("  public static void main(final String args[]) {\n");
		this.source.append("\n");

		if (this.copy) {
			addClass(NeuralDataSet.class);
			this.source
					.append("    final NeuralDataSet trainingSet = getTraining();\n");
			this.source.append("    BasicNetwork network = getNetwork();\n");
		} else {
			addClass(EncogPersistedCollection.class);
			addClass(NeuralDataSet.class);
			this.source
					.append("      EncogPersistedCollection encog = new EncogPersistedCollection();\n");
			this.source.append("      encog.load(\"");
			this.source.append(fixPath(EncogWorkBench.getInstance()
					.getCurrentFileName()));
			this.source.append("\");\n");
			this.source.append("\n");
			this.source
					.append("      final NeuralDataSet trainingSet = (NeuralDataSet) encog.find(\"");
			this.source.append(this.training.getName());
			this.source.append("\");\n");
			this.source
					.append("      BasicNetwork network = (BasicNetwork) encog.find(\"");
			this.source.append(this.network.getName());
			this.source.append("\");\n");
		}

		if (this.trainMethod != TrainingMethod.NoTraining) {
			this.source
					.append("    network = trainNetwork(network,trainingSet);\n");
		}
		this.source.append("    queryNetwork(network,trainingSet);\n");
		this.source.append("  }\n");

	}

	private void generateNetwork() {
/*		addClass(BasicNetwork.class);
		this.source.append("private static BasicNetwork getNetwork() {\n");
		this.source.append("  BasicNetwork network = new BasicNetwork();\n");

		for (final Layer layer : this.network.getLayers()) {
			this.source.append("  network.addLayer(new ");
			this.source.append(layer.getClass().getSimpleName());
			this.source.append('(');
			this.source.append(layer.getNeuronCount());
			addObject(layer);

			if (layer instanceof FeedforwardLayer) {
				final FeedforwardLayer fflayer = (FeedforwardLayer) layer;
				if (!(fflayer.getActivationFunction() instanceof ActivationSigmoid)) {
					addObject(fflayer.getActivationFunction());
					this.source.append(", new ");
					this.source.append(fflayer.getActivationFunction()
							.getClass().getSimpleName());
					this.source.append("()");
				}
			} else if (layer instanceof SOMLayer) {
				final SOMLayer somlayer = (SOMLayer) layer;
				addClass(NormalizeInput.class);

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

		this.source.append("  network.reset();\n");
		this.source.append("  return network;\n");
		this.source.append("}\n");*/
	}

	public void generateQuery() {
		addClass(NeuralData.class);
		addClass(NeuralDataPair.class);
		this.source
				.append("  public static void queryNetwork(BasicNetwork network,NeuralDataSet trainingSet) {\n");
		this.source.append("    // test the neural network\n");
		this.source
				.append("    System.out.println(\"Neural Network Query:\");\n");

		this.source.append("    for(NeuralDataPair pair: trainingSet ) {\n");
		this.source
				.append("      final NeuralData output = network.compute(pair.getInput());\n");
		this.source.append("\n");
		this.source.append("      System.out.print(\"Input: \");\n");
		this.source
				.append("      for(int i=0;i<pair.getInput().size();i++) {\n");
		this.source.append("        if( i!=0 )\n");
		this.source.append("          System.out.print(\",\");\n");
		this.source
				.append("        System.out.print(pair.getInput().getData(i));\n");
		this.source.append("      }\n");
		this.source.append("      System.out.print(\", Output: \");\n");
		this.source.append("      for(int i=0;i<output.size();i++) {\n");
		this.source.append("        if( i!=0 )\n");
		this.source.append("          System.out.print(\",\");\n");
		this.source.append("        System.out.print(output.getData(i));\n");
		this.source.append("      }\n");
		this.source.append("\n");
		if (this.training.getIdealSize() != 0) {
			this.source.append("      System.out.print(\", Expected: \");\n");
			this.source
					.append("      for(int i=0;i<pair.getIdeal().size();i++) {\n");
			this.source.append("        if( i!=0 )\n");
			this.source.append("          System.out.print(\",\");\n");
			this.source
					.append("        System.out.print(pair.getIdeal().getData(i));\n");
			this.source.append("      }\n");
			this.source.append("    System.out.println(\"\");\n");
		}
		this.source.append("  }\n");
		this.source.append("  }\n");
	}

	public void generateTraining() {
/*
		addClass(Train.class);
		this.source
				.append("  public static BasicNetwork trainNetwork(BasicNetwork network,NeuralDataSet trainingSet) {\n");

		switch (this.trainMethod) {
		case Backpropagation:
			addClass(Backpropagation.class);
			this.source
					.append("    final Train train = new Backpropagation(\n");
			this.source.append("      network,\n");
			this.source.append("      trainingSet,\n");
			this.source.append("      LEARNING_RATE,\n");
			this.source.append("      MOMENTUM);\n");
			break;

		case Genetic:
			addClass(TrainingSetNeuralGeneticAlgorithm.class);
			this.source
					.append("    final Train train = new TrainingSetNeuralGeneticAlgorithm(\n");
			this.source.append("      network,\n");
			this.source.append("      true,\n");
			this.source.append("      trainingSet,\n");
			this.source.append("      POPULATION_SIZE,\n");
			this.source.append("      MUTATION_PERCENT,\n");
			this.source.append("      MATE_PERCENT);\n");
			break;
		case Anneal:
			addClass(NeuralSimulatedAnnealing.class);
			this.source
					.append("    final Train train = new NeuralSimulatedAnnealing(\n");
			this.source.append("      network, \n");
			this.source.append("      trainingSet, \n");
			this.source.append("      HIGH_TEMP, \n");
			this.source.append("      LOW_TEMP, \n");
			this.source.append("      CYCLES);\n");
			break;
		case TrainHopfield:
			addClass(TrainHopfield.class);
			this.source
					.append("    TrainHopfield train = new TrainHopfield(\n");
			this.source.append("      trainingSet,\n");
			this.source.append("      network);\n");
			this.source.append("\n");
			this.source.append("    train.iteration();");
			break;
		case TrainSOM:
			this.source
					.append("    final TrainSelfOrganizingMap train = new TrainSelfOrganizingMap(\n");
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
			this.source.append("      train.iteration();\n");
			this.source
					.append("      System.out.println(\"Iteration #\" + epoch + \" Error:\" + train.getError());\n");
			this.source.append("      epoch++;\n");
			this.source
					.append("    } while ((epoch < MAX_ITERATION) && (train.getError() > MAX_ERROR));\n");
			this.source.append("\n");
		}

		this.source.append("    return (BasicNetwork)train.getNetwork();\n");
		this.source.append("  }\n");
*/
	}

	private void generateTrainingData() {
		addClass(NeuralDataSet.class);
		addClass(BasicNeuralDataSet.class);
		this.source.append("private static NeuralDataSet getTraining() {\n");
		this.source.append("  final double[][] INPUT = {\n");

		for (final NeuralDataPair pair : this.training) {
			this.source.append("    { ");
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
			this.source.append("  final double[][] IDEAL = {\n");

			for (final NeuralDataPair pair : this.training) {
				this.source.append("    { ");
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
