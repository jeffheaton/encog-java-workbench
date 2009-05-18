package org.encog.workbench.dialogs.common;

import javax.swing.JTextField;

public class DoubleField extends PropertiesField {

	private final int min;
	private final int max;
	private double value;
	
	public DoubleField(String name, String label, boolean required, int min, int max ) {
		super(name, label, required);
		this.max = max;
		this.min = min;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
		((JTextField)this.getField()).setText(""+value);
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Override
	public void collect() throws ValidationException {
		
		double d = 0;
		
		try
		{
			d = Double.parseDouble(((JTextField)this.getField()).getText());
		}
		catch(NumberFormatException e)
		{
			throw new ValidationException("The field " + this.getName() + " requires a valid number.");
		}
		
		if ((this.max>this.min) && d < (this.min)) {
			throw new ValidationException("Must enter a value above " + this.min
					+ " for: " + this.getName());
		}
		if( (this.max>this.min) && (d > this.max) ) {
			throw new ValidationException("Must enter a value below " + this.max
					+ " for: " + this.getName());
		}
		
		this.value = d;
		
	}
	
	

}
