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
