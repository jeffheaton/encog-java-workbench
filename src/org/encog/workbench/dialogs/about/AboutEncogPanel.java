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

package org.encog.workbench.dialogs.about;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.EncogFonts;
import org.encog.Encog;

/**
 * The information panel for the about box.
 * @author jheaton
 *
 */
public class AboutEncogPanel extends JPanel {
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * JAR files currently in use.
	 */
	private final List<String> jars = new ArrayList<String>();

	/**
	 * Construct the panel.
	 */
	public AboutEncogPanel() {
		
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
		g.drawString( "Copyright 2009 by Heaton Research, Inc.", 0, y);
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
