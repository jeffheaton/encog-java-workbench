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
package org.encog.workbench;

import org.encog.script.EncogScript;
import org.encog.script.EncogScriptError;
import org.encog.script.EncogScriptRuntimeError;
import org.encog.workbench.util.WorkbenchConsoleInputOutput;

public class ExecuteScript implements Runnable {
	private EncogScript currentScript;

	public void run() {
		
		try
		{
			this.currentScript.run(new WorkbenchConsoleInputOutput());
		}
		catch(EncogScriptRuntimeError ex)
		{
			EncogWorkBench.getInstance().output(ex.getMessage()+"\n");
		}
		catch(EncogScriptError ex)
		{
			EncogWorkBench.displayError("Error",ex);
		}
		catch(Throwable t)
		{
			EncogWorkBench.displayError("Error",t);
		}		
		finally {
			this.currentScript = null;
		}
	}
	
	public void execute(EncogScript script)
	{
		if( this.currentScript!=null )
		{
			throw new WorkBenchError("Can't execute script now, another is already running.\nPlease stop the other script first.");
		}
		
		this.currentScript = script;
		Thread thread = new Thread(this);
		thread.start();
	}
}
