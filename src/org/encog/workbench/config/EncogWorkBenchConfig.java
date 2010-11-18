/*
 * Encog(tm) Workbench v2.6 
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
package org.encog.workbench.config;

import org.encog.engine.util.ErrorCalculationMode;
import org.encog.persist.EncogCollection;
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

	public EncogCollection getCollection() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCollection(EncogCollection collection) {
		// TODO Auto-generated method stub
		
	}

}
