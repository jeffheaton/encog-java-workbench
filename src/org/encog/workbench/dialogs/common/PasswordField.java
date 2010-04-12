package org.encog.workbench.dialogs.common;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PasswordField extends PropertiesField {
	private String value;
	
	public PasswordField(String name, String label, boolean required) {
		super(name, label, required);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		((JTextField)this.getField()).setText(value);
	}

	@Override
	public void collect() throws ValidationException {
		this.value = ((JTextField)this.getField()).getText();
		if( this.value.length()<1 && this.isRequired())
		{
			throw new ValidationException("The field " + this.getName() + " is required.");
		}
	}
	
	public int createField(JPanel panel, int x, int y,int width)
	{
		JPasswordField field = new JPasswordField();
		
		this.setField(field);
		field.setLocation(x, y);
		field.setSize(field.getPreferredSize());
		field.setSize(width,field.getHeight());
		JLabel label = createLabel();
		label.setLocation(label.getX(), y);
		panel.add(label);
		panel.add(field);
		
		return y+field.getHeight();
	}
	

}
