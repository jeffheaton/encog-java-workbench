/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.layers;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;

public class AddEditTagDialog extends EncogPropertiesDialog  {
	
	private final ComboBoxField tag;
	
	public AddEditTagDialog(JDialog owner) {
		
		super(owner);
		
		List<String> list = new ArrayList<String>();
		list.add("INPUT");
		list.add("OUTPUT");
		list.add("F1");
		list.add("F2");
		list.add("INSTAR");
		list.add("OUTSTAR");
		
		setTitle("Tag");
		setSize(600, 100);
		setLocation(200, 200);
		addProperty(this.tag = new ComboBoxField("tag name",
				"Tag Name", true, list));
		
		render();
	}

	public ComboBoxField getTag() {
		return tag;
	}
	
	
}
