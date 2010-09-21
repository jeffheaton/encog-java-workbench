/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.tabs;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.encog.Encog;
import org.encog.persist.EncogPersistedObject;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.EncogFonts;

public class AboutTab extends EncogCommonTab {
	
	/**
	 * JAR files currently in use.
	 */
	private final List<String> jars = new ArrayList<String>();

	public AboutTab() {
		super(null);
		
		setPreferredSize(new Dimension(800, 3000));
		
		final String path = System.getProperty("java.class.path");
		final StringTokenizer tok = new StringTokenizer(path, ""
				+ File.pathSeparatorChar);
		while (tok.hasMoreTokens()) {
			final String jarPath = tok.nextToken();
			final File jarFile = new File(jarPath);
			if (jarFile.isFile()) {
				try {
					final JarFile jarData = new JarFile(jarFile);
					final Manifest manifest = jarData.getManifest();
					this.jars.add(jarFile.getName() + " (" + jarFile + ")");

				} catch (final IOException e) {
					// ignore the JAR
				}
			}
		}
	}
	
	/**
	 * Paint the panel.
	 * @param g The graphics object to use.
	 */
	public void paint(final Graphics g) {
		super.paint(g);
		final FontMetrics fm = g.getFontMetrics();
		g.setFont(EncogFonts.getInstance().getTitleFont());
		int y = fm.getHeight();
		g.setFont(EncogFonts.getInstance().getTitleFont());
		g.drawString("Encog Workbench v" + EncogWorkBench.VERSION, 0, y);
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString( EncogWorkBench.COPYRIGHT, 0, y);
		y += g.getFontMetrics().getHeight();
		g.drawString( "Released under the LGPL license", 0, y);
		y += g.getFontMetrics().getHeight();
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Java Version:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(System.getProperty("java.version"), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Java 64/32-Bit:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(System.getProperty("sun.arch.data.model"), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Processor Count:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(""+Runtime.getRuntime().availableProcessors(), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Numeric Format:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(""+ByteOrder.nativeOrder().toString(), 150, y);
		y += g.getFontMetrics().getHeight();
		
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("OS Name/Version:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(System.getProperty("os.name") + "("
				+ System.getProperty("os.version") + ")", 150, y);
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Encog Core Version:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(Encog.getInstance().getProperties().get(
				Encog.ENCOG_VERSION), 150, y);
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Encog File Version:", 10, y);
		g.setFont(EncogFonts.getInstance().getBodyFont());
		g.drawString(Encog.getInstance().getProperties().get(
				Encog.ENCOG_FILE_VERSION), 150, y);
		y += g.getFontMetrics().getHeight();

		y += g.getFontMetrics().getHeight();
		g.setFont(EncogFonts.getInstance().getHeadFont());
		g.drawString("Active JAR Files:", 10, y);
		y += g.getFontMetrics().getHeight();

		g.setFont(EncogFonts.getInstance().getBodyFont());
		for (final String file : this.jars) {
			g.drawString(file, 20, y);
			y += g.getFontMetrics().getHeight();
		}

	}

}
