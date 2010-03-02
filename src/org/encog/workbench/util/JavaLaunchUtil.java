/*
 * Encog(tm) Workbench v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.encog.Encog;

public class JavaLaunchUtil {

	private List<String> classpath = new ArrayList<String>();
	private String mainJar;
	
	public void scanJARs(File jarDir)
	{
		File[] list = jarDir.listFiles();
		for(int i=0;i<list.length;i++)
		{
			File f = list[i];
			if( f.isFile() )
			{
				String str = f.toString();
				final int idx = str.indexOf("jar");

				if (idx != -1) {
					str = str.substring(idx);
				}
				classpath.add(str);
				if( str.indexOf("encog-workbench")!=-1 )
				{
					mainJar = str;
				}
			}
		}
	}
	
	public void writeLaunchConfig(File stageDir, File utilDir) throws IOException
	{
		File pathEXE = new File(stageDir,"EncogWorkbench.exe");
		File pathICO = new File(utilDir,"Encog.ico");
		scanJARs(new File(stageDir,"jar"));
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(utilDir,"launch.xml"))));
		
		out.println("<launch4jConfig>");
		out.println("<dontWrapJar>true</dontWrapJar>");
		out.println("<headerType>gui</headerType>");
		out.println("<jar>"+this.mainJar+"</jar>");
		out.println("<outfile>"+pathEXE+"</outfile>");
		out.println("<errTitle></errTitle>");
		out.println("<cmdLine></cmdLine>");
		out.println("<chdir></chdir>");
		out.println("<priority>normal</priority>");
		out.println("<downloadUrl>http://java.com/download</downloadUrl>");
		out.println("<supportUrl></supportUrl>");
		out.println("<customProcName>false</customProcName>");
		out.println("<stayAlive>false</stayAlive>");
		out.println("<manifest></manifest>");
		out.println("<icon>"+pathICO+"</icon>");
		out.println("<classPath>");
		out.println("<mainClass>org.encog.workbench.EncogWorkBench</mainClass>");
		for(String cp: this.classpath)
		{
			out.println("<cp>"+cp+"</cp>");
		}
		out.println("</classPath>");
		out.println("<jre>");
		out.println("<path></path>");
		out.println("<minVersion>1.5.0</minVersion>");
		out.println("<maxVersion></maxVersion>");
		out.println("<jdkPreference>preferJre</jdkPreference>");
		out.println("</jre>");
		String version = Encog.VERSION + ".0";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		out.println("<versionInfo>");
		out.println("<fileVersion>"+version+"</fileVersion>");
		out.println("<txtFileVersion>"+version+"</txtFileVersion>");
		out.println("<fileDescription>Encog Workbench</fileDescription>");
		out.println("<copyright>Copyright "+year+" by Heaton Research, Inc.</copyright>");
		out.println("<productVersion>"+version+"</productVersion>");
		out.println("<txtProductVersion>"+version+"</txtProductVersion>");
		out.println("<productName>Encog Workbench</productName>");
		out.println("<companyName>Heaton Research, Inc.</companyName>\n");
		out.println("<internalName>Encog Workbench</internalName>");
		out.println("<originalFilename>EncogWorkbench.exe</originalFilename>");
		out.println("</versionInfo>");
		out.println("</launch4jConfig>\n");
		out.close();
	}
	
	public static void main(String args[]) throws IOException
	{
		JavaLaunchUtil utl = new JavaLaunchUtil();
		if( args.length==2 )
		{
			File stageDir = new File(args[0]);
			File utilDir = new File(args[1]);
			utl.writeLaunchConfig(stageDir, utilDir);
		}
		else
		{
			System.err.println("Usage: [stage dir] [util dir]");
		}
	}
}
