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
