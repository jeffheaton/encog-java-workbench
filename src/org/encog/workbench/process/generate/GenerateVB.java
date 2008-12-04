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

public class GenerateVB implements Generate {

	private StringBuilder source;
	private BasicNetwork network;
	private BasicNeuralDataSet training;
	private boolean copy;
	private TrainingMethod trainMethod;
	private Set<String> using = new TreeSet<String>();

	private String generateImports() {
		StringBuilder results = new StringBuilder();
		for (String c : using) {
			results.append("Imports ");
			results.append(c);
			results.append("\n");
		}
		return results.toString();
	}

	private void addUsing(String str)
	{
		using.add(str);
	}

	private void generateConst() {
		source.append("  \' fill these in with your training parameters\n");
		switch (this.trainMethod) {
		case Backpropagation:
			source
					.append("  Public Const LEARNING_RATE as Double = 0.7\n");
			source.append("  Public Const MOMENTUM as Double = 0.7\n");
			source.append("  Public Const MAX_ITERATION as Integer = 5000\n");
			source.append("  Public Const MAX_ERROR as Double = 0.01\n");
			break;

		case Genetic:
			source.append("  Public Const MAX_ITERATION as Integer = 1000\n");
			source
					.append("  Public Const POPULATION_SIZE as Integer = 5000\n");
			source
					.append("  Public Const MUTATION_PERCENT as Double = 0.1\n");
			source
					.append("  Public Const MATE_PERCENT as Double = 0.25\n");
			source.append("  Public Const MAX_ERROR as Double = 0.01\n");
			break;

		case Anneal:
			source.append("  Public Const MAX_ITERATION as Integer = 1000\n");
			source.append("  Public Const MAX_ERROR as Double = 0.01\n");
			source.append("  Public Const HIGH_TEMP as Double = 10\n");
			source.append("  Public Const LOW_TEMP as Double = 2\n");
			source.append("  Public Const CYCLES as Integer = 100\n");
			break;

		case TrainHopfield:
			break;
			
		case TrainSOM:
			addUsing("Encog.Neural.Networks.Training.SOM");
			source.append("  Public Const LEARNING_RATE as Double = 0.7\n");
			source.append("  Public Const LEARNING_METHOD as TrainSelfOrganizingMap.LearningMethod = TrainSelfOrganizingMap.LearningMethod.SUBTRACTIVE\n");
			source.append("  Public Const MAX_ITERATION as Integer = 5000\n");
			source.append("  Public Const MAX_ERROR as Double = 0.01\n");
			break;
		}
	}

	private void generateTrainingData() {
		addUsing("Encog.Neural.NeuralData");
		addUsing("Encog.Neural.Data.Basic");
		source.append("Private Function GetTraining() As INeuralDataSet\n");
		source.append("  Dim INPUT As Double()() = { ");

		boolean first = true;
		for (NeuralDataPair pair : this.training) {
			if(!first)
			{
				source.append(',');
			}
			first = false;
			source.append("New Double(");
			source.append(pair.getInput().size()-1);
			source.append(") { ");
			for (int i = 0; i < pair.getInput().size(); i++) {
				if (i != 0)
					source.append(',');
				source.append(pair.getInput().getData(i));
			}
			source.append(" } ");
		}

		source.append("  }\n");

		if (this.training.getIdealSize() > 0) {
			source.append("  Dim IDEAL As Double()() = {");

			first = true;
			for (NeuralDataPair pair : this.training) {
				if(!first)
				{
					source.append(',');
				}
				first = false;
				source.append("new double(");
				source.append(pair.getIdeal().size()-1);
				source.append(") { ");
				for (int i = 0; i < pair.getIdeal().size(); i++) {
					if (i != 0)
						source.append(',');
					source.append(pair.getIdeal().getData(i));
				}
				source.append(" }");
			}

			source.append("  }\n");
			source.append("  Return New BasicNeuralDataSet(INPUT, IDEAL)\n");
		}
		else
			source.append("  Return New BasicNeuralDataSet(INPUT, null)\n");
		source.append("End Function\n");
	}

	private void generateNetwork() {
		addUsing("Encog.Neural.Networks.Layers");
		addUsing("Encog.Neural.Networks");
		source.append("Private Function GetNetwork() As BasicNetwork\n");
		source.append("  Dim network As New BasicNetwork()\n");

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

			source.append("))\n");
		}

		source.append("  network.Reset()\n");
		source.append("  Return network\n");
		source.append("End Function\n");
	}

	public void generateTraining() {

		addUsing("Encog.Neural.Networks.Training");
		source
				.append("  Public Function TrainNetwork(ByVal network As BasicNetwork, ByVal trainingSet As INeuralDataSet) As BasicNetwork\n");

		switch (this.trainMethod) {
		case Backpropagation:
			addUsing("Encog.Neural.Networks.Training.Backpropagation");
			source.append("    Dim train As ITrain = New Backpropagation(");
			source.append("      network,");
			source.append("      trainingSet,");
			source.append("      LEARNING_RATE,");
			source.append("      MOMENTUM)\n");
			break;

		case Genetic:
			addUsing("Encog.Neural.Networks.Training.Genetic");
			source
					.append("    Dim train as ITrain = New TrainingSetNeuralGeneticAlgorithm(");
			source.append("      network,");
			source.append("      true,");
			source.append("      trainingSet,");
			source.append("      POPULATION_SIZE,");
			source.append("      MUTATION_PERCENT,");
			source.append("      MATE_PERCENT)\n");
			break;
		case Anneal:
			addUsing("Encog.Neural.Networks.Training.Anneal");
			source
					.append("    Dim train as ITrain = New NeuralSimulatedAnnealing(");
			source.append("      network,");
			source.append("      trainingSet,");
			source.append("      HIGH_TEMP,");
			source.append("      LOW_TEMP,");
			source.append("      CYCLES)\n");
			break;
		case TrainHopfield:
			addUsing("Encog.Neural.Networks.Training.Hopfield");
			source.append("    Dim train as TrainHopfield = new TrainHopfield(");
			source.append("      trainingSet,");
			source.append("      network)\n");
			source.append("\n");
			source.append("    train.iteration();");
			break;
		case TrainSOM:
			source.append("    Dim train as ITrain = new TrainSelfOrganizingMap(");
			source.append("      network,");
			source.append("      trainingSet,");
			source.append("      LEARNING_METHOD,");
			source.append("      LEARNING_RATE)\n");
			break;

		}

		if (this.trainMethod != TrainingMethod.TrainHopfield) {
			source.append("\n");
			source.append("    Dim epoch As Integer = 1\n");
			source.append("\n");
			source.append("    do \n");
			source.append("      train.Iteration()\n");
			source
					.append("      Console.WriteLine(\"Iteration #\" & epoch & \" Error:\" & train.[Error])\n");
			source.append("      epoch+=1\n");
			source
					.append("    Loop While (epoch < MAX_ITERATION) AndAlso (train.[Error] > MAX_ERROR)\n");
			source.append("\n");
		}

		source.append("    Return DirectCast(train.TrainedNetwork, BasicNetwork)\n");
		source.append("  End Function\n");

	}

	public void generateQuery() {
		addUsing("Encog.Neural.NeuralData");

		source.append("Public Sub QueryNetwork(ByVal network As BasicNetwork, ByVal trainingSet As INeuralDataSet)\n");
		source.append("        ' test the neural network\n");
		source.append("        Console.WriteLine(\"Neural Network Query:\")\n");
		source.append("        For Each pair As INeuralDataPair In trainingSet\n");
		source.append("            Dim output As INeuralData = network.Compute(pair.Input)\n");
		source.append("\n");
		source.append("            Console.Write(\"Input: \")\n");
		source.append("            For i As Integer = 0 To pair.Input.Count - 1\n");
		source.append("                If i <> 0 Then\n");
		source.append("                    Console.Write(\",\")\n");
		source.append("                End If\n");
		source.append("                Console.Write(pair.Input(i))\n");
		source.append("            Next\n");
		source.append("            Console.Write(\", Output: \")\n");
		source.append("            For i As Integer = 0 To output.Count - 1\n");
		source.append("                If i <> 0 Then\n");
		source.append("                    Console.Write(\",\")\n");
		source.append("                End If\n");
		source.append("                Console.Write(output.Data(i))\n");
		source.append("            Next\n");
		source.append("\n");
		if (this.training.getIdealSize() != 0) {
		source.append("            Console.Write(\", Expected: \")\n");
		source.append("            For i As Integer = 0 To pair.Ideal.Count - 1\n");
		source.append("                If i <> 0 Then\n");
		source.append("                    Console.Write(\",\")\n");
		source.append("                End If\n");
		source.append("                Console.Write(pair.Ideal(i))\n");
		source.append("            Next\n");
		source.append("            Console.WriteLine(\"\")\n");
		}
		source.append("        Next\n");
		source.append("    End Sub\n");		
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
		source.append("  Public Sub Main()\n");
		source.append("\n");

		if (this.copy) {
			addUsing("Encog.Neural.NeuralData");
			source
					.append("    Dim trainingSet as INeuralDataSet = GetTraining()\n");
			source.append("    Dim network as BasicNetwork = GetNetwork()\n");
		} else {
			addUsing("Encog.Neural.Persist");
			addUsing("Encog.Neural.NeuralData");
			source
					.append("      Dim encog as EncogPersistedCollection = New EncogPersistedCollection()\n");
			source.append("      encog.Load(\"");
			source.append(fixPath(EncogWorkBench.getInstance()
					.getCurrentFileName()));
			source.append("\")\n");
			source.append("\n");
			source
					.append("      Dim trainingSet trainingSet as INeuralDataSet = (INeuralDataSet) encog.Find(\"");
			source.append(this.training.getName());
			source.append("\")\n");
			source
					.append("      Dim network as BasicNetwork = (BasicNetwork) encog.Find(\"");
			source.append(this.network.getName());
			source.append("\")\n");
		}

		if (this.trainMethod != TrainingMethod.NoTraining) {
			source.append("    network = TrainNetwork(network,trainingSet)\n");
		}
		source.append("    QueryNetwork(network,trainingSet)\n");
		source.append("  End Sub\n");

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

		
		source.append("Module Module1\n");
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
		source.append("End Module\n");

		String importStr = generateImports();

		return importStr + source.toString();
	}
}
