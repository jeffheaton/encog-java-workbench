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
package org.encog.workbench.dialogs.wizard.analyst;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import org.encog.app.analyst.AnalystGoal;
import org.encog.app.analyst.wizard.NormalizeRange;
import org.encog.app.analyst.wizard.WizardMethodType;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class RealTimeAnalystWizardDialog extends EncogPropertiesDialog {
	
	private BuildingListField sourceData;
	private final TextField baseName;
	private final ComboBoxField target;
	
	private final List<String> methods = new ArrayList<String>();
	
	public RealTimeAnalystWizardDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		
		List<String> targets = new ArrayList<String>();
		targets.add("Ninjatrader 7");
		targets.add("Metatrader 4 (MQL4)");
				
				
		this.setSize(640, 360);
		this.setTitle("Realtime Encog Analyst Wizard");
		
		beginTab("Data");
		addProperty(this.baseName = new TextField("ega file","EGA File Name",true));
		addProperty(this.target = new ComboBoxField("target","Target Platform",true,targets));
		addProperty(this.sourceData = new BuildingListField("source fields","Source Fields"));
						
		render();
		
		this.sourceData.getModel().addElement("HIGH");
		this.sourceData.getModel().addElement("LOW");
		this.sourceData.getModel().addElement("OPEN");
		this.sourceData.getModel().addElement("CLOSE");
		this.sourceData.getModel().addElement("VOL");
		((JComboBox)this.target.getField()).setSelectedIndex(0);
	}
	

	/**
	 * @return the egaFile
	 */
	public TextField getBaseName() {
		return baseName;
	}
	
	public List<String> getSourceData() {
		DefaultListModel ctrl = this.sourceData.getModel();
		List<String> result = new ArrayList<String>();
		for(int i=0; i<ctrl.getSize();i++)
		{
			result.add(ctrl.get(i).toString());
		}
		return result;
	}
	
	
}
