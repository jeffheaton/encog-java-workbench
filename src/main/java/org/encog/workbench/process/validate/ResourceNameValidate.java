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
package org.encog.workbench.process.validate;

import org.encog.workbench.EncogWorkBench;

public class ResourceNameValidate {
	
	final public static String VALID_SYMBOL = "-_";

	public static boolean hasInvalidChars(String name)
	{
		for(int i=0;i<name.length();i++)
		{
			char ch = name.charAt(i);
			
			if( !Character.isLetterOrDigit(ch) && VALID_SYMBOL.indexOf(ch)==-1 )
				return true;
		}
		
		return false;
	}
	
	public static String validateResourceName(String name)
	{
		/*if (EncogWorkBench.getInstance().getCurrentFile().find(
				name) != null) {
			return("That name is already in use, please choose another.");
		}*/
		
		if( hasInvalidChars(name) ) {
			return "That name has invalid chars, only use alphanumeric, dash or underbar.";
		}
		
		if( name.length()>64 ) {
			return "Name must be at most 64 characters.";
		}
		
		if( name.length()<2 ) {
			return "Name must be at least 2 characters.";
		}
		
		return null;
	}
}
