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
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.layers.SOMLayer;
import org.encog.util.NormalizeInput;
import org.encog.workbench.EncogWorkBench;

public class GenerateVB implements Generate {

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

		this.source.append("Module Module1\n");
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
		this.source.append("End Module\n");

		final String importStr = generateImports();

		return importStr + this.source.toString();
	}

	private void generateConst() {
		this.source
				.append("  \' fill these in with your training parameters\n");
		switch (this.trainMethod) {
		case Backpropagation:
			this.source
					.append("  Public Const LEARNING_RATE as Double = 0.7\n");
			this.source.append("  Public Const MOMENTUM as Double = 0.7\n");
			this.source
					.append("  Public Const MAX_ITERATION as Integer = 5000\n");
			this.source.append("  Public Const MAX_ERROR as Double = 0.01\n");
			break;

		case Genetic:
			this.source
					.append("  Public Const MAX_ITERATION as Integer = 1000\n");
			this.source
					.append("  Public Const POPULATION_SIZE as Integer = 5000\n");
			this.source
					.append("  Public Const MUTATION_PERCENT as Double = 0.1\n");
			this.source
					.append("  Public Const MATE_PERCENT as Double = 0.25\n");
			this.source.append("  Public Const MAX_ERROR as Double = 0.01\n");
			break;

		case Anneal:
			this.source
					.append("  Public Const MAX_ITERATION as Integer = 1000\n");
			this.source.append("  Public Const MAX_ERROR as Double = 0.01\n");
			this.source.append("  Public Const HIGH_TEMP as Double = 10\n");
			this.source.append("  Public Const LOW_TEMP as Double = 2\n");
			this.source.append("  Public Const CYCLES as Integer = 100\n");
			break;

		case TrainHopfield:
			break;

		case TrainSOM:
			addUsing("Encog.Neural.Networks.Training.SOM");
			this.source
					.append("  Public Const LEARNING_RATE as Double = 0.7\n");
			this.source
					.append("  Public Const LEARNING_METHOD as TrainSelfOrganizingMap.LearningMethod = TrainSelfOrganizingMap.LearningMethod.SUBTRACTIVE\n");
			this.source
					.append("  Public Const MAX_ITERATION as Integer = 5000\n");
			this.source.append("  Public Const MAX_ERROR as Double = 0.01\n");
			break;
			
		case NoTraining:
			break;
		}
	}

	private String generateImports() {
		final StringBuilder results = new StringBuilder();
		for (final String c : this.using) {
			results.append("Imports ");
			results.append(c);
			results.append("\n");
		}
		return results.toString();
	}

	public void generateMain() {
		this.source.append("  Public Sub Main()\n");
		this.source.append("\n");

		if (this.copy) {
			addUsing("Encog.Neural.NeuralData");
			this.source
					.append("    Dim trainingSet as INeuralDataSet = GetTraining()\n");
			this.source
					.append("    Dim network as BasicNetwork = GetNetwork()\n");
		} else {
			addUsing("Encog.Neural.Persist");
			addUsing("Encog.Neural.NeuralData");
			this.source
					.append("      Dim encog as EncogPersistedCollection = New EncogPersistedCollection()\n");
			this.source.append("      encog.Load(\"");
			this.source.append(fixPath(EncogWorkBench.getInstance()
					.getCurrentFileName()));
			this.source.append("\")\n");
			this.source.append("\n");
			this.source
					.append("      Dim trainingSet trainingSet as INeuralDataSet = (INeuralDataSet) encog.Find(\"");
			this.source.append(this.training.getName());
			this.source.append("\")\n");
			this.source
					.append("      Dim network as BasicNetwork = (BasicNetwork) encog.Find(\"");
			this.source.append(this.network.getName());
			this.source.append("\")\n");
		}

		if (this.trainMethod != TrainingMethod.NoTraining) {
			this.source
					.append("    network = TrainNetwork(network,trainingSet)\n");
		}
		this.source.append("    QueryNetwork(network,trainingSet)\n");
		this.source.append("  End Sub\n");

	}

	private void generateNetwork() {
		addUsing("Encog.Neural.Networks.Layers");
		addUsing("Encog.Neural.Networks");
		this.source.append("Private Function GetNetwork() As BasicNetwork\n");
		this.source.append("  Dim network As New BasicNetwork()\n");

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

			this.source.append("))\n");
		}

		this.source.append("  network.Reset()\n");
		this.source.append("  Return network\n");
		this.source.append("End Function\n");
	}

	public void generateQuery() {
		addUsing("Encog.Neural.NeuralData");

		this.source
				.append("Public Sub QueryNetwork(ByVal network As BasicNetwork, ByVal trainingSet As INeuralDataSet)\n");
		this.source.append("        ' test the neural network\n");
		this.source
				.append("        Console.WriteLine(\"Neural Network Query:\")\n");
		this.source
				.append("        For Each pair As INeuralDataPair In trainingSet\n");
		this.source
				.append("            Dim output As INeuralData = network.Compute(pair.Input)\n");
		this.source.append("\n");
		this.source.append("            Console.Write(\"Input: \")\n");
		this.source
				.append("            For i As Integer = 0 To pair.Input.Count - 1\n");
		this.source.append("                If i <> 0 Then\n");
		this.source.append("                    Console.Write(\",\")\n");
		this.source.append("                End If\n");
		this.source.append("                Console.Write(pair.Input(i))\n");
		this.source.append("            Next\n");
		this.source.append("            Console.Write(\", Output: \")\n");
		this.source
				.append("            For i As Integer = 0 To output.Count - 1\n");
		this.source.append("                If i <> 0 Then\n");
		this.source.append("                    Console.Write(\",\")\n");
		this.source.append("                End If\n");
		this.source.append("                Console.Write(output.Data(i))\n");
		this.source.append("            Next\n");
		this.source.append("\n");
		if (this.training.getIdealSize() != 0) {
			this.source.append("            Console.Write(\", Expected: \")\n");
			this.source
					.append("            For i As Integer = 0 To pair.Ideal.Count - 1\n");
			this.source.append("                If i <> 0 Then\n");
			this.source.append("                    Console.Write(\",\")\n");
			this.source.append("                End If\n");
			this.source
					.append("                Console.Write(pair.Ideal(i))\n");
			this.source.append("            Next\n");
			this.source.append("            Console.WriteLine(\"\")\n");
		}
		this.source.append("        Next\n");
		this.source.append("    End Sub\n");
	}

	public void generateTraining() {

		addUsing("Encog.Neural.Networks.Training");
		this.source
				.append("  Public Function TrainNetwork(ByVal network As BasicNetwork, ByVal trainingSet As INeuralDataSet) As BasicNetwork\n");

		switch (this.trainMethod) {
		case Backpropagation:
			addUsing("Encog.Neural.Networks.Training.Backpropagation");
			this.source
					.append("    Dim train As ITrain = New Backpropagation(");
			this.source.append("      network,");
			this.source.append("      trainingSet,");
			this.source.append("      LEARNING_RATE,");
			this.source.append("      MOMENTUM)\n");
			break;

		case Genetic:
			addUsing("Encog.Neural.Networks.Training.Genetic");
			this.source
					.append("    Dim train as ITrain = New TrainingSetNeuralGeneticAlgorithm(");
			this.source.append("      network,");
			this.source.append("      true,");
			this.source.append("      trainingSet,");
			this.source.append("      POPULATION_SIZE,");
			this.source.append("      MUTATION_PERCENT,");
			this.source.append("      MATE_PERCENT)\n");
			break;
		case Anneal:
			addUsing("Encog.Neural.Networks.Training.Anneal");
			this.source
					.append("    Dim train as ITrain = New NeuralSimulatedAnnealing(");
			this.source.append("      network,");
			this.source.append("      trainingSet,");
			this.source.append("      HIGH_TEMP,");
			this.source.append("      LOW_TEMP,");
			this.source.append("      CYCLES)\n");
			break;
		case TrainHopfield:
			addUsing("Encog.Neural.Networks.Training.Hopfield");
			this.source
					.append("    Dim train as TrainHopfield = new TrainHopfield(");
			this.source.append("      trainingSet,");
			this.source.append("      network)\n");
			this.source.append("\n");
			this.source.append("    train.iteration();");
			break;
		case TrainSOM:
			this.source
					.append("    Dim train as ITrain = new TrainSelfOrganizingMap(");
			this.source.append("      network,");
			this.source.append("      trainingSet,");
			this.source.append("      LEARNING_METHOD,");
			this.source.append("      LEARNING_RATE)\n");
			break;
		case NoTraining:
			break;
		}

		if (this.trainMethod != TrainingMethod.TrainHopfield) {
			this.source.append("\n");
			this.source.append("    Dim epoch As Integer = 1\n");
			this.source.append("\n");
			this.source.append("    do \n");
			this.source.append("      train.Iteration()\n");
			this.source
					.append("      Console.WriteLine(\"Iteration #\" & epoch & \" Error:\" & train.[Error])\n");
			this.source.append("      epoch+=1\n");
			this.source
					.append("    Loop While (epoch < MAX_ITERATION) AndAlso (train.[Error] > MAX_ERROR)\n");
			this.source.append("\n");
		}

		this.source
				.append("    Return DirectCast(train.TrainedNetwork, BasicNetwork)\n");
		this.source.append("  End Function\n");

	}

	private void generateTrainingData() {
		addUsing("Encog.Neural.NeuralData");
		addUsing("Encog.Neural.Data.Basic");
		this.source
				.append("Private Function GetTraining() As INeuralDataSet\n");
		this.source.append("  Dim INPUT As Double()() = { ");

		boolean first = true;
		for (final NeuralDataPair pair : this.training) {
			if (!first) {
				this.source.append(',');
			}
			first = false;
			this.source.append("New Double(");
			this.source.append(pair.getInput().size() - 1);
			this.source.append(") { ");
			for (int i = 0; i < pair.getInput().size(); i++) {
				if (i != 0) {
					this.source.append(',');
				}
				this.source.append(pair.getInput().getData(i));
			}
			this.source.append(" } ");
		}

		this.source.append("  }\n");

		if (this.training.getIdealSize() > 0) {
			this.source.append("  Dim IDEAL As Double()() = {");

			first = true;
			for (final NeuralDataPair pair : this.training) {
				if (!first) {
					this.source.append(',');
				}
				first = false;
				this.source.append("new double(");
				this.source.append(pair.getIdeal().size() - 1);
				this.source.append(") { ");
				for (int i = 0; i < pair.getIdeal().size(); i++) {
					if (i != 0) {
						this.source.append(',');
					}
					this.source.append(pair.getIdeal().getData(i));
				}
				this.source.append(" }");
			}

			this.source.append("  }\n");
			this.source
					.append("  Return New BasicNeuralDataSet(INPUT, IDEAL)\n");
		} else {
			this.source
					.append("  Return New BasicNeuralDataSet(INPUT, null)\n");
		}
		this.source.append("End Function\n");
	}
}
