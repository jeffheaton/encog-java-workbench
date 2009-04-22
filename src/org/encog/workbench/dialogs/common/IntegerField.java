package org.encog.workbench.dialogs.common;

import javax.swing.JTextField;

public class IntegerField extends PropertiesField {

	private final int min;
	private final int max;
	private int value;
	
	public IntegerField(String name, String label, boolean required, int min, int max ) {
		super(name, label, required);
		this.max = max;
		this.min = min;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Override
	void collect() throws ValidationException {
		
		int i = 0;
		
		try
		{
			i = Integer.parseInt(((JTextField)this.getField()).getText());
		}
		catch(NumberFormatException e)
		{
			throw new ValidationException("The field " + this.getName() + " requires a valid number.");
		}
		
		if (i < this.min) {
			throw new ValidationException("Must enter a value above " + this.min
					+ " for: " + this.getName());
		}
		if( (this.max>this.min) && (i > this.max) ) {
			throw new ValidationException("Must enter a value below " + this.max
					+ " for: " + this.getName());
		}
		
		this.value = i;
		
	}
	
	

}
