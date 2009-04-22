package org.encog.workbench.dialogs.common;

import javax.swing.JTextField;

public class DecimalField extends PropertiesField {

	private final double min;
	private final double max;
	private double value;
	
	public DecimalField(String name, String label,
			boolean required, double max, double min) {
		super(name, label, required);
		this.min = min;
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	void collect() throws ValidationException {
		
		double d = 0;
		
		String str = ((JTextField)this.getField()).getText();
		if( str.length()<1 && this.isRequired())
		{
			throw new ValidationException("The field " + this.getName() + " is required.");
		}
		
		try
		{
			d = Double.parseDouble(str);
		}
		catch(NumberFormatException e)
		{
			throw new ValidationException("The field " + this.getName() + " requires a valid number.");
		}
		
		if (d < this.min) {
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
