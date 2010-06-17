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

package org.encog.workbench.config;

import org.encog.EncogError;
import org.encog.mathutil.error.ErrorCalculationMode;
import org.encog.persist.EncogPersistedObject;
import org.encog.persist.Persistor;
import org.encog.workbench.util.SimpleEncrypt;

public class EncogWorkBenchConfig implements EncogPersistedObject {

	private String name = "workbenchConfig";
	private String description = "workbenchDescription";

	private String encogCloudNetwork = "http://cloud.encog.com";
	private String encogCloudPassword;
	private String encogCloudUserID;
	private boolean autoConnect = false;
	private double defaultError = 0.01;
	private int threadCount = 0;
	private boolean useOpenCL;
	private int errorCalculation;

	public Persistor createPersistor() {
		return null;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setDescription(String theDescription) {
		this.description = theDescription;

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncogCloudNetwork() {
		return encogCloudNetwork;
	}

	public void setEncogCloudNetwork(String encogCloudNetwork) {
		this.encogCloudNetwork = encogCloudNetwork;
	}

	public String getEncogCloudPassword() {
		return SimpleEncrypt.decode(encogCloudPassword);
	}

	public void setEncogCloudPassword(String encogCloudPassword) {
		this.encogCloudPassword = SimpleEncrypt.encode(encogCloudPassword);
	}

	public String getEncogCloudUserID() {
		return encogCloudUserID;
	}

	public void setEncogCloudUserID(String encogCloudUserID) {
		this.encogCloudUserID = encogCloudUserID;
	}

	public double getDefaultError() {
		return defaultError;
	}

	public void setDefaultError(double defaultError) {
		this.defaultError = defaultError;
	}

	public boolean isAutoConnect() {
		return autoConnect;
	}

	public void setAutoConnect(boolean autoConnect) {
		this.autoConnect = autoConnect;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public boolean isUseOpenCL() {
		return useOpenCL;
	}

	public void setUseOpenCL(boolean useOpenCL) {
		this.useOpenCL = useOpenCL;
	}

	public ErrorCalculationMode getErrorCalculation() {
		switch (this.errorCalculation) {
		case 0:
			return ErrorCalculationMode.RMS;
		case 1:
			return ErrorCalculationMode.MSE;
		case 2:
			return ErrorCalculationMode.ARCTAN;
		default:
			return ErrorCalculationMode.RMS;
		}
	}

	public void setErrorCalculation(ErrorCalculationMode errorCalculation) {
		switch(errorCalculation)
		{
			case RMS:
				this.errorCalculation = 0;
				break;
			case MSE:
				this.errorCalculation = 1;
				break;
			case ARCTAN:
				this.errorCalculation = 2;
				break;
		}
	}

}
