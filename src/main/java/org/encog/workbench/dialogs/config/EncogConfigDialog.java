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
package org.encog.workbench.dialogs.config;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.FolderField;
import org.encog.workbench.dialogs.common.IntegerField;

public class EncogConfigDialog extends EncogPropertiesDialog {

	private DoubleField defaultError;
	private IntegerField threadCount;
	private CheckField useOpenCL;
	private ComboBoxField errorCalculation;
	private IntegerField trainingChartHistory;
	private IntegerField iterationStepCount;
	private CheckField displayTrainingImprovement;
	private CheckField allowConnections;
	private IntegerField port;
	private FolderField rootDirectory;
	
	public EncogConfigDialog(Frame owner) {
		super(owner);
		List<String> errorMethods = new ArrayList<String>();
		
		errorMethods.add("Root Mean Square");
		errorMethods.add("Mean Square Error");
		setTitle("Encog Configuration");
		setSize(500,300);
		beginTab("Training");
		addProperty(this.defaultError = new DoubleField("default error","Default Error Percent",true,0,100));
		addProperty(this.errorCalculation = new ComboBoxField("error calculation", "Error Calculation", true, errorMethods));
		addProperty(this.threadCount = new IntegerField("thread count","Thread Count (0=auto)",true,0,10000));
		addProperty(this.trainingChartHistory = new IntegerField("training historycount","Training Chart History (-1 = infinite)",true,-1,Integer.MAX_VALUE));
		addProperty(this.iterationStepCount = new IntegerField("step count","Iteration Step Count",true,1,1000));
		addProperty(this.displayTrainingImprovement = new CheckField("show improvement","Show Training Improvement"));
		//addProperty(this.useOpenCL = new CheckField("use opencl","Use Graphics Card(GPU)"));
		beginTab("Indicator");
		addProperty(this.allowConnections = new CheckField("open server", "Allow Connections"));
		addProperty(this.port = new IntegerField("server port", "Server Port",true,1,60000));
		beginTab("Paths");
		addProperty(this.rootDirectory = new FolderField("root dir","Root Project Path",true));
		render();
	}

	public DoubleField getDefaultError() {
		return defaultError;
	}

	public IntegerField getThreadCount() {
		return threadCount;
	}

	/*public CheckField getUseOpenCL() {
		return useOpenCL;
	}*/

	public ComboBoxField getErrorCalculation() {
		return errorCalculation;
	}

	public IntegerField getTrainingChartHistory() {
		return trainingChartHistory;
	}

	public IntegerField getIterationStepCount() {
		return iterationStepCount;
	}

	public CheckField getDisplayTrainingImprovement() {
		return displayTrainingImprovement;
	}
	
	

	public FolderField getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * @return the allowConnections
	 */
	public CheckField getAllowConnections() {
		return allowConnections;
	}

	/**
	 * @return the port
	 */
	public IntegerField getPort() {
		return port;
	}
}
