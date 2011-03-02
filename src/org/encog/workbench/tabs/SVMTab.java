/*
 * Encog(tm) Workbench v2.6 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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

import java.awt.FontMetrics;
import java.awt.Graphics;

import org.encog.engine.util.Format;
import org.encog.mathutil.libsvm.svm_model;
import org.encog.ml.svm.SVM;
import org.encog.persist.EncogPersistedObject;
import org.encog.util.HTMLReport;
import org.encog.workbench.util.EncogFonts;

public class SVMTab extends HTMLTab {

	private SVM network;
	
	public SVMTab(EncogPersistedObject encogObject) {
		super(encogObject);
		this.network = (SVM)encogObject;
		
		HTMLReport report = new HTMLReport();
		String title = "Support Vector Machine (SVM)";
		report.beginHTML();
		report.title(title);
		report.beginBody();
		report.h1(title);
		report.beginTable();
		SVM svm = (SVM)this.getEncogObject();
		report.tablePair("Name",this.getEncogObject().getName());
		report.tablePair("Input Count",""+svm.getInputCount());
		report.tablePair("SVM Type",svm.getSvmType().toString());
		report.tablePair("Kernel Type",svm.getKernelType().toString());
		report.endTable();
		report.endBody();
		report.endHTML();
		
		this.display(report.toString());
	}
	

}
