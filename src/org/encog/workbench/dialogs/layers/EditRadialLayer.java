package org.encog.workbench.dialogs.layers;

import java.awt.Frame;

import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.layers.RadialBasisFunctionLayer;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.ChartField;
import org.encog.workbench.dialogs.common.ChartListener;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TableField;

public class EditRadialLayer extends EncogPropertiesDialog implements ChartListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751974232848993273L;
	
	private final static String[] COLUMN_HEADS = {"Neuron","Center","Peak","Width"};
	
	private ChartField chart;
	private IntegerField neuronCount;
	private TableField radial;

	public EditRadialLayer(Frame owner, RadialBasisFunctionLayer layer) {
		super(owner);
		setTitle("Edit Radial Basis Layer");
		setSize(600, 400);
		setLocation(200, 200);
		addProperty(this.chart = new ChartField("chart",new RBFChartGenerator(this),200));
		addProperty(this.neuronCount = new IntegerField("neuron count",
			"Neuron Count", true, 1, -1));
		addProperty(this.radial = new TableField("radial basis functions",
			"Radial Basis Functions",true,100,layer.getNeuronCount(),COLUMN_HEADS));
		this.chart.setListener(this);
		render();
	}

	
	public IntegerField getNeuronCount() {
		return neuronCount;
	}


	public TableField getRadial() {
		return radial;
	}


	public void refresh(ChartField chart) {
		chart.refresh();
	}


	public ChartField getChart() {
		return this.chart;
	}

	
	
	
}

