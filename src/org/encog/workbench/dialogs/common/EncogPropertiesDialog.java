package org.encog.workbench.dialogs.common;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EncogPropertiesDialog extends EncogCommonDialog {

	List<PropertiesField> properties = new ArrayList<PropertiesField>();
	
	public EncogPropertiesDialog(Frame owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}
	
	public void render()
	{
		int y = 0;
		int maxLabelWidth = 0;
		int dialogWidth = getWidth();
		int labelHeight=0;
		
		JPanel contents = this.getBodyPanel();
		contents.setLayout(null);
		
		// create the labels
		
		for(PropertiesField field: this.properties)
		{
			JLabel label = new JLabel(field.getLabel());
			label.setSize(label.getPreferredSize());
			contents.add(label);
			label.setLocation(10, y);
			labelHeight = label.getHeight();
			y+=labelHeight;
			maxLabelWidth = Math.max(maxLabelWidth, label.getWidth());
		}
		
		y=0;
		// create the text fields
		for(PropertiesField field: this.properties)
		{
			JTextField tx = new JTextField();
			field.setField(tx);
			tx.setSize(tx.getPreferredSize());
			contents.add(tx);
			tx.setLocation(maxLabelWidth+30, y);
			tx.setSize(dialogWidth-maxLabelWidth-50, tx.getHeight());
			y+=labelHeight;
		}
	}

	@Override
	public void collectFields() throws ValidationException {
		for(PropertiesField field: this.properties)
		{
			field.collect();
		}
	}

	@Override
	public void setFields() {
		// nothing to do here
		
	}
	
	public void addProperty(PropertiesField field) {
		this.properties.add(field);
		
	}

}
