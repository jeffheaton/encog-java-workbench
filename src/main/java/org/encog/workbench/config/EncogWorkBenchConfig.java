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
package org.encog.workbench.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.encog.mathutil.error.ErrorCalculationMode;
import org.encog.persist.EncogFileSection;
import org.encog.persist.EncogReadHelper;
import org.encog.persist.EncogWriteHelper;
import org.encog.persist.PersistError;
import org.encog.workbench.EncogWorkBench;

public class EncogWorkBenchConfig {

	public static final String PROPERTY_DEFAULT_ERROR = "defaultError";
	public static final String PROPERTY_THREAD_COUNT = "threadCount";
	public static final String PROPERTY_USE_GPU = "useGPU";
	public static final String PROPERTY_ERROR_CALC = "errorCalculation";
	private static final String PROPERTY_STEP_COUNT = "stepCount";
	private static final String PROPERTY_TRAINING_HISTORY = "trainingHistory";
	private static final String PROPERTY_TRAINING_IMPROVEMENT = "trainingImprovement";
	private static final String PROPERTY_CLOUD_SERVER_PORT = "serverPort";
	private static final String PROPERTY_CLOUD_ALLOW_CONNECTIONS = "allowConnections";
	private static final String PROPERTY_PROJECT_ROOT = "allowConnections";
	private static final String PROPERTY_LOG_LEVEL = "logLevel";
	
	private double defaultError;
	private int threadCount;
	private boolean useOpenCL;
	private int errorCalculation;
	private int iterationStepCount;
	private int trainingHistory;
	private boolean showTrainingImprovement;
	private String projectRoot;
	
	public EncogWorkBenchConfig() {
		resetDefaults();
	}

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
		case 1:
			return ErrorCalculationMode.RMS;
		case 0:
			return ErrorCalculationMode.MSE;
		default:
			return ErrorCalculationMode.MSE;
		}
	}

	public void setErrorCalculation(ErrorCalculationMode errorCalculation) {
		switch(errorCalculation)
		{
			case RMS:
				this.errorCalculation = 1;
				break;
			case MSE:
				this.errorCalculation = 0;
				break;
		}
	}
	
	
	
	public int getIterationStepCount() {
		return iterationStepCount;
	}

	public void setIterationStepCount(int iterationStepCount) {
		this.iterationStepCount = iterationStepCount;
	}

	public int getTrainingHistory() {
		return trainingHistory;
	}

	public void setTrainingHistory(int trainingHistory) {
		this.trainingHistory = trainingHistory;
	}

	public boolean isShowTrainingImprovement() {
		return showTrainingImprovement;
	}

	public void setShowTrainingImprovement(boolean showTrainingImprovement) {
		this.showTrainingImprovement = showTrainingImprovement;
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
			out.writeProperty(EncogWorkBenchConfig.PROPERTY_STEP_COUNT, this.iterationStepCount);
			out.writeProperty(EncogWorkBenchConfig.PROPERTY_TRAINING_HISTORY, this.trainingHistory);
			out.writeProperty(EncogWorkBenchConfig.PROPERTY_TRAINING_IMPROVEMENT,this.showTrainingImprovement);
			out.writeProperty(EncogWorkBenchConfig.PROPERTY_LOG_LEVEL, getLogLevel());

			out.addSubSection("PATHS");
			out.writeProperty(EncogWorkBenchConfig.PROPERTY_PROJECT_ROOT, this.projectRoot);
			
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
					Map<String, String> params = section.parseParams();
					try {
					this.defaultError = EncogFileSection.parseDouble(params, EncogWorkBenchConfig.PROPERTY_DEFAULT_ERROR);
					this.threadCount = EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_THREAD_COUNT);
					this.useOpenCL = EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_THREAD_COUNT)>0;
					this.errorCalculation = EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_ERROR_CALC);
					this.iterationStepCount = EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_STEP_COUNT);
					this.trainingHistory = EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_TRAINING_HISTORY);
					setLogLevel(EncogFileSection.parseInt(params, EncogWorkBenchConfig.PROPERTY_LOG_LEVEL));
					this.showTrainingImprovement = EncogFileSection.parseBoolean(params, EncogWorkBenchConfig.PROPERTY_TRAINING_IMPROVEMENT);
					} catch(PersistError e) {
						resetDefaults();
					}
				}
				else if( section.getSectionName().equals("ENCOG") && section.getSubSectionName().equals("PATHS") ) {
					Map<String, String> params = section.parseParams();
					if( params.containsKey(EncogWorkBenchConfig.PROPERTY_PROJECT_ROOT) ) {
						this.projectRoot  = params.get(EncogWorkBenchConfig.PROPERTY_CLOUD_ALLOW_CONNECTIONS);
					} 
					
					if( this.projectRoot==null || this.projectRoot.trim().length()==0 ) {
						this.projectRoot = EncogWorkBenchConfig.getDefaultProjectRoot().toString();
					}
					
					
				}
			}
						
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void resetDefaults() {
		this.defaultError = 1;
		this.threadCount = 0;
		this.useOpenCL = false;
		this.errorCalculation = 0;
		this.iterationStepCount = 1;
		this.trainingHistory = 100;
		this.showTrainingImprovement = true;
		this.projectRoot = EncogWorkBenchConfig.getDefaultProjectRoot().toString();
		
	}

	public String getProjectRoot() {
		return projectRoot;
	}

	public void setProjectRoot(String projectRoot) {
		this.projectRoot = projectRoot;
	}

	public static File getDefaultProjectRoot() {
		String home = System.getProperty("user.home");
		File encogFolders = new File(home, "EncogProjects");
		encogFolders.mkdir();
		return encogFolders;
	}

	public int getLogLevel() {
		int index = EncogWorkBench.getInstance().getMainWindow().getOutputPane().getComboLogLevel().getSelectedIndex();
		return index;
	}

	public void setLogLevel(int logLevel) {
		EncogWorkBench.getInstance().getMainWindow().getOutputPane().getComboLogLevel().setSelectedIndex(logLevel);
	}
	
	

}
