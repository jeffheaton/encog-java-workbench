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
package org.encog.workbench.tabs.visualize.epl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.encog.ml.prg.EncogProgram;
import org.encog.ml.prg.ProgramNode;
import org.encog.ml.prg.expvalue.ExpressionValue;
import org.encog.ml.prg.extension.ProgramExtensionTemplate;
import org.encog.ml.prg.extension.StandardExtensions;
import org.encog.util.Format;
import org.encog.workbench.tabs.EncogCommonTab;

import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class EPLTreeTab extends EncogCommonTab {

	private VisualizationViewer<ProgramNode, Integer> vv;
	private Forest<ProgramNode,Integer> graph;
	private int edgeIndex = 0;
	
	public EPLTreeTab(final EncogProgram prg) {
		super(null);
		
		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		// Graph<V, E> where V is the type of the vertices 
        // and E is the type of the edges
		this.graph = new DelegateForest<ProgramNode, Integer>(new DirectedOrderedSparseMultigraph<ProgramNode, Integer>());

        buildGraph(prg);
        // Add some vertices. From above we defined these to be type Integer.
		// The Layout<V, E> is parameterized by the vertex and edge types
        TreeLayout<ProgramNode,Integer> treeLayout = new TreeLayout<ProgramNode,Integer>(graph);
        
        Transformer<ProgramNode,Paint> vertexPaint = new Transformer<ProgramNode,Paint>() {
			public Paint transform(ProgramNode v) { return Color.white; }
		};
	

		//layout.setSize(new Dimension(5000,5000)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		//BasicVisualizationServer<DrawnNeuron, DrawnConnection> vv = new BasicVisualizationServer<DrawnNeuron, DrawnConnection>(
		//		layout);
		
		//Dimension d = new Dimension(600,600);
		
		vv =  new VisualizationViewer<ProgramNode, Integer>(treeLayout, new Dimension(600,600));
		
		//vv.setPreferredSize(d); //Sets the viewing area size

		vv.getRenderer().getVertexLabelRenderer()
				.setPosition(Renderer.VertexLabel.Position.CNTR);
		vv.getRenderContext().setVertexLabelTransformer(new Transformer<ProgramNode,String>(){

			@Override
			public String transform(ProgramNode node) {
				ProgramExtensionTemplate temp = node.getTemplate();
				if( temp == StandardExtensions.EXTENSION_VAR_SUPPORT ) {
					int varIndex = (int)node.getData()[0].toIntValue();
					return prg.getVariables().getVariableName(varIndex);
				} else if( temp == StandardExtensions.EXTENSION_CONST_SUPPORT ) {
					ExpressionValue expr = node.getData()[0];
					if( expr.isFloat() ) {
						return Format.formatDouble(expr.toFloatValue(), 2);
					} else {
						return node.getData()[0].toStringValue();
					}
				} else if( node.getTemplate().getNodeType().isOperator() ){
					return node.getTemplate().getName();
				} else {
					return node.getTemplate().getName() + "()";	
				}
				
			}});
		
		vv.setVertexToolTipTransformer(new ToStringLabeller<ProgramNode>());
		
		vv.setVertexToolTipTransformer(new Transformer<ProgramNode,String>() {
			public String transform(ProgramNode node) {
				ProgramExtensionTemplate temp = node.getTemplate();
				if( temp == StandardExtensions.EXTENSION_CONST_SUPPORT ) {
					return node.getData()[0].toStringValue();
				} else {
					return null;
				}
			}});
		
		/*vv.setEdgeToolTipTransformer(new Transformer<DrawnConnection,String>() {
			public String transform(DrawnConnection edge) {
				return edge.getToolTip();
			}});*/
		
	
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
		this.setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse<ProgramNode,Integer>();
        vv.setGraphMouse(graphMouse);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
        vv.getRenderContext().setEdgeArrowPredicate(new Predicate(){
			@Override
			public boolean evaluate(Object arg0) {
				// TODO Auto-generated method stub
				return false;
			}});
        Predicate d;
        
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
	
	private void buildGraph(EncogProgram prg) {
	    ProgramNode root = prg.getRootNode();
	    graph.addVertex(root);
	    graphNode(root);
	}
	
	private void graphNode(ProgramNode parentNode) {
		for(int i=0;i<parentNode.getChildNodes().size();i++) {
			ProgramNode childNode  = (ProgramNode)parentNode.getChildNode(i); 
			graphNode(childNode);
			graph.addEdge(new Integer(edgeIndex++), parentNode, childNode);
		}
	}
	@Override
	public String getName() {
		return "Structure: " + this.getEncogObject().getName();
	}
}
