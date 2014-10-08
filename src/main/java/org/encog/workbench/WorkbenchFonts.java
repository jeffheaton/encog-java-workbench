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
package org.encog.workbench;

import java.awt.Font;

public class WorkbenchFonts {
	
	private static Font title1Font;
	private static Font title2Font;
	private static Font textFont;
	
	private static void createFonts()
	{
		if( title1Font==null )
		{
			title1Font = new Font("Serif", Font.BOLD, 16);
			title2Font = new Font("Serif",Font.BOLD,14);
			textFont = new Font("SansSerif",0,10);
		}
	}
	
	public static Font getTitle1Font()
	{
		createFonts();
		return title1Font;
	}
	
	public static Font getTitle2Font()
	{
		createFonts();
		return title2Font;
	}
	
	public static Font getTextFont()
	{
		createFonts();
		return textFont;
	}
	
}
