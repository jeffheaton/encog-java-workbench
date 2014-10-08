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
package org.encog.workbench.dialogs;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.FileField;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.frames.document.EncogDocumentFrame;

public class SaveImageDialog extends EncogPropertiesDialog {
	
	private final FileField targetFile;
	private final ComboBoxField fileType;
	private final IntegerField width;
	private final IntegerField height;
	
	public SaveImageDialog(Frame owner) {
		super(owner);
		
		List<String> list = new ArrayList<String>();
		list.add("PNG (*.png)");
		list.add("JPG (*.jpg)");
		list.add("PDF (*.pdf)");
		list.add("SVG (*.svg)");
		
		this.setSize(640, 200);
		this.setTitle("Export Image");
		
		addProperty(this.targetFile = new FileField("target file","Target File",true,true,null));
		addProperty(this.fileType = new ComboBoxField("type type", "Export File Type",true,list));
		addProperty(this.height = new IntegerField("height","Image Height (pixels)",true,32,Integer.MAX_VALUE));
		addProperty(this.width = new IntegerField("width","Image Width (pixels)",true,32,Integer.MAX_VALUE));
		render();
	}

	public ComboBoxField getFileType() {
		return fileType;
	}

	/**
	 * @return the targetFile
	 */
	public FileField getTargetFile() {
		return targetFile;
	}

	/**
	 * @return the width
	 */
	public IntegerField getImageWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public IntegerField getImageHeight() {
		return height;
	}
	
	
	


}
