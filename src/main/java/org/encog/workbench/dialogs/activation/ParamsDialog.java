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
package org.encog.workbench.dialogs.activation;

import java.awt.Frame;

import javax.swing.JDialog;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.workbench.dialogs.common.DecimalField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;

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
