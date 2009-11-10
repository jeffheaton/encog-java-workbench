/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
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
			if( ch==File.pathSeparatorChar )
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
