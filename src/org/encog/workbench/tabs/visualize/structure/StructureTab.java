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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.commons.collections15.Transformer;
import org.encog.engine.network.flat.FlatNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.tabs.EncogCommonTab;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class StructureTab extends EncogCommonTab {

	private FlatNetwork flat;
	private VisualizationViewer<DrawnNeuron, DrawnConnection> vv;
	
	public StructureTab(EncogPersistedObject encogObject) {
		super(encogObject);

		this.flat = ((BasicNetwork) (this.getEncogObject())).getStructure()
				.getFlat();

		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		Graph<DrawnNeuron, DrawnConnection> g = buildGraph();

		Transformer<DrawnNeuron, Point2D> staticTranformer = new Transformer<DrawnNeuron, Point2D>() {

			public Point2D transform(DrawnNeuron n) {
				int x = (int) (n.getX() * 600);
				int y = (int) (n.getY() * 300);

				Point2D result = new Point(x + 32, y);
				return result;
			}
		};

		Transformer<DrawnNeuron, Paint> vertexPaint = new Transformer<DrawnNeuron, Paint>() {
			public Paint transform(DrawnNeuron neuron) {
				switch (neuron.getType()) {
				case Bias:
					return Color.yellow;
				case Input:
					return Color.white;
				case Output:
					return Color.green;
				case Context:
					return Color.cyan;
				default:
					return Color.red;
				}
			}

		};

		// The Layout<V, E> is parameterized by the vertex and edge types
		StaticLayout<DrawnNeuron, DrawnConnection> layout = new StaticLayout<DrawnNeuron, DrawnConnection>(
				g, staticTranformer);
	

		layout.setSize(new Dimension(5000,5000)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		//BasicVisualizationServer<DrawnNeuron, DrawnConnection> vv = new BasicVisualizationServer<DrawnNeuron, DrawnConnection>(
		//		layout);
		
		//Dimension d = new Dimension(600,600);
		
		vv =  new VisualizationViewer<DrawnNeuron, DrawnConnection>(layout);
		
		//vv.setPreferredSize(d); //Sets the viewing area size

		vv.getRenderer().getVertexLabelRenderer()
				.setPosition(Renderer.VertexLabel.Position.CNTR);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.setVertexToolTipTransformer(new ToStringLabeller());
		
		vv.setVertexToolTipTransformer(new Transformer<DrawnNeuron,String>() {
			public String transform(DrawnNeuron edge) {
				return edge.getToolTip();
			}});
		
		vv.setEdgeToolTipTransformer(new Transformer<DrawnConnection,String>() {
			public String transform(DrawnConnection edge) {
				return edge.getToolTip();
			}});
		
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
		this.setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);
        
        vv.addKeyListener(graphMouse.getModeKeyListener());
        
        final ScalingControl scaler = new CrossoverScalingControl();        

        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });

        JButton reset = new JButton("reset");
        reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();
				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setToIdentity();
			}});

        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout(FlowLayout.LEFT));
        controls.add(plus);
        controls.add(minus);
        controls.add(reset);
        Border border = BorderFactory.createEtchedBorder();
        controls.setBorder(border);
        add(controls, BorderLayout.NORTH);
        
        
	}

	public Graph<DrawnNeuron, DrawnConnection> buildGraph() {
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

		for (int currentLayer = 0; currentLayer < layerCount; currentLayer++) {
			lastFedNeurons = new ArrayList<DrawnNeuron>();

			double x = (double) (layerCount - currentLayer - 1)
					/ (double) layerCount;
			int neuronCount = flat.getLayerCounts()[currentLayer];
			int feedCount = flat.getLayerFeedCounts()[currentLayer];
			for (int currentNeuron = 0; currentNeuron < neuronCount; currentNeuron++) {
				DrawnNeuronType type;

				String name = "?";
				// not a bias or context
				if (currentNeuron < feedCount) {
					if (currentLayer == 0) {
						type = DrawnNeuronType.Output;
						name = "O" + (outputCount++);
					} else if (currentLayer == (layerCount - 1)) {
						type = DrawnNeuronType.Input;
						name = "I" + (inputCount++);
					} else {
						type = DrawnNeuronType.Hidden;
						name = "H" + (hiddenCount++);
					}
				}
				// is a bias
				else if (currentNeuron == feedCount) {
					type = DrawnNeuronType.Bias;
					name = "B" + (biasCount++);
				}
				// is a context
				else {
					type = DrawnNeuronType.Context;
					name = "C" + (contextCount++);
				}

				double y = (double) currentNeuron / (double) neuronCount;

				double margin = ((double) (neuronCount - 1) / (double) neuronCount);
				margin = 1.0 - margin;
				margin /= 2.0;

				DrawnNeuron neuron = new DrawnNeuron(type, name, x, y + margin);
				neurons.add(neuron);

				if (neuron.getType() == DrawnNeuronType.Hidden
						|| neuron.getType() == DrawnNeuronType.Output) {
					lastFedNeurons.add(neuron);
				}

				int toNeuron = 0;
				int count = connections.size();				
				for (DrawnNeuron connectTo : connections) {					
					int weightIndex = flat.getLayerIndex()[currentLayer]+(toNeuron*count)+currentNeuron;
					double w = this.flat.getWeights()[weightIndex];
					DrawnConnection connection = new DrawnConnection(neuron, connectTo, w);
					neuron.getOutbound().add(connection);
					neuron.getInbound().add(connection);
					toNeuron++;
				}
			}

			connections = lastFedNeurons;
		}

		for (DrawnNeuron neuron : neurons) {
			result.addVertex(neuron);
			for (DrawnConnection connection : neuron.getOutbound()) {
				result.addEdge(connection, connection.getFrom(),
						connection.getTo(), EdgeType.DIRECTED);
			}
		}

		return result;

	}
}
