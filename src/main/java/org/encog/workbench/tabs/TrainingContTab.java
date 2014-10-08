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
package org.encog.workbench.tabs;

import org.encog.neural.networks.training.propagation.TrainingContinuation;
import org.encog.util.HTMLReport;
import org.encog.workbench.frames.document.tree.ProjectEGFile;

public class TrainingContTab extends HTMLTab {

	public TrainingContTab(ProjectEGFile encogObject) {
		super(encogObject);
		HTMLReport report = new HTMLReport();
		report.beginHTML();
		String title = "Encog Training Continuation";
		report.title(title);
		report.beginBody();
		report.h1(title);
		report.para("This is an Encog training continuation object.  It allows training to begin exactly where it left off.  When a neural network, or other machine learning method, is trained certian data must be kept to track the training progress.  Think of this as the notes you take in a class.  Without this training data Encog will lose some ground when training is continued. This file will be used automatically if training can be continued. ");
		report.para("However, the neural network does not need this data to function.  Once training is done, this file can be deleted.");
		report.para("Data designed for: " + ((TrainingContinuation)encogObject.getObject()).getTrainingType() );
		report.endBody();
		report.endHTML();
		this.display(report.toString());
		
	}
	
	@Override
	public String getName() {
		return "Unknown :" + this.getEncogObject().getName();
	}

}
