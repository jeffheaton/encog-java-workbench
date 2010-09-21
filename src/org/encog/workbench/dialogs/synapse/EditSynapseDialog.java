/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2010 Heaton Research, Inc.
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
package org.encog.workbench.dialogs.synapse;

import java.awt.Frame;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.synapse.Synapse;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.TableField;

public class EditSynapseDialog extends EncogPropertiesDialog {

	private final Synapse synapse;
	private final MatrixTableField matrixTable;
	
	public EditSynapseDialog(Frame owner, BasicNetwork network, Synapse synapse) {
		super(owner);

		setTitle("Edit Synapse");
		setSize(600,500);
		setLocation(20,20);
		this.synapse = synapse;
		addProperty(matrixTable = new MatrixTableField("weight matrix","Weight Matrix",network,synapse));
		render();
	}

	public Synapse getSynapse() {
		return synapse;
	}

	public MatrixTableField getMatrixTable() {
		return matrixTable;
	}
	
	
	
}
