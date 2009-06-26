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

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * A filter for the Java standard file open and save dialogs that allows files
 * to be filtered by extension.
 * @author jheaton
 *
 */
public class ExtensionFilter extends FileFilter {
	public static String getExtension(final File file) {
		final String filename = file.getName();
		final String ext = filename.lastIndexOf(".") == -1 ? null : filename
				.substring(filename.lastIndexOf(".") + 1, filename.length());
		return ext;
	}

	/**
	 * Extensions supported.
	 */
	private final String extensions[];

	/**
	 * The description of these extensions.
	 */
	private final String description;

	public ExtensionFilter(final String description, final String extension) {
		this(description, new String[] { extension });
	}

	public ExtensionFilter(final String description, final String extensions[]) {
		this.description = description;
		this.extensions = extensions.clone();
	}

	public boolean accept(final File file) {
		if (file.isDirectory()) {
			return true;
		}
		final int count = this.extensions.length;
		final String path = file.getAbsolutePath();
		for (int i = 0; i < count; i++) {
			final String ext = this.extensions[i];
			if (path.endsWith(ext)
					&& path.charAt(path.length() - ext.length()) == '.') {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the description.
	 */
	public String getDescription() {
		return this.description == null ? this.extensions[0] : this.description;
	}
}
