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
			switch(field.getType())
			{
			case string:
				String strVal = this.validateFieldString(field.getName(), (JTextField)field.getField(), field.isRequired());
				field.setValue(strVal);
				break;
			case integer:
				int intValue = (int)this.validateFieldNumeric(field.getName(), (JTextField)field.getField(), field.getMin(), field.getMax());
				field.setValue(""+intValue);
				break;
			case decimal:
				int decValue = (int)this.validateFieldNumeric(field.getName(), (JTextField)field.getField(), field.getMin(), field.getMax());
				field.setValue(""+decValue);
				break;
			}
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
