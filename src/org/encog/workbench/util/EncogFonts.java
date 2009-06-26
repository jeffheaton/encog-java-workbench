/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
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
 */

package org.encog.workbench.util;

import java.awt.Font;

/**
 * Define the fonts used by Encog.
 * @author jheaton
 */
public class EncogFonts {
	
	private static EncogFonts instance;
	private Font codeFont;
	private Font bodyFont;
	private Font headFont;
	private Font titleFont;
	
	private EncogFonts()
	{		
		this.codeFont = new Font("monospaced", 0, 12);
		this.bodyFont = new Font("serif", 0, 12);
		this.headFont = new Font("serif", Font.BOLD, 12);
		this.titleFont = new Font("serif", Font.BOLD, 16);
	}
	
	public static EncogFonts getInstance()
	{
		if( instance==null )
			instance = new EncogFonts();
		return instance;
	}

	/**
	 * @return the codeFont
	 */
	public Font getCodeFont() {
		return codeFont;
	}

	/**
	 * @return the bodyFont
	 */
	public Font getBodyFont() {
		return bodyFont;
	}

	/**
	 * @return the headFont
	 */
	public Font getHeadFont() {
		return headFont;
	}

	/**
	 * @return the titleFont
	 */
	public Font getTitleFont() {
		return titleFont;
	}
	
	
	
}
