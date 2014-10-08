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
package org.encog.workbench.util;

import org.encog.StatusReportable;
import org.encog.workbench.EncogWorkBench;

public class OutputStatusReportable implements StatusReportable {

	public void report(int total, int current, String message) {
		StringBuilder str = new StringBuilder();
		
		if( total==0 )
		{
			str.append(current);
		}
		else
		{
			str.append(current);
			str.append('/');
			str.append(total);
		}
		
		str.append(": ");
		str.append(message);
		
		EncogWorkBench.getInstance().outputLine(str.toString());
		
	}

	public void reportPhase(int arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

}
