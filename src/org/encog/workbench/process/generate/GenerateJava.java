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

import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.ContextLayer;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.neural.networks.synapse.Synapse;

public class GenerateJava implements Generate {

	private StringBuilder source;
	private BasicNetwork network;
	private BasicNeuralDataSet training;
	private final Set<String> imports = new TreeSet<String>();
	private int hiddenLayerNumber;
	private Map<Layer,String> layerMap = new HashMap<Layer,String>();

	@SuppressWarnings("unchecked")
	private void addClass(final Class c) {
		this.imports.add(c.getName());
	}
	
	private String nameLayer(Layer layer)
	{
		if( this.layerMap.containsKey(layer) )
		{
			return this.layerMap.get(layer);
		}
		
		StringBuilder result = new StringBuilder();
		
		if( layer==this.network.getInputLayer() )
		{
			result.append("inputLayer");
		}
		else if( layer==this.network.getOutputLayer() )
		{
			result.append("outputLayer");
		}
		else
		{
			result.append("hiddenLayer");
			result.append(hiddenLayerNumber++);
		}
		
		this.layerMap.put(layer, result.toString());
		return result.toString();
		
	}

	private void addObject(final Object obj) {
		this.imports.add(obj.getClass().getName());
	}

	public String generate(final BasicNetwork network) {

		this.network = network;
		this.training = (BasicNeuralDataSet) training;
		this.hiddenLayerNumber = 1;

		this.source = new StringBuilder();

		addClass(this.network.getClass());

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
	
	public void generateLayer(Layer previousLayer, Layer currentLayer)
	{		
		this.source.append("Layer ");
		this.source.append(nameLayer(currentLayer));
		this.source.append(" = ");
		
		this.addObject(currentLayer);
		
		if( currentLayer instanceof ContextLayer )
		{
			this.addObject(currentLayer.getActivationFunction());
			this.source.append("new ContextLayer( new ");
			this.source.append(currentLayer.getActivationFunction().getClass().getSimpleName());
			this.source.append("()");
			this.source.append(",");
			this.source.append(currentLayer.hasThreshold()?"true":"false");
			this.source.append(",");
			this.source.append(currentLayer.getNeuronCount());
			this.source.append(");");
		}
		else if( currentLayer instanceof RadialBasisFunctionLayer )
		{
			this.source.append("new RadialBasisFunctionLayer(");
			this.source.append(currentLayer.getNeuronCount());
			this.source.append(");");
		}
		else if( currentLayer instanceof BasicLayer )
		{
			this.addObject(currentLayer.getActivationFunction());
			this.source.append("new BasicLayer( new ");
			this.source.append(currentLayer.getActivationFunction().getClass().getSimpleName());
			this.source.append("()");
			this.source.append(",");
			this.source.append(currentLayer.hasThreshold()?"true":"false");
			this.source.append(",");
			this.source.append(currentLayer.getNeuronCount());
			this.source.append(");");
		}
		
		if( previousLayer==null) {
			this.source.append("\nnetwork.addLayer(");
			this.source.append(nameLayer(currentLayer));
			this.source.append(");\n");
		}
		else
		{
			this.source.append("\nlayer");
			this.source.append(nameLayer(previousLayer));
			this.source.append(".addNext(");
			this.source.append(nameLayer(currentLayer));
			this.source.append(");\n");
		}
		
		// next layers
		for(Synapse nextSynapse: currentLayer.getNext() )
		{
			Layer nextLayer = nextSynapse.getToLayer();
			if( this.layerMap.containsKey(nextLayer))
			{
				this.source.append(nameLayer(currentLayer));
				this.source.append(".addNext(");
				this.source.append(nameLayer(nextLayer));
				this.source.append(");\n");
			}
			else
			{
				generateLayer( currentLayer, nextSynapse.getToLayer());
			}
		}

	}

	public void generateMain() {
		this.source
				.append("  public static void main(final String args[]) {\n");
		this.source.append("\n");

		this.source.append("  BasicNetwork network = new BasicNetwork();\n");

		generateLayer(null, this.network.getInputLayer());
		
		this.source.append("  network.getStructure().finalizeStructure();\n");
		this.source.append("  network.reset();\n");
		
		this.source.append("  }\n");

	}
}
