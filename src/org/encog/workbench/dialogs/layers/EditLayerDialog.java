package org.encog.workbench.dialogs.layers;

import java.awt.Frame;

import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.BuildingListListener;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;

public class EditLayerDialog extends EncogPropertiesDialog implements BuildingListListener {

	private BuildingListField tags;
	private IntegerField neuronCount;
	
	public EditLayerDialog(Frame owner) {
		super(owner);
		addProperty(this.neuronCount = new IntegerField("neuron count",
				"Neuron Count", true, 1, -1));
		addProperty(this.tags = new BuildingListField("tags",
		"Tags"));
	}
	
	public void add(BuildingListField list, int index) {
		AddEditTagDialog dialog = new AddEditTagDialog(this);
		if( dialog.process() )
		{
			String tag = dialog.getTag().getSelectedValue().toString();
			if( !list.getModel().contains(tag) )
				list.getModel().addElement(tag);
		}
		
	}

	public void del(BuildingListField list, int index) {
		if (index != -1) {
			list.getModel().remove(index);
		}
		
	}

	public void edit(BuildingListField list, int index) {
		
		AddEditTagDialog dialog = new AddEditTagDialog(this);
		if( dialog.process() )
		{
			// delete
			if (index != -1) {
				list.getModel().remove(index);
			}
			
			// add
			String tag = dialog.getTag().getSelectedValue().toString();
			if( !list.getModel().contains(tag) )
				list.getModel().addElement(tag);
		}
		
	}

	public BuildingListField getTags() {
		return tags;
	}

	public IntegerField getNeuronCount() {
		return neuronCount;
	}
	
	
	

}
