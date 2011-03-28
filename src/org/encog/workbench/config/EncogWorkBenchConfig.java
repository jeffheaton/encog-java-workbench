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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.encog.engine.util.ErrorCalculationMode;
import org.encog.persist.EncogFileSection;
import org.encog.persist.EncogReadHelper;
import org.encog.persist.EncogWriteHelper;
import org.encog.persist.PersistConst;
import org.encog.util.csv.CSVFormat;
import org.encog.util.csv.NumberList;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.util.SimpleEncrypt;

public class EncogWorkBenchConfig {

	public static final String PROPERTY_DEFAULT_ERROR = "defaultError";
	public static final String PROPERTY_THREAD_COUNT = "threadCount";
	public static final String PROPERTY_USE_GPU = "useGPU";
	public static final String PROPERTY_ERROR_CALC = "errorCalculation";
	
	private double defaultError = 1;
	private int threadCount = 0;
	private boolean useOpenCL;
	private int errorCalculation;

	public double getDefaultError() {
		return defaultError;
	}

	public void setDefaultError(double defaultError) {
		this.defaultError = defaultError;
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
	
	public void saveConfig() {
		File file = null;
		try {
			String home = System.getProperty("user.home");
			file = new File(home, EncogWorkBench.CONFIG_FILENAME);

			OutputStream os = new FileOutputStream(file);
			EncogWriteHelper out = new EncogWriteHelper(os);
			out.addSection("ENCOG");
			out.addSubSection("TRAINING");

			out.writeProperty(EncogWorkBenchConfig.PROPERTY_DEFAULT_ERROR, this.defaultError);
			out.writeProperty(EncogWorkBenchConfig.PROPERTY_THREAD_COUNT, this.threadCount);
			out.writeProperty(EncogWorkBenchConfig.PROPERTY_USE_GPU, this.useOpenCL);
			out.writeProperty(EncogWorkBenchConfig.PROPERTY_ERROR_CALC, this.errorCalculation);
						
			out.flush();
			os.close();
		} catch (IOException ex) {
			if (file != null)
				EncogWorkBench.displayError("Can't write config file",
						file.toString());
		}

	}

	public void loadConfig() {
		try {
			String home = System.getProperty("user.home");
			File file = new File(home, EncogWorkBench.CONFIG_FILENAME);
			InputStream is = new FileInputStream(file);
			EncogReadHelper in = new EncogReadHelper(is);
			
			// read the config file
			EncogFileSection section;
			
			while( (section = in.readNextSection()) != null ) {
				if( section.getSectionName().equals("ENCOG") && section.getSubSectionName().equals("TRAINING") ) {
					Map<String,String> params = section.parseParams();
					this.defaultError = EncogFileSection.parseDouble(params, EncogWorkBenchConfig.PROPERTY_DEFAULT_ERROR);
					this.threadCount = EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_THREAD_COUNT);
					this.useOpenCL = EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_THREAD_COUNT)>0;
					this.errorCalculation = EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_ERROR_CALC);
				}
			}
						
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
