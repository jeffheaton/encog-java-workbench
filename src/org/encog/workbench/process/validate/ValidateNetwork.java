package org.encog.workbench.process.validate;

import org.encog.neural.networks.BasicNetwork;

public class ValidateNetwork {
	
	
	public static Exception finalizeStructure (BasicNetwork network) {
		try
		{
			network.getStructure().finalizeStructure();
			return null;
		}
		catch(Exception e)
		{
			return e;
		}
		
	}
	
}
