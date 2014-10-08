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

import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.FileField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.frames.document.EncogDocumentFrame;

public class DialogExternal2Binary extends EncogPropertiesDialog {

	private final IntegerField inputCount;
	private final IntegerField idealCount;
	private final FileField binaryFile;
	private final FileField externalFile;
	private final ComboBoxField fileType;
	private final CheckField containsSignificance;

	
	public DialogExternal2Binary(Frame owner) {
		super(owner);
		
		List<String> list = new ArrayList<String>();
		list.add("CSV");
		list.add("Excel (*.xlsx)");
		
		this.setSize(640, 200);
		this.setTitle("Convert Other Format File to Encog Binary Training");

		addProperty(this.externalFile = new FileField("source file","Source File",true,false,EncogDocumentFrame.CSV_FILTER));
		addProperty(this.binaryFile = new FileField("target file","Target Encog Binary File(*.egb)",true,true,EncogDocumentFrame.ENCOG_BINARY));
		addProperty(this.fileType = new ComboBoxField("type type", "Export File Type",true,list));
		addProperty(this.inputCount = new IntegerField("input count","Input Count",true,1,10000));
		addProperty(this.idealCount = new IntegerField("ideal count","Ideal Count",true,0,10000));
		addProperty(this.containsSignificance = new CheckField("contains significance column","Significance Column Present"));

		render();
	}


	public IntegerField getInputCount() {
		return inputCount;
	}


	public IntegerField getIdealCount() {
		return idealCount;
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


	/**
	 * @return the containsSignificance
	 */
	public final CheckField getContainsSignificance() {
		return containsSignificance;
	}

	
	

}
