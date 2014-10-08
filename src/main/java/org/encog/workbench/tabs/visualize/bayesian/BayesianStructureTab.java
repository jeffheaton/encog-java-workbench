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
package org.encog.workbench.tabs.visualize.bayesian;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.commons.collections15.Transformer;
import org.encog.ml.bayesian.BayesianEvent;
import org.encog.ml.bayesian.BayesianNetwork;
import org.encog.workbench.tabs.EncogCommonTab;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
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
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer.InsidePositioner;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.util.Animator;

public class BayesianStructureTab extends EncogCommonTab {

	private VisualizationViewer<DrawnEvent, DrawnEventConnection> vv;
	private Graph<DrawnEvent, DrawnEventConnection> graph;
	
	private final class LayoutChooser implements ActionListener
    {
        private final JComboBox jcb;
        private final VisualizationViewer<DrawnEvent,DrawnEventConnection> vv;

        private LayoutChooser(JComboBox jcb, VisualizationViewer<DrawnEvent,DrawnEventConnection> vv)
        {
            super();
            this.jcb = jcb;
            this.vv = vv;
        }

        public void actionPerformed(ActionEvent arg0)
        {
            Object[] constructorArgs =
                { graph };

            Class<? extends Layout<Integer,Number>> layoutC = 
                (Class<? extends Layout<Integer,Number>>) jcb.getSelectedItem();
//            Class lay = layoutC;
            try
            {
                Constructor<? extends Layout<Integer, Number>> constructor = layoutC
                        .getConstructor(new Class[] {Graph.class});
                Object o = constructor.newInstance(constructorArgs);
                Layout<DrawnEvent,DrawnEventConnection> l = (Layout<DrawnEvent,DrawnEventConnection>) o;
                l.setInitializer(vv.getGraphLayout());
                l.setSize(vv.getSize());
                
				LayoutTransition<DrawnEvent,DrawnEventConnection> lt =
					new LayoutTransition<DrawnEvent,DrawnEventConnection>(vv, vv.getGraphLayout(), l);
				Animator animator = new Animator(lt);
				animator.start();
				vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
				vv.repaint();
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
	
    private static Class<? extends Layout>[] getCombos()
    {
        List<Class<? extends Layout>> layouts = new ArrayList<Class<? extends Layout>>();
        layouts.add(KKLayout.class);
        layouts.add(FRLayout.class);
        layouts.add(CircleLayout.class);
        layouts.add(SpringLayout.class);
        layouts.add(SpringLayout2.class);
        layouts.add(ISOMLayout.class);
        return layouts.toArray(new Class[0]);
    }

	
	public BayesianStructureTab(BayesianNetwork method) {
		super(null);
		
		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		this.graph = buildGraph(method);
				
		Layout<DrawnEvent, DrawnEventConnection> layout = new KKLayout(graph);
		
		vv =  new VisualizationViewer<DrawnEvent, DrawnEventConnection>(layout);
		
		vv.getRenderer().getVertexLabelRenderer()
				.setPosition(Renderer.VertexLabel.Position.CNTR);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<DrawnEvent>());
        vv.getRenderer().getVertexLabelRenderer().setPositioner(new InsidePositioner());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.N);
		
		vv.setVertexToolTipTransformer(new Transformer<DrawnEvent,String>() {
			public String transform(DrawnEvent edge) {
				return edge.getToolTip();
			}});
		
		Transformer<DrawnEvent, Paint> vertexPaint = new Transformer<DrawnEvent, Paint>() {
			public Paint transform(DrawnEvent neuron) {
				return Color.white;
			}
		};
		
		
		
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
		this.setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);
        
        vv.addKeyListener(graphMouse.getModeKeyListener());
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        final ScalingControl scaler = new CrossoverScalingControl();     
        
        Class[] combos = getCombos();
        final JComboBox jcb = new JComboBox(combos);
        // use a renderer to shorten the layout name presentation
        jcb.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String valueString = value.toString();
                valueString = valueString.substring(valueString.lastIndexOf('.')+1);
                return super.getListCellRendererComponent(list, valueString, index, isSelected,
                        cellHasFocus);
            }
        });
        jcb.addActionListener(new LayoutChooser(jcb, vv));
        jcb.setSelectedItem(FRLayout.class);
        

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
        
        JComboBox modeBox = graphMouse.getModeComboBox();
        	modeBox.addItemListener(((DefaultModalGraphMouse<Integer,Number>)vv.getGraphMouse()).getModeListener());

        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout(FlowLayout.LEFT));        
        controls.add(plus);
        controls.add(minus);
        controls.add(reset);
        controls.add(modeBox);
        controls.add(jcb);
        Border border = BorderFactory.createEtchedBorder();
        controls.setBorder(border);
        add(controls, BorderLayout.NORTH);
        
        
	}

	private Graph<DrawnEvent, DrawnEventConnection> buildGraph(BayesianNetwork network) {
		
		List<DrawnEvent> events = new ArrayList<DrawnEvent>();
		Graph<DrawnEvent, DrawnEventConnection> result = new SparseMultigraph<DrawnEvent, DrawnEventConnection>();
		List<DrawnEventConnection> connections = new ArrayList<DrawnEventConnection>();
		Map<BayesianEvent,DrawnEvent> eventMap = new HashMap<BayesianEvent,DrawnEvent>();
	
		// place the events
		for(BayesianEvent event : network.getEvents() ) {
			DrawnEvent drawnEvent = new DrawnEvent(event);
			events.add(drawnEvent);
			eventMap.put(event, drawnEvent);
		}
		
	
		// place all the connections
		for(BayesianEvent event : network.getEvents() ) {
			for(BayesianEvent childEvent: event.getChildren() ) {
				DrawnEvent fromEvent = eventMap.get(event);
				DrawnEvent toEvent = eventMap.get(childEvent);
				if( fromEvent==null || toEvent==null ) 
					continue;
				DrawnEventConnection connection = new DrawnEventConnection(fromEvent,toEvent);
				fromEvent.getOutbound().add(connection);
				toEvent.getInbound().add(connection);
			}
		}

		
		
		for (DrawnEvent event : events) {
			result.addVertex(event);
			for (DrawnEventConnection connection : event.getOutbound()) {
				result.addEdge(connection, connection.getFrom(),
						connection.getTo(), EdgeType.DIRECTED);
			}
		}

		return result;
	}

	
	@Override
	public String getName() {
		return "Structure: " + this.getEncogObject().getName();
	}
}
