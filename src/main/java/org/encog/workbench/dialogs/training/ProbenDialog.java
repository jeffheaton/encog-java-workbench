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
package org.encog.workbench.dialogs.training;

import java.awt.TextArea;

import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.factory.MLTrainFactory;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TextAreaField;
import org.encog.workbench.dialogs.common.TextField;

/**
 * Basic dialog box that displays two combo boxes used to select
 * the training set and network to be used.  Subclasses can
 * add additional fields.  This class is based on the Encog
 * common dialog box.
 * @author jheaton
 */
public class ProbenDialog extends EncogPropertiesDialog {
	
	private TextField methodName;
	private TextAreaField methodArchitecture;
	private TextField trainingName;
	private TextAreaField trainingArgs;

	/**
	 * Construct the dialog box.
	 * @param owner The owner of the dialog box.
	 */
	public ProbenDialog() {
		
		super(EncogWorkBench.getInstance().getMainWindow());
		setSize(400,400);
		setLocation(200,200);
		
		addProperty(this.methodName = new TextField("method name", "Method Name", true));
		addProperty(this.methodArchitecture = new TextAreaField("architecture", "Method Architecture", false));
		addProperty(this.trainingName = new TextField("training name", "Method Name", true));
		addProperty(this.trainingArgs = new TextAreaField("training args", "Training Args", false));
		render();
		
		this.methodName.setValue(MLMethodFactory.TYPE_FEEDFORWARD);
		this.trainingName.setValue(MLTrainFactory.TYPE_RPROP);
		this.methodArchitecture.setValue("?:B->SIGMOID->40:B->SIGMOID->?");		

	}
	

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName.getValue();
	}

	/**
	 * @return the methodArchitecture
	 */
	public String getMethodArchitecture() {
		return methodArchitecture.getValue();
	}

	/**
	 * @return the trainingName
	 */
	public String getTrainingName() {
		return trainingName.getValue();
	}

	/**
	 * @return the trainingArgs
	 */
	public String getTrainingArgs() {
		return trainingArgs.getValue();
	}
	
	

}
