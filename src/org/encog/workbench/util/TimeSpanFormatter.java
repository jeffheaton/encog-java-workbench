package org.encog.workbench.util;

public class TimeSpanFormatter {
	
	private TimeSpanFormatter()
	{		
	}
	
	public static final int SECONDS_IN_MINUTE = 60;
	public static final int SECONDS_IN_HOUR = (SECONDS_IN_MINUTE*60);
	public static final int SECONDS_IN_DAY = (SECONDS_IN_HOUR*24);
	
	static public String formatTime(long seconds)
	{
		StringBuilder result = new StringBuilder();
		long days = seconds/SECONDS_IN_DAY;
		seconds = seconds - (days*SECONDS_IN_DAY);
		long hours = seconds/SECONDS_IN_HOUR;
		seconds = seconds - (hours*SECONDS_IN_HOUR);
		long minutes = seconds/SECONDS_IN_MINUTE;
		seconds = seconds - (minutes*SECONDS_IN_MINUTE);
		
		if( days>0 )
		{
			result.append(days);
			result.append(" days, ");
		}
		
		result.append(formatDigit(hours));
		result.append(':');
		result.append(formatDigit(minutes));
		result.append(':');
		result.append(formatDigit(seconds));
			
		return result.toString();
	}
	
	static public String formatDigit(long d)
	{
		StringBuilder result = new StringBuilder();
		if( d<10 )
		{
			result.append('0');
		}
		result.append(d);
		return result.toString();
	}
}
