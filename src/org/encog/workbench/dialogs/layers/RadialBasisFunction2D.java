package org.encog.workbench.dialogs.layers;

import org.encog.util.math.rbf.RadialBasisFunction;
import org.jfree.data.function.Function2D;

public class RadialBasisFunction2D implements Function2D {

	private RadialBasisFunction radial;
	
	public RadialBasisFunction2D(RadialBasisFunction radial)
	{
		this.radial = radial;
	}
	
	public double getValue(double x) {
		return radial.calculate(x);
	}

}
