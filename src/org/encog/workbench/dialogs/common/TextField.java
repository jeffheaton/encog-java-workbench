package org.encog.workbench.dialogs.common;

import javax.swing.JTextField;

public class TextField extends PropertiesField {

	private String value;
	
	public TextField(String name, String label, boolean required) {
		super(name, label, required);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void collect() throws ValidationException {
		this.value = ((JTextField)this.getField()).getText();
		if( this.value.length()<1 && this.isRequired())
		{
			throw new ValidationException("The field " + this.getName() + " is required.");
		}
	}
	
	

}
