/*
 * Encog(tm) Workbench v2.4
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
