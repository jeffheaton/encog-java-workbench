/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.commons.collections15.Transformer;
import org.encog.engine.network.activation.ActivationBipolarSteepenedSigmoid;
import org.encog.engine.network.activation.ActivationClippedLinear;
import org.encog.engine.network.activation.ActivationGaussian;
import org.encog.engine.network.activation.ActivationSIN;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.neural.neat.training.NEATLinkGene;
import org.encog.neural.neat.training.NEATNeuronGene;
import org.encog.workbench.WorkBenchError;
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

public class GenomeStructureTab extends EncogCommonTab {

	private VisualizationViewer<DrawnNeuron, DrawnConnection> vv;
	private NEATGenome genome;

	public GenomeStructureTab(NEATGenome genome) {
		super(null);
		this.genome = genome;

		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		Graph<DrawnNeuron, DrawnConnection> g = null;
		g = buildGraph(genome);

		if (g == null) {
			throw new WorkBenchError("Can't visualize genome");
		}

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
				case Linear:
					return Color.blue;					
				case Sigmoid:
					return Color.magenta;
				case Gaussian:
					return Color.cyan;
				case SIN:
					return Color.gray;
				default:
					return Color.red;
				}
			}

		};

		Transformer<DrawnConnection, Paint> edgePaint = new Transformer<DrawnConnection, Paint>() {
			public Paint transform(DrawnConnection connection) {
				if (connection.isContext()) {
					return Color.lightGray;
				} else {
					return Color.black;
				}
			}
		};

		// The Layout<V, E> is parameterized by the vertex and edge types
		StaticLayout<DrawnNeuron, DrawnConnection> layout = new StaticLayout<DrawnNeuron, DrawnConnection>(
				g, staticTranformer);

		layout.setSize(new Dimension(5000, 5000)); // sets the initial size of
													// the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		// BasicVisualizationServer<DrawnNeuron, DrawnConnection> vv = new
		// BasicVisualizationServer<DrawnNeuron, DrawnConnection>(
		// layout);

		// Dimension d = new Dimension(600,600);

		vv = new VisualizationViewer<DrawnNeuron, DrawnConnection>(layout);

		// vv.setPreferredSize(d); //Sets the viewing area size

		vv.getRenderer().getVertexLabelRenderer()
				.setPosition(Renderer.VertexLabel.Position.CNTR);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
		vv.getRenderContext().setArrowDrawPaintTransformer(edgePaint);
		vv.getRenderContext().setArrowFillPaintTransformer(edgePaint);

		vv.setVertexToolTipTransformer(new ToStringLabeller());

		vv.setVertexToolTipTransformer(new Transformer<DrawnNeuron, String>() {
			public String transform(DrawnNeuron edge) {
				return edge.getToolTip();
			}
		});

		vv.setEdgeToolTipTransformer(new Transformer<DrawnConnection, String>() {
			public String transform(DrawnConnection edge) {
				return edge.getToolTip();
			}
		});

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
				scaler.scale(vv, 1 / 1.1f, vv.getCenter());
			}
		});

		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				vv.getRenderContext().getMultiLayerTransformer()
						.getTransformer(Layer.LAYOUT).setToIdentity();
				vv.getRenderContext().getMultiLayerTransformer()
						.getTransformer(Layer.VIEW).setToIdentity();
			}
		});

		JPanel controls = new JPanel();
		controls.setLayout(new FlowLayout(FlowLayout.LEFT));
		controls.add(plus);
		controls.add(minus);
		controls.add(reset);
		Border border = BorderFactory.createEtchedBorder();
		controls.setBorder(border);
		add(controls, BorderLayout.NORTH);
		add(new LegendPanel(true),BorderLayout.SOUTH);

	}

	private int calculateDepths(Map<Integer, DrawnNeuron> neuronMap) {
		List<DrawnNeuron> outputList = new ArrayList<DrawnNeuron>();

		int maxDepth = 0;
		int maxOutputDepth = 0;

		for (int pass = 0; pass < 1; pass++) {
			boolean done = false;
			while (!done ) {
				done = true;

				for (NEATLinkGene neatLinkGene : genome.getLinksChromosome()) {
					if (neatLinkGene.getFromNeuronID() != neatLinkGene
							.getToNeuronID()) {
						DrawnNeuron fromNeuron = neuronMap
								.get((int) neatLinkGene.getFromNeuronID());
						DrawnNeuron toNeuron = neuronMap.get((int) neatLinkGene
								.getToNeuronID());

						// do not calculate a depth if the from is undefined
						if (fromNeuron.getDepth() != -1) {
							// if the to is depth 0 (bias or input)
							if (toNeuron.getDepth() != 0) {
								if( toNeuron.getDepth()==-1) {
									done = false;
								}
								
								int depth = fromNeuron.getDepth() + 1;
								toNeuron.setDepth(Math.max(toNeuron.getDepth(),
										depth));
								maxDepth = Math.max(depth, maxDepth);

								if (toNeuron.getType() == DrawnNeuronType.Output) {
									maxOutputDepth = Math.max(maxOutputDepth,
											depth);
									outputList.add(toNeuron);
								}
							}
						}
					}
				}
			}
		}

		maxDepth++;
		// all output at the same level
		for (DrawnNeuron neuron : outputList) {
			neuron.setDepth(maxDepth);
		}
		
		// handle any unassigned neurons, these are hidden neurons with no input.
		// put them at depth zero, as they are basically bias-like neurons.
		for (NEATLinkGene neatLinkGene : genome.getLinksChromosome()) {
			DrawnNeuron fromNeuron = neuronMap
						.get((int) neatLinkGene.getFromNeuronID());
			DrawnNeuron toNeuron = neuronMap.get((int) neatLinkGene
						.getToNeuronID());
			if( fromNeuron.getDepth()==-1 ) {
				fromNeuron.setDepth(0);
			}
			if( toNeuron.getDepth()==-1 ) {
				toNeuron.setDepth(0);
			}
		}

		return maxDepth;
	}

	private void calculateXY(List<DrawnNeuron> neurons, int maxDepth) {
		int[] layerTotal = new int[maxDepth + 1];
		int[] layerCurrent = new int[maxDepth + 1];

		for (DrawnNeuron neuron : neurons) {
			if( neuron.getDepth()<0 ) {
				neuron.setDepth(0);
			}
			layerTotal[neuron.getDepth()]++;
		}

		for (DrawnNeuron neuron : neurons) {
			layerCurrent[neuron.getDepth()]++;
			neuron.setX(neuron.getDepth() * (1.0 / layerTotal.length));
			neuron.setY(layerCurrent[neuron.getDepth()]
					* (1.0 / layerTotal[neuron.getDepth()]));
		}

	}

	private Graph<DrawnNeuron, DrawnConnection> buildGraph(NEATGenome genome) {

		int inputCount = 1;
		int outputCount = 1;
		int hiddenCount = 1;
		int biasCount = 1;

		List<DrawnNeuron> neurons = new ArrayList<DrawnNeuron>();
		Graph<DrawnNeuron, DrawnConnection> result = new SparseMultigraph<DrawnNeuron, DrawnConnection>();
		List<DrawnNeuron> connections = new ArrayList<DrawnNeuron>();
		Map<Integer, DrawnNeuron> neuronMap = new HashMap<Integer, DrawnNeuron>();

		// place all the neurons
		for (NEATNeuronGene neuronGene : genome.getNeuronsChromosome()) {
			String name = "";
			int depth = -1;
			DrawnNeuronType t = DrawnNeuronType.Hidden;

			switch (neuronGene.getNeuronType()) {
			case Bias:
				depth = 0;
				t = DrawnNeuronType.Bias;
				name = "B" + (biasCount++);
				break;
			case Input:
				depth = 0;
				t = DrawnNeuronType.Input;
				name = "I" + (inputCount++);
				break;
			case Output:
				t = DrawnNeuronType.Output;
				name = "O" + (outputCount++);
				break;
			case Hidden:
				if( neuronGene.getActivationFunction() instanceof ActivationClippedLinear) {
					t = DrawnNeuronType.Linear;
				} else if( neuronGene.getActivationFunction() instanceof ActivationBipolarSteepenedSigmoid) {
					t = DrawnNeuronType.Sigmoid;
				} else if( neuronGene.getActivationFunction() instanceof ActivationGaussian) {
					t = DrawnNeuronType.Gaussian;
				} else if( neuronGene.getActivationFunction() instanceof ActivationSIN) {
					t = DrawnNeuronType.SIN;
				}

				name = "H" + (hiddenCount++);
				break;
			}

			DrawnNeuron neuron = new DrawnNeuron(t, name);
			neurons.add(neuron);
			neuron.setDepth(depth);
			neuronMap.put((int) neuronGene.getId(), neuron);
		}

		// place all the connections
		for (NEATLinkGene neatLinkGene : genome.getLinksChromosome()) {
			if (neatLinkGene.isEnabled()) {
				DrawnNeuron fromNeuron = neuronMap.get((int) neatLinkGene
						.getFromNeuronID());
				DrawnNeuron toNeuron = neuronMap.get((int) neatLinkGene
						.getToNeuronID());
				DrawnConnection connection = new DrawnConnection(fromNeuron,
						toNeuron, neatLinkGene.getWeight());
				fromNeuron.getOutbound().add(connection);
				toNeuron.getInbound().add(connection);
			}
		}

		int maxDepth = calculateDepths(neuronMap);
		calculateXY(neurons, maxDepth);

		for (DrawnNeuron neuron : neurons) {
			result.addVertex(neuron);
			for (DrawnConnection connection : neuron.getOutbound()) {
				result.addEdge(connection, connection.getFrom(),
						connection.getTo(), EdgeType.DIRECTED);
			}
		}

		return result;
	}

	@Override
	public String getName() {
		return "NEAT Genome";
	}
}
