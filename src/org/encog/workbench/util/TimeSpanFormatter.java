package org.encog.workbench.util;

public class TimeSpanFormatter {

	public static final int SECONDS_IN_MINUTE = 60;

	public static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60;
	public static final int SECONDS_IN_DAY = SECONDS_IN_HOUR * 24;

	static public String formatDigit(final long d) {
		final StringBuilder result = new StringBuilder();
		if (d < 10) {
			result.append('0');
		}
		result.append(d);
		return result.toString();
	}

	static public String formatTime(long seconds) {
		final StringBuilder result = new StringBuilder();
		final long days = seconds / SECONDS_IN_DAY;
		seconds = seconds - days * SECONDS_IN_DAY;
		final long hours = seconds / SECONDS_IN_HOUR;
		seconds = seconds - hours * SECONDS_IN_HOUR;
		final long minutes = seconds / SECONDS_IN_MINUTE;
		seconds = seconds - minutes * SECONDS_IN_MINUTE;

		if (days > 0) {
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

	private TimeSpanFormatter() {
	}
}
