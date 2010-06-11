/*
 * Encog(tm) Workbench v2.5
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
		if( str==null )
			return null;
		
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
		if( str==null )
			return null;
		
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
