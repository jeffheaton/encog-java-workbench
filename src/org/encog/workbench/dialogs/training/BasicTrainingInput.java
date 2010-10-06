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
package org.encog.workbench.dialogs.training;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.encog.Encog;
import org.encog.engine.opencl.EncogCLDevice;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.buffer.BufferedNeuralDataSet;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.NetworkAndTrainingDialog;

/**
 * A common training input dialog box used by all of the other training
 * input boxes.
 * @author jheaton
 */
public abstract class BasicTrainingInput extends NetworkAndTrainingDialog {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 6577905476465280583L;
	
	/**
	 * Text field that holds the maximum training error.
	 */
	private final DoubleField maxError;
	private DoubleField clRatio;
	
	private final ComboBoxField buffering;
	
	private ComboBoxField comboDevice;
	private boolean showDevice = false;

	private final List<String> devices = new ArrayList<String>();


	public BasicTrainingInput(final Frame owner) {
		this(owner,false);
	}
	
	public BasicTrainingInput(Frame owner, boolean b) {
		super(owner);
		findData();
		this.showDevice = b;
		List<String> list = new ArrayList<String>();
		
		if( this.showDevice ) {
			addProperty(this.comboDevice = new ComboBoxField("device","Target Device",true,this.devices));
			addProperty(this.clRatio = new DoubleField("OpenCL ratio","OpenCL Ratio",true,0,1));
		}
		
		list.add("Memory (Fastest, if dataset fits)");
		list.add("Disk (Slower, good w/large datasets");
		
		addProperty(this.buffering = new ComboBoxField("buffering", "Buffering", true, list));
		addProperty(this.maxError = new DoubleField("max error","Maximum Error",true,0,1));
		

	}

	private void findData() {
		
		
		this.devices.add("CPU (non-OpenCL)");
		
		if( Encog.getInstance().getCL()!=null )
		{
			for(EncogCLDevice device: Encog.getInstance().getCL().getDevices() ) {
				this.devices.add(device.toString());
			}
		}
	}



	/**
	 * @return the maxError
	 */
	public DoubleField getMaxError() {
		return this.maxError;
	}


	public ComboBoxField getBuffering() {
		return buffering;
	}
	
	/**
	 * @return The training set that the user chose.
	 */
	public NeuralDataSet getTrainingSet() {
		NeuralDataSet result = (NeuralDataSet)super.getTrainingSet();
		
		if( result instanceof BufferedNeuralDataSet && this.buffering.getSelectedIndex()==0 )
		{
			result = ((BufferedNeuralDataSet)result).loadToMemory();
		}

		return result;
	}

	public EncogCLDevice getDevice() {
		if( this.comboDevice==null )
			return null;
		
		int selected = this.comboDevice.getSelectedIndex();
		
		if( selected==0 )
			return null;
		else
			return Encog.getInstance().getCL().getDevices().get(selected-1);
	}

	/**
	 * @return the showDevice
	 */
	public boolean isShowDevice() {
		return showDevice;
	}

	/**
	 * @param showDevice the showDevice to set
	 */
	public void setShowDevice(boolean showDevice) {
		this.showDevice = showDevice;
	}
	
	
	public DoubleField getRatio() {
		return this.clRatio;
	}

	
}
