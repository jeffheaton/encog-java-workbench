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

package org.encog.workbench.dialogs.binary;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

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

	
	public DialogExternal2Binary(Frame owner) {
		super(owner);
		
		List<String> list = new ArrayList<String>();
		list.add("CSV");
		
		this.setSize(640, 200);
		this.setTitle("Convert Other Format File to Encog Binary Training");

		addProperty(this.externalFile = new FileField("source file","Source File(*.csv)",true,false,null));
		addProperty(this.binaryFile = new FileField("target file","Target Encog Binary File(*.egb)",true,true,EncogDocumentFrame.ENCOG_BINARY));
		addProperty(this.fileType = new ComboBoxField("type type", "Export File Type",true,list));
		addProperty(this.inputCount = new IntegerField("input count","Input Count",true,1,10000));
		addProperty(this.idealCount = new IntegerField("ideal count","Ideal Count",true,0,10000));

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

	
	

}
