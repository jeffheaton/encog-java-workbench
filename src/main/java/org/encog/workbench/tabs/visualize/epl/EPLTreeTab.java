/*
 * Encog(tm) Workbench v3.2
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
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
import org.encog.ml.prg.extension.StandardExtensions;
import org.encog.ml.prg.util.MapProgram;
import org.encog.ml.prg.util.MappedNode;
import org.encog.workbench.tabs.EncogCommonTab;

import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
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

	private VisualizationViewer<MappedNode, Integer> vv;
	private Forest<MappedNode,Integer> graph;
	private int edgeIndex = 0;
	
	public EPLTreeTab(final EncogProgram prg) {
		super(null);
		
		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		// Graph<V, E> where V is the type of the vertices 
        // and E is the type of the edges
		this.graph = new DelegateForest<MappedNode, Integer>();

        buildGraph(prg);
        // Add some vertices. From above we defined these to be type Integer.
		// The Layout<V, E> is parameterized by the vertex and edge types
        TreeLayout<MappedNode,Integer> treeLayout = new TreeLayout<MappedNode,Integer>(graph);
        
        Transformer<MappedNode,Paint> vertexPaint = new Transformer<MappedNode,Paint>() {
			public Paint transform(MappedNode v) { return Color.white; }
		};
	

		//layout.setSize(new Dimension(5000,5000)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		//BasicVisualizationServer<DrawnNeuron, DrawnConnection> vv = new BasicVisualizationServer<DrawnNeuron, DrawnConnection>(
		//		layout);
		
		//Dimension d = new Dimension(600,600);
		
		vv =  new VisualizationViewer<MappedNode, Integer>(treeLayout, new Dimension(600,600));
		
		//vv.setPreferredSize(d); //Sets the viewing area size

		vv.getRenderer().getVertexLabelRenderer()
				.setPosition(Renderer.VertexLabel.Position.CNTR);
		vv.getRenderContext().setVertexLabelTransformer(new Transformer<MappedNode,String>(){

			@Override
			public String transform(MappedNode node) {
				int opcode = node.getTemplate().getOpcode();
				if( opcode == StandardExtensions.OPCODE_VAR ) {
					int varIndex = (int)node.getParam2(); 
					return prg.getVariables().getVariableName(varIndex);
				} else if( opcode == StandardExtensions.OPCODE_CONST_BOOL ) {
					return (node.getParam1()==0?"[F]":"[T]");
				} else if( opcode == StandardExtensions.OPCODE_CONST_FLOAT ) {
					double d = node.getConstValue().toFloatValue();
					return (""+prg.getContext().getFormat().format(d,1));
				} else if( opcode == StandardExtensions.OPCODE_CONST_INT ) {
					return (""+((int)node.getParam1()));
				} else if( node.getTemplate().isOperator() ){
					return node.getTemplate().getName() + "()";	
				} else {
					return node.getTemplate().getName();
				}
				
			}});
		
		vv.setVertexToolTipTransformer(new ToStringLabeller());
		
		/*vv.setVertexToolTipTransformer(new Transformer<DrawnNeuron,String>() {
			public String transform(DrawnNeuron edge) {
				return edge.getToolTip();
			}});
		
		vv.setEdgeToolTipTransformer(new Transformer<DrawnConnection,String>() {
			public String transform(DrawnConnection edge) {
				return edge.getToolTip();
			}});*/
		
	
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
		this.setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse();
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
	    MapProgram map = new MapProgram(prg);
	    MappedNode root = map.getRootNode();
	    graph.addVertex(root);
	    graphNode(root);
	}
	
	private void graphNode(MappedNode parentNode) {
		for( MappedNode childNode : parentNode.getChildren() ) {
			graphNode(childNode);
			graph.addEdge(new Integer(edgeIndex++), parentNode, childNode);
		}
	}
	@Override
	public String getName() {
		return "Structure: " + this.getEncogObject().getName();
	}
}
