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
