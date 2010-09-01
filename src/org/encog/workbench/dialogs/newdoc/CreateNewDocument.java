/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
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

		if (project.toString().indexOf('.') != -1) {
			EncogWorkBench.displayError("Error",
					"A project name must not have an extension.");
			return false;
		}

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
