package org.encog.workbench.dialogs.layers;

import java.awt.Frame;
import java.awt.Window;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.networks.layers.Layer;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.activation.ActivationDialog;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.BuildingListListener;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.CheckListener;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.PopupField;
import org.encog.workbench.dialogs.common.PopupListener;
import org.encog.workbench.dialogs.common.TableField;

public class EditBasicLayer extends EncogPropertiesDialog implements
		PopupListener, CheckListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751974232848993273L;
	
	public final static String[] COLUMN_HEADS = {"Neuron","Threshold"};
	
	private IntegerField neuronCount;
	private PopupField activationField;
	private CheckField useThreshold;
	private TableField thresholdTable;
	
	private ActivationFunction activationFunction;

	public EditBasicLayer(Frame owner, Layer layer) {
		super(owner);
		setTitle("Edit Basic Layer");
		setSize(600, 400);
		setLocation(200, 200);
		addProperty(this.neuronCount = new IntegerField("neuron count",
				"Neuron Count", true, 1, -1));
		addProperty(this.activationField = new PopupField("activation",
				"Activation Function", true));
		addProperty(this.useThreshold = new CheckField("use threshold",
				"Use Threshold Values"));
		addProperty(this.thresholdTable = new TableField("threshold values", 
				"Threshold Values",true, 100, layer.getNeuronCount(), COLUMN_HEADS));
		this.useThreshold.setListener(this);
		render();
		setTableEditable();
	}

	public String popup(PopupField field) {
		ActivationDialog dialog = new ActivationDialog(this);
		dialog.setActivation(this.activationFunction);
		if( !dialog.process()  )
			return null;
		else
		{
			this.activationFunction = dialog.getActivation();
			return dialog.getActivation().getClass().getSimpleName();
		}
	}

	public PopupField getActivationField() {
		return activationField;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
		this.activationField.setValue(this.activationFunction.getClass().getSimpleName());
	}

	public IntegerField getNeuronCount() {
		return neuronCount;
	}

	public TableField getThresholdTable() {
		return thresholdTable;
	}

	public void check(CheckField check) {
		generateThresholdValues();
		setTableEditable();
	}

	public CheckField getUseThreshold() {
		return useThreshold;
	}
	
	public void generateThresholdValues()
	{
		for(int i=0;i<this.neuronCount.getValue();i++)
		{
			this.thresholdTable.setValue(i,0,"#"+(i+1));
			if( this.useThreshold.getValue() )
				this.thresholdTable.setValue(i,1,"0");
			else
				this.thresholdTable.setValue(i,1,"N/A");
		}
		this.thresholdTable.repaint();
	}
	
	public void setTableEditable()
	{
		this.thresholdTable.getModel().setEditable(0,false);
		this.thresholdTable.getModel().setEditable(1,true);
	}
	
	
}
