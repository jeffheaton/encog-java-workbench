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
package org.encog.workbench.frames.document.tree;

import java.io.File;

import org.encog.util.file.FileUtil;

public class ProjectFile extends ProjectItem {
	private File file;
	private boolean error;
	
	public ProjectFile(File file)
	{
		this(file,false);
	}
	
	public ProjectFile(File file, boolean error)
	{
		this.file = file;
		this.error = error;
	}
		
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	public String toString()
	{
		if( !error )
			return file.getName();
		else
			return file.getName() + " - Error";
	}

	public String getExtension() {
		return FileUtil.getFileExt(file);
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return this.file.getName();
	}
}
