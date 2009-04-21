package org.encog.workbench.dialogs.common;

import javax.swing.JComponent;

public class PropertiesField {
	
	private final PropertyType type;
	private final String label;
	private final double min;
	private final double max;
	private final String name;
	private final boolean required;
	private String value;
	private JComponent field;
	
	public PropertiesField(PropertyType type,String name, String label, double min, double max,boolean required)
	{
		this.type = type;
		this.name = name;
		this.label = label;
		this.min = min;
		this.max = max;
		this.required = required;
	}

	public PropertyType getType() {
		return type;
	}

	public String getLabel() {
		return label;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public JComponent getField() {
		return field;
	}

	public void setField(JComponent field) {
		this.field = field;
	}

	public int getValueInt() {
		int result = 0;
		try
		{
			result = Integer.parseInt(this.value);
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
		return result;
	}

	public void setValueInt(int count) {
		this.value = ""+count;
		
	}
	
	
	
	
	
}
