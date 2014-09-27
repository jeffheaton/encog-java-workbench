/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
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
		((JTextField)this.getField()).setText(""+value);
		this.value = value;
	}

	@Override
	public void collect() throws ValidationException {
		
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
		
		if ((this.max>this.min) && (d < this.min)) {
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
