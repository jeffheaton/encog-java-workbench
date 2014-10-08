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
