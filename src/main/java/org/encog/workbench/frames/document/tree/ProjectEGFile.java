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

import org.encog.ml.MLMethod;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.workbench.EncogWorkBench;

public class ProjectEGFile extends ProjectFile {

	private Object obj;
	private String encogType;

	public ProjectEGFile(File file) {
		super(file);

		try {
			this.encogType = EncogWorkBench.getInstance().getProject()
					.getEncogType(file.getName());
		} catch (Throwable t) {
			this.encogType = "Unknown";
		}
	}

	public String toString() {
		return this.getFile().getName() + " (" + encogType + ")";
	}
	
	public void refresh() {
		if( this.obj!=null ) {
			this.obj = EncogWorkBench.getInstance().getProject().loadFromDirectory(getName());
		}
	}

	public Object getObject() {
		if( this.obj == null ) {
			this.obj = EncogWorkBench.getInstance().getProject().loadFromDirectory(getName());
		}
		
		return this.obj;
	}
	
	public void save() {
		if( this.getObject()!=null ) {
			EncogWorkBench.getInstance().getProject().saveToDirectory(this.getName(), this.getObject());
		}
	}
	
	public void revert() {
		if( this.getObject()!=null ) {
			this.obj = EncogWorkBench.getInstance().getProject().loadFromDirectory(this.getName());
		}
	}

	/**
	 * @return the encogType
	 */
	public String getEncogType() {
		return encogType;
	}
	
	public void setObject(Object o) {
		this.obj = o;
	}

	public void save(MLMethod method) {
		this.obj = method;
		this.save();
	}

}
