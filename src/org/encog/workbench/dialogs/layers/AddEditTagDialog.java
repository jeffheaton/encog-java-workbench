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
