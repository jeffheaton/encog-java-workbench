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
package org.encog.workbench.dialogs.binary;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.FileField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.frames.document.EncogDocumentFrame;

public class DialogBinary2External extends EncogPropertiesDialog {
	
	private final FileField binaryFile;
	private final FileField externalFile;
	private final ComboBoxField fileType;
	
	public DialogBinary2External(Frame owner) {
		super(owner);
		
		List<String> list = new ArrayList<String>();
		list.add("CSV");
		list.add("Excel (*.xlsx)");
		
		this.setSize(640, 200);
		this.setTitle("Convert Encog Binary Training to Other Format File");
		
		addProperty(this.binaryFile = new FileField("source file","Source Encog Binary File(*.egb)",true,false,EncogDocumentFrame.ENCOG_BINARY));
		addProperty(this.externalFile = new FileField("target file","Target File",true,true,null));
		addProperty(this.fileType = new ComboBoxField("type type", "Export File Type",true,list));
		render();
	}

	public FileField getBinaryFile() {
		return binaryFile;
	}

	public FileField getExternalFile() {
		return externalFile;
	}

	public ComboBoxField getFileType() {
		return fileType;
	}
	
	


}
