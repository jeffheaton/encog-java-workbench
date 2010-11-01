/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.tabs.visualize.structure;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.encog.engine.network.flat.FlatNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.tabs.EncogCommonTab;

public class StructureTab extends EncogCommonTab  {
	
	private FlatNetwork flat;
	private List<DrawnNeuron> neurons = new ArrayList<DrawnNeuron>();
	
	public StructureTab(EncogPersistedObject encogObject) {
		super(encogObject);
		
		List<DrawnNeuron> lastFedNeurons;
		List<DrawnNeuron> connections = new ArrayList<DrawnNeuron>();
		this.flat = ((BasicNetwork)encogObject).getStructure().getFlat();
		int layerCount = flat.getLayerCounts().length;
		
		for(int currentLayer=0;currentLayer<layerCount;currentLayer++)
		{
			lastFedNeurons  = new ArrayList<DrawnNeuron>();
			
			double x = (double)(layerCount-currentLayer-1)/(double)layerCount;
			int neuronCount = flat.getLayerCounts()[currentLayer];
			int feedCount = flat.getLayerFeedCounts()[currentLayer];
			for(int currentNeuron = 0; currentNeuron<neuronCount; currentNeuron++)
			{
				DrawnNeuronType type;
				
				// not a bias or context
				if( currentNeuron<feedCount )
				{
					if( currentLayer==0 )
						type = DrawnNeuronType.Output;
					else if( currentLayer==(layerCount-1) )
						type = DrawnNeuronType.Input;
					else
						type = DrawnNeuronType.Hidden;
				}
				// is a bias
				else if( currentNeuron==feedCount )
				{
					type = DrawnNeuronType.Bias;
				}
				// is a context
				else
				{
					type = DrawnNeuronType.Context;
				}
				
				double y = (double)currentNeuron/(double)neuronCount;
				
				double margin = ((double)(neuronCount-1)/(double)neuronCount);
				margin = 1.0 - margin;
				margin/=2.0;
				
				DrawnNeuron neuron = new DrawnNeuron(type,x,y+margin,32);
				neurons.add(neuron);
				
				if( neuron.getType()==DrawnNeuronType.Hidden 
						|| neuron.getType()==DrawnNeuronType.Output ) 
				{
					lastFedNeurons.add(neuron);
				}
				
				for( DrawnNeuron connectTo : connections )
				{
					DrawnConnection connection = new DrawnConnection(neuron,connectTo);
					neuron.getOutbound().add(connection);
					neuron.getInbound().add(connection);
				}
			}
			
			connections = lastFedNeurons;
		}		
	}
		
	 	
	public void paint(Graphics g)
	{
		int height = getHeight()-10;
		int width = getWidth()-10;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		g.setColor(Color.BLUE);
		
		for(DrawnNeuron neuron: this.neurons)
		{
			neuron.paint(g,width,height);
			for(DrawnConnection connection: neuron.getOutbound())
			{
				connection.paint(g, width, height);
			}
		}		
	}
}
