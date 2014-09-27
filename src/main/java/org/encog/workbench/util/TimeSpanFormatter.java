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
