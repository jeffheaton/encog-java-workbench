/*
 * Encog Workbench v2.x
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

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
