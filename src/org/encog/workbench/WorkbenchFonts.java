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
