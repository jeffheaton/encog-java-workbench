/*
 * Encog(tm) Workbench v2.6 
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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.encog.engine.network.flat.FlatNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.tabs.EncogCommonTab;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class StructureTab extends EncogCommonTab  {
	
	private FlatNetwork flat;
	private List<DrawnNeuron> neurons = new ArrayList<DrawnNeuron>();
	
	public StructureTab(EncogPersistedObject encogObject) {
		super(encogObject);

		this.flat = ((BasicNetwork)(this.getEncogObject())).getStructure().getFlat();
		
		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		Graph<DrawnNeuron, DrawnConnection> g = buildGraph();
		
		Transformer<DrawnNeuron, Point2D> staticTranformer = new Transformer<DrawnNeuron, Point2D>() {

            public Point2D transform(DrawnNeuron n) {
            	int x = (int)(n.getX()*300);
            	int y = (int)(n.getY()*300);


                Point2D result = new Point(x+32, y);
                return result;
            }
        };
		
		// The Layout<V, E> is parameterized by the vertex and edge types
		StaticLayout<DrawnNeuron, DrawnConnection> layout = new StaticLayout<DrawnNeuron, DrawnConnection>(g, staticTranformer);
		
		layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<DrawnNeuron, DrawnConnection> vv =
		new BasicVisualizationServer<DrawnNeuron, DrawnConnection>(layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		
		this.add(vv);		
	}
	
	public Graph<DrawnNeuron, DrawnConnection> buildGraph()
	{
		int inputCount = 1;
		int outputCount = 1;
		int hiddenCount = 1;
		int biasCount = 1;
		int contextCount = 1;
		
		List<DrawnNeuron> neurons = new ArrayList<DrawnNeuron>();
		Graph<DrawnNeuron, DrawnConnection> result = new SparseMultigraph<DrawnNeuron, DrawnConnection>();
		List<DrawnNeuron> lastFedNeurons;
		List<DrawnNeuron> connections = new ArrayList<DrawnNeuron>();
		
		int layerCount = flat.getLayerCounts().length;
		int neuronNumber = 1;
		
		for(int currentLayer=0;currentLayer<layerCount;currentLayer++)
		{
			lastFedNeurons  = new ArrayList<DrawnNeuron>();
			
			double x = (double)(layerCount-currentLayer-1)/(double)layerCount;
			int neuronCount = flat.getLayerCounts()[currentLayer];
			int feedCount = flat.getLayerFeedCounts()[currentLayer];
			for(int currentNeuron = 0; currentNeuron<neuronCount; currentNeuron++)
			{
				DrawnNeuronType type;
				
				String name = "?";
				// not a bias or context
				if( currentNeuron<feedCount )
				{
					if( currentLayer==0 )
					{
						type = DrawnNeuronType.Output;
						name = "O"+(outputCount++);
					}
					else if( currentLayer==(layerCount-1) )
					{
						type = DrawnNeuronType.Input;
						name = "I"+(inputCount++);
					}
					else
					{
						type = DrawnNeuronType.Hidden;
						name = "H"+(hiddenCount++);
					}
				}
				// is a bias
				else if( currentNeuron==feedCount )
				{
					type = DrawnNeuronType.Bias;
					name = "B"+(biasCount++);
				}
				// is a context
				else
				{
					type = DrawnNeuronType.Context;
					name = "C"+(contextCount++);
				}
				
				double y = (double)currentNeuron/(double)neuronCount;
				
				double margin = ((double)(neuronCount-1)/(double)neuronCount);
				margin = 1.0 - margin;
				margin/=2.0;
				
				DrawnNeuron neuron = new DrawnNeuron(type,name,x,y+margin);
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
		
		for(DrawnNeuron neuron: neurons)
		{
			result.addVertex(neuron);
			for(DrawnConnection connection: neuron.getOutbound() )
			{
				result.addEdge(connection,connection.getFrom(),connection.getTo(),EdgeType.DIRECTED);
			}
		}		
		
		return result;

	}
}
