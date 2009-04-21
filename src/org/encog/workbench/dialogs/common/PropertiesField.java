package org.encog.workbench.dialogs.common;

public class PropertiesField {
	
	private final PropertyType type;
	private final String label;
	private final double min;
	private final double max;
	private final String name;
	
	public PropertiesField(PropertyType type,String name, String label, double min, double max)
	{
		this.type = type;
		this.name = name;
		this.label = label;
		this.min = min;
		this.max = max;
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
	
	
	
}
