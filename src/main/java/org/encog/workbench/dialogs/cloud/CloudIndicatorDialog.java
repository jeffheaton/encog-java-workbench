/*
 * Encog(tm) Workbanch v3.1 - Java Version
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
package org.encog.workbench.dialogs.cloud;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;

public class CloudIndicatorDialog extends EncogPropertiesDialog {
	
	private BuildingListField sourceData;
		
	public CloudIndicatorDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
						
		this.setSize(640, 360);
		this.setTitle("Connect to External Indicator");
		
		addProperty(this.sourceData = new BuildingListField("source fields","Source Fields",250));
				
		render();
		
		this.sourceData.getModel().addElement("HIGH");
		this.sourceData.getModel().addElement("LOW");
		this.sourceData.getModel().addElement("OPEN");
		this.sourceData.getModel().addElement("CLOSE");
		this.sourceData.getModel().addElement("VOL");	
	}
		
	public List<String> getSourceData() {
		DefaultListModel<String> ctrl = this.sourceData.getModel();
		List<String> result = new ArrayList<String>();
		for(int i=0; i<ctrl.getSize();i++)
		{
			result.add(ctrl.get(i));
		}
		return result;
	}
	
	
}
