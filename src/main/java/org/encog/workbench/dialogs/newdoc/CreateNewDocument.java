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
package org.encog.workbench.dialogs.newdoc;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.FolderField;
import org.encog.workbench.dialogs.common.InformationField;
import org.encog.workbench.dialogs.common.TextField;

public class CreateNewDocument extends EncogPropertiesDialog {

	private final FolderField parentDirectory;
	private final TextField projectFilename;

	public CreateNewDocument(Frame owner) {
		super(owner);

		List<String> list = new ArrayList<String>();
		list.add("CSV");

		this.setSize(640, 200);
		this.setTitle("Create New Encog Project");

		addProperty(this.parentDirectory = new FolderField("folder",
				"Parent Folder", true));
		addProperty(this.projectFilename = new TextField("name",
				"Project Name", true));
		addProperty(new InformationField(
				4,
				"Encog projects are typically placed in a project folder.  Choose the parent folder, and a name for your project.  A subfolder with the same name as your project will be created.  Your Encog project file will be placed in this folder, along with any training sets you create."));
		render();
	}

	public FolderField getParentDirectory() {
		return parentDirectory;
	}

	public TextField getProjectFilename() {
		return projectFilename;
	}

	public boolean passesValidation() {

		File parent = new File(this.parentDirectory.getValue());
		File project = new File(parent, this.projectFilename.getValue());

		if (project.exists()) {
			if (!EncogWorkBench
					.askQuestion(
							"Exists",
							"A directory with that project name already exists. \nDo you wish to overwrite it?"))
				return false;
		}

		return true;
	}

}
