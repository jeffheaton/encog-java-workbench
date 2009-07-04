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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.neural.networks.synapse.Synapse;
import org.encog.persist.EncogPersistedCollection;
import org.encog.workbench.EncogWorkBench;

public class GenerateJava implements Generate {

	private StringBuilder source;
	private BasicNetwork network;
	private BasicNeuralDataSet training;
	private final Set<String> imports = new TreeSet<String>();
	private int currentLayerNumber;
	private Map<Layer,Integer> layerMap = new HashMap<Layer,Integer>();

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
		this.currentLayerNumber = 1;

		this.source = new StringBuilder();

		addClass(BasicNetwork.class);

		this.source.append("\n");
		this.source.append("public class EncogGeneratedClass {\n");
		this.source.append("\n");

		generateMain();
		this.source.append("}\n");

		final String importStr = generateImports();

		return importStr + this.source.toString();
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
	
	public void generateLayer(Layer layer)
	{
		this.source.append("Layer layer");
		this.source.append(currentLayerNumber);
		this.source.append(" = ");
		
		if( layer instanceof ContextLayer )
		{
			
		}
		else if( layer instanceof RadialBasisFunctionLayer )
		{
			
		}
		else if( layer instanceof BasicLayer )
		{
			this.source.append("new BasicLayer( new ");
			this.source.append(layer.getActivationFunction().getClass().getSimpleName());
			this.source.append("()");
			this.source.append(",");
			this.source.append(layer.hasThreshold()?"true":"false");
			this.source.append(",");
			this.source.append(layer.getNeuronCount());
			this.source.append(");");
		}
		
		this.source.append("\nnetwork.addLayer(");
		this.source.append("layer");
		this.source.append(currentLayerNumber);
		this.source.append(");\n");
		
		this.layerMap.put(layer, currentLayerNumber);
		
		currentLayerNumber++;
		
		if( layer.getNext().size()==1 )
		{
			generateLayer( layer.getNext().get(0).getToLayer() );
		}
		else
		{
			// next layers
			for(Synapse nextSynapse: layer.getNext() )
			{
				generateLayer( nextSynapse.getToLayer());
			}	
		}

	}

	public void generateMain() {
		this.source
				.append("  public static void main(final String args[]) {\n");
		this.source.append("\n");

		this.source.append("  BasicNetwork network = new BasicNetwork();\n");

		generateLayer(this.network.getInputLayer());
		
		this.source.append("  network.getStructure().finalizeStructure();\n");
		this.source.append("  network.reset();\n");
		
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



}
