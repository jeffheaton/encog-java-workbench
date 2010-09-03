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
		if (EncogWorkBench.getInstance().getCurrentFile().find(
				name) != null) {
			return("That name is already in use, please choose another.");
		}
		
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
