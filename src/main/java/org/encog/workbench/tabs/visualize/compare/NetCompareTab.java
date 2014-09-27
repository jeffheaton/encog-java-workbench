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
package org.encog.workbench.tabs.visualize.compare;

import org.encog.ml.MLEncodable;
import org.encog.ml.MLMethod;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.HTMLReport;
import org.encog.workbench.WorkBenchError;
import org.encog.workbench.tabs.HTMLTab;

public class NetCompareTab extends HTMLTab {
	
	private MLEncodable network1;
	private MLEncodable network2;
	
	public NetCompareTab(MLMethod theNetwork1, MLMethod theNetwork2) {
		super(null);
		
		if( !(theNetwork1 instanceof MLEncodable) ||
			!(theNetwork2 instanceof MLEncodable) ) {
			throw new WorkBenchError("The networks must be an encodable type.");
		}
		
		this.network1 = (MLEncodable)theNetwork1;
		this.network2 = (MLEncodable)theNetwork2;
		
		if( this.network1.encodedArrayLength()!=
			this.network2.encodedArrayLength() ) {
			throw new WorkBenchError("The two networks must have the same number of weights to compare.");
		}
		
		generate();
	}
	
	public void generate() {
		HTMLReport report = new HTMLReport();
		
		report.h1("Neural Network Report");
		
		this.display(report.toString());
	}
	
	@Override
	public String getName() {
		return "Data Report";
	}

}
