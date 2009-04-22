package org.encog.workbench.dialogs.common;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class PropertiesField {

	private final String label;
	private final String name;
	private final boolean required;
	private JComponent field;
	private JLabel labelControl;
	
	public PropertiesField(String name, String label,boolean required)
	{
		this.name = name;
		this.label = label;
		this.required = required;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}

	public JComponent getField() {
		return field;
	}

	public void setField(JComponent field) {
		this.field = field;
	}	
	
	public abstract void collect() throws ValidationException;
	
	public JLabel createLabel() 
	{
		if(this.labelControl!=null)
		{
			return this.labelControl;
		}
		
		this.labelControl = new JLabel(this.label);
		this.labelControl.setSize(this.labelControl.getPreferredSize());
		return this.labelControl;
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		this.field = new JTextField();
		this.field.setLocation(x, y);
		this.field.setSize(this.field.getPreferredSize());
		this.field.setSize(width,this.field.getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(this.field);
		
		return y+this.field.getHeight();
	}
}
