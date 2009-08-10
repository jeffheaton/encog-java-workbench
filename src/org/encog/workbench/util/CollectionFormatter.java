package org.encog.workbench.util;

import java.util.Collection;

/**
 * A class that contains collection formatting utilities.
 *
 */
public class CollectionFormatter {
	
	/**
	 * Private constructor
	 */
	private CollectionFormatter()
	{
		
	}
	
	/**
	 * Format a collection as a string with commas.
	 * @param collection The collection to format.
	 * @return The collection formatted with commas.
	 */
	public static String formatCollection(Collection<?> collection)
	{
		StringBuilder result = new StringBuilder();
		for(Object obj: collection)
		{
			if( result.length()!=0)
				result.append(',');
			result.append(obj.toString());
		}
		return result.toString();
	}
}
