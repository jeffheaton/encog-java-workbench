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
