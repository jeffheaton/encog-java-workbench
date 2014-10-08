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

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.encog.util.logging.EncogFormatter;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.EncogOutputPanel;

public class WorkbenchLogHandler extends Handler {

	public WorkbenchLogHandler()
	{
		setFormatter(new EncogFormatter());
	}
	
	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(LogRecord rec) {
		String str = this.getFormatter().format(rec);
		EncogWorkBench.getInstance().output(str);
	}
}
