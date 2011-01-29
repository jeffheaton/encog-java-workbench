package org.encog.workbench.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtil {
	public static String getFileName(File file)
	{
		String fileName = file.toString();
		int mid= fileName.lastIndexOf(".");
	    return fileName.substring(0,mid); 
	}
	
	public static String getFileExt(File file)
	{
		String fileName = file.toString();
		int mid= fileName.lastIndexOf(".");
	    return fileName.substring(mid+1,fileName.length()); 
	}
	
	 public static String readFileAsString(File filePath)
	    throws java.io.IOException{
	        StringBuffer fileData = new StringBuffer(1000);
	        BufferedReader reader = new BufferedReader(
	                new FileReader(filePath));
	        char[] buf = new char[1024];
	        int numRead=0;
	        while((numRead=reader.read(buf)) != -1){
	            String readData = String.valueOf(buf, 0, numRead);
	            fileData.append(readData);
	            buf = new char[1024];
	        }
	        reader.close();
	        return fileData.toString();
	    }


}
