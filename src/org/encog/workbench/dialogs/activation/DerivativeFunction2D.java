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

package org.encog.workbench.dialogs.activation;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationUtil;
import org.jfree.data.function.Function2D;

public class DerivativeFunction2D implements Function2D {

	private final ActivationFunction activation;
	
	public double getValue(double d) {
		double[] array = ActivationUtil.toArray(d);
		this.activation.derivativeFunction(array);
		return ActivationUtil.fromArray(array);
	}

	public DerivativeFunction2D(ActivationFunction activation)
	{
		this.activation = activation;
	}
	
}
