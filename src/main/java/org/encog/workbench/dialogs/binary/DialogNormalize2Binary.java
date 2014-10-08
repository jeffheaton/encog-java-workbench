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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.script.AnalystScript;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.FileField;
import org.encog.workbench.frames.document.EncogDocumentFrame;
import org.encog.workbench.frames.document.tree.ProjectFile;
import org.encog.workbench.frames.document.tree.ProjectTraining;

public class DialogNormalize2Binary extends EncogPropertiesDialog {

	private final FileField binaryFile;
	private final FileField externalFile;
	private final ComboBoxField analystFile;
	private final CheckField containsSignificance;

	private List<ProjectFile> analystList;
	
	public DialogNormalize2Binary() {
		super(EncogWorkBench
				.getInstance().getMainWindow());
		
		List<String> list = new ArrayList<String>();
		list.add("CSV");
		list.add("Excel (*.xlsx)");
		
		this.analystList = EncogWorkBench.getInstance().getAnalystFiles();
		
		this.setSize(640, 200);
		this.setTitle("Normalize to Encog Binary Training");

		addProperty(this.externalFile = new FileField("source file","Source File",true,false,EncogDocumentFrame.CSV_FILTER));
		addProperty(this.binaryFile = new FileField("target file","Target Encog Binary File(*.egb)",true,true,EncogDocumentFrame.ENCOG_BINARY));
		addProperty(this.analystFile = new ComboBoxField("analyst file", "Analyst File",true,analystList));
		addProperty(this.containsSignificance = new CheckField("contains significance column","Significance Column Present"));

		render();
	}


	public FileField getBinaryFile() {
		return binaryFile;
	}


	public FileField getExternalFile() {
		return externalFile;
	}

	/**
	 * @return the containsSignificance
	 */
	public final CheckField getContainsSignificance() {
		return containsSignificance;
	}

	public EncogAnalyst getAnalyst() {
		if( this.analystFile.getSelectedValue()==null )			
			return null;
		File file = ((ProjectFile)this.analystFile.getSelectedValue()).getFile();
		
		EncogAnalyst result = new EncogAnalyst();
		result.load(file);
		return result;
	}

}
