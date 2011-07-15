package org.encog.workbench;

import java.util.Date;

import org.encog.plugin.EncogPluginBase;
import org.encog.plugin.EncogPluginLogging1;
import org.encog.plugin.system.SystemLoggingPlugin;
import org.encog.util.logging.EncogLogging;

public class WorkbenchLogging implements EncogPluginLogging1 {

	 private int currentLevel = EncogLogging.LEVEL_DISABLE;
	
	@Override
	public int getPluginType() {
		return 1;
	}

	@Override
	public int getPluginServiceType() {
		return EncogPluginBase.TYPE_LOGGING;
	}

	@Override
	public String getPluginName() {
		return "HRI-System-Logging";
	}

	@Override
	public String getPluginDescription() {
		// TODO Auto-generated method stub
		return "Workbench logging";
	}

	@Override
	public int getLogLevel() {
		return currentLevel;
	}
	
	public void setLogLevel(int l) {
		this.currentLevel = l;
	}

	@Override
	public void log(int level, String message) {
		if (this.currentLevel <= level) {
			final Date now = new Date();
			final StringBuilder line = new StringBuilder();
			line.append(now.toString());
			line.append(" [");
			switch (level) {
			case EncogLogging.LEVEL_CRITICAL:
				line.append("CRITICAL");
				break;
			case EncogLogging.LEVEL_ERROR:
				line.append("ERROR");
				break;
			case EncogLogging.LEVEL_INFO:
				line.append("INFO");
				break;
			case EncogLogging.LEVEL_DEBUG:
				line.append("DEBUG");
				break;
			default:
				line.append("?");
				break;
			}
			line.append("][");
			line.append(Thread.currentThread().getName());
			line.append("]: ");
			line.append(message);

			EncogWorkBench.getInstance().outputLine(line.toString());
		}
		
	}

	@Override
	public void log(int level, Throwable t) {
		String str = SystemLoggingPlugin.getStackTrace(t);
		System.err.println(str);
		log(level, str);		
	}

}
