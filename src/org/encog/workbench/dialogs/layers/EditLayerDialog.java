/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

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
				"Neuron Count", true, 1, 100000));
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
