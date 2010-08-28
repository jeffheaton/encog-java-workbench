package org.encog.workbench.dialogs.activation;

import java.awt.Frame;

import javax.swing.JDialog;

import org.encog.neural.activation.ActivationFunction;
import org.encog.workbench.dialogs.common.DecimalField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;

import sun.rmi.server.Activation;

public class ParamsDialog extends EncogPropertiesDialog {

	DecimalField[] fields;
	
	public ParamsDialog(Frame owner, ActivationFunction activation) {
		super(owner);
		init(activation);
	}
	
	public ParamsDialog(JDialog owner, ActivationFunction activation) {
		super(owner);
		init(activation);
	}
	
	private void init(ActivationFunction activation)
	{
		setTitle( activation.getClass().getSimpleName() +  " Parameters");
		setSize(400,400);
		setLocation(200,200);
		
		int len = activation.getParamNames().length;
		this.fields = new DecimalField[len];
		
		for(int i=0; i<len;i++ )
		{
			String name = activation.getParamNames()[i];
			this.fields[i] = new DecimalField(name.toLowerCase(),name,true,-1,1);
			addProperty(this.fields[i]);
		}

		render();
	}
	
	public void load(ActivationFunction activation)
	{
		for(int i=0; i<activation.getParamNames().length;i++ )
		{
			fields[i].setValue(activation.getParams()[i]);
		}		
	}

	public void save(ActivationFunction activation) {
		for(int i=0; i<activation.getParamNames().length;i++ )
		{
			activation.getParams()[i] = fields[i].getValue();
		}				
	}


}
