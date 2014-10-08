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
package org.encog.workbench.dialogs.visualize.scatter;

import java.util.ArrayList;
import java.util.List;

import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.script.normalize.AnalystField;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.CheckListener;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.frames.document.tree.ProjectTraining;

/**
 * Basic dialog box that displays two combo boxes used to select
 * the training set and network to be used.  Subclasses can
 * add additional fields.  This class is based on the Encog
 * common dialog box.
 * @author jheaton
 */
public class ScatterChooseClass extends EncogPropertiesDialog implements CheckListener {

	private ComboBoxField comboClass;
	private List<CheckField> fields = new ArrayList<CheckField>();
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 3506669325409959724L;

	/**
	 * All available training sets to display in the combo box.
	 */
	private List<ProjectTraining> trainingSets;
	
	/**
	 * All available networks to display in the combo box.
	 */
	private List<String> classNames = new ArrayList<String>();
	
	private CheckField selectAllField;

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public ScatterChooseClass(EncogAnalyst analyst) {
		
		super(EncogWorkBench.getInstance().getMainWindow());
		setTitle("Create Scatter Plot");
		setSize(400,400);
		setLocation(200,200);
		
		
		
		for(AnalystField field: analyst.getScript().getNormalize().getNormalizedFields() ) {
			if( !field.isIgnored() && field.isOutput() ) {
				this.classNames.add(field.getName());
			}
		}
		
		addProperty(this.comboClass = new ComboBoxField("training set","Training Set",true,this.classNames));
		
		for(AnalystField field: analyst.getScript().getNormalize().getNormalizedFields() ) {
			if( !field.isIgnored() && field.isInput() ) {			
				CheckField cf = new CheckField(field.getName(),field.getName());
				addProperty(cf);
				fields.add(cf);
				cf.setListener(this);
			}
		}
		
		addProperty(selectAllField = new CheckField("all", "Select All"));
		this.selectAllField.setListener(this);
		
		render();
		
	}

	public String getClassName() {
		return (String)this.comboClass.getSelectedValue();
	}
	
	public List<String> getAxis() {		
		List<String> result = new ArrayList<String>();
		
		for(CheckField check: this.fields) {
			if( check.getValue() )
				result.add(check.getName());
		}
		
		return result;
	}

	@Override
	public void check(CheckField check) {
		if( check==this.selectAllField ) {
			boolean v = this.selectAllField.getValue();
			for(CheckField field : this.fields ) {
				field.setValue(v);
			}
		} else {
			boolean v = true;
			for(CheckField field : this.fields ) {
				if( !field.getValue() ) {
					v = false;
					break;
				}
			}
			this.selectAllField.setValue(v);
		}
		
	}

}
