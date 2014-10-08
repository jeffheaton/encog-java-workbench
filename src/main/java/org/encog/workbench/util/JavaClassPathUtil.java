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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class JavaClassPathUtil {

	public static String generateClassPath(final char sep, final File jarDir) {
		final StringBuilder builder = new StringBuilder();
		final File[] contents = jarDir.listFiles();
		for (final File entry : contents) {
			if (entry.isFile()) {
				String str = entry.toString();
				final int idx = str.indexOf("jar");

				if (idx != -1) {
					str = str.substring(idx);
				}
				if (builder.length() > 0) {
					builder.append(sep);
				}
				
				if( sep==':' )
					str = adjustDirSymbol(str,'/');
				else
					str = adjustDirSymbol(str,'\\');
				builder.append(str);
			}
		}

		return builder.toString();
	}
	
	private static String adjustDirSymbol(String str, char replace) {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i<str.length(); i++)
		{
			char ch = str.charAt(i);
			if( ch==File.separatorChar )
				ch = replace;
			result.append(ch);
		}
		return result.toString();
	}

	public static void main(final String args[]) {
		if (args.length != 1) {
			System.out.println("Please pass the workbench directory as arg 1");
		} else {
			try {
				// generate the directories
				final File baseDir = new File(args[0]);
				final File jarDir = new File(baseDir, "jar");

				// build the batch command
				final StringBuilder batCommand = new StringBuilder();
				batCommand.append("start javaw -classpath ");
				batCommand.append(JavaClassPathUtil.generateClassPath(';', jarDir));
				batCommand.append(" org.encog.workbench.EncogWorkBench");

				PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(new File(baseDir, "workbench.bat"))));
				out.println(batCommand);
				out.close();

				// build the shell command
				final StringBuilder shCommand = new StringBuilder();
				shCommand.append("java -classpath ");
				shCommand.append(JavaClassPathUtil.generateClassPath(':', jarDir));
				shCommand.append(" org.encog.workbench.EncogWorkBench");

				out = new PrintWriter(new BufferedWriter(new FileWriter(
						new File(baseDir, "workbench.sh"))));
				out.println(shCommand);
				out.close();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
}
