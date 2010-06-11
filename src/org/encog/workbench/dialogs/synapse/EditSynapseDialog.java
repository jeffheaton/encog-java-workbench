/*
 * Encog(tm) Workbench v2.5
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
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
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
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
