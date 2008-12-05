/*
 * Encog Workbench v1.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
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
