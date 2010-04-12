package org.encog.workbench.util;

public class SimpleEncrypt {
	
	private final static String hex = "0123456789ABCDEF";
	
	private static String toHex(int num)
	{
		StringBuilder result = new StringBuilder();
		int high = num/16;
		int low = num - (high*16);
		result.append(hex.charAt(high));
		result.append(hex.charAt(low));
		return result.toString();
	}
	
	public static String encode(String str)
	{
		StringBuilder result = new StringBuilder();
		
		for(int i=0;i<str.length();i++)
		{
			int ch=str.charAt(i);
			ch+=101;
			if( ch>255)
				ch-=255;
			result.append(toHex(ch));
			
		}
		
		return result.toString();
	}
	
	public static String decode(String str)
	{
		StringBuilder result = new StringBuilder();
		
		for(int i=0;i<str.length();i+=2)
		{
			int high = hex.indexOf(str.charAt(i));
			int low = hex.indexOf(str.charAt(i+1));
			int ch = (high*16) + low;
			ch-=101;
			if( ch<0 )
				ch+=255;
			
			result.append((char)ch);
		}
		
		return result.toString();
	}
	
}
