package org.encog.workbench.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
		File pathICO = new File(utilDir,"");
		scanJARs(new File(stageDir,"jar"));
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(stageDir,"launch.xml"))));
		
		out.println("<launch4jConfig>\n");
		out.println("<dontWrapJar>true</dontWrapJar>\n");
		out.println("<headerType>gui</headerType>\n");
		out.println("<jar>"+this.mainJar+"</jar>\n");
		out.println("<outfile>"+pathEXE+"</outfile>\n");
		out.println("<errTitle></errTitle>\n");
		out.println("<cmdLine></cmdLine>\n");
		out.println("<chdir></chdir>\n");
		out.println("<priority>normal</priority>\n");
		out.println("<downloadUrl>http://java.com/download</downloadUrl>\n");
		out.println("<supportUrl></supportUrl>\n");
		out.println("<customProcName>false</customProcName>\n");
		out.println("<stayAlive>false</stayAlive>\n");
		out.println("<manifest></manifest>\n");
		out.println("<icon>"+pathICO+"</icon>\n");
		out.println("<classPath>\n");
		out.println("<mainClass>org.encog.workbench.EncogWorkBench</mainClass>\n");
		for(String cp: this.classpath)
		{
			out.println("<cp>"+cp+"</cp>\n");
		}
		out.println("</classPath>\n");
		out.println("<jre>\n");
		out.println("<path></path>\n");
		out.println("<minVersion>1.5.0</minVersion>\n");
		out.println("<maxVersion></maxVersion>\n");
		out.println("<jdkPreference>preferJre</jdkPreference>\n");
		out.println("</jre>\n");
		out.println("<txtFileVersion>1.0</txtFileVersion>\n");
		out.println("<fileDescription>Encog Workbench</fileDescription>\n");
		out.println("<copyright>Copyright 2008 by Heaton Research, Inc.</copyright>\n");
		out.println("<productVersion>1.0.0.0</productVersion>\n");
		out.println("<txtProductVersion>1.0</txtProductVersion>\n");
		out.println("<productName>Encog Workbench</productName>\n");
		out.println("<companyName>Heaton Research, Inc.</companyName>\n");
		out.println("<internalName>Encog Workbench</internalName>\n");
		out.println("<originalFilename>EncogWorkbench.exe</originalFilename>\n");
		out.println("</versionInfo>\n");
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
