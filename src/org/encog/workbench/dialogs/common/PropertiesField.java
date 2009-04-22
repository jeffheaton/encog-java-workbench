package org.encog.workbench.dialogs.common;

import javax.swing.JComponent;

public abstract class PropertiesField {

	private final String label;
	private final String name;
	private final boolean required;
	private JComponent field;
	
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
	
	abstract void collect() throws ValidationException;
}
