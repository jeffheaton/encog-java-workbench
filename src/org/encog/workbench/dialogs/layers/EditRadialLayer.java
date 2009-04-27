package org.encog.workbench.dialogs.layers;

import java.awt.Frame;

import org.encog.neural.networks.layers.Layer;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TableField;

public class EditRadialLayer extends EncogPropertiesDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751974232848993273L;
	
	private final static String[] COLUMN_HEADS = {"Neuron","Center","Peak","Width"};
	
	private IntegerField neuronCount;
	private TableField radial;

	public EditRadialLayer(Frame owner, Layer layer) {
		super(owner);
		setTitle("Edit Radial Basis Layer");
		setSize(600, 400);
		setLocation(200, 200);
		addProperty(this.neuronCount = new IntegerField("neuron count",
			"Neuron Count", true, 1, -1));
		addProperty(this.radial = new TableField("radial basis functions",
			"Radial Basis Functions",true,100,layer.getNeuronCount(),COLUMN_HEADS));
		
		render();
	}

	
	public IntegerField getNeuronCount() {
		return neuronCount;
	}


	public TableField getRadial() {
		return radial;
	}

	
	
	
}

