package org.encog.workbench.util;

public abstract class TaskComplete {
	
	protected Object[] params;
	
	public TaskComplete(Object[] params)
	{
		this.params = params;
	}
	
	public abstract void complete();
}
