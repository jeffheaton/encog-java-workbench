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
		
		int i = 0;
		
		try
		{
			i = Integer.parseInt(((JTextField)this.getField()).getText());
		}
		catch(NumberFormatException e)
		{
			throw new ValidationException("The field " + this.getName() + " requires a valid number.");
		}
		
		if ((this.max>this.min) && i < (this.min)) {
			throw new ValidationException("Must enter a value at or above " + this.min
					+ " for: " + this.getName());
		}
		if( (this.max>this.min) && (i > this.max) ) {
			throw new ValidationException("Must enter a value below " + this.max
					+ " for: " + this.getName());
		}
		
		this.value = i;
		
	}
	
	

}
