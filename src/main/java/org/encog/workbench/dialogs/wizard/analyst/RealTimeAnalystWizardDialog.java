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
package org.encog.workbench.dialogs.wizard.analyst;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.script.DataField;
import org.encog.app.analyst.wizard.PredictionType;
import org.encog.app.analyst.wizard.SourceElement;
import org.encog.app.generate.TargetLanguage;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.BuildingListListener;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class RealTimeAnalystWizardDialog extends EncogPropertiesDialog
		implements BuildingListListener {

	private BuildingListField sourceData;
	private final TextField baseName;
	private final ComboBoxField target;
	private final TextField predictionField;
	private final ComboBoxField prediction;
	private final IntegerField forwardWindow;
	private final IntegerField backwardWindow;

	private final List<String> methods = new ArrayList<String>();

	public RealTimeAnalystWizardDialog(File existingFile) {
		super(EncogWorkBench.getInstance().getMainWindow());

		List<String> targets = new ArrayList<String>();
		targets.add("Ninjatrader 7");
		targets.add("Metatrader 4 (MQL4)");

		List<String> predictionList = new ArrayList<String>();
		predictionList.add("Max Value (fieldmax)");
		predictionList.add("Max PIPs (fieldmaxpip)");

		this.setSize(640, 450);
		this.setTitle("Realtime Encog Analyst Wizard");

		beginTab("Data");
		addProperty(this.baseName = new TextField("ega file", "EGA File Name",
				true));
		addProperty(this.target = new ComboBoxField("target",
				"Target Platform", true, targets));
		addProperty(this.sourceData = new BuildingListField("source fields",
				"Source Fields"));

		addProperty(this.predictionField = new TextField("predict field",
				"Field (above) to predict", true));
		addProperty(this.prediction = new ComboBoxField("prediction",
				"Prediction Type", true, predictionList));
		addProperty(this.forwardWindow = new IntegerField("forward window",
				"Forward Window", true, 0, 10000));
		addProperty(this.backwardWindow = new IntegerField("backward window",
				"Backward Window", true, 0, 10000));

		this.sourceData.setOwner(this);

		render();

		this.sourceData.getModel().addElement("Name: time, Source: time");
		this.sourceData.getModel().addElement("Name: close, Source: Close[##]");
		this.predictionField.setValue("close");
		this.forwardWindow.setValue(60);
		this.backwardWindow.setValue(30);
		((JComboBox) this.prediction.getField()).setSelectedIndex(0);
		((JComboBox) this.target.getField()).setSelectedIndex(0);
		
		if( existingFile!=null ) {
			loadExistingFile(existingFile);
		}
	}

	private void loadExistingFile(File existingFile) {
		EncogAnalyst existingAnalyst = new EncogAnalyst();
		existingAnalyst.load(existingFile);
		this.sourceData.getModel().clear();
		for(DataField field : existingAnalyst.getScript().getFields()) {
			if( !field.getName().equalsIgnoreCase("prediction") ) {
				this.sourceData.getModel().addElement("Name: "+field.getName()+", Source: " + field.getSource());
			}
		}
	}

	/**
	 * @return the egaFile
	 */
	public TextField getBaseName() {
		return baseName;
	}

	public List<SourceElement> getSourceData() {
		DefaultListModel ctrl = this.sourceData.getModel();
		List<SourceElement> result = new ArrayList<SourceElement>();
		for (int i = 0; i < ctrl.getSize(); i++) {
			String current = this.sourceData.getModel().get(i).toString();
			int idx = current.indexOf(',');
			String currentName = current.substring(5, idx).trim();
			idx = current.indexOf("Source:");
			String currentSource = current.substring(idx + 7).trim();

			result.add(new SourceElement(currentName, currentSource));
		}
		return result;
	}

	private String askSource(int index) {
		IndicatorSourceDialog dialog = new IndicatorSourceDialog();

		if (index != -1) {
			String current = this.sourceData.getModel().get(index).toString();
			int idx = current.indexOf(',');
			String currentName = current.substring(5, idx).trim();
			idx = current.indexOf("Source:");
			String currentSource = current.substring(idx + 7).trim();
			dialog.getSourceName().setValue(currentName);
			dialog.getSource().setValue(currentSource);
		}

		if (dialog.process()) {

			return "Name: " + dialog.getSourceName().getValue() + ", Source: "
					+ dialog.getSource().getValue();
		} else {
			return null;
		}
	}

	@Override
	public void add(BuildingListField list, int index) {

		String str = askSource(-1);
		if (str != null) {
			list.getModel().addElement(str);
		}

	}

	@Override
	public void del(BuildingListField list, int index) {
		if (index != -1) {
			list.getModel().remove(index);
		}
	}

	@Override
	public void edit(BuildingListField list, int index) {
		if (index != -1) {
			String str = askSource(index);
			if (str != null) {
				list.getModel().remove(index);
				list.getModel().add(index, str);
			}
		}
	}

	public ComboBoxField getTarget() {
		return target;
	}

	public TextField getPredictionField() {
		return predictionField;
	}

	public PredictionType getPrediction() {
		switch (this.prediction.getSelectedIndex()) {
		case 0:
			return PredictionType.fieldmax;
		case 1:
			return PredictionType.fieldmaxpip;

		default:
			return PredictionType.fieldmaxpip;
		}
	}

	public IntegerField getForwardWindow() {
		return forwardWindow;
	}

	public IntegerField getBackwardWindow() {
		return backwardWindow;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setSourceData(BuildingListField sourceData) {
		this.sourceData = sourceData;
	}

	public TargetLanguage getTargetLanguage() {
		if( this.target.getSelectedIndex()==0) {
			return TargetLanguage.NinjaScript;
		} else {
			return TargetLanguage.MQL4;
		}
	}

}
