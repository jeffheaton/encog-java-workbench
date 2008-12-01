package org.encog.workbench.process.generate;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;

public class GenerateVB implements Generate {

	public String generate(BasicNetwork network, NeuralDataSet training,
			boolean copy, TrainingMethod trainMethod) {
		return "Generated VB";
	}

}
